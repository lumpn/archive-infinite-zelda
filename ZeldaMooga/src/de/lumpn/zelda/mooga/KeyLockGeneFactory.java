package de.lumpn.zelda.mooga;

import java.util.Random;

public final class KeyLockGeneFactory implements GeneFactory<KeyLockGene> {

	@Override
	public KeyLockGene createGene(ZeldaConfiguration configuration, Random random) {
		return new KeyLockGene(configuration, random);
	}
}
