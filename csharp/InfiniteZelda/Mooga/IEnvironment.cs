namespace Lumpn.Mooga
{
    public interface IEnvironment
    {
        IIndividual Evaluate(IGenome genome);
    }
}
