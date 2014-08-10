package de.lumpn.zelda.mooga;

import java.util.Random;
import de.lumpn.zelda.puzzle.VariableLookup;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;
import de.lumpn.zelda.puzzle.script.IdentityScript;
import de.lumpn.zelda.puzzle.script.ZeldaScript;
import de.lumpn.zelda.puzzle.script.ZeldaScripts;

public final class PistonGene extends ZeldaGene {

	private static enum Color {
		COLOR_RED, COLOR_BLUE,
	}

	private static Color randomColor(Random random) {
		if (random.nextBoolean()) {
			return Color.COLOR_RED;
		}
		return Color.COLOR_BLUE;
	}

	public PistonGene(ZeldaConfiguration configuration, Random random) {
		super(configuration);
		this.color = randomColor(random);
		this.pistonStart = randomLocation(random);
		this.pistonEnd = differentLocation(pistonStart, random);
	}

	@Override
	public PistonGene mutate(Random random) {
		return new PistonGene(getConfiguration(), random);
	}

	@Override
	public void express(ZeldaPuzzleBuilder builder) {
		VariableLookup lookup = builder.lookup();
		ZeldaScript script;
		switch (color) {
			case COLOR_RED:
				script = ZeldaScripts.createRedPiston(lookup);
				break;
			case COLOR_BLUE:
				script = ZeldaScripts.createBluePiston(lookup);
				break;
			default:
				script = IdentityScript.INSTANCE;
				assert false;
		}
		builder.addUndirectedTransition(pistonStart, pistonEnd, script);
	}

	@Override
	public String toString() {
		return String.format("Piston: %d--%d", pistonStart, pistonEnd);
	}

	/**
	 * Piston color
	 */
	private final Color color;

	/**
	 * Piston transition location
	 */
	private final int pistonStart, pistonEnd;
}
