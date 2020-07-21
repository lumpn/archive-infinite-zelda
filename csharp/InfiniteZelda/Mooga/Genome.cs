using System;
using System.Collections.Generic;
using Lumpn.Utils;

namespace Lumpn.Mooga
{
    public interface Genome
    {
        /// Cross two genomes producing two offsprings.
        IEnumerable<Genome> Crossover(Genome other, RandomNumberGenerator random);

        /// Mutate genome returning the mutated copy.
        Genome Mutate(RandomNumberGenerator random);
    }
}
