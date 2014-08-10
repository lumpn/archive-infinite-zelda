package de.lumpn.zelda.mooga;

import java.util.Arrays;
import de.lumpn.mooga.Environment;
import de.lumpn.mooga.Genome;
import de.lumpn.report.MockProgressBar;
import de.lumpn.report.ProgressConsumer;
import de.lumpn.zelda.mooga.evaluators.ErrorCounter;
import de.lumpn.zelda.mooga.evaluators.PathFinder;
import de.lumpn.zelda.puzzle.State;
import de.lumpn.zelda.puzzle.ZeldaPuzzle;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;

public final class ZeldaEnvironment implements Environment {

	public ZeldaEnvironment(State initialState, int maxSteps) {
		this.initialState = initialState;
		this.maxSteps = maxSteps;
	}

	@Override
	public ZeldaIndividual evaluate(Genome g) {
		ZeldaGenome genome = (ZeldaGenome) g;

		// build puzzle
		ZeldaPuzzleBuilder builder = new ZeldaPuzzleBuilder();
		genome.express(builder);
		ZeldaPuzzle puzzle = builder.puzzle();

		// crawl puzzle
		puzzle.crawl(Arrays.asList(initialState), maxSteps, progress);

		// evaluate puzzle
		int numErrors = ErrorCounter.countErrors(puzzle);
		int shortestPathLength = PathFinder.shortestPathLength(puzzle, initialState);

		// create individual
		return new ZeldaIndividual(genome, puzzle, numErrors, shortestPathLength);
	}

	private final State initialState;
	private final int maxSteps;
	private static final ProgressConsumer progress = new MockProgressBar();
}
