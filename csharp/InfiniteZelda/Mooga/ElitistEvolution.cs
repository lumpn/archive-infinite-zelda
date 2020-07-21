using System;
using System.Collections.Generic;

namespace Lumpn.Mooga
{
    public sealed class ElitistEvolution
    {
        public ElitistEvolution(int populationSize, int archiveSize, GenomeFactory factory, Environment environment)
        {
            this.archiveSize = archiveSize;
            this.evolution = new Evolution(populationSize, 0.5, 0.4, factory, new BinaryTournamentSelection(new Random()));
            this.environment = environment;
            this.ranking = new CrowdingDistanceRanking();
        }

        public List<Genome> initialize()
        {
            return evolution.initialize();
        }

        public List<Genome> evolve(List<Genome> genomes, Random random)
        {

            // spawn individuals
            long t1 = System.currentTimeMillis();
            List<Individual> population = new ArrayList<Individual>();
            for (Genome genome : genomes)
            {
                // TODO: only evaluate genomes not previously seen
                Individual individual = environment.evaluate(genome);
                population.add(individual);
            }
            long t2 = System.currentTimeMillis();

            // combine with archive
            population.addAll(archive);

            // distinct
            population = new ArrayList<Individual>(new HashSet<Individual>(population));

            // rank population
            long t3 = System.currentTimeMillis();
            List<Individual> rankedPopulation = ranking.rank(population);
            long t4 = System.currentTimeMillis();

            // update archive
            archive = rankedPopulation.subList(0, Math.min(archiveSize, rankedPopulation.size()));
            print(rankedPopulation.subList(0, Math.min(10, rankedPopulation.size())));
            printStats(rankedPopulation, t1, t2, t3, t4);
            System.out.println(rankedPopulation.size() + " distinct individuals");

            // evolve population
            return evolution.evolve(rankedPopulation, random);
        }

        public Individual getBest()
        {
            for (Individual individual : archive)
            {
                if (individual.isElite())
                {
                    return individual;
                }
            }
            return null;
        }

        private static void print(Iterable<Individual> individuals)
        {
            System.out.println("----------------------------------------------------");
            for (Individual individual : individuals)
            {
                System.out.println(individual);
            }
            System.out.println("----------------------------------------------------");
        }

        private static void printStats(Iterable<Individual> individuals, long t1, long t2, long t3, long t4)
        {
            List<Double> mins = new ArrayList<Double>();
            List<Double> maxs = new ArrayList<Double>();
            List<Double> avgs = new ArrayList<Double>();

            int count = 0;
            int attributes = 0;
            for (Individual individual : individuals)
            {

                // initialize lists
                if (mins.isEmpty())
                {
                    attributes = individual.numAttributes();
                    mins.addAll(repeat(Double.MAX_VALUE, attributes));
                    maxs.addAll(repeat(Double.MIN_VALUE, attributes));
                    avgs.addAll(repeat(0, attributes));
                }

                // record stats
                for (int i = 0; i < attributes; i++)
                {
                    double score = individual.getScore(i);
                    mins.set(i, Math.min(mins.get(i), score));
                    maxs.set(i, Math.max(maxs.get(i), score));
                    avgs.set(i, avgs.get(i) + score);
                    count++;
                }
            }

            // print stats
            for (int i = 0; i < attributes; i++)
            {
                System.out.format("%d: min %f, max %f, mid %f, avg %f\n", i, mins.get(i), maxs.get(i), (mins.get(i) + maxs.get(i)) / 2, avgs.get(i) / count);
            }

            // print times
            System.out.format("%dms eval, %dms rank, %dms total\n", t2 - t1, t4 - t3, t4 - t1);
        }

        private static List<Double> repeat(double value, int times)
        {
            List<Double> result = new ArrayList<Double>(times);
            for (int i = 0; i < times; i++)
                result.add(value);
            return result;
        }

        private readonly int archiveSize;

        private readonly Evolution evolution;
        private readonly Environment environment;

        private readonly IRanking ranking;

        private readonly List<Individual> archive = new List<Individual>();
    }
}