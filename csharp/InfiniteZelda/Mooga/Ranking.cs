using System.Collections.Generic;

namespace Lumpn.Mooga
{
    public interface IRanking
    {
        /// Rank individuals. Highest score comes first.
        IEnumerable<Individual> Rank(IEnumerable<Individual> individuals);
    }
}
