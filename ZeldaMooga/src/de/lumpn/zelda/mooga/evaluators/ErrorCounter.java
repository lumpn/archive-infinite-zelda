package de.lumpn.zelda.mooga.evaluators;

import de.lumpn.zelda.puzzle.Step;
import de.lumpn.zelda.puzzle.ZeldaPuzzle;

public final class ErrorCounter {

	public static int countErrors(ZeldaPuzzle puzzle) {
		int errors = 0;
		errors += countDeadEnds(puzzle);
		return errors;
	}

	private static int countDeadEnds(ZeldaPuzzle puzzle) {
		int deadEnds = 0;
		for (Step step : puzzle.getSteps()) {
			if (step.distanceFromExit() == Step.UNREACHABLE) deadEnds++;
		}

		return deadEnds;
	}
}
