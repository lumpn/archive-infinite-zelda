package de.lumpn.zelda.puzzle.test;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;
import de.lumpn.report.ConsoleProgressBar;
import de.lumpn.report.ProgressConsumer;
import de.lumpn.zelda.puzzle.State;
import de.lumpn.zelda.puzzle.Step;
import de.lumpn.zelda.puzzle.VariableIdentifier;
import de.lumpn.zelda.puzzle.VariableLookup;
import de.lumpn.zelda.puzzle.ZeldaPuzzle;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;
import de.lumpn.zelda.puzzle.script.IdentityScript;
import de.lumpn.zelda.puzzle.script.ZeldaScripts;

public class ZeldaPuzzleTest {

	@Test
	public void testCrawlLinear() {

		// 0 -- 2 -- 3 -- 1
		ZeldaPuzzleBuilder builder = new ZeldaPuzzleBuilder();
		builder.addUndirectedTransition(0, 2, IdentityScript.INSTANCE);
		builder.addUndirectedTransition(2, 3, IdentityScript.INSTANCE);
		builder.addUndirectedTransition(3, 1, IdentityScript.INSTANCE);
		ZeldaPuzzle puzzle = builder.puzzle();

		State initialState = new State(Collections.<VariableIdentifier, Integer> emptyMap());
		puzzle.crawl(Arrays.asList(initialState), maxSteps, progress);

		Assert.assertNotNull(puzzle.getStep(0, initialState));
		Assert.assertNotNull(puzzle.getStep(1, initialState));
		Assert.assertNotNull(puzzle.getStep(2, initialState));
		Assert.assertNotNull(puzzle.getStep(3, initialState));
	}

	@Test
	public void testCrawlCyclic() {

		// 0 -- 2 -- 1
		// .\ /
		// . 3
		ZeldaPuzzleBuilder builder = new ZeldaPuzzleBuilder();
		builder.addUndirectedTransition(0, 2, IdentityScript.INSTANCE);
		builder.addUndirectedTransition(2, 3, IdentityScript.INSTANCE);
		builder.addUndirectedTransition(3, 0, IdentityScript.INSTANCE);
		builder.addUndirectedTransition(2, 1, IdentityScript.INSTANCE);
		ZeldaPuzzle puzzle = builder.puzzle();

		State initialState = new State(Collections.<VariableIdentifier, Integer> emptyMap());
		puzzle.crawl(Arrays.asList(initialState), maxSteps, progress);

		Assert.assertNotNull(puzzle.getStep(0, initialState));
		Assert.assertNotNull(puzzle.getStep(1, initialState));
		Assert.assertNotNull(puzzle.getStep(2, initialState));
		Assert.assertNotNull(puzzle.getStep(3, initialState));
	}

	@Test
	public void testCrawlUnreachable() {
		// 0 -- 2 -- 1
		//
		// 3
		ZeldaPuzzleBuilder builder = new ZeldaPuzzleBuilder();
		builder.addUndirectedTransition(0, 2, IdentityScript.INSTANCE);
		builder.addUndirectedTransition(2, 1, IdentityScript.INSTANCE);
		builder.addScript(3, IdentityScript.INSTANCE);
		ZeldaPuzzle puzzle = builder.puzzle();

		State initialState = new State(Collections.<VariableIdentifier, Integer> emptyMap());
		puzzle.crawl(Arrays.asList(initialState), maxSteps, progress);

		Assert.assertNotNull(puzzle.getStep(0, initialState));
		Assert.assertNotNull(puzzle.getStep(1, initialState));
		Assert.assertNotNull(puzzle.getStep(2, initialState));
		Assert.assertNull(puzzle.getStep(3, initialState));
	}

	@Test
	public void testCrawlDeadEnd() {

		// 0 -> 3
		// .\
		// . 2 -- 1
		ZeldaPuzzleBuilder builder = new ZeldaPuzzleBuilder();
		builder.addUndirectedTransition(0, 2, IdentityScript.INSTANCE);
		builder.addUndirectedTransition(2, 1, IdentityScript.INSTANCE);
		builder.addDirectedTransition(0, 3, IdentityScript.INSTANCE);
		ZeldaPuzzle puzzle = builder.puzzle();

		State initialState = new State(Collections.<VariableIdentifier, Integer> emptyMap());
		puzzle.crawl(Arrays.asList(initialState), maxSteps, progress);

		Assert.assertNotNull(puzzle.getStep(0, initialState));
		Assert.assertNotNull(puzzle.getStep(1, initialState));
		Assert.assertNotNull(puzzle.getStep(2, initialState));
		Assert.assertNotNull(puzzle.getStep(3, initialState));
		Assert.assertTrue(isEmpty(puzzle.getStep(3, initialState).successors()));
	}

