using System;
using System.Collections.Generic;

namespace Lumpn.Mooga
{
    public sealed class BinaryTournamentSelection : ISelection
    {
        public BinaryTournamentSelection(IRandom random)
        {
            this.random = random;
        }

        public IIndividual Select(List<IIndividual> individuals)
        {
            int size = individuals.Count;
            int pos1 = random.NextInt(size);
            int pos2 = random.NextInt(size);
            int pos = Math.Min(pos1, pos2);
            return individuals[pos];
        }

        public List<IIndividual> Select(List<IIndividual> individuals, int count)
        {
            Debug.Assert(count < individuals.Count);

            var result = new List<IIndividual>();
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

        private readonly IRandom random;
    }
}