using NUnit.Framework;

namespace Lumpn.Mooga.Test
{
    [TestFixture]
    public sealed class CrowdingDistanceRankingTest
    {
        [Test]
        public void TestRankSimple()
        {
            // create individuals
            List<Individual> individuals = new ArrayList<Individual>();
            individuals.add(new SimpleIndividual(3.0));
            individuals.add(new SimpleIndividual(1.0));
            individuals.add(new SimpleIndividual(4.0));
            individuals.add(new SimpleIndividual(1.0));
            individuals.add(new SimpleIndividual(5.0));
            individuals.add(new SimpleIndividual(9.0)); // dominating

            // rank
            Ranking ranking = new CrowdingDistanceRanking();
            List<Individual> ranked = ranking.rank(individuals);

            // assert highest score comes first
            final double delta = 0.1;
            Assert.assertEquals(9.0, ranked.get(0).getScore(0), delta);
            Assert.assertEquals(5.0, ranked.get(1).getScore(0), delta);
            Assert.assertEquals(4.0, ranked.get(2).getScore(0), delta);
            Assert.assertEquals(3.0, ranked.get(3).getScore(0), delta);
            Assert.assertEquals(1.0, ranked.get(4).getScore(0), delta);
            Assert.assertEquals(1.0, ranked.get(5).getScore(0), delta);
        }

        [Test]
        public void testRankPareto()
        {

            // create individuals
            List<Individual> individuals = new ArrayList<Individual>();
            individuals.add(new ParetoIndividual(3.0, 6.0, 0, 0, 0, 0)); // rank 1, dominates (1, 5)
            individuals.add(new ParetoIndividual(1.0, 5.0, 0, 0, 0, 0)); // rank 2, dominates (1, 3)
            individuals.add(new ParetoIndividual(4.0, 4.0, 0, 0, 0, 0)); // rank 1, dominates (1, 3)
            individuals.add(new ParetoIndividual(1.0, 3.0, 0, 0, 0, 0)); // rank 3
            individuals.add(new ParetoIndividual(5.0, 2.0, 0, 0, 0, 0)); // rank 1
            individuals.add(new ParetoIndividual(9.0, 1.0, 0, 0, 0, 0)); // rank 1

            // rank
            Ranking ranking = new CrowdingDistanceRanking();
            List<Individual> ranked = ranking.rank(individuals);

            // assert highest score comes first
            final double delta = 0.1;
            Assert.assertEquals(1.0, ranked.get(0).getScore(1), delta); // rank 1 extreme
            Assert.assertEquals(6.0, ranked.get(1).getScore(1), delta); // rank 1 extreme
            Assert.assertEquals(2.0, ranked.get(2).getScore(1), delta); // rank 1 middle
            Assert.assertEquals(4.0, ranked.get(3).getScore(1), delta); // rank 1 crowded
            Assert.assertEquals(5.0, ranked.get(4).getScore(1), delta); // rank 2
            Assert.assertEquals(3.0, ranked.get(5).getScore(1), delta); // rank 3
        }

        [Test]
        public void testRankPriorityPareto()
        {

            // create individuals
            List<Individual> individuals = new ArrayList<Individual>();
            individuals.add(new ParetoIndividual(3.0, 6.0, 1.0, 0, 0, 1)); // rank 3
            individuals.add(new ParetoIndividual(1.0, 5.0, 2.0, 0, 0, 1)); // rank 1, dominates (1, 3)
            individuals.add(new ParetoIndividual(4.0, 4.0, 1.0, 0, 0, 1)); // rank 3
            individuals.add(new ParetoIndividual(1.0, 3.0, 2.0, 0, 0, 1)); // rank 2
            individuals.add(new ParetoIndividual(5.0, 2.0, 0.0, 0, 0, 1)); // rank 4
            individuals.add(new ParetoIndividual(9.0, 1.0, 1.0, 0, 0, 1)); // rank 3

            // rank
            Ranking ranking = new CrowdingDistanceRanking();
            List<Individual> ranked = ranking.rank(individuals);

            // assert highest score comes first
            final double delta = 0.1;
            Assert.assertEquals(5.0, ranked.get(0).getScore(1), delta); // rank 1
            Assert.assertEquals(3.0, ranked.get(1).getScore(1), delta); // rank 2
            Assert.assertEquals(1.0, ranked.get(2).getScore(1), delta); // rank 3 extreme
            Assert.assertEquals(6.0, ranked.get(3).getScore(1), delta); // rank 3 extreme
            Assert.assertEquals(4.0, ranked.get(4).getScore(1), delta); // rank 3 middle
            Assert.assertEquals(2.0, ranked.get(5).getScore(1), delta); // rank 4
        }

    }

}