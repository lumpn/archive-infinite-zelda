package de.lumpn.zelda.mooga;

import java.util.Random;

public final class ZeldaGeneFactory implements GeneFactory<ZeldaGene> {

	@Override
	public ZeldaGene createGene(ZeldaConfiguration configuration, Random random) {
		// TODO: support weighted probability
		switch (random.nextInt(5)) {
			case 0:
				return new TwoWayGene(configuration, random);
			case 1:
				return new OneWayGene(configuration, random);
			case 2:
				return new KeyDoorGene(configuration, random);
			case 3:
				return new SwitchGene(configuration, random);
			case 4:
				return new PistonGene(configuration, random);
			default:
				return new TwoWayGene(configuration, random);
		}
	}
}
