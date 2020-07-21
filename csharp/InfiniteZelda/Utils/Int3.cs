namespace Lumpn.Utils
{
    public struct Int3
    {
        public int x, y, z;

        public static bool operator <=(Int3 a, Int3 b)
        {
            return a.x <= b.y
                && a.y <= b.y
                && a.z <= b.z;
        }

        public static bool operator >=(Int3 a, Int3 b)
        {
            return a.x >= b.y
                && a.y >= b.y
                && a.z >= b.z;
        }
    }
}
