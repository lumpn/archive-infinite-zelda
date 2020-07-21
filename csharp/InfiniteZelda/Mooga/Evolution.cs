using System;
using System.Collections.Generic;
using Lumpn.Utils;

namespace Lumpn.Mooga
{
    public class Evolution
    {
        public Evolution(int populationSize, double crossoverFraction, double mutationFraction, GenomeFactory factory, Selection selection)
        {
            this.populationSize = populationSize;
            this.crossoverQuota = (int)(populationSize * crossoverFraction);
            this.mutationQuota = (int)(populationSize * mutationFraction);
            this.factory = factory;
            this.selection = selection;
        }

        public List<Genome> Initialize()
        {
            List<Genome> result = new List<Genome>();
            for (int i = 0; i < populationSize; i++)
            {
                Genome genome = factory.CreateGenome();
                result.Add(genome);
            }
            return result;
        }

        public List<Genome> Evolve(List<Individual> rankedPopulation, RandomNumberGenerator random)
        {
            List<Genome> result = new List<Genome>();

            // crossover
            for (int i = 0; i < crossoverQuota; i += 2)
            {
                List<Individual> parents = selection.Select(rankedPopulation, 2);
                Genome a = parents[0].Genome;
                Genome b = parents[1].Genome;
                var children = a.Crossover(b, random);
                result.AddRange(children);
            }

            // mutation
            for (int i = 0; i < mutationQuota; i++)
            {
                Individual parent = selection.Select(rankedPopulation);
                Genome child = parent.Genome.Mutate(random);
                result.Add(child);
            }

            // fill up
            for (int i = result.Count; i < populationSize; i++)
            {
                Genome genome = factory.CreateGenome();
                result.Add(genome);
            }

            return result;
        }

        private readonly int populationSize;
        private readonly int crossoverQuota;
        private readonly int mutationQuota;

        private readonly GenomeFactory factory;

        private readonly Selection selection;
    }
}