package de.lumpn.zelda.mooga;

import java.util.Arrays;
import de.lumpn.mooga.Environment;
import de.lumpn.mooga.Genome;
import de.lumpn.report.ConsoleProgressBar;
import de.lumpn.zelda.mooga.evaluators.ErrorCounter;
import de.lumpn.zelda.mooga.evaluators.PathFinder;
import de.lumpn.zelda.puzzle.State;
import de.lumpn.zelda.puzzle.ZeldaPuzzle;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;

public final class ZeldaEnvironment implements Environment {

	public ZeldaEnvironment(State initialState) {
		this.initialState = initialState;
	}

	@Override
	public ZeldaIndividual evaluate(Genome g) {
		ZeldaGenome genome = (ZeldaGenome) g;

		// build puzzle
		System.out.println("building puzzle " + genome);
		ZeldaPuzzleBuilder builder = new ZeldaPuzzleBuilder();
		genome.express(builder);
		ZeldaPuzzle puzzle = builder.puzzle();

		// crawl puzzle
		System.out.println("crawling puzzle " + genome);
		puzzle.crawl(Arrays.asList(initialState), new ConsoleProgressBar());

		// evaluate puzzle
		System.out.println("evaluating puzzle " + genome);
		int numErrors = ErrorCounter.countErrors(puzzle, initialState);
		int shortestPathLength = PathFinder.shortestPathLength(puzzle, initialState, ZeldaPuzzle.entranceId, ZeldaPuzzle.exitId);

		// create individual
		System.out.println("creating individual " + genome);
		return new ZeldaIndividual(genome, puzzle, numErrors, shortestPathLength);
	}

	private final State initialState;
}
