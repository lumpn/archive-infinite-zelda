package de.lumpn.mooga.ranking.impl;

import java.util.Comparator;

public class ScoreComparator implements Comparator<ScoredIndividual> {

	@Override
	public int compare(ScoredIndividual a, ScoredIndividual b) {
		double scoreA = a.getScore();
		double scoreB = b.getScore();
		if (scoreA > scoreB) {
			return 1;
		}
		if (scoreA < scoreB) {
			return -1;
		}
		return 0;
	}
}
