package de.lumpn.zelda.mooga;

import java.util.Random;

public class SwitchGeneFactory implements GeneFactory<SwitchGene> {

	@Override
	public SwitchGene createGene(ZeldaConfiguration configuration, Random random) {
		return new SwitchGene(configuration, random);
	}
}
