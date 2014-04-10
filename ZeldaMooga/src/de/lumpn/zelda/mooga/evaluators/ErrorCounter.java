package de.lumpn.zelda.mooga.evaluators;

import de.lumpn.zelda.puzzle.Location;
import de.lumpn.zelda.puzzle.State;
import de.lumpn.zelda.puzzle.ZeldaPuzzle;

public final class ErrorCounter {

	public static int countErrors(ZeldaPuzzle puzzle) {
		int errors = 0;
		errors += countDeadEnds(puzzle);
		return errors;
	}

	private static int countDeadEnds(ZeldaPuzzle puzzle) {
		int deadEnds = 0;

		// find path to exit for every reached location & state
		for (Location location : puzzle.locations()) {
			int source = location.id();
			for (State state : location.states()) {
				int pathLength = PathFinder.shortestPathLength(puzzle, state, source,
						ZeldaPuzzle.exitLocation);
				if (pathLength < 0) deadEnds++;
			}
		}
		return deadEnds;
	}
}
