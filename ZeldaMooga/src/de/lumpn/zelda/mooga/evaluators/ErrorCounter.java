package de.lumpn.zelda.mooga.evaluators;

import de.lumpn.zelda.puzzle.State;
import de.lumpn.zelda.puzzle.ZeldaPuzzle;

public final class ErrorCounter {

	public static int countErrors(ZeldaPuzzle puzzle, State initialState) {
		int errors = 0;
		errors += countDeadEnds(puzzle, initialState);
		return errors;
	}

	private static int countDeadEnds(ZeldaPuzzle puzzle, State initialState) {
		int deadEnds = 0;

		// TODO find path to exit for every reached location & state
		int pathLength = PathFinder.shortestPathLength(puzzle, initialState,
				ZeldaPuzzle.entranceId, ZeldaPuzzle.exitId);
		if (pathLength < 0) deadEnds++;

		return deadEnds;
	}
}
