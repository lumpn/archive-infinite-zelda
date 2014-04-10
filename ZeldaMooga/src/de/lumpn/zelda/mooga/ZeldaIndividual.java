package de.lumpn.zelda.mooga;

import de.lumpn.mooga.Genome;
import de.lumpn.mooga.Individual;
import de.lumpn.zelda.mooga.evaluators.ErrorCounter;
import de.lumpn.zelda.mooga.evaluators.PathFinder;
import de.lumpn.zelda.puzzle.State;
import de.lumpn.zelda.puzzle.ZeldaPuzzle;

public final class ZeldaIndividual implements Individual {

	private static int minimize(int value) {
		return -value;
	}

	private static int maximize(int value) {
		return value;
	}

	public ZeldaIndividual(ZeldaGenome genome, ZeldaPuzzle puzzle, State initialState) {
		this.genome = genome;
		this.puzzle = puzzle;
		numErrors = ErrorCounter.countErrors(puzzle, initialState);
		shortestPathLength = PathFinder.shortestPathLength(puzzle, initialState,
				ZeldaPuzzle.entranceId, ZeldaPuzzle.exitId);
	}

	@Override
	public Genome getGenome() {
		return genome;
	}

	@Override
	public int numAttributes() {
		return 2;
	}

	@Override
	public double getScore(int attribute) {
		switch (attribute) {
			case 0:
				return minimize(numErrors);
			case 1:
				return maximize(shortestPathLength);
			default:
				assert false;
		}
		return 0;
	}

	@Override
	public String toString() {
		return String.format("%s", puzzle);
	}

	private final ZeldaGenome genome;

	private final ZeldaPuzzle puzzle;

	// statistics
	private final int numErrors;
	private final int shortestPathLength;
}
