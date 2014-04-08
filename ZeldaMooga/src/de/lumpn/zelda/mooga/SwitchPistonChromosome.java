package de.lumpn.zelda.mooga;

import java.util.List;
import java.util.Random;
import de.lumpn.util.CollectionUtils;
import de.lumpn.util.Pair;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;

public final class SwitchPistonChromosome extends ZeldaGene {

	public SwitchPistonChromosome(ZeldaConfiguration configuration, Random random) {
		super(configuration);

		int numSwitches = configuration.calcNumInitialSwitches(random);
		int numPistons = configuration.calcNumInitialPistons(random);
		this.switches = CollectionUtils.immutable(GeneUtils.generate(numSwitches, switchFactory, configuration, random));
		this.pistons = CollectionUtils.immutable(GeneUtils.generate(numPistons, pistonFactory, configuration, random));
	}

	public SwitchPistonChromosome(ZeldaConfiguration configuration, List<SwitchGene> switches, List<PistonGene> pistons) {
		super(configuration);
		this.switches = CollectionUtils.immutable(switches);
		this.pistons = CollectionUtils.immutable(pistons);
	}

	@Override
	public SwitchPistonChromosome mutate(Random random) {
		List<SwitchGene> newSwitches = GeneUtils.mutate(switches, switchFactory, getConfiguration(), random);
		List<PistonGene> newPistons = GeneUtils.mutate(pistons, pistonFactory, getConfiguration(), random);
		return new SwitchPistonChromosome(getConfiguration(), newSwitches, newPistons);
	}

	public Pair<SwitchPistonChromosome> crossover(SwitchPistonChromosome other, Random random) {

		// randomly distribute genes
		Pair<List<SwitchGene>> switchDistribution = CollectionUtils.distribute(switches, other.switches, random);
		Pair<List<PistonGene>> pistonDistribution = CollectionUtils.distribute(pistons, other.pistons, random);

		// create offsprings
		SwitchPistonChromosome x = new SwitchPistonChromosome(getConfiguration(), switchDistribution.first(),
				pistonDistribution.first());
		SwitchPistonChromosome y = new SwitchPistonChromosome(getConfiguration(), switchDistribution.second(),
				pistonDistribution.second());
		return new Pair<SwitchPistonChromosome>(x, y);
	}

	@Override
	public void express(ZeldaPuzzleBuilder builder) {
		for (SwitchGene gene : switches) {
			gene.express(builder);
		}
		for (PistonGene gene : pistons) {
			gene.express(builder);
		}
	}

	@Override
	public String toString() {
		return String.format("%s, %s", switches, pistons);
	}

	private final List<SwitchGene> switches;
	private final List<PistonGene> pistons;

	private static final SwitchGeneFactory switchFactory = new SwitchGeneFactory();
	private static final PistonGeneFactory pistonFactory = new PistonGeneFactory();
}
