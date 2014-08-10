package de.lumpn.zelda.mooga;

import java.util.Random;
import de.lumpn.zelda.puzzle.VariableLookup;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;
import de.lumpn.zelda.puzzle.script.IdentityScript;
import de.lumpn.zelda.puzzle.script.ZeldaScript;
import de.lumpn.zelda.puzzle.script.ZeldaScripts;

public final class ObstacleGene extends ZeldaGene {

	public ObstacleGene(ZeldaConfiguration configuration, Random random) {
		super(configuration);
		this.requiredItem = configuration.randomItem(random);
		int a = randomLocation(random);
		int b = differentLocation(a, random);
		this.obstacleStart = Math.min(a, b);
		this.obstacleEnd = Math.max(a, b);
	}

	@Override
	public ObstacleGene mutate(Random random) {
		return new ObstacleGene(getConfiguration(), random);
	}

	@Override
	public void express(ZeldaPuzzleBuilder builder) {
		VariableLookup lookup = builder.lookup();
		builder.addUndirectedTransition(obstacleStart, obstacleEnd, ZeldaScripts.createObstacle(requiredItem, lookup));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + requiredItem;
		result = prime * result + obstacleStart;
		result = prime * result + obstacleEnd;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof ObstacleGene)) return false;
		ObstacleGene other = (ObstacleGene) obj;
		if (requiredItem != other.requiredItem) return false;
		if (obstacleStart != other.obstacleStart) return false;
		if (obstacleEnd != other.obstacleEnd) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("obstacle %s %d--%d", requiredItem, obstacleStart, obstacleEnd);
	}

	/**
	 * item to overcome obstacle
	 */
	private final int requiredItem;

	/**
	 * obstacle transition location
	 */
	private final int obstacleStart, obstacleEnd;
}