	@Test
	public void testCrawlDeadCycle() {

		// 0 -> 2 -- 3
		// | . . \ /
		// 1 . .. 4
		ZeldaPuzzleBuilder builder = new ZeldaPuzzleBuilder();
		builder.addUndirectedTransition(0, 1, IdentityScript.INSTANCE);
		builder.addUndirectedTransition(2, 3, IdentityScript.INSTANCE);
		builder.addUndirectedTransition(3, 4, IdentityScript.INSTANCE);
		builder.addUndirectedTransition(4, 2, IdentityScript.INSTANCE);
		builder.addDirectedTransition(0, 2, IdentityScript.INSTANCE);
		ZeldaPuzzle puzzle = builder.puzzle();

		State initialState = new State(Collections.<VariableIdentifier, Integer> emptyMap());
		puzzle.crawl(Arrays.asList(initialState), maxSteps, progress);

		Assert.assertNotNull(puzzle.getStep(0, initialState));
		Assert.assertNotNull(puzzle.getStep(1, initialState));
		Assert.assertNotNull(puzzle.getStep(2, initialState));
		Assert.assertNotNull(puzzle.getStep(3, initialState));
		Assert.assertNotNull(puzzle.getStep(4, initialState));
		Assert.assertNotEquals(Step.UNREACHABLE, puzzle.getStep(0, initialState).distanceFromExit());
		Assert.assertEquals(Step.UNREACHABLE, puzzle.getStep(2, initialState).distanceFromExit());
		Assert.assertEquals(Step.UNREACHABLE, puzzle.getStep(3, initialState).distanceFromExit());
		Assert.assertEquals(Step.UNREACHABLE, puzzle.getStep(4, initialState).distanceFromExit());
	}

	@Test
	public void testCrawlKeyLockLinear() {

		// 0 -d- 2 -d- 3 -d- 1
		// k . . k . . k
		ZeldaPuzzleBuilder builder = new ZeldaPuzzleBuilder();
		VariableLookup lookup = builder.lookup();

		builder.addUndirectedTransition(0, 2, ZeldaScripts.createLock(lookup));
		builder.addUndirectedTransition(2, 3, ZeldaScripts.createLock(lookup));
		builder.addUndirectedTransition(3, 1, ZeldaScripts.createLock(lookup));
		builder.addScript(0, ZeldaScripts.createKey(lookup));
		builder.addScript(2, ZeldaScripts.createKey(lookup));
		builder.addScript(3, ZeldaScripts.createKey(lookup));
		ZeldaPuzzle puzzle = builder.puzzle();

		State initialState = new State(Collections.<VariableIdentifier, Integer> emptyMap());
		puzzle.crawl(Arrays.asList(initialState), maxSteps, progress);

		// test for exit reached
		Assert.assertNotEquals(Step.UNREACHABLE, puzzle.getStep(0, initialState).distanceFromExit());
	}

	@Test
	public void testCrawlKeyLockBatch() {

		// .0 -d- 2 -d- 3 -d- 1
		// kkk
		ZeldaPuzzleBuilder builder = new ZeldaPuzzleBuilder();
		VariableLookup lookup = builder.lookup();

		builder.addUndirectedTransition(0, 2, ZeldaScripts.createLock(lookup));
		builder.addUndirectedTransition(2, 3, ZeldaScripts.createLock(lookup));
		builder.addUndirectedTransition(3, 1, ZeldaScripts.createLock(lookup));
		builder.addScript(0, ZeldaScripts.createKey(lookup));
		builder.addScript(0, ZeldaScripts.createKey(lookup));
		builder.addScript(0, ZeldaScripts.createKey(lookup));
		ZeldaPuzzle puzzle = builder.puzzle();

		State initialState = new State(Collections.<VariableIdentifier, Integer> emptyMap());
		puzzle.crawl(Arrays.asList(initialState), maxSteps, progress);

		// test for exit reached
		Assert.assertNotEquals(Step.UNREACHABLE, puzzle.getStep(0, initialState).distanceFromExit());
	}

	private static <T> boolean isEmpty(Iterable<T> iterable) {
		return !(iterable.iterator().hasNext());
	}

	private static final int maxSteps = 1000;
	private static final ProgressConsumer progress = new ConsoleProgressBar();
}
