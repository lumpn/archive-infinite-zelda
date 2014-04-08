package de.lumpn.zelda.mooga;

import java.util.Random;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;
import de.lumpn.zelda.puzzle.script.IdentityScript;

public final class OneWayGene extends ZeldaGene {

	public OneWayGene(ZeldaConfiguration configuration, Random random) {
		super(configuration);

		this.wayStart = randomNode(random);
		this.wayEnd = differentNode(wayStart, random);
	}

	public OneWayGene(ZeldaConfiguration configuration, int wayStart, int wayEnd) {
		super(configuration);
		this.wayStart = wayStart;
		this.wayEnd = wayEnd;
	}

	@Override
	public OneWayGene mutate(Random random) {
		// TODO: make less random than fully random constructor!
		int newStart = randomNode(random);
		int newEnd = differentNode(newStart, random);
		return new OneWayGene(getConfiguration(), newStart, newEnd);
	}

	@Override
	public void express(ZeldaPuzzleBuilder builder) {
		builder.addDirectedTransition(wayStart, wayEnd, IdentityScript.INSTANCE);
	}

	@Override
	public String toString() {
		return String.format("%d->%d", wayStart, wayEnd);
	}

	// transition location of one-way
	private final int wayStart, wayEnd;
}
