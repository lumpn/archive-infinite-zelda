using System;

namespace Lumpn.Mooga
{
    public sealed class SystemRandom : IRandom
    {
        private readonly Random random;

        public SystemRandom(int seed)
        {
            random = new Random(seed);
        }

        public int NextInt(int max)
        {
            return random.Next(max);
        }
    }
}
