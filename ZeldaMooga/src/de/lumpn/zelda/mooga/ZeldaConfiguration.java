package de.lumpn.zelda.mooga;

import java.util.Random;
import de.lumpn.util.MathUtils;

public final class ZeldaConfiguration {

	// environment
	private final int numNodes = 10;

	// initialization
	private final double initialKeyLockMedian = 5;
	private final double initialSwitchMedian = 2;
	private final double initialPistonMedian = 4;
	private final double initialGeneMedian = numNodes; // linear

	// complexification
	private final double mutationCoefficient = 0.10; // ~10% ([0%, 75%])
	private final double deletionCoefficient = 0.05; // ~5% ([0%, 35%])
	private final double insertionCoefficient = 0.10; // ~10% ([0%, 75%])

	public int randomNode(Random random) {
		return random.nextInt(numNodes);
	}

	public int calcNumInitialKeyLocks(Random random) {
		return (int) (initialKeyLockMedian * MathUtils.randomWeibull2(random));
	}

	public int calcNumInitialSwitches(Random random) {
		return (int) (initialSwitchMedian * MathUtils.randomWeibull2(random));
	}

	public int calcNumInitialPistons(Random random) {
		return (int) (initialPistonMedian * MathUtils.randomWeibull2(random));
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
