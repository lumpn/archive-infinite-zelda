package de.lumpn.mooga;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import de.lumpn.mooga.ranking.CrowdingDistanceRanking;
import de.lumpn.mooga.ranking.Ranking;
import de.lumpn.mooga.selection.BinaryTournamentSelection;

public class ElitistEvolution {

	public ElitistEvolution(int populationSize, int archiveSize, GenomeFactory factory, Environment environment) {
		this.archiveSize = archiveSize;
		this.evolution = new Evolution(populationSize, 0.5, 0.4, factory, new BinaryTournamentSelection(new Random()));
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
			// TODO: only evaluate genomes not previously seen
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
		print(rankedPopulation.subList(0, Math.min(10, rankedPopulation.size())));
		printStats(rankedPopulation);
		System.out.println(rankedPopulation.size() + " distinct individuals");

		// evolve population
		return evolution.evolve(rankedPopulation, random);
	}

	public Individual getBest() {
		for (Individual individual : archive) {
			if (individual.isElite()) {
				return individual;
			}
		}
		return null;
	}

	private static void print(Iterable<Individual> individuals) {
		System.out.println("----------------------------------------------------");
		for (Individual individual : individuals) {
			System.out.println(individual);
		}
		System.out.println("----------------------------------------------------");
	}

	private static void printStats(Iterable<Individual> individuals) {
		List<Double> mins = new ArrayList<Double>();
		List<Double> maxs = new ArrayList<Double>();
		List<Double> avgs = new ArrayList<Double>();

		int count = 0;
		int attributes = 0;
		for (Individual individual : individuals) {

			// initialize lists
			if (mins.isEmpty()) {
				attributes = individual.numAttributes();
				mins.addAll(repeat(Double.MAX_VALUE, attributes));
				maxs.addAll(repeat(Double.MIN_VALUE, attributes));
				avgs.addAll(repeat(0, attributes));
			}

			// record stats
			for (int i = 0; i < attributes; i++) {
				double score = individual.getScore(i);
				mins.set(i, Math.min(mins.get(i), score));
				maxs.set(i, Math.max(maxs.get(i), score));
				avgs.set(i, avgs.get(i) + score);
				count++;
			}
		}

		// print stats
		for (int i = 0; i < attributes; i++) {
			System.out.format("%d: min %f, max %f, mid %f, avg %f\n", i, mins.get(i), maxs.get(i), (mins.get(i) + maxs.get(i)) / 2, avgs.get(i) / count);
		}
	}

	private static List<Double> repeat(double value, int times) {
		List<Double> result = new ArrayList<Double>(times);
		for (int i = 0; i < times; i++)
			result.add(value);
		return result;
	}

	private final int archiveSize;

	private final Evolution evolution;
	private final Environment environment;

	private final Ranking ranking;

	private List<Individual> archive = new ArrayList<Individual>();
}
