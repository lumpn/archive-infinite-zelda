package de.lumpn.zelda.puzzle;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import de.lumpn.util.CollectionUtils;

public final class ZeldaPuzzle {

	public static final int entranceId = 0;
	public static final int exitId = 1;

	public ZeldaPuzzle(Map<Integer, Location> locations) {
		this.locations = CollectionUtils.immutable(locations);
	}

	public void crawl(State initialState) {

		// find entrance and exit
		Location entrance = locations.get(entranceId);
		if (entrance == null) {
			return; // invalid puzzle
		}

		// crawl!
		Step initialStep = entrance.createStep(initialState);
		Queue<Step> queue = new ArrayDeque<Step>();

		// BFS
		queue.add(initialStep);
		while (!queue.isEmpty()) {
			Step step = queue.remove();

			Location location = step.location();
			State state = step.state();
			for (Transition transition : location.transitions()) {

				// execute transition
				Location nextLocation = transition.destination();
				State nextState = transition.execute(state);
				if (nextState == null) continue;

				// build next step
				Step nextStep = nextLocation.getStep(nextState);
				if (nextStep == null) {

					// location reached with new state -> enqueue
					nextStep = nextLocation.createStep(nextState);
					queue.add(nextStep);
				}

				// link steps
				nextStep.addPredecessor(step);
				step.addSuccessor(nextStep);
			}
		}
	}

	public Step getStep(int locationId, State state) {
		Location location = locations.get(locationId);
		if (location == null) return null;
		return location.getStep(state);
	}

	public void express(DotBuilder builder) {
		builder.begin();
		for (Location location : locations.values()) {
			location.express(builder);
		}
		builder.end();
	}

	private final Map<Integer, Location> locations;
}
