package de.lumpn.zelda.puzzle;

import java.util.HashMap;
import java.util.Map;

public final class VariableLookup {

	public VariableIdentifier unique() {
		return new VariableIdentifier(serial++);
	}

	public VariableIdentifier resolve(String name) {
		VariableIdentifier identifier = lookup.get(name);
		if (identifier == null) {
			identifier = unique();
			lookup.put(name, identifier);
		}
		return identifier;
	}

	private int serial = 0;

	private final Map<String, VariableIdentifier> lookup = new HashMap<String, VariableIdentifier>();
}
