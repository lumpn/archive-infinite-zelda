package de.lumpn.zelda.mooga;

import de.lumpn.mooga.Genome;
import de.lumpn.mooga.Individual;
import de.lumpn.zelda.puzzle.Step;
import de.lumpn.zelda.puzzle.ZeldaPuzzle;

public final class ZeldaIndividual implements Individual {

	private static int minimize(int value) {
		return -value;
	}

	private static int maximize(int value) {
		return value;
	}

	private static int prefer(boolean value) {
		return value ? 1 : 0;
	}

	public ZeldaIndividual(ZeldaGenome genome, ZeldaPuzzle puzzle, int numErrors, int shortestPathLength) {
		this.genome = genome;
		this.puzzle = puzzle;
		this.numErrors = numErrors;
		this.shortestPathLength = shortestPathLength;
	}

	@Override
	public Genome getGenome() {
		return genome;
	}

	@Override
	public int numAttributes() {
		return 3;
	}

	@Override
	public double getScore(int attribute) {
		switch (attribute) {
			case 0:
				return prefer(shortestPathLength != Step.UNREACHABLE);
			case 1:
				return minimize(numErrors);
			case 2:
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
