using System;
using System.Collections.Generic;

namespace Lumpn.Mooga
{
    ///Compares individuals by domination of attributes.
    public sealed class DominationComparator : Comparer<Individual>
    {
        public int Compare(Individual a, Individual b)
        {
            Debug.Assert(a.NumAttributes == b.NumAttributes);

            bool aIsPartiallyBetter = false;
            bool bIsPartiallyBetter = false;
            int aPriority = int.MinValue;
            int bPriority = int.MinValue;

            // compare each score
            int numScores = a.NumAttributes;
            for (int i = 0; i < numScores; i++)
            {
                double aScore = a.Score(i);
                double bScore = b.Score(i);
                int priority = a.Priority(i);
                if (aScore > bScore)
                {
                    aIsPartiallyBetter = true;
                    aPriority = Math.Max(aPriority, priority);
                }
                else if (aScore < bScore)
                {
                    bIsPartiallyBetter = true;
                    bPriority = Math.Max(bPriority, priority);
                }
            }

            // look at priorities
            if (aPriority != bPriority)
            {
                return Comparer<int>.Default.Compare(aPriority, bPriority);
            }

            // determine domination
            return Comparer<bool>.Default.Compare(aIsPartiallyBetter, bIsPartiallyBetter);
        }
    }
}