using System.Collections.Generic;

namespace Lumpn.Mooga
{
    public interface ISelection
    {
        /// Selects a single individual.
        IIndividual Select(List<IIndividual> individuals);

        /// Selects a sample of mutually different individuals.
        List<IIndividual> Select(List<IIndividual> individuals, int count);
    }
}
