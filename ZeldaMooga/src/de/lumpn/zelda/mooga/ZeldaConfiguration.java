package de.lumpn.zelda.mooga;

import java.util.Random;
import de.lumpn.util.MathUtils;

public final class ZeldaConfiguration {
	
	// constraints
	public final int maxKeyDoors = 4;

	// environment
	private final int numLocations = 10;
	private final int numItems = 2;

	// initialization
	private final double initialGeneMedian = 2;

	// complexification
	private final double mutationCoefficient = 0.10; // ~10% ([0%, 75%])
	private final double deletionCoefficient = 0.05; // ~5% ([0%, 35%])
	private final double insertionCoefficient = 0.10; // ~10% ([0%, 75%])

	public int randomLocation(Random random) {
		return random.nextInt(numLocations);
	}

	public int randomItem(Random random) {
		return random.nextInt(numItems);
	}

	public int calcNumInitialGenes(Random random) {
		return (int) (initialGeneMedian * MathUtils.randomWeibull2(random));
	}

	public int calcNumMutations(int size, Random random) {
		return (int) (size * mutationCoefficient * MathUtils.randomHalfGaussian(random));
	}

	public int calcNumDeletions(int size, Random random) {
		return (int) (size * deletionCoefficient * MathUtils.randomHalfGaussian(random));
	}

	public int calcNumInsertions(int size, Random random) {
		return (int) (size * insertionCoefficient * MathUtils.randomHalfGaussian(random));
	}
}
