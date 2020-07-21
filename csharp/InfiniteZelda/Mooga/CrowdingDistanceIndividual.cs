namespace Lumpn.Mooga
{
    /// Wrapper around individuals to store total crowding distance.
    public sealed class CrowdingDistanceIndividual
    {
        public CrowdingDistanceIndividual(IIndividual individual)
        {
            this.individual = individual;
        }

        public void AddCrowdingDistance(double distance)
        {
            totalDistance += distance;
        }

        public override string ToString()
        {
            return string.Format("CrowdingDistanceIndividual [totalDistance={0}, individual={1}]",
                    totalDistance, individual);
        }

        public double Distance { get { return totalDistance; } }

        private double totalDistance = 0.0;

        public readonly IIndividual individual;
    }
}