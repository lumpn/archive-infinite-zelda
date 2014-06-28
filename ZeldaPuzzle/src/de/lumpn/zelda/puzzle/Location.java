package de.lumpn.zelda.puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<Transition> transitions() {
		return transitions;
	}

	public Step getStep(State state) {
		return steps.get(state);
	}

	public Step createStep(State state) {
		Step step = new Step(this, state);
		Step previous = steps.put(state, step);
		assert previous == null;
		return step;
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

	private final Map<State, Step> steps = new HashMap<State, Step>();
}
