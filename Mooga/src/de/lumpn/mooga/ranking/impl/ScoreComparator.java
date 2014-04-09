package de.lumpn.mooga.ranking.impl;

import java.util.Comparator;

/**
 * Compares individuals by score of specific attribute.
 */
public final class ScoreComparator implements Comparator<CrowdingDistanceIndividual> {

	public ScoreComparator(int attribute) {
		this.attribute = attribute;
	}

	@Override
	public int compare(CrowdingDistanceIndividual a, CrowdingDistanceIndividual b) {
		double score1 = a.getIndividual().getScore(attribute);
		double score2 = b.getIndividual().getScore(attribute);
		return Double.compare(score1, score2);
	}

	private final int attribute;
}
