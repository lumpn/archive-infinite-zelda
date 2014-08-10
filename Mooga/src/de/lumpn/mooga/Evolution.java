package de.lumpn.mooga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import de.lumpn.mooga.selection.Selection;
import de.lumpn.util.Pair;

public class Evolution {

	public Evolution(int populationSize, double crossoverFraction, double mutationFraction, GenomeFactory factory, Selection selection) {

		this.populationSize = populationSize;
		this.crossoverQuota = (int) (populationSize * crossoverFraction);
		this.mutationQuota = (int) (populationSize * mutationFraction);
		this.factory = factory;
		this.selection = selection;
	}

	public List<Genome> initialize() {
		List<Genome> result = new ArrayList<Genome>();
		for (int i = 0; i < populationSize; i++) {
			Genome genome = factory.createGenome();
			result.add(genome);
		}
		return result;
	}

	public List<Genome> evolve(List<Individual> rankedPopulation, Random random) {
		List<Genome> result = new ArrayList<Genome>();

		// crossover
		for (int i = 0; i < crossoverQuota; i += 2) {
			List<Individual> parents = selection.select(rankedPopulation, 2);
			Genome a = parents.get(0).getGenome();
			Genome b = parents.get(1).getGenome();
			Pair<Genome> children = a.crossover(b, random);
			result.add(children.first());
			result.add(children.second());
		}

		// mutation
		for (int i = 0; i < mutationQuota; i++) {
			Individual parent = selection.select(rankedPopulation);
			Genome child = parent.getGenome().mutate(random);
			result.add(child);
		}

		// fill up
		for (int i = result.size(); i < populationSize; i++) {
			Genome genome = factory.createGenome();
			result.add(genome);
		}

		return result;
	}

	private final int populationSize;
	private final int crossoverQuota;
	private final int mutationQuota;

	private final GenomeFactory factory;

	private final Selection selection;
}
