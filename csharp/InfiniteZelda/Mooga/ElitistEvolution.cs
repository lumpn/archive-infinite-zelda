using Lumpn.Utils;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;

namespace Lumpn.Mooga
{
    public sealed class ElitistEvolution
    {
        public ElitistEvolution(int populationSize, int archiveSize, GenomeFactory factory, Environment environment)
        {
            var random = new SystemRandom(42);
            var selection = new BinaryTournamentSelection(random);

            this.archiveSize = archiveSize;
            this.evolution = new Evolution(populationSize, 0.5, 0.4, factory, selection);
            this.environment = environment;
            this.ranking = new CrowdingDistanceRanking();
        }

        public List<Genome> Initialize()
        {
            return evolution.Initialize();
        }

        public List<Genome> Evolve(List<Genome> genomes, RandomNumberGenerator random)
        {
            // spawn individuals
            List<Individual> population = new List<Individual>();
            foreach (Genome genome in genomes)
            {
                // TODO: only evaluate genomes not previously seen
                Individual individual = environment.Evaluate(genome);
                population.Add(individual);
            }

            // combine with archive
            population.AddRange(archive);

            // rank population
            var rankedPopulation = ranking.Rank(population.Distinct()).ToList();

            // update archive
            archive.Clear();
            archive.AddRange(rankedPopulation.Take(archiveSize));
            Print(rankedPopulation.Take(10));
            PrintStats(rankedPopulation);
            Console.WriteLine("{0} distinct individuals", rankedPopulation.Count);

            // evolve population
            return evolution.Evolve(rankedPopulation, random);
        }

        public Individual GetBest()
        {
            foreach (Individual individual in archive)
            {
                if (individual.IsElite)
                {
                    return individual;
                }
            }
            return null;
        }

        private static void Print(IEnumerable<Individual> individuals)
        {
            Console.WriteLine("----------------------------------------------------");
            foreach (Individual individual in individuals)
            {
                Console.WriteLine(individual);
            }
            Console.WriteLine("----------------------------------------------------");
        }

        private static void PrintStats(IEnumerable<Individual> individuals)
        {
            List<double> mins = new List<double>();
            List<double> maxs = new List<double>();
            List<double> avgs = new List<double>();

            int count = 0;
            int attributes = 0;
            foreach (Individual individual in individuals)
            {
                // initialize lists
                if (mins.Count < 1)
                {
                    attributes = individual.NumAttributes;
                    mins.AddRange(Enumerable.Repeat(double.MaxValue, attributes));
                    maxs.AddRange(Enumerable.Repeat(double.MinValue, attributes));
                    avgs.AddRange(Enumerable.Repeat(0.0, attributes));
                }

                // record stats
                for (int i = 0; i < attributes; i++)
                {
                    double score = individual.Score(i);
                    mins[i] = Math.Min(mins[i], score);
                    maxs[i] = Math.Max(maxs[i], score);
                    avgs[i] = avgs[i] + score;
                    count++;
                }
            }

            // print stats
            for (int i = 0; i < attributes; i++)
            {
                Console.WriteLine("{0}: min {1}, max {2}, mid {3}, avg {4}\n", i, mins[i], maxs[i], (mins[i] + maxs[i]) / 2, avgs[i] / count);
            }
        }

        private readonly int archiveSize;

        private readonly Evolution evolution;
        private readonly Environment environment;

        private readonly Ranking ranking;

        private readonly List<Individual> archive = new List<Individual>();
    }
}