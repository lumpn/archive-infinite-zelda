package de.lumpn.mooga.ranking;

import java.util.List;
import de.lumpn.mooga.Individual;

public interface Ranking {

	/**
	 * Rank individuals. Highest score comes first.
	 * 
	 * @param individuals Individuals to rank
	 * @return Individuals sorted by rank
	 */
	public List<Individual> rank(List<Individual> individuals);
}
