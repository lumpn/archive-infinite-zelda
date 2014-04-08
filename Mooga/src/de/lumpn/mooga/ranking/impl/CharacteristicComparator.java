package de.lumpn.mooga.ranking.impl;

import java.util.Comparator;
import de.lumpn.mooga.CharacteristicEvaluator;

public class CharacteristicComparator implements Comparator<ScoredIndividual> {

	public CharacteristicComparator(CharacteristicEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	@Override
	public int compare(ScoredIndividual a, ScoredIndividual b) {
		double valueA = evaluator.getValue(a.getIndividual());
		double valueB = evaluator.getValue(b.getIndividual());
		if (valueA > valueB) {
			return 1;
		}
		if (valueA < valueB) {
			return -1;
		}
		return 0;
	}

	private final CharacteristicEvaluator evaluator;
}
