package de.lumpn.zelda.puzzle;

import java.util.Map;
import de.lumpn.util.CollectionUtils;

/**
 * Immutable state
 */
public final class State {

	public State(Map<VariableIdentifier, Integer> variables) {
		this.variables = CollectionUtils.immutable(variables);
	}

	public int getOrDefault(VariableIdentifier identifier, int defaultValue) {
		return StateUtils.getOrDefault(variables, identifier, defaultValue);
	}

	public StateBuilder mutable() {
		return new StateBuilder(variables);
	}

	@Override
	public int hashCode() {
		return variables.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		State other = (State) obj;
		return variables.equals(other.variables);
	}

	private final Map<VariableIdentifier, Integer> variables;
}
