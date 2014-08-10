package de.lumpn.zelda.mooga;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import de.lumpn.mooga.ElitistEvolution;
import de.lumpn.mooga.Genome;
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
		System.out.println("test: " + individual);

		ElitistEvolution evolution = new ElitistEvolution(100, 100, factory, environment);

		List<Genome> genomes = evolution.initialize();
		for (int i = 0; i < 10; i++) {
			System.out.print("gen " + i + ": ");
			genomes = evolution.evolve(genomes, random);
		}
	}
}
