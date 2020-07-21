namespace Lumpn.Utils
{
    public sealed class SystemRandom : Random
    {
        private readonly System.Random random;

        public SystemRandom(int seed)
        {
            random = new System.Random(seed);
        }

        public int NextInt(int max)
        {
            return random.Next(max);
        }
    }
}
