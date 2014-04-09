package de.lumpn.mooga.ranking.impl;

import java.util.Comparator;

/**
 * Compares individuals by total crowding distance.
 */
public final class CrowdingDistanceComparator implements
		Comparator<CrowdingDistanceIndividual> {

	@Override
	public int compare(CrowdingDistanceIndividual a, CrowdingDistanceIndividual b) {
		double distance1 = a.getTotalCrowdingDistance();
		double distance2 = b.getTotalCrowdingDistance();
		return Double.compare(distance1, distance2);
	}
}
