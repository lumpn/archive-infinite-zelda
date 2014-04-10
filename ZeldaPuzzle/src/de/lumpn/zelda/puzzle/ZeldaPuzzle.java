package de.lumpn.zelda.puzzle;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import de.lumpn.report.ProgressConsumer;
import de.lumpn.util.CollectionUtils;

public final class ZeldaPuzzle {

	public static final int entranceId = 0;
	public static final int exitId = 1;

	public ZeldaPuzzle(Map<Integer, Location> locations) {
		this.locations = CollectionUtils.immutable(locations);
	}

	public void crawl(State initialState, ProgressConsumer progressConsumer) {

		// find entrance
		Location entrance = locations.get(entranceId);
		if (entrance == null) {
			return; // invalid puzzle
		}

		// initialize first step
		Step initialStep = entrance.createStep(initialState);
		initialStep.setDistanceFromEntry(0);

		// forward crawl (keep track of states at exit location for backward pass)
		List<Step> terminalSteps = forwardPass(initialStep, progressConsumer);

		// initialize distance from exit
		for (Step step : terminalSteps) {
			step.setDistanceFromExit(0);
		}

		// backward crawl
		backwardPass(terminalSteps, progressConsumer);
	}

	private static List<Step> forwardPass(Step initialStep,
			ProgressConsumer progressConsumer) {

		// keep track of terminals
		List<Step> terminalSteps = new ArrayList<Step>();

		// initialize BFS
		Queue<Step> queue = new ArrayDeque<Step>();
		queue.add(initialStep);
		int visitedSteps = 0;
		int totalSteps = 1;

		// crawl!
		progressConsumer.reset("forward pass");
		progressConsumer.set(visitedSteps, totalSteps);
		while (!queue.isEmpty()) {

			// fetch step
			Step step = queue.remove();
			visitedSteps++;
			progressConsumer.set(visitedSteps, totalSteps);

			// keep track of terminals
			Location location = step.location();
			if (location.id() == exitId) {
				terminalSteps.add(step);
			}

			// try every transition
			State state = step.state();
			int distanceFromEntry = step.distanceFromEntry();
			for (Transition transition : location.transitions()) {

				// execute transition
				Location nextLocation = transition.destination();
				State nextState = transition.execute(state);
				if (nextState == null) continue; // transition impossible

				// find reached step
				Step nextStep = nextLocation.getStep(nextState);
				if (nextStep == null) {

					// location reached with new state -> enqueue
					nextStep = nextLocation.createStep(nextState);
					step.setDistanceFromEntry(distanceFromEntry + 1);
					queue.add(nextStep);
					totalSteps++;
				}

				// connect steps
				nextStep.addPredecessor(step);
				step.addSuccessor(nextStep);
			}
		}

		return terminalSteps;
	}

	private static void backwardPass(List<Step> terminalSteps,
			ProgressConsumer progressConsumer) {

		// initialize BFS
		Queue<Step> queue = new ArrayDeque<Step>(terminalSteps);
		Set<Step> visited = new HashSet<Step>();
		int visitedSteps = 0;
		int totalSteps = terminalSteps.size();

		// crawl
		progressConsumer.reset("backward pass");
		progressConsumer.set(visitedSteps, totalSteps);
		while (!queue.isEmpty()) {

			// fetch step
			Step step = queue.remove();
			visitedSteps++;
			progressConsumer.set(visitedSteps, totalSteps);

			// try every predecessor
			int distanceFromExit = step.distanceFromExit();
			for (Step nextStep : step.precedessors()) {
				if (visited.add(nextStep)) {

					// unseen step reached -> enqueue
					nextStep.setDistanceFromExit(distanceFromExit + 1);
					queue.add(nextStep);
					totalSteps++;
				}
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
