package de.lumpn.mooga;

public interface Individual {

	/**
	 * Returns the individual's genome.
	 */
	public Genome getGenome();

	/**
	 * Number of attributes evaluated.
	 */
	public int numAttributes();

	/**
	 * Score of specific attribute.
	 */
	public double getScore(int attribute);
}
