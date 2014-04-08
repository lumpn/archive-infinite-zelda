package de.lumpn.mooga.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import de.lumpn.mooga.Individual;

public class BinaryTournamentSelection implements Selection {

	@Override
	public Individual select(List<Individual> individuals) {
		int size = individuals.size();
		int a = random.nextInt(size);
		int b = random.nextInt(size);
		if (a < b) {
			return individuals.get(a);
		}
		return individuals.get(b);
	}

	@Override
	public List<Individual> select(List<Individual> individuals, int count) {
		List<Individual> result = new ArrayList<Individual>();
		while (result.size() < count) {
			Individual individual = select(individuals);
			if (!result.contains(individual)) {
				result.add(individual);
			}
		}
		return result;
	}

	private final Random random = new Random();
}
