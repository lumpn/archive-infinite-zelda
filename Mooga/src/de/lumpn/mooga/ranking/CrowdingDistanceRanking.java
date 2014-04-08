package de.lumpn.mooga.ranking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import de.lumpn.mooga.CharacteristicEvaluator;
import de.lumpn.mooga.Individual;
import de.lumpn.mooga.ranking.impl.CharacteristicComparator;
import de.lumpn.mooga.ranking.impl.ScoreComparator;
import de.lumpn.mooga.ranking.impl.ScoredIndividual;

public class CrowdingDistanceRanking implements Ranking {

	public CrowdingDistanceRanking(Comparator<Individual> comparator,
			Iterable<CharacteristicEvaluator> evaluators) {
		this.comparator = comparator;
		this.evaluators = evaluators;
	}

	@Override
	public List<Individual> rank(List<Individual> individuals) {

		// trivial?
		if (individuals.isEmpty()) {
			return new ArrayList<Individual>();
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

		// concatenate
		List<Individual> result = new ArrayList<Individual>();
		result.addAll(sortedNonDominated);
		result.addAll(rankedDominated);

		return result;
	}

	/**
	 * crowding distance calculation
	 */
	private List<Individual> sortByCrowdingDistance(List<Individual> individuals) {

		// trivial?
		if (individuals.isEmpty()) {
			return new ArrayList<Individual>();
		}

		// initialize scores
		List<ScoredIndividual> scoredIndividuals = new ArrayList<ScoredIndividual>();
		for (Individual individual : individuals) {
			scoredIndividuals.add(new ScoredIndividual(individual));
		}

		// calculate each crowding distance
		int lastIndex = scoredIndividuals.size() - 1;
		for (CharacteristicEvaluator evaluator : evaluators) {

			scoredIndividuals.sort(new CharacteristicComparator(evaluator));

			ScoredIndividual min = scoredIndividuals.get(0);
			ScoredIndividual max = scoredIndividuals.get(lastIndex);

			double minValue = evaluator.getValue(min.getIndividual());
			double maxValue = evaluator.getValue(max.getIndividual());

			// no divergence?
			if (minValue >= maxValue) continue;

			// calculate crowding distance
			for (int j = 1; j < lastIndex; j++) {
				ScoredIndividual current = scoredIndividuals.get(j);
				ScoredIndividual leftNeighbor = scoredIndividuals.get(j - 1);
				ScoredIndividual rightNeighbor = scoredIndividuals.get(j + 1);

				// calculate normalized crowding distance & update score
				double leftValue = evaluator.getValue(leftNeighbor.getIndividual());
				double rightValue = evaluator.getValue(rightNeighbor.getIndividual());
				double distance = (rightValue - leftValue) / (maxValue - minValue);
				current.accumulateScore(distance);
			}

			// update extremes
			min.accumulateScore(1);
			max.accumulateScore(1);
		}

		// sort by contribution
		scoredIndividuals.sort(new ScoreComparator());
		List<Individual> result = new ArrayList<Individual>();
		for (ScoredIndividual scoredIndividual : scoredIndividuals) {
			result.add(scoredIndividual.getIndividual());
		}
		return result;
	}

	private final Comparator<Individual> comparator;

	private final Iterable<CharacteristicEvaluator> evaluators;
}
