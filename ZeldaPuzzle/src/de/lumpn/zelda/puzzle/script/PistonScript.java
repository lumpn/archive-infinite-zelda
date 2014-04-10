package de.lumpn.zelda.puzzle.script;

import de.lumpn.zelda.puzzle.DotTransitionBuilder;
import de.lumpn.zelda.puzzle.State;
import de.lumpn.zelda.puzzle.VariableIdentifier;
import de.lumpn.zelda.puzzle.ZeldaStates;

public final class PistonScript implements ZeldaScript {

	public PistonScript(VariableIdentifier colorSwitch, int pistonColor) {
		this.colorSwitch = colorSwitch;
		this.pistonColor = pistonColor;
	}

	@Override
	public State execute(State state) {

		// color correct?
		if (state.getOrDefault(colorSwitch, ZeldaStates.SWITCH_DEFAULT) == pistonColor) {
			return state; // pass
		}

		return null; // you shall not pass!
	}

	@Override
	public void express(DotTransitionBuilder builder) {
		switch (pistonColor) {
			case ZeldaStates.SWITCH_RED:
				builder.setLabel("red\npistons");
				break;
			case ZeldaStates.SWITCH_BLUE:
				builder.setLabel("blue\npistons");
				break;
			default:
				assert false;
		}
	}

	private final VariableIdentifier colorSwitch;

	private final int pistonColor;
}
