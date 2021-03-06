package de.lumpn.zelda.mooga;

import java.util.List;
import java.util.Random;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;

public abstract class ZeldaGene implements Gene {

	public ZeldaGene(ZeldaConfiguration configuration) {
		this.configuration = configuration;
	}

	public ZeldaConfiguration getConfiguration() {
		return configuration;
	}

	public int randomLocation(Random random) {
		return configuration.randomLocation(random);
	}

	public int differentLocation(int forbidden, Random random) {
		int location;
		do {
			location = configuration.randomLocation(random);
		} while (location == forbidden);
		return location;
	}

	public abstract int countErrors(List<ZeldaGene> genes);

	public abstract void express(ZeldaPuzzleBuilder builder);

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);

	private final ZeldaConfiguration configuration;
}
