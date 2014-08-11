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
	 * Priority of specific attribute for multi stage multi object optimization
	 */
	public int getPriority(int attribute);

	/**
	 * Score of specific attribute. Make sure getting these values is fast (use cache)!
	 */
	public double getScore(int attribute);

	/**
	 * Wether or not this individual can be considered best
	 */
	public boolean isElite();

	@Override
	public int hashCode();

	@Override
	public boolean equals(Object obj);
}
