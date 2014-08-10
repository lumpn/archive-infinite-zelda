package de.lumpn.zelda.puzzle.script;

import de.lumpn.zelda.puzzle.VariableLookup;
import de.lumpn.zelda.puzzle.ZeldaStates;

public final class ZeldaScripts {

	private static final String KEY_NAME = "small key";
	private static final String SWITCH_NAME = "red/blue switch";

	public static SmallKeyScript createKey(VariableLookup lookup) {
		return new SmallKeyScript(lookup.resolve(KEY_NAME), lookup);
	}

	public static DoorScript createDoor(VariableLookup lookup) {
		return new DoorScript(lookup.resolve(KEY_NAME), lookup);
	}

	public static ColorSwitchScript createSwitch(VariableLookup lookup) {
		return new ColorSwitchScript(lookup.resolve(SWITCH_NAME));
	}

	public static PistonScript createRedPiston(VariableLookup lookup) {
		return new PistonScript(lookup.resolve(SWITCH_NAME), ZeldaStates.SWITCH_RED);
	}

	public static PistonScript createBluePiston(VariableLookup lookup) {
		return new PistonScript(lookup.resolve(SWITCH_NAME), ZeldaStates.SWITCH_BLUE);
	}

}
