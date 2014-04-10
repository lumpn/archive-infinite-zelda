package de.lumpn.zelda.puzzle;

import java.util.ArrayList;
import java.util.List;

public final class Step {

	public Step(Location location, State state) {
		this.location = location;
		this.state = state;
	}

	public Location location() {
		return location;
	}

	public State state() {
		return state;
	}

	public Iterable<Step> precedessors() {
		return predecessors;
	}

	public Iterable<Step> successors() {
		return successors;
	}

	public void addPredecessor(Step predecessor) {
		predecessors.add(predecessor);
	}

	public void addSuccessor(Step successor) {
		successors.add(successor);
	}

	private final Location location;
	private final State state;

	private final List<Step> predecessors = new ArrayList<Step>();
	private final List<Step> successors = new ArrayList<Step>();
}
