package de.lumpn.mooga.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import de.lumpn.mooga.Individual;

public final class BinaryTournamentSelection implements Selection {

	public BinaryTournamentSelection(Random random) {
		this.random = random;
	}

	@Override
	public Individual select(List<Individual> individuals) {
		int size = individuals.size();
		int pos1 = random.nextInt(size);
		int pos2 = random.nextInt(size);
		return individuals.get(Math.min(pos1, pos2));
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

	private final Random random;
}
