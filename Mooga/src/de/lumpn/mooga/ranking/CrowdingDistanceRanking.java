package de.lumpn.mooga.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import de.lumpn.mooga.Individual;
import de.lumpn.mooga.ranking.impl.CrowdingDistanceComparator;
import de.lumpn.mooga.ranking.impl.CrowdingDistanceIndividual;
import de.lumpn.mooga.ranking.impl.DominationComparator;
import de.lumpn.mooga.ranking.impl.ScoreComparator;

public final class CrowdingDistanceRanking implements Ranking {

	@Override
	public List<Individual> rank(List<Individual> individuals) {

		// trivial?
		if (individuals.size() < 2) {
			return new ArrayList<Individual>(individuals);
		}

		// split into non-dominated and dominated
		List<Individual> nonDominated = new ArrayList<Individual>();
		List<Individual> dominated = new ArrayList<Individual>();
		for (Individual individual : individuals) {

			// check domination
			boolean isDominated = false;
			for (Individual other : individuals) {
				if (other == individual) continue;
				if (comparator.compare(individual, other) < 0) {
					isDominated = true;
					break;
				}
			}

			// put in respective list
			if (isDominated) {
				dominated.add(individual);
			} else {
				nonDominated.add(individual);
			}
		}

		// sort non-dominated by crowding distance
		List<Individual> sortedNonDominated = sortByCrowdingDistance(nonDominated);

		// recursively rank the dominated individuals
		List<Individual> rankedDominated = rank(dominated);

		// concatenate both lists
		List<Individual> result = new ArrayList<Individual>();
		result.addAll(sortedNonDominated);
		result.addAll(rankedDominated);
		return result;
	}

	private List<Individual> sortByCrowdingDistance(List<Individual> individuals) {

		// trivial?
		if (individuals.size() < 2) {
			return new ArrayList<Individual>(individuals);
		}

		// create crowding distance wrapper
		List<CrowdingDistanceIndividual> wrappedIndividuals = new ArrayList<CrowdingDistanceIndividual>();
		for (Individual individual : individuals) {
			wrappedIndividuals.add(new CrowdingDistanceIndividual(individual));
		}

		// calculate each crowding distance
		final int lastIndex = wrappedIndividuals.size() - 1;
		final int numAttributes = individuals.get(0).numAttributes();
		for (int attribute = 0; attribute < numAttributes; attribute++) {
			wrappedIndividuals.sort(new ScoreComparator(attribute));

			CrowdingDistanceIndividual min = wrappedIndividuals.get(0);
			CrowdingDistanceIndividual max = wrappedIndividuals.get(lastIndex);

			final double minValue = min.getIndividual().getScore(attribute);
			final double maxValue = max.getIndividual().getScore(attribute);

			// no divergence?
			if (minValue >= maxValue) continue;

			// calculate crowding distance
			for (int i = 1; i < lastIndex; i++) {
				CrowdingDistanceIndividual current = wrappedIndividuals.get(i);
				CrowdingDistanceIndividual leftNeighbor = wrappedIndividuals.get(i - 1);
				CrowdingDistanceIndividual rightNeighbor = wrappedIndividuals.get(i + 1);

				// calculate & accumulate normalized crowding distance
				final double leftValue = leftNeighbor.getIndividual().getScore(attribute);
				final double rightValue = rightNeighbor.getIndividual().getScore(attribute);
				final double distance = (rightValue - leftValue) / (maxValue - minValue);
				current.addCrowdingDistance(distance);
			}

			// update extremes
			min.addCrowdingDistance(2.0);
			max.addCrowdingDistance(2.0);
		}

		// sort by descending crowding distance
		wrappedIndividuals.sort(Collections.reverseOrder(new CrowdingDistanceComparator()));

		// strip crowding distance wrapper
		List<Individual> result = new ArrayList<Individual>();
		for (CrowdingDistanceIndividual crowdingDistanceIndividual : wrappedIndividuals) {
			result.add(crowdingDistanceIndividual.getIndividual());
		}

		return result;
	}

	private final DominationComparator comparator = new DominationComparator();
}
