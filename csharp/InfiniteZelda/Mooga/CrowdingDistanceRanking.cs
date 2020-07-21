using System;
using System.Collections.Generic;

namespace Lumpn.Mooga
{
    public sealed class CrowdingDistanceRanking : IRanking
    {
        public IEnumerable<IIndividual> Rank(IList<IIndividual> individuals)
        {
            // trivial?
            if (individuals.Count < 2)
            {
                return new List<IIndividual>(individuals);
            }

            // split into non-dominated and dominated
            List<IIndividual> nonDominated = new List<IIndividual>();
            List<IIndividual> dominated = new List<IIndividual>();
            foreach (IIndividual individual in individuals)
            {
                // check domination
                bool isDominated = false;
                foreach (IIndividual other in individuals)
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
            List<IIndividual> sortedNonDominated = sortByCrowdingDistance(nonDominated);

            // recursively rank the dominated individuals
            List<IIndividual> rankedDominated = Rank(dominated);

            // concatenate both lists
            List<IIndividual> result = new List<IIndividual>();
            result.AddRange(sortedNonDominated);
            result.AddRange(rankedDominated);
            return result;
        }

        private List<IIndividual> SortByCrowdingDistance(List<IIndividual> individuals)
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
            int numAttributes = individuals[0].numAttributes();
            for (int attribute = 0; attribute < numAttributes; attribute++)
            {
                wrappedIndividuals.sort(new ScoreComparator(attribute));

                CrowdingDistanceIndividual min = wrappedIndividuals[0];
                CrowdingDistanceIndividual max = wrappedIndividuals[lastIndex];

                double minValue = min.getIndividual().getScore(attribute);
                double maxValue = max.getIndividual().getScore(attribute);

                // no divergence?
                if (minValue >= maxValue) continue;

                // calculate crowding distance
                for (int i = 1; i < lastIndex; i++)
                {
                    CrowdingDistanceIndividual current = wrappedIndividuals.get(i);
                    CrowdingDistanceIndividual leftNeighbor = wrappedIndividuals.get(i - 1);
                    CrowdingDistanceIndividual rightNeighbor = wrappedIndividuals.get(i + 1);

                    // calculate & accumulate normalized crowding distance
                    double leftValue = leftNeighbor.getIndividual().getScore(attribute);
                    double rightValue = rightNeighbor.getIndividual().getScore(attribute);
                    double distance = (rightValue - leftValue) / (maxValue - minValue);
                    current.addCrowdingDistance(distance);
                }

                // update extremes
                min.addCrowdingDistance(2.0);
                max.addCrowdingDistance(2.0);
            }

            // sort by descending crowding distance
            wrappedIndividuals.sort(Collections.reverseOrder(new CrowdingDistanceComparator()));

            // strip crowding distance wrapper
            List<Individual> result = new List<Individual>();
            foreach (CrowdingDistanceIndividual crowdingDistanceIndividual in wrappedIndividuals)
            {
                result.Add(crowdingDistanceIndividual.getIndividual());
            }

            return result;
        }

        private readonly DominationComparator comparator = new DominationComparator();
    }
}