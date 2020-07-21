namespace Lumpn.Mooga.Test
{
    public sealed class SimpleIndividual : Individual
    {
        public SimpleIndividual(double score)
        {
            this.score = score;
        }

        public Genome Genome
        {
            get
            {
                return null; // not needed for test
            }
        }

        public int NumAttributes
        {
            get
            {
                return 1;
            }
        }

        public int Priority(int attribute)
        {
            return 0;
        }

        public double Score(int attribute)
        {
            return score;
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
            return string.Format("SimpleIndividual (score {0})", score);
        }

        private readonly double score;
    }
}
