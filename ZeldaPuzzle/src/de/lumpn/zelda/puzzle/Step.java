package de.lumpn.zelda.puzzle;

import java.util.ArrayList;
import java.util.List;

public final class Step {

	public Step(Location location, State state) {
		this.location = location;
		this.state = state;
		this.distanceFromEntry = -1;
		this.distanceFromExit = -1;
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

	public int distanceFromEntry() {
		return distanceFromEntry;
	}

	public int distanceFromExit() {
		return distanceFromExit;
	}

	public void addPredecessor(Step predecessor) {
		predecessors.add(predecessor);
	}

	public void addSuccessor(Step successor) {
		successors.add(successor);
	}

	public void setDistanceFromEntry(int distance) {
		distanceFromEntry = distance;
	}

	public void setDistanceFromExit(int distance) {
		distanceFromExit = distance;
	}

	private final Location location;
	private final State state;

	private final List<Step> predecessors = new ArrayList<Step>();
	private final List<Step> successors = new ArrayList<Step>();

	private int distanceFromEntry, distanceFromExit;

}
