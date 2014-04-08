package de.lumpn.zelda.mooga;

import java.util.Random;
import de.lumpn.mooga.GenomeFactory;

public final class ZeldaGenomeFactory implements GenomeFactory {

	public ZeldaGenomeFactory(ZeldaConfiguration configuration, Random random) {
		this.configuration = configuration;
		this.random = random;
	}

	@Override
	public ZeldaGenome createChromosome() {
		return new ZeldaGenome(configuration, random);
	}

	private final ZeldaConfiguration configuration;

	private final Random random;
}
