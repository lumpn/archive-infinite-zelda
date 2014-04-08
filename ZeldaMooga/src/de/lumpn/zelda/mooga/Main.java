package de.lumpn.zelda.mooga;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import de.lumpn.mooga.CharacteristicEvaluator;
import de.lumpn.mooga.Genome;
import de.lumpn.mooga.ElitistEvolution;

public class Main {

	public static void main(String[] args) {

		Random random = new Random();

		ZeldaConfiguration configuration = new ZeldaConfiguration();

		ZeldaGenomeFactory factory = new ZeldaGenomeFactory(configuration, random);

		ZeldaGenome best = factory.createGenome();
		ZeldaIndividual individual = best.spawn();
		/*
		System.out.println("target: " + individual);
		DummyIndividualComparator comparator = new DummyIndividualComparator(individual.toString());

		DummyIndividual last = individual;
		for (int i = 0; i < 10; i++) {
			ZeldaGenome candidate = factory.createChromosome();
			DummyIndividual other = candidate.spawn();
			int distance = comparator.getDistance(other.toString());
			int comparison = comparator.compare(other, last);
			System.out.format("%d\t%d\t%s\n", comparison, distance, other);
			last = other;
		}

		ElitistEvolution evolution = new ElitistEvolution(factory, comparator,
				Arrays.<CharacteristicEvaluator> asList(comparator));

		List<Genome> genomes = evolution.initialize();
		for (int i = 0; i < 100; i++) {
			genomes = evolution.evolve(genomes, random);
			System.out.println(individual);
		}
*/
	}
}
