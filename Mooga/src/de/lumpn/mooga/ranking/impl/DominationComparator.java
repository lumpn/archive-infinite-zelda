package de.lumpn.mooga.ranking.impl;

import java.util.Comparator;
import de.lumpn.mooga.Individual;

/**
 * Compares individuals by domination of attributes.
 */
public final class DominationComparator implements Comparator<Individual> {

	@Override
	public int compare(Individual a, Individual b) {
		assert (a.numAttributes() == b.numAttributes());

		boolean aIsPartiallyBetter = false;
		boolean bIsPartiallyBetter = false;
		int aPriority = Integer.MIN_VALUE;
		int bPriority = Integer.MIN_VALUE;

		// compare each score
		int numScores = a.numAttributes();
		for (int i = 0; i < numScores; i++) {
			double aScore = a.getScore(i);
			double bScore = b.getScore(i);
			int priority = a.getPriority(i);
			if (aScore > bScore) {
				aIsPartiallyBetter = true;
				aPriority = Math.max(aPriority, priority);
			} else if (aScore < bScore) {
				bIsPartiallyBetter = true;
				bPriority = Math.max(bPriority, priority);
			}
		}

		// look at priorities
		if (aPriority != bPriority) {
			return Integer.compare(aPriority, bPriority);
		}

		// determine domination
		return Boolean.compare(aIsPartiallyBetter, bIsPartiallyBetter);
	}
}
