package de.lumpn.mooga;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import de.lumpn.mooga.ranking.CrowdingDistanceRanking;
import de.lumpn.mooga.ranking.Ranking;
import de.lumpn.mooga.selection.BinaryTournamentSelection;

public class ElitistEvolution {

	public ElitistEvolution(int populationSize, int archiveSize, GenomeFactory factory,
			Environment environment) {
		this.archiveSize = archiveSize;
		this.evolution = new Evolution(populationSize, 0.5, 0.4, factory,
				new BinaryTournamentSelection(new Random()));
		this.environment = environment;
		this.ranking = new CrowdingDistanceRanking();
	}

	public List<Genome> initialize() {
		return evolution.initialize();
	}

	public List<Genome> evolve(List<Genome> genomes, Random random) {

		// spawn individuals
		List<Individual> population = new ArrayList<Individual>();
		for (Genome genome : genomes) {
			Individual individual = environment.evaluate(genome);
			population.add(individual);
		}

		// combine with archive
		population.addAll(archive);

		// distinct
		population = new ArrayList<Individual>(new HashSet<Individual>(population));

		// rank population
		List<Individual> rankedPopulation = ranking.rank(population);

		// update archive
		archive = rankedPopulation.subList(0, Math.min(archiveSize, rankedPopulation.size()));
		// print(rankedPopulation.subList(0, Math.min(10, rankedPopulation.size())));
		System.out.println(rankedPopulation.size() + " distinct individuals");
		Individual best = rankedPopulation.get(0);
		System.out.print("best: ");
		for (int i = 0; i < best.numAttributes(); i++) {
			System.out.print(best.getScore(i) + ", ");
		}
		System.out.println(best.getGenome());

		// evolve population
		return evolution.evolve(rankedPopulation, random);
	}

	private static void print(Iterable<Individual> individuals) {
		System.out.println("----------------------------------------------------");
		for (Individual individual : individuals) {
			System.out.println(individual);
		}
		System.out.println("----------------------------------------------------");
	}

	private final int archiveSize;

	private final Evolution evolution;
	private final Environment environment;

	private final Ranking ranking;

	private List<Individual> archive = new ArrayList<Individual>();
}
