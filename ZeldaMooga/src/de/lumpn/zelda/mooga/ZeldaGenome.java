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

		// add a unique key/lock supergene
		this.keyLock = new KeyLockChromosome(configuration, random);

		// add a unique switch/piston supergene
		this.switchPiston = new SwitchPistonChromosome(configuration, random);

		// add some more genes
		int count = configuration.calcNumInitialGenes(random);
		this.genes = CollectionUtils.immutable(GeneUtils.generate(count, factory,
				configuration, random));
	}

	private ZeldaGenome(ZeldaConfiguration configuration, KeyLockChromosome keyLock,
			SwitchPistonChromosome switchPiston, List<ZeldaGene> genes) {
		this.configuration = configuration;
		this.keyLock = keyLock;
		this.switchPiston = switchPiston;
		this.genes = CollectionUtils.immutable(genes);
	}

	@Override
	public Pair<Genome> crossover(Genome o, Random random) {
		ZeldaGenome other = (ZeldaGenome) o;

		// crossover unique genes
		Pair<KeyLockChromosome> keyLocks = keyLock.crossover(other.keyLock, random);
		Pair<SwitchPistonChromosome> switchPistons = switchPiston.crossover(
				other.switchPiston, random);

		// randomly distribute remaining genes
		Pair<List<ZeldaGene>> distributedGenes = CollectionUtils.distribute(genes,
				other.genes, random);

		// assemble offsprings
		ZeldaGenome x = new ZeldaGenome(configuration, keyLocks.first(),
				switchPistons.first(), distributedGenes.first());
		ZeldaGenome y = new ZeldaGenome(configuration, keyLocks.second(),
				switchPistons.second(), distributedGenes.second());
		return new Pair<Genome>(x, y);
	}

	@Override
	public Genome mutate(Random random) {

		// mutate unique genes
		KeyLockChromosome newKeyLock = keyLock.mutate(random);
		SwitchPistonChromosome newSwitchPiston = switchPiston.mutate(random);

		// mutate remaining genes
		List<ZeldaGene> newGenes = GeneUtils.mutate(genes, factory, configuration, random);

		// assemble offspring
		return new ZeldaGenome(configuration, newKeyLock, newSwitchPiston, newGenes);
	}

	public void express(ZeldaPuzzleBuilder builder) {
		keyLock.express(builder);
		switchPiston.express(builder);
		for (ZeldaGene gene : genes) {
			gene.express(builder);
		}
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s", keyLock, switchPiston, genes);
	}

	private final ZeldaConfiguration configuration;

	private final KeyLockChromosome keyLock;
	private final SwitchPistonChromosome switchPiston;
	private final List<ZeldaGene> genes;

	private static final ZeldaGeneFactory factory = new ZeldaGeneFactory();
}
