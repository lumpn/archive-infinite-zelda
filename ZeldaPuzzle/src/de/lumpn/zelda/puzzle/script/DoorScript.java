package de.lumpn.zelda.puzzle.script;

import de.lumpn.zelda.puzzle.DotTransitionBuilder;
import de.lumpn.zelda.puzzle.State;
import de.lumpn.zelda.puzzle.StateBuilder;
import de.lumpn.zelda.puzzle.VariableIdentifier;
import de.lumpn.zelda.puzzle.VariableLookup;
import de.lumpn.zelda.puzzle.ZeldaStates;

public final class DoorScript implements ZeldaScript {

	public DoorScript(VariableIdentifier keyIdentifier, VariableLookup resolution) {
		this.keyIdentifier = keyIdentifier;
		this.lockIdentifier = resolution.unique();
	}

	@Override
	public State execute(State state) {

		// already unlocked?
		if (state.getOrDefault(lockIdentifier, ZeldaStates.DOOR_LOCKED) == ZeldaStates.DOOR_UNLOCKED) {
			return state; // pass
		}

		// has key?
		int numKeys = state.getOrDefault(keyIdentifier, 0);
		if (numKeys == 0) {
			return null; // you shall not pass!
		}

		// consume key & unlock
		StateBuilder mutable = state.mutable();
		mutable.set(keyIdentifier, numKeys - 1);
		mutable.set(lockIdentifier, ZeldaStates.DOOR_UNLOCKED);
		return mutable.state();
	}

	@Override
	public void express(DotTransitionBuilder builder) {
		builder.setLabel("door");
	}

	private final VariableIdentifier keyIdentifier, lockIdentifier;
}
