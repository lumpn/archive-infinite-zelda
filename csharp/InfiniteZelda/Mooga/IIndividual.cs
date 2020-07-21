namespace Lumpn.Mooga
{
    public interface IIndividual
    {
        /// The individual's genome.
        IGenome Genome { get; }

        /// Number of attributes evaluated.
        int NumAttributes { get; }

        /// Priority of specific attribute for multi stage multi object optimization
        int Priority(int attribute);

        /// Score of specific attribute. Make sure getting these values is fast (use cache)!
        double Score(int attribute);

        /// Wether or not this individual can be considered best
        bool IsElite { get; }
    }
}
