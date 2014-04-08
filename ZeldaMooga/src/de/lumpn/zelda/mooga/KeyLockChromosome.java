package de.lumpn.zelda.mooga;

import java.util.List;
import java.util.Random;
import de.lumpn.util.CollectionUtils;
import de.lumpn.util.Pair;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;

public final class KeyLockChromosome extends ZeldaGene {

	public KeyLockChromosome(ZeldaConfiguration configuration, Random random) {
		super(configuration);

		// generate a bunch of key/locks
		int count = configuration.calcNumInitialKeyLocks(random);
		this.genes = CollectionUtils.immutable(GeneUtils.generate(count, factory, configuration, random));
	}

	public KeyLockChromosome(ZeldaConfiguration configuration, List<KeyLockGene> genes) {
		super(configuration);
		this.genes = CollectionUtils.immutable(genes);
	}

	public Pair<KeyLockChromosome> crossover(KeyLockChromosome other, Random random) {

		// randomly distribute genes
		Pair<List<KeyLockGene>> distribution = CollectionUtils.distribute(genes, other.genes, random);

		// create offsprings
		KeyLockChromosome x = new KeyLockChromosome(getConfiguration(), distribution.first());
		KeyLockChromosome y = new KeyLockChromosome(getConfiguration(), distribution.second());
		return new Pair<KeyLockChromosome>(x, y);
	}

	@Override
	public KeyLockChromosome mutate(Random random) {
		List<KeyLockGene> newGenes = GeneUtils.mutate(genes, factory, getConfiguration(), random);
		return new KeyLockChromosome(getConfiguration(), newGenes);
	}

	@Override
	public void express(ZeldaPuzzleBuilder builder) {
		for (KeyLockGene gene : genes) {
			gene.express(builder);
		}
	}

	@Override
	public String toString() {
		return genes.toString();
	}

	private final List<KeyLockGene> genes;

	private static final KeyLockGeneFactory factory = new KeyLockGeneFactory();
}
