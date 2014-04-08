package de.lumpn.zelda.puzzle;

import de.lumpn.zelda.puzzle.script.ZeldaScript;

public final class Transition {

	public Transition(Node destination, ZeldaScript script) {
		this.destination = destination;
		this.script = script;
	}

	public void express(DotTransitionBuilder builder) {
		destination.express(builder);
		script.express(builder);
	}

	private final Node destination;
	private final ZeldaScript script;
}
