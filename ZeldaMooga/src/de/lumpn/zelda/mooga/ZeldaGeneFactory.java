package de.lumpn.zelda.mooga;

import java.util.Random;

public final class ZeldaGeneFactory implements GeneFactory<ZeldaGene> {

	@Override
	public ZeldaGene createGene(ZeldaConfiguration configuration, Random random) {
		switch (random.nextInt(2)) {
			case 1:
				return new OneWayGene(configuration, random);
			default:
				return new TwoWayGene(configuration, random);
		}
	}
}
