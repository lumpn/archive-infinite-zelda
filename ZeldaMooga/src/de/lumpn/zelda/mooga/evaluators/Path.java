package de.lumpn.zelda.mooga.evaluators;

import de.lumpn.zelda.puzzle.Step;

public final class Path {

	public Path(Path prefix, Step step) {
		this.prefix = prefix;
		this.step = step;
	}

	public Step step() {
		return step;
	}

	public boolean endsAtLocation(int location) {
		return step.location().id() == location;
	}

	public int length() {
		if (prefix == null) return 0;
		return prefix.length() + 1;
	}

	private final Path prefix;
	private final Step step;
}
