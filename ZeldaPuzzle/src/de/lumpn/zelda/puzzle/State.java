package de.lumpn.zelda.puzzle;

import java.util.Map;
import de.lumpn.util.CollectionUtils;

/**
 * Immutable state
 */
public final class State {

	public static final State NULL = null;

	public State(Map<VariableIdentifier, Integer> variables) {
		this.variables = CollectionUtils.immutable(variables);
	}

	public int getOrDefault(VariableIdentifier identifier, int defaultValue) {
		return StateUtils.getOrDefault(variables, identifier, defaultValue);
	}

	public StateBuilder mutable() {
		return new StateBuilder(variables);
	}

	private final Map<VariableIdentifier, Integer> variables;
}
