package de.lumpn.zelda.mooga;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import de.lumpn.mooga.ElitistEvolution;
import de.lumpn.mooga.Genome;
import de.lumpn.zelda.puzzle.DotBuilder;
import de.lumpn.zelda.puzzle.State;
import de.lumpn.zelda.puzzle.VariableIdentifier;
import de.lumpn.zelda.puzzle.ZeldaPuzzle;

public class Main {

	public static void main(String[] args) {

		Random random = new Random();

		ZeldaConfiguration configuration = new ZeldaConfiguration();

		ZeldaGenomeFactory factory = new ZeldaGenomeFactory(configuration, random);

		State initialState = new State(Collections.<VariableIdentifier, Integer> emptyMap());
		ZeldaEnvironment environment = new ZeldaEnvironment(initialState, 10000);

		ZeldaGenome example = factory.createGenome();
		ZeldaIndividual individual = environment.evaluate(example);
		System.out.println("test: " + individual);

		ElitistEvolution evolution = new ElitistEvolution(100, 1000, factory, environment);

		List<Genome> genomes = evolution.initialize();
		for (int i = 0; i < 1000; i++) {
			System.out.println("gen " + i);
			genomes = evolution.evolve(genomes, random);
		}

		ZeldaIndividual best = (ZeldaIndividual) evolution.getBest();
		System.out.println(best.toString());

		ZeldaPuzzle puzzle = best.puzzle();
		DotBuilder builder = new DotBuilder();
		puzzle.express(builder);
		
		// TODO: output genome to puzzle unit test (puzzle building statements)
	}
}
