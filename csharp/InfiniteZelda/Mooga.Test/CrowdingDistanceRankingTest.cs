using System.Collections.Generic;
using System.Linq;
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
            var individuals = new List<Individual>();
            individuals.Add(new SimpleIndividual(3.0));
            individuals.Add(new SimpleIndividual(1.0));
            individuals.Add(new SimpleIndividual(4.0));
            individuals.Add(new SimpleIndividual(1.0));
            individuals.Add(new SimpleIndividual(5.0));
            individuals.Add(new SimpleIndividual(9.0)); // dominating

            // rank
            var ranking = new CrowdingDistanceRanking();
            var ranked = ranking.Rank(individuals).ToList();

            // assert highest score comes first
            const double delta = 0.1;
            Assert.AreEqual(9.0, ranked[0].Score(0), delta);
            Assert.AreEqual(5.0, ranked[1].Score(0), delta);
            Assert.AreEqual(4.0, ranked[2].Score(0), delta);
            Assert.AreEqual(3.0, ranked[3].Score(0), delta);
            Assert.AreEqual(1.0, ranked[4].Score(0), delta);
            Assert.AreEqual(1.0, ranked[5].Score(0), delta);
        }

        [Test]
        public void testRankPareto()
        {

            // create individuals
            List<Individual> individuals = new List<Individual>();
            individuals.Add(new ParetoIndividual(3.0, 6.0, 0, 0, 0, 0)); // rank 1, dominates (1, 5)
            individuals.Add(new ParetoIndividual(1.0, 5.0, 0, 0, 0, 0)); // rank 2, dominates (1, 3)
            individuals.Add(new ParetoIndividual(4.0, 4.0, 0, 0, 0, 0)); // rank 1, dominates (1, 3)
            individuals.Add(new ParetoIndividual(1.0, 3.0, 0, 0, 0, 0)); // rank 3
            individuals.Add(new ParetoIndividual(5.0, 2.0, 0, 0, 0, 0)); // rank 1
            individuals.Add(new ParetoIndividual(9.0, 1.0, 0, 0, 0, 0)); // rank 1

            // rank
            Ranking ranking = new CrowdingDistanceRanking();
            List<Individual> ranked = ranking.Rank(individuals).ToList();

            // assert highest score comes first
            const double delta = 0.1;
            Assert.AreEqual(1.0, ranked[0].Score(1), delta); // rank 1 extreme
            Assert.AreEqual(6.0, ranked[1].Score(1), delta); // rank 1 extreme
            Assert.AreEqual(2.0, ranked[2].Score(1), delta); // rank 1 middle
            Assert.AreEqual(4.0, ranked[3].Score(1), delta); // rank 1 crowded
            Assert.AreEqual(5.0, ranked[4].Score(1), delta); // rank 2
            Assert.AreEqual(3.0, ranked[5].Score(1), delta); // rank 3
        }

        [Test]
        public void testRankPriorityPareto()
        {

            // create individuals
            List<Individual> individuals = new List<Individual>();
            individuals.Add(new ParetoIndividual(3.0, 6.0, 1.0, 0, 0, 1)); // rank 3
            individuals.Add(new ParetoIndividual(1.0, 5.0, 2.0, 0, 0, 1)); // rank 1, dominates (1, 3)
            individuals.Add(new ParetoIndividual(4.0, 4.0, 1.0, 0, 0, 1)); // rank 3
            individuals.Add(new ParetoIndividual(1.0, 3.0, 2.0, 0, 0, 1)); // rank 2
            individuals.Add(new ParetoIndividual(5.0, 2.0, 0.0, 0, 0, 1)); // rank 4
            individuals.Add(new ParetoIndividual(9.0, 1.0, 1.0, 0, 0, 1)); // rank 3

            // rank
            Ranking ranking = new CrowdingDistanceRanking();
            List<Individual> ranked = ranking.Rank(individuals).ToList();

            // assert highest score comes first
            const double delta = 0.1;
            Assert.AreEqual(5.0, ranked[0].Score(1), delta); // rank 1
            Assert.AreEqual(3.0, ranked[1].Score(1), delta); // rank 2
            Assert.AreEqual(1.0, ranked[2].Score(1), delta); // rank 3 extreme
            Assert.AreEqual(6.0, ranked[3].Score(1), delta); // rank 3 extreme
            Assert.AreEqual(4.0, ranked[4].Score(1), delta); // rank 3 middle
            Assert.AreEqual(2.0, ranked[5].Score(1), delta); // rank 4
        }

    }

}