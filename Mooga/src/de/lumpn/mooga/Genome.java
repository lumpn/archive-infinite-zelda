package de.lumpn.mooga;

import java.util.Random;
import de.lumpn.util.Pair;

public interface Genome {

	/**
	 * Cross two genomes producing two offsprings.
	 */
	Pair<Genome> crossover(Genome other, Random random);

	/**
	 * Mutate genome returning the mutation.
	 */
	Genome mutate(Random random);
}
