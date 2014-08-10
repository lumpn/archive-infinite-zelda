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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + switchLocation;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof SwitchGene)) return false;
		SwitchGene other = (SwitchGene) obj;
		if (switchLocation != other.switchLocation) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Switch: %d", switchLocation);
	}

	private final int switchLocation;
}
