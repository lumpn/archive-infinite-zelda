namespace Lumpn.Mooga
{
    public interface Individual
    {
        /// The individual's genome.
        Genome Genome { get; }

        // TODO Jonas: numAttributes is not an attribute of individuals but of the configuration
        /// Number of attributes evaluated.
        int NumAttributes { get; }

        // TODO Jonas: priority is not an attribute of individuals but of the configuration
        /// Priority of specific attribute for multi stage multi object optimization
        int Priority(int attribute);

        /// Score of specific attribute. Make sure getting these values is fast (use cache)!
        double Score(int attribute);

        // TODO Jonas: remove
        /// Whether or not this individual can be considered best
        bool IsElite { get; }
    }
}
