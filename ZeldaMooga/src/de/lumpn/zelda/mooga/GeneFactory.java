package de.lumpn.zelda.mooga;

import java.util.Random;

public interface GeneFactory<T extends Gene> {

	T createGene(ZeldaConfiguration configuration, Random random);
}
