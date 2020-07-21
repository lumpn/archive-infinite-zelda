using System.Collections.Generic;

namespace Lumpn.Mooga
{
    /// Compares individuals by score of specific attribute.
    public sealed class ScoreComparer : IComparer<CrowdingDistanceIndividual>
    {
        private static readonly Comparer<double> comparer = Comparer<double>.Default;

        public ScoreComparer(int attribute)
        {
            this.attribute = attribute;
        }

        public int Compare(CrowdingDistanceIndividual a, CrowdingDistanceIndividual b)
        {
            double score1 = a.individual.Score(attribute);
            double score2 = b.individual.Score(attribute);
            return comparer.Compare(score1, score2);
        }

        private readonly int attribute;
    }
}