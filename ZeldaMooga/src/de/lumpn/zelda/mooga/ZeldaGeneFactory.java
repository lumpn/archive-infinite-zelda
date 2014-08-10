package de.lumpn.zelda.mooga;

import java.util.Random;

public final class ZeldaGeneFactory implements GeneFactory<ZeldaGene> {

	@Override
	public ZeldaGene createGene(ZeldaConfiguration configuration, Random random) {
		// TODO: support weighted probability
		switch (random.nextInt(7)) {
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
			case 5:
				return new ItemGene(configuration, random);
			case 6:
				return new ObstacleGene(configuration, random);
			default:
				return new TwoWayGene(configuration, random);
		}
	}
}
