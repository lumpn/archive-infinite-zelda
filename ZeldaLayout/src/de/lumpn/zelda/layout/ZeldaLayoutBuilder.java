package de.lumpn.zelda.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import de.lumpn.util.list.ImmutableArrayList;

public class ZeldaLayoutBuilder {

	public static final int preId = -1;
	public static final int postId = -2;
	public static final int entranceId = 0;
	public static final int exitId = 1;

	private static final int epsilon = 0;

	public ZeldaLayoutBuilder() {
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
		Map<Position, Cell> initialCells = new HashMap<Position, Cell>();
		initialCells.put(new Position(0, 0, 0),
				new Cell(new Position(0, 0, 0), lookup.resolve(preId, "A"),
						ScriptIdentifier.EMPTY, ScriptIdentifier.BLOCKED, ScriptIdentifier.BLOCKED,
						ScriptIdentifier.BLOCKED));
		Grid initialGrid = new Grid(initialCells);
		State initialState = new State(initialGrid, new ImmutableArrayList<Transition>(
				schedule));

		Set<State> closedSet = new HashSet<State>();
		Set<State> openSet = new HashSet<State>();
		openSet.add(initialState);

		while (!openSet.isEmpty()) {
			State current = getMinimum(openSet);

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

	private static State getMinimum(Set<State> openSet) {
		State min = null;
		for (State state : openSet) {
			if (min == null || estimatedTotalCost(state) < estimatedTotalCost(min)) {
				min = state;
			}
		}
		return min;
	}

	private static double estimatedTotalCost(State state) {
		// static weighting relaxation on A*
		return state.getCost() + (1 + epsilon) * state.getEstimate();
	}

	private List<Transition> schedule = new ArrayList<Transition>();
	private RoomLookup lookup = new RoomLookup();
}
