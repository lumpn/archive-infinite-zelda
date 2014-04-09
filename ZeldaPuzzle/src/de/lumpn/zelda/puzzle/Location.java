package de.lumpn.zelda.puzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * Mutable location
 */
public class Location {

	public Location(int id) {
		this.id = id;
	}

	public int id() {
		return id;
	}

	public void addTransition(Transition transition) {
		transitions.add(transition);
	}

	public void express(DotBuilder builder) {
		builder.addNode(id);
		for (Transition transition : transitions) {
			DotTransitionBuilder transitionBuilder = new DotTransitionBuilder();
			transition.express(transitionBuilder);
			transitionBuilder.express(builder);
		}
	}

	private final int id;

	private final List<Transition> transitions = new ArrayList<Transition>();
}
