package de.lumpn.zelda.mooga;

import de.lumpn.mooga.Environment;
import de.lumpn.mooga.Genome;
import de.lumpn.mooga.Individual;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;

public final class ZeldaEnvironment implements Environment {

	@Override
	public ZeldaIndividual evaluate(Genome g) {
		ZeldaGenome genome = (ZeldaGenome) g;
		ZeldaPuzzleBuilder builder = new ZeldaPuzzleBuilder();
		genome.express(builder);
		return new ZeldaIndividual(genome, builder.puzzle());
	}
}
