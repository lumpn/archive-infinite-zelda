using System;
using System.Collections.Generic;
using System.Linq;

namespace Lumpn.Mooga
{
    public sealed class CrowdingDistanceRanking : Ranking
    {
        public IEnumerable<Individual> Rank(IEnumerable<Individual> individuals)
        {
            // split into non-dominated and dominated
            var nonDominated = new List<Individual>();
            var dominated = new List<Individual>();
            foreach (Individual individual in individuals)
            {
                // check domination
                bool isDominated = false;
                foreach (Individual other in individuals)
                {
                    if (other == individual) continue;
                    if (comparer.Compare(individual, other) < 0)
                    {
                        isDominated = true;
                        break;
                    }
                }

                // put in respective list
                var list = isDominated ? dominated : nonDominated;
                list.Add(individual);
            }

            // sort non-dominated by crowding distance
            var sortedNonDominated = SortByCrowdingDistance(nonDominated);

            // recursively rank the dominated individuals
            var rankedDominated = Rank(dominated);

            // concatenate both lists
            return sortedNonDominated.Concat(rankedDominated);
        }

        private IEnumerable<Individual> SortByCrowdingDistance(List<Individual> individuals)
        {
            if (individuals.Count < 2)
            {
                return individuals;
            }

            // create crowding distance wrapper
            var wrappedIndividuals = individuals.Select(p => new CrowdingDistanceIndividual(p)).ToList();

            // calculate each crowding distance
            int lastIndex = wrappedIndividuals.Count - 1;
            int numAttributes = wrappedIndividuals[0].NumAttributes; // TODO Jonas: use configuration
            for (int attribute = 0; attribute < numAttributes; attribute++)
            {
                wrappedIndividuals.Sort(new ScoreComparer(attribute));

                CrowdingDistanceIndividual min = wrappedIndividuals[0];
                CrowdingDistanceIndividual max = wrappedIndividuals[lastIndex];

                double minValue = min.Score(attribute);
                double maxValue = max.Score(attribute);
                double range = maxValue - minValue;

                // no divergence?
                if (minValue >= maxValue) continue;

                // calculate crowding distance
                for (int i = 1; i < lastIndex; i++)
                {
                    CrowdingDistanceIndividual current = wrappedIndividuals[i];
                    CrowdingDistanceIndividual leftNeighbor = wrappedIndividuals[i - 1];
                    CrowdingDistanceIndividual rightNeighbor = wrappedIndividuals[i + 1];

                    // calculate & accumulate normalized crowding distance
                    double leftValue = leftNeighbor.Score(attribute);
                    double rightValue = rightNeighbor.Score(attribute);
                    double crowdingRange = rightValue - leftValue;
                    double distance = crowdingRange / range;
                    current.AddCrowdingDistance(distance);
                }

                // update extremes
                min.AddCrowdingDistance(2.0);
                max.AddCrowdingDistance(2.0);
            }

            // sort by descending crowding distance
            wrappedIndividuals.Sort(new CrowdingDistanceComparer());
            wrappedIndividuals.Reverse();

            // strip crowding distance wrapper
            return wrappedIndividuals.Select(p => p.individual);
        }

        private readonly DominationComparer comparer = new DominationComparer();
    }
}