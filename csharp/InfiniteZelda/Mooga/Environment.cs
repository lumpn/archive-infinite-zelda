namespace Lumpn.Mooga
{
    public interface IEnvironment
    {
        Individual Evaluate(Genome genome);
    }
}
