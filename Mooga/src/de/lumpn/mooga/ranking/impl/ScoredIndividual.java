package de.lumpn.mooga.ranking.impl;

import de.lumpn.mooga.Individual;

public class ScoredIndividual {

	public ScoredIndividual(Individual individual) {
		this.individual = individual;
	}

	public Individual getIndividual() {
		return individual;
	}

	public double getScore() {
		return score;
	}

	public void accumulateScore(double increment) {
		score += increment;
	}

	private final Individual individual;
	
	private double score = 0.0;
}
