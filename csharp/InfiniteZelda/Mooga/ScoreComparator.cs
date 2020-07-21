using System.Collections.Generic;

namespace Lumpn.Mooga
{
    /// Compares individuals by score of specific attribute.
    public sealed class ScoreComparator : IComparer<CrowdingDistanceIndividual>
    {
        private static readonly Comparer<double> comparer = Comparer<double>.Default;

        public ScoreComparator(int attribute)
        {
            this.attribute = attribute;
        }

        public int Compare(CrowdingDistanceIndividual a, CrowdingDistanceIndividual b)
        {
            double score1 = a.Individual.getScore(attribute);
            double score2 = b.Individual.getScore(attribute);
            return comparer.Compare(score1, score2);
        }

        private readonly int attribute;
    }
}