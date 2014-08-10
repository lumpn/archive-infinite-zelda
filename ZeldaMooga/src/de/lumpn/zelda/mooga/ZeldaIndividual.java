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
		assert genome != null;
		assert puzzle != null;
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
		return 4;
	}

	@Override
	public double getScore(int attribute) {
		switch (attribute) {
			case 0:
				return minimize(Math.max(genome.size() - 10, 0)); // TODO: minimize unused transitions
			case 1:
				return prefer(shortestPathLength != Step.UNREACHABLE);
			case 2:
				return minimize(numErrors);
			case 3:
				return maximize(shortestPathLength);
			default:
				assert false;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		return genome.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof ZeldaIndividual)) return false;
		ZeldaIndividual other = (ZeldaIndividual) obj;
		if (!genome.equals(other.genome)) return false; // TODO: compare genomes in canonical form
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < numAttributes(); i++) {
			builder.append(getScore(i));
			builder.append(" ");
		}
		return String.format("%s: %s", builder.toString(), genome);
	}

	private final ZeldaGenome genome;

	private final ZeldaPuzzle puzzle;

	// statistics
	private final int numErrors;
	private final int shortestPathLength;
}
