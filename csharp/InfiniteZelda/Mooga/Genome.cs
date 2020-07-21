using System;
using System.Collections.Generic;

namespace Lumpn.Mooga
{
    public interface Genome
    {
        /// Cross two genomes producing two offsprings.
        IEnumerable<Genome> Crossover(Genome other, Random random);

        /// Mutate genome returning the mutated copy.
        Genome Mutate(Random random);
    }
}
