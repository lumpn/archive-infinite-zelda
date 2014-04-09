package de.lumpn.zelda.mooga;

import java.util.Random;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;
import de.lumpn.zelda.puzzle.script.IdentityScript;

public final class TwoWayGene extends ZeldaGene {

	public TwoWayGene(ZeldaConfiguration configuration, Random random) {
		super(configuration);

		this.wayStart = randomLocation(random);
		this.wayEnd = differentLocation(wayStart, random);
	}

	public TwoWayGene(ZeldaConfiguration configuration, int wayStart, int wayEnd) {
		super(configuration);
		this.wayStart = wayStart;
		this.wayEnd = wayEnd;
	}

	@Override
	public TwoWayGene mutate(Random random) {
		// TODO: make less random than fully random constructor!
		int newStart = randomLocation(random);
		int newEnd = differentLocation(newStart, random);
		return new TwoWayGene(getConfiguration(), newStart, newEnd);
	}

	@Override
	public void express(ZeldaPuzzleBuilder builder) {
		builder.addUndirectedTransition(wayStart, wayEnd, IdentityScript.INSTANCE);
	}

	@Override
	public String toString() {
		return String.format("%d--%d", wayStart, wayEnd);
	}

	// transition location of one-way
	private final int wayStart, wayEnd;
}
