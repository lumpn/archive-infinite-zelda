package de.lumpn.mooga.ranking.impl;

import de.lumpn.mooga.Individual;

/**
 * Wrapper around individuals to store total crowding distance.
 */
public final class CrowdingDistanceIndividual {

	public CrowdingDistanceIndividual(Individual individual) {
		this.individual = individual;
	}

	public Individual getIndividual() {
		return individual;
	}

	public double getTotalCrowdingDistance() {
		return totalDistance;
	}

	public void addCrowdingDistance(double distance) {
		totalDistance += distance;
	}

	@Override
	public String toString() {
		return String.format("CrowdingDistanceIndividual [totalDistance=%s, individual=%s]",
				totalDistance, individual);
	}

	private double totalDistance = 0.0;

	private final Individual individual;
}
