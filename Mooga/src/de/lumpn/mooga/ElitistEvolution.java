package de.lumpn.mooga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import de.lumpn.mooga.ranking.CrowdingDistanceRanking;
import de.lumpn.mooga.ranking.Ranking;
import de.lumpn.mooga.selection.BinaryTournamentSelection;

public class ElitistEvolution {

	public ElitistEvolution(int populationSize, int archiveSize, GenomeFactory factory) {
		this.archiveSize = archiveSize;
		this.evolution = new Evolution(populationSize, 0.5, 0.4, factory,
				new BinaryTournamentSelection(new Random()));
		this.ranking = new CrowdingDistanceRanking();
	}

	public List<Genome> initialize() {
		return evolution.initialize();
	}

	public List<Genome> evolve(List<Genome> genomes, Random random) {

		// spawn individuals
		List<Individual> population = new ArrayList<Individual>();
		for (Genome genome : genomes) {
			Individual individual = genome.evaluate();
			population.add(individual);
		}

		// combine with archive
		population.addAll(archive);

		// rank population
		List<Individual> rankedPopulation = ranking.rank(population);

		// update archive
		archive = rankedPopulation.subList(0, Math.min(archiveSize, rankedPopulation.size()));
		print(rankedPopulation.subList(0, Math.min(10, rankedPopulation.size())));

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

	private final Ranking ranking;

	private List<Individual> archive = new ArrayList<Individual>();
}
