using System;
using System.Collections.Generic;

namespace Lumpn.Mooga
{
    public interface IGenome
    {
        /// Cross two genomes producing two offsprings.
        IEnumerable<IGenome> Crossover(IGenome other, IRandom random);

        /// Mutate genome returning the mutated copy.
        IGenome Mutate(IRandom random);
    }
}
