package de.lumpn.zelda.mooga;

import java.util.Random;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;
import de.lumpn.zelda.puzzle.script.IdentityScript;

public final class OneWayGene extends ZeldaGene {

	public OneWayGene(ZeldaConfiguration configuration, Random random) {
		super(configuration);

		this.wayStart = randomLocation(random);
		this.wayEnd = differentLocation(wayStart, random);
	}

	public OneWayGene(ZeldaConfiguration configuration, int wayStart, int wayEnd) {
		super(configuration);
		this.wayStart = wayStart;
		this.wayEnd = wayEnd;
	}

	@Override
	public OneWayGene mutate(Random random) {
		return new OneWayGene(getConfiguration(), random);
	}

	@Override
	public void express(ZeldaPuzzleBuilder builder) {
		builder.addDirectedTransition(wayStart, wayEnd, IdentityScript.INSTANCE);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + wayEnd;
		result = prime * result + wayStart;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof OneWayGene)) return false;
		OneWayGene other = (OneWayGene) obj;
		if (wayEnd != other.wayEnd) return false;
		if (wayStart != other.wayStart) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%d->%d", wayStart, wayEnd);
	}

	// transition location of one-way
	private final int wayStart, wayEnd;
}
