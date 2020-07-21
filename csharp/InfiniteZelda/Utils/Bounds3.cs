namespace Lumpn.Utils
{
    ///Immutable bounds.
    public class Bounds3
    {
        /// Inclusive
        public Bounds3(Int3 min, Int3 max)
        {
            this.min = min;
            this.max = max;
        }

        public bool Contains(Int3 position)
        {
            return (min <= position && position <= max);
        }

        private readonly Int3 min, max;
    }
}
