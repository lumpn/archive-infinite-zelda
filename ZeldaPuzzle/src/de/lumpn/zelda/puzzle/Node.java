package de.lumpn.zelda.puzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * Mutable node
 */
public class Node {

	public Node(int id) {
		this.id = id;
	}

	public void addTransition(Transition transition) {
		transitions.add(transition);
	}

	public void express(DotBuilder builder) {
		builder.addNode(id);
		for (Transition transition : transitions) {
			DotTransitionBuilder transitionBuilder = new DotTransitionBuilder();
			transitionBuilder.setStart(id);
			transition.express(transitionBuilder);
			transitionBuilder.express(builder);
		}
	}

	public void express(DotTransitionBuilder builder) {
		builder.setDestination(id);
	}

	private final int id;

	private final List<Transition> transitions = new ArrayList<Transition>();
}
