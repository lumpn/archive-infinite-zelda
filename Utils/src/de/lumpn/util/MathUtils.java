package de.lumpn.util;

import java.util.Random;

public final class MathUtils {

	/**
	 * Positive half of median-normalized Gaussian distribution
	 * 
	 * Min: 0.0
	 * Max: ~7.5 [inf]
	 * Mean: ~1.2
	 * Median: ~1.0
	 * StdDev: ~0.9
	 */
	public static double randomHalfGaussian(Random random) {
		return 1.5 * Math.abs(random.nextGaussian());
	}

	/**
	 * Positive half of Cauchy distribution
	 * 
	 * Min: 0.0
	 * Max: inf
	 * Mean: ~10 [inf]
	 * Median: 1
	 * StdDev: inf
	 */
	public static double randomHalfCauchy(Random random) {
		return Math.abs(randomCauchy(random));
	}

	/**
	 * Standard Cauchy distribution
	 */
	public static double randomCauchy(Random random) {
		double p = random.nextDouble();
		return Math.tan(Math.PI * (p - 0.5));
	}

	/**
	 * Exponential distribution
	 * 
	 * Min: 0.0
	 * Max: ~15 [inf]
	 * Mean: 1
	 * Median ~0.69 [ln(2)]
	 * StdDev: 1
	 * 
	 */
	public static double randomExponential(Random random) {
		double p = random.nextDouble();
		return -Math.log(1.0 - p);
	}

	/**
	 * Pareto distribution
	 * 
	 * Min: 1.0
	 * Max: inf
	 * Mean: alpha / (alpha-1) for alpha > 1
	 * Median: 2^(1/alpha)
	 * Variance: alpha / ((alpha-1)^2 * (alpha-2)) for alpha > 2
	 * 
	 * @param alpha Shape parameter. Tail index.
	 */
	public static double randomPareto(Random random, double alpha) {
		double p = random.nextDouble();
		return 1.0 / Math.pow(1.0 - p, 1.0 / alpha);
	}

	/**
	 * Weibull distribution
	 * 
	 * Min: 0.0
	 * Max: inf
	 * Mean: ~Median
	 * Median: (ln(2))^(1/k)
	 * StdDev: 1/k
	 * 
	 * @param k Shape parameter.
	 */
	public static double randomWeibull(Random random, double k) {
		double p = random.nextDouble();
		return Math.pow(-Math.log(1.0 - p), 1.0 / k);
	}

	/**
	 * Median-normalized Weibull distribution with k=2
	 * 
	 * Min: 0.0
	 * Max: ~4.7 [inf]
	 * Mean: 1.1
	 * Median: 1.0
	 * StdDev: 0.6
	 */
	public static double randomWeibull2(Random random) {
		return 1.2 * randomWeibull(random, 2);
	}
}
