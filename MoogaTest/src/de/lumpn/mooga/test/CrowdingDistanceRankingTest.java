package de.lumpn.mooga.test;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import de.lumpn.mooga.Genome;
import de.lumpn.mooga.Individual;
import de.lumpn.mooga.ranking.CrowdingDistanceRanking;
import de.lumpn.mooga.ranking.Ranking;

public class CrowdingDistanceRankingTest {

	@Test
	public void testRankSimple() {

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

	@Test
	public void testRankPareto() {

		// create individuals
		List<Individual> individuals = new ArrayList<Individual>();
		individuals.add(new ParetoIndividual(3.0, 6.0)); // rank 1, dominates (1, 5)
		individuals.add(new ParetoIndividual(1.0, 5.0)); // rank 2, dominates (1, 3)
		individuals.add(new ParetoIndividual(4.0, 4.0)); // rank 1, dominates (1, 3)
		individuals.add(new ParetoIndividual(1.0, 3.0)); // rank 3
		individuals.add(new ParetoIndividual(5.0, 2.0)); // rank 1
		individuals.add(new ParetoIndividual(9.0, 1.0)); // rank 1

		// rank
		Ranking ranking = new CrowdingDistanceRanking();
		List<Individual> ranked = ranking.rank(individuals);

		// assert highest score comes first
		final double delta = 0.1;
		Assert.assertEquals(9.0, ranked.get(0).getScore(0), delta); // rank 1 extreme
		Assert.assertEquals(3.0, ranked.get(1).getScore(0), delta); // rank 1 extreme
		Assert.assertEquals(5.0, ranked.get(2).getScore(0), delta); // rank 1 middle
		Assert.assertEquals(4.0, ranked.get(3).getScore(0), delta); // rank 1 crowded
		Assert.assertEquals(1.0, ranked.get(4).getScore(0), delta); // rank 2
		Assert.assertEquals(1.0, ranked.get(5).getScore(0), delta); // rank 3
	}

	private static class SimpleIndividual implements Individual {

		public SimpleIndividual(double score) {
			this.score = score;
		}

		@Override
		public Genome getGenome() {
			return null; // not needed for test
		}

		@Override
		public int numAttributes() {
			return 1;
		}

		@Override
		public double getScore(int attribute) {
			return score;
		}

		@Override
		public String toString() {
			return String.format("SimpleIndividual [score=%s]", score);
		}

		private final double score;
	}

	private static class ParetoIndividual implements Individual {

		public ParetoIndividual(double score1, double score2) {
			this.score1 = score1;
			this.score2 = score2;
		}

		@Override
		public Genome getGenome() {
			return null; // not needed for test
		}

		@Override
		public int numAttributes() {
			return 2;
		}

		@Override
		public double getScore(int attribute) {
			switch (attribute) {
				case 0:
					return score1;
				case 1:
					return score2;
				default:
					Assert.fail();
					return 0.0;
			}
		}

		@Override
		public String toString() {
			return String.format("ParetoIndividual [score1=%s, score2=%s]", score1, score2);
		}

		private final double score1, score2;
	}
}
