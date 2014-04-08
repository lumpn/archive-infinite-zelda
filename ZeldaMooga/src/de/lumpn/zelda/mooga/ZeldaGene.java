package de.lumpn.zelda.mooga;

import java.util.Random;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;

public abstract class ZeldaGene implements Gene {

	public ZeldaGene(ZeldaConfiguration configuration) {
		this.configuration = configuration;
	}

	public ZeldaConfiguration getConfiguration() {
		return configuration;
	}

	public int randomNode(Random random) {
		return configuration.randomNode(random);
	}

	public int differentNode(int forbiddenNode, Random random) {
		int node;
		do {
			node = configuration.randomNode(random);
		} while (node == forbiddenNode);
		return node;
	}

	public abstract void express(ZeldaPuzzleBuilder builder);

	private final ZeldaConfiguration configuration;
}
