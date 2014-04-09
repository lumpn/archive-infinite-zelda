package de.lumpn.zelda.mooga;

import de.lumpn.mooga.Genome;
import de.lumpn.mooga.Individual;
import de.lumpn.zelda.puzzle.ZeldaPuzzle;

public final class ZeldaIndividual implements Individual {

	public ZeldaIndividual(ZeldaGenome genome, ZeldaPuzzle puzzle) {
		this.genome = genome;
		this.puzzle = puzzle;
	}

	@Override
	public Genome getGenome() {
		return genome;
	}

	@Override
	public int numAttributes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getScore(int attribute) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return String.format("%s", puzzle);
	}

	private final ZeldaGenome genome;

	private final ZeldaPuzzle puzzle;
}
