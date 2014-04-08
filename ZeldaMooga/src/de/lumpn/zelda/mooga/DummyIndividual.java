package de.lumpn.zelda.mooga;

import de.lumpn.mooga.Genome;
import de.lumpn.mooga.Individual;

public class DummyIndividual implements Individual {

	public DummyIndividual(ZeldaGenome chromosome) {
		this.chromosome = chromosome;
	}

	@Override
	public Genome getGenome() {
		return chromosome;
	}

	@Override
	public String toString() {
		return chromosome.toString();
	}

	private final ZeldaGenome chromosome;
}
