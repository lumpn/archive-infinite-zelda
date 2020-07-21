using System;
using System.Collections.Generic;

namespace Lumpn.Mooga
{
    public sealed class CrowdingDistanceRanking : IRanking
    {
        public IEnumerable<Individual> Rank(IList<Individual> individuals)
        {
            // trivial?
            if (individuals.Count < 2)
            {
                return new List<Individual>(individuals);
            }

            // split into non-dominated and dominated
            List<Individual> nonDominated = new List<Individual>();
            List<Individual> dominated = new List<Individual>();
            foreach (Individual individual in individuals)
            {
                // check domination
                bool isDominated = false;
                foreach (Individual other in individuals)
                {
                    if (other == individual) continue;
                    if (comparator.Compare(individual, other) < 0)
                    {
                        isDominated = true;
                        break;
                    }
                }

                // put in respective list
                if (isDominated)
                {
                    dominated.Add(individual);
                }
                else
                {
                    nonDominated.Add(individual);
                }
            }

            // sort non-dominated by crowding distance
            List<Individual> sortedNonDominated = sortByCrowdingDistance(nonDominated);

            // recursively rank the dominated individuals
            List<Individual> rankedDominated = Rank(dominated);

            // concatenate both lists
            List<Individual> result = new List<Individual>();
            result.AddRange(sortedNonDominated);
            result.AddRange(rankedDominated);
            return result;
        }

        private List<Individual> SortByCrowdingDistance(List<Individual> individuals)
        {
            // trivial?
            if (individuals.Count < 2)
            {
                return new List<Individual>(individuals);
            }

            // create crowding distance wrapper
            List<CrowdingDistanceIndividual> wrappedIndividuals = new List<CrowdingDistanceIndividual>();
            foreach (Individual individual in individuals)
            {
                wrappedIndividuals.Add(new CrowdingDistanceIndividual(individual));
            }

            // calculate each crowding distance
            int lastIndex = wrappedIndividuals.Count - 1;
            int numAttributes = individuals[0].NumAttributes;
            for (int attribute = 0; attribute < numAttributes; attribute++)
            {
                wrappedIndividuals.Sort(new ScoreComparator(attribute));

                CrowdingDistanceIndividual min = wrappedIndividuals[0];
                CrowdingDistanceIndividual max = wrappedIndividuals[lastIndex];

                double minValue = min.individual.Score(attribute);
                double maxValue = max.individual.Score(attribute);

                // no divergence?
                if (minValue >= maxValue) continue;

                // calculate crowding distance
                for (int i = 1; i < lastIndex; i++)
                {
                    CrowdingDistanceIndividual current = wrappedIndividuals[i];
                    CrowdingDistanceIndividual leftNeighbor = wrappedIndividuals[i - 1];
                    CrowdingDistanceIndividual rightNeighbor = wrappedIndividuals[i + 1];

                    // calculate & accumulate normalized crowding distance
                    double leftValue = leftNeighbor.individual.Score(attribute);
                    double rightValue = rightNeighbor.individual.Score(attribute);
                    double distance = (rightValue - leftValue) / (maxValue - minValue);
                    current.AddCrowdingDistance(distance);
                }

                // update extremes
                min.AddCrowdingDistance(2.0);
                max.AddCrowdingDistance(2.0);
            }

            // sort by descending crowding distance
            wrappedIndividuals.Sort(new CrowdingDistanceComparator());
            wrappedIndividuals.Reverse();

            // strip crowding distance wrapper
            List<Individual> result = new List<Individual>();
            foreach (CrowdingDistanceIndividual crowdingDistanceIndividual in wrappedIndividuals)
            {
                result.Add(crowdingDistanceIndividual.individual);
            }

            return result;
        }

        private readonly DominationComparator comparator = new DominationComparator();
    }
}