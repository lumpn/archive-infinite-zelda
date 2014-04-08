package de.lumpn.zelda.puzzle.script;

import de.lumpn.zelda.puzzle.DotTransitionBuilder;
import de.lumpn.zelda.puzzle.State;
import de.lumpn.zelda.puzzle.StateBuilder;
import de.lumpn.zelda.puzzle.VariableIdentifier;
import de.lumpn.zelda.puzzle.VariableLookup;
import de.lumpn.zelda.puzzle.ZeldaStates;

public final class SmallKeyScript implements ZeldaScript {

	public SmallKeyScript(VariableIdentifier key, VariableLookup lookup) {
		this.key = key;
		this.keyState = lookup.unique();
	}

	@Override
	public State execute(State state) {

		// already taken?
		if (state.getOrDefault(keyState, ZeldaStates.KEY_AVAILABLE) == ZeldaStates.KEY_TAKEN) {
			return state;
		}

		// acquire key
		int numKeys = state.getOrDefault(key, 0);
		StateBuilder mutable = state.mutable();
		mutable.set(key, numKeys + 1);
		mutable.set(keyState, ZeldaStates.KEY_TAKEN);
		return mutable.state();
	}

	@Override
	public void express(DotTransitionBuilder builder) {
		builder.setLabel("key");
	}

	private final VariableIdentifier key, keyState;
}
