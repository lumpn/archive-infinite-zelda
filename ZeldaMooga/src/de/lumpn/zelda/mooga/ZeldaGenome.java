package de.lumpn.zelda.mooga;

import java.util.List;
import java.util.Random;
import de.lumpn.mooga.Genome;
import de.lumpn.util.CollectionUtils;
import de.lumpn.util.Pair;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;

/**
 * Immutable Zelda genome
 */
public final class ZeldaGenome implements Genome {

	public ZeldaGenome(ZeldaConfiguration configuration, Random random) {
		this.configuration = configuration;

		// add some more genes
		int count = configuration.calcNumInitialGenes(random);
		this.genes = CollectionUtils.immutable(GeneUtils.generate(count, factory, configuration, random));
	}

	private ZeldaGenome(ZeldaConfiguration configuration, List<ZeldaGene> genes) {
		this.configuration = configuration;
		this.genes = CollectionUtils.immutable(genes);
	}

	@Override
	public Pair<Genome> crossover(Genome o, Random random) {
		ZeldaGenome other = (ZeldaGenome) o;

		// randomly distribute genes
		Pair<List<ZeldaGene>> distributedGenes = CollectionUtils.distribute(genes, other.genes, random);

		// assemble offsprings
		ZeldaGenome x = new ZeldaGenome(configuration, distributedGenes.first());
		ZeldaGenome y = new ZeldaGenome(configuration, distributedGenes.second());
		return new Pair<Genome>(x, y);
	}

	@Override
	public Genome mutate(Random random) {

		// mutate genes
		List<ZeldaGene> newGenes = GeneUtils.mutate(genes, factory, configuration, random);

		// assemble offspring
		return new ZeldaGenome(configuration, newGenes);
	}

	public int size() {
		return genes.size();
	}

	public void express(ZeldaPuzzleBuilder builder) {
		for (ZeldaGene gene : genes) {
			gene.express(builder);
		}
	}

	@Override
	public int hashCode() {
		return genes.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof ZeldaGenome)) return false;
		ZeldaGenome other = (ZeldaGenome) obj;
		if (!genes.equals(other.genes)) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s", genes);
	}

	private final ZeldaConfiguration configuration;

	private final List<ZeldaGene> genes;

	private static final ZeldaGeneFactory factory = new ZeldaGeneFactory();
}
