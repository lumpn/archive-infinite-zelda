package de.lumpn.zelda.mooga;

import java.util.Random;
import de.lumpn.zelda.puzzle.VariableLookup;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;
import de.lumpn.zelda.puzzle.script.ZeldaScripts;

public final class SwitchGene extends ZeldaGene {

	public SwitchGene(ZeldaConfiguration configuration, Random random) {
		super(configuration);
		this.switchLocation = randomLocation(random);
	}

	@Override
	public SwitchGene mutate(Random random) {
		return new SwitchGene(getConfiguration(), random);
	}

	@Override
	public void express(ZeldaPuzzleBuilder builder) {
		VariableLookup lookup = builder.lookup();
		builder.addScript(switchLocation, ZeldaScripts.createSwitch(lookup));
	}

	@Override
	public String toString() {
		return String.format("Switch: %d", switchLocation);
	}

	private final int switchLocation;
}
