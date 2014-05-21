package de.lumpn.zelda.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import de.lumpn.util.list.ImmutableArrayList;

public class ZeldaLayoutBuilder {

	public static final int preId = -1;
	public static final int postId = -2;
	public static final int entranceId = 0;
	public static final int exitId = 1;

	private static final int epsilon = 0;

	public ZeldaLayoutBuilder(Boundary boundary, Random random) {
		this.boundary = boundary;
		this.random = random;

		schedule.add(new Transition(lookup.resolve(preId, "A"), lookup.resolve(entranceId),
				ScriptIdentifier.OPEN));
		schedule.add(new Transition(lookup.resolve(exitId), lookup.resolve(postId, "B"),
				ScriptIdentifier.OPEN));
	}

	public void addDirectedTransition(int start, int end, String script) {
		RoomIdentifier source = lookup.resolve(start);
		RoomIdentifier destination = lookup.resolve(end);
		schedule.add(new Transition(source, destination, new ScriptIdentifier(script)));
	}

	public void addUndirectedTransition(int loc1, int loc2, String script) {
		int min = Math.min(loc1, loc2);
		int max = Math.max(loc1, loc2);
		addDirectedTransition(min, max, script);
	}

	public void addScript(int location, String script) {
		schedule.add(new Transition(lookup.resolve(location), new ScriptIdentifier(script)));
	}

	public ZeldaLayout build() {
		// 1 -- 2
		// 0 -- 1

		// 2 -- 3
		// 0 -- 1
		// 0 -- 2
		// 1 -- 3

		// create entrance at 0,0
		// transition entrance to 1 (implicitly place)
		// transition 1 to 2 (implicitly create node 2)
		//

		// really simple algorithm (backtracking automata):
		// create entrance at predefined location
		// T: on script | on transition
		// - on script: find room, find place in room, place script, T
		// - find room: lookup room | create room
		// - lookup room: lookup, T
		// - create room: place room, T
		// - find place in room: find place | extend room
		// - extend room: place extension, T
		// bah!

		// options automata:
		// - find room: lookup room ?? create room
		// - create room: return complete list of available positions
		// - lookup room: return list of room positions

		// input
		// B
		// |
		// A - 0 - 1 - 4
		// | \ | /
		// 3 - 2

		// topological sort
		// A - 0 - 1 - 2 - 4
		// 3
		// B

		// sort transitions by destination then source
		// A - 0
		// 0 - 1
		// 0 - 2
		// 1 - 2
		// 0 - 3
		// 2 - 3
		// 1 - 4
		// 2 - 4
		// 1 - B

		// create entrance A at predefined position
		// transition A to 0 (implicit room creation)
		// transition 0 to 1 (implicit room creation)
		// transition 0 to 2 (implicit room creation)
		// transition 1 to 2 (connect existing rooms)

		// TODO sort schedule for faster convergence

		// TODO create pre-room A

		Position preRoomPosition = new Position(0, -1, 0);
		Map<Position, Cell> initialCells = new HashMap<Position, Cell>();
		initialCells
				.put(preRoomPosition, new Cell(preRoomPosition, lookup.resolve(preId, "A"),
						ScriptIdentifier.EMPTY, ScriptIdentifier.BLOCKED, ScriptIdentifier.BLOCKED,
						ScriptIdentifier.BLOCKED));
		Grid initialGrid = new Grid(boundary, initialCells);
		State initialState = new State(initialGrid, new ImmutableArrayList<Transition>(
				schedule));

		Set<State> closedSet = new HashSet<State>();
		Set<State> openSet = new HashSet<State>();
		openSet.add(initialState);

		while (!openSet.isEmpty()) {
			// choose one of the minimum states at random
			State current = getRandomMinimum(openSet, random);

			// goal reached?
			if (current.getSchedule().isEmpty()) {
				return new ZeldaLayout(current.getGrid());
			}

			openSet.remove(current);
			closedSet.add(current);

			List<State> neighbors = current.getNeighbors(); // magic happens here
			for (State next : neighbors) {
				if (closedSet.contains(next)) continue;
				openSet.add(next);
			}
		}

		return null;
	}

	private static State getRandomMinimum(Set<State> states, Random random) {
		State min = null;
		double minCost = 0;
		int minWeight = 0;
		for (State state : states) {
			double cost = estimateTotalCost(state);
			int weight = random.nextInt();
			if (min == null || cost < minCost || (cost == minCost && weight < minWeight)) {
				min = state;
				minCost = cost;
				minWeight = weight;
			}
		}
		return min;
	}

	private static List<State> getMinima(Set<State> states) {

		// first pass: find minimum cost
		double minCost = Double.MAX_VALUE;
		for (State state : states) {
			double cost = estimateTotalCost(state);
			if (cost < minCost) {
				minCost = cost;
			}
		}

		// second pass: select minima
		List<State> minima = new ArrayList<State>();
		for (State state : states) {
			double cost = estimateTotalCost(state);
			if (cost <= minCost) {
				minima.add(state);
			}
		}

		return minima;
	}

	private static double estimateTotalCost(State state) {
		// static weighting relaxation on A*
		return state.getCost() + (1 + epsilon) * state.getEstimate();
	}

	private Boundary boundary;
	private Random random;
	private RoomLookup lookup = new RoomLookup();
	private List<Transition> schedule = new ArrayList<Transition>();
}
