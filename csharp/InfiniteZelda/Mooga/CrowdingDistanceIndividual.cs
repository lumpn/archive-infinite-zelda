namespace Lumpn.Mooga
{
    /// Wrapper around individuals to store total crowding distance.
    public sealed class CrowdingDistanceIndividual : Individual
    {
        public CrowdingDistanceIndividual(Individual individual)
        {
            this.individual = individual;
        }

        public void AddCrowdingDistance(double distance)
        {
            totalDistance += distance;
        }

        public Genome Genome { get { return individual.Genome; } }
        public int NumAttributes { get { return individual.NumAttributes; } }
        public int Priority(int attribute) { return individual.Priority(attribute); }
        public double Score(int attribute) { return individual.Score(attribute); }
        public bool IsElite { get { return individual.IsElite; } }

        public override string ToString()
        {
            return string.Format("(totalDistance {0}, individual {1})", totalDistance, individual);
        }

        public double Distance { get { return totalDistance; } }

        private double totalDistance = 0.0;

        public readonly Individual individual;
    }
}
