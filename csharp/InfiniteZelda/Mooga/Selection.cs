using System.Collections.Generic;

namespace Lumpn.Mooga
{
    public interface Selection
    {
        /// Selects a single individual.
        Individual Select(List<Individual> individuals);

        /// Selects a sample of mutually different individuals.
        List<Individual> Select(List<Individual> individuals, int count);
    }
}
