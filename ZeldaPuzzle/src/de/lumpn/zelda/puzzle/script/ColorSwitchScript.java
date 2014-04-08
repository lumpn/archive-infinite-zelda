package de.lumpn.zelda.puzzle.script;

import de.lumpn.zelda.puzzle.DotTransitionBuilder;
import de.lumpn.zelda.puzzle.State;
import de.lumpn.zelda.puzzle.StateBuilder;
import de.lumpn.zelda.puzzle.VariableIdentifier;
import de.lumpn.zelda.puzzle.ZeldaStates;

public final class ColorSwitchScript implements ZeldaScript {

	public ColorSwitchScript(VariableIdentifier colorSwitch) {
		this.colorSwitch = colorSwitch;
	}

	@Override
	public State execute(State state) {

		int switchState = state.getOrDefault(colorSwitch, ZeldaStates.SWITCH_DEFAULT);

		// switch color
		StateBuilder mutable = state.mutable();
		switch (switchState) {
			case ZeldaStates.SWITCH_RED:
				mutable.set(colorSwitch, ZeldaStates.SWITCH_BLUE);
				break;
			case ZeldaStates.SWITCH_BLUE:
				mutable.set(colorSwitch, ZeldaStates.SWITCH_RED);
				break;
			default:
				assert false;
		}
		return mutable.state();
	}

	@Override
	public void express(DotTransitionBuilder builder) {
		builder.setLabel("switch");
	}

	private final VariableIdentifier colorSwitch;
}
