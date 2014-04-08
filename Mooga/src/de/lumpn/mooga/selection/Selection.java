package de.lumpn.mooga.selection;

import java.util.List;
import de.lumpn.mooga.Individual;

public interface Selection {

	/**
	 * Selects a single individual
	 * 
	 * @param individuals Candidates
	 * @return Selected candidate
	 */
	Individual select(List<Individual> individuals);

	/**
	 * Selects a bunch of mutually different individuals
	 * 
	 * @param individuals Candidates
	 * @param count Number of candidates to select
	 * @return Selected candidates
	 */
	List<Individual> select(List<Individual> individuals, int count);
}
