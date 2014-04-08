package de.lumpn.zelda.mooga;

import java.util.Comparator;
import de.lumpn.mooga.CharacteristicEvaluator;
import de.lumpn.mooga.Individual;

public final class DummyIndividualComparator implements Comparator<Individual>, CharacteristicEvaluator {

	public DummyIndividualComparator(String target) {
		this.target = target;
	}

	@Override
	public int compare(Individual a, Individual b) {

		// evaluate against target
		int d1 = getDistance(a.toString());
		int d2 = getDistance(b.toString());

		// shorter distance dominates
		return d2 - d1;
	}

	public int getDistance(String current) {

		// find differences
		int distance = 0;
		int length = Math.min(current.length(), target.length());
		for (int i = 0; i < length; i++) {
			if (current.charAt(i) != target.charAt(i)) {
				distance++;
			}
		}

		// add size mismatch
		distance += Math.abs(current.length() - target.length());
		return distance;
	}

	@Override
	public double getValue(Individual individual) {
		return getDistance(individual.toString());
	}

	private final String target;

}
