using System.Collections.Generic;
using Lumpn.Utils;
using System.Diagnostics;

namespace Lumpn.Mooga
{
    public sealed class BinaryTournamentSelection : Selection
    {
        public BinaryTournamentSelection(Random random)
        {
            this.random = random;
        }

        public Individual Select(List<Individual> individuals)
        {
            int size = individuals.Count;
            int pos1 = random.NextInt(size);
            int pos2 = random.NextInt(size);
            int pos = System.Math.Min(pos1, pos2);
            return individuals[pos];
        }

        public List<Individual> Select(List<Individual> individuals, int count)
        {
            System.Diagnostics.Debug.Assert(count < individuals.Count);

            var result = new List<Individual>();
            while (result.Count < count)
            {
                var individual = Select(individuals);
                if (!result.Contains(individual))
                {
                    result.Add(individual);
                }
            }
            return result;
        }

        private readonly Random random;
    }
}