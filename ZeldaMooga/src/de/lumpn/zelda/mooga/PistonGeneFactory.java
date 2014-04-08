package de.lumpn.zelda.mooga;

import java.util.Random;

public class PistonGeneFactory implements GeneFactory<PistonGene> {

	@Override
	public PistonGene createGene(ZeldaConfiguration configuration, Random random) {
		return new PistonGene(configuration, random);
	}
}
