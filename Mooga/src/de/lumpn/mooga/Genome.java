package de.lumpn.mooga;

import java.util.Random;
import de.lumpn.util.Pair;

public interface Genome {

	Pair<Genome> crossover(Genome other, Random random);

	Genome mutate(Random random);

	Individual spawn();
}
