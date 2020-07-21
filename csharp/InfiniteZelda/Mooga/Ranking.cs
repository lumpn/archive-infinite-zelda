using System.Collections.Generic;

namespace Lumpn.Mooga
{
    public interface Ranking
    {
        /// Rank individuals. Highest score comes first.
        IEnumerable<Individual> Rank(IEnumerable<Individual> individuals);
    }
}
