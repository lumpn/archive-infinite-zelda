using Lumpn.Utils;

namespace Lumpn.Mooga.Test
{
    public sealed class ParetoIndividual : Individual
    {
        public ParetoIndividual(double score1, double score2, double score3, int priority1, int priority2, int priority3)
        {
            this.score1 = score1;
            this.score2 = score2;
            this.score3 = score3;
            this.priority1 = priority1;
            this.priority2 = priority2;
            this.priority3 = priority3;
        }

        public Genome Genome
        {
            get
            {
                {
                    return null; // not needed for test
                }
            }
        }

        public int NumAttributes
        {
            get
            {
                return 3;
            }
        }

        public int Priority(int attribute)
        {
            switch (attribute)
            {
                case 0:
                    return priority1;
                case 1:
                    return priority2;
                case 2:
                    return priority3;
                default:
                    Debug.Fail();
                    return 0;
            }
        }

        public double Score(int attribute)
        {
            switch (attribute)
            {
                case 0:
                    return score1;
                case 1:
                    return score2;
                case 2:
                    return score3;
                default:
                    Debug.Fail();
                    return 0.0;
            }
        }

        public bool IsElite
        {
            get
            {
                return true;
            }
        }

        public override string ToString()
        {
            return string.Format("ParetoIndividual (%f %f %f)", score1, score2, score3);
        }

        private readonly double score1, score2, score3;
        private readonly int priority1, priority2, priority3;
    }
}
