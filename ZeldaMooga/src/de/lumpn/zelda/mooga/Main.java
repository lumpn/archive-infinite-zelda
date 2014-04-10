package de.lumpn.zelda.mooga;

import java.util.Collections;
import java.util.Random;
import de.lumpn.zelda.puzzle.State;
import de.lumpn.zelda.puzzle.VariableIdentifier;

public class Main {

	public static void main(String[] args) {

		Random random = new Random();

		ZeldaConfiguration configuration = new ZeldaConfiguration();

		ZeldaGenomeFactory factory = new ZeldaGenomeFactory(configuration, random);

		State initialState = new State(Collections.<VariableIdentifier, Integer> emptyMap());
		ZeldaEnvironment environment = new ZeldaEnvironment(initialState);

		ZeldaGenome best = factory.createGenome();
		ZeldaIndividual individual = environment.evaluate(best);
		System.out.println("target: " + individual);

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
