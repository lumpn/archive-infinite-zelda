package de.lumpn.zelda.layout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import de.lumpn.util.list.ImmutableArrayList;
import de.lumpn.util.list.ImmutableList;

/**
 * Intermediate state of the search for a valid lowering of the schedule.
 * Consists of an intermediate grid, its cost, and a schedule of remaining
 * transitions to lower.
 */
public final class State {

	private static final int roomCost = 10;
	private static final int scriptCost = 1;

	/**
	 * Each room cost something, each door cost at least as much 
	 * as a transition in schedule!
	 */
	private static int calcCost(Grid grid) {
		int result = 0;
		result += grid.numRooms() * roomCost;
		result += grid.numScripts() * scriptCost;
		return result;
	}

	/**
	 * Estimate must be lower bound of final cost!
	 */
	private static int calcEstimate(Grid grid, ImmutableList<Transition> schedule) {

		// find set of scheduled rooms
		Set<RoomIdentifier> scheduledRooms = new HashSet<RoomIdentifier>();
		for (Transition transition : schedule) {
			scheduledRooms.add(transition.getSource());
			scheduledRooms.add(transition.getDestination());
		}

		// calculate number of missing rooms
		int missing = 0;
		for (RoomIdentifier room : scheduledRooms) {
			if (!grid.containsRoom(room)) missing++;
		}

		// calculate result
		int result = 0;
		result += missing * roomCost; // at least these cost will occur for creating rooms
		result += schedule.size() * scriptCost; // at least these scripts will be created
		return result;
	}

	public State(Grid grid, ImmutableList<Transition> schedule) {
		this.grid = grid;
		this.schedule = schedule;
		this.cost = calcCost(grid);
		this.estimate = calcEstimate(grid, schedule);
	}

	public Grid getGrid() {
		return grid;
	}

	public ImmutableList<Transition> getSchedule() {
		return schedule;
	}

	public int getCost() {
		return cost;
	}

	public int getEstimate() {
		return estimate;
	}

	public List<State> getNeighbors() {

		// implement transition
		List<State> result = new ArrayList<State>();
		for (Transition transition : schedule) {

			// source exists?
			RoomIdentifier source = transition.getSource();
			if (!grid.containsRoom(source)) continue;

			// remove from schedule
			List<Transition> tmpSchedule = schedule.toList();
			tmpSchedule.remove(transition);
			ImmutableList<Transition> nextSchedule = new ImmutableArrayList<Transition>(
					tmpSchedule);

			// implement transition
			List<Grid> implementations = grid.implement(transition);
			for (Grid implementation : implementations) {
				result.add(new State(implementation, nextSchedule));
			}
		}

		// extend room
		List<Grid> extensions = grid.extend();
		for (Grid extension : extensions) {
			result.add(new State(extension, schedule));
		}

		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + grid.hashCode();
		result = prime * result + schedule.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof State)) return false;
		State other = (State) obj;
		if (!grid.equals(other.grid)) return false;
		if (!schedule.equals(other.schedule)) return false;
		return true;
	}

	private final Grid grid;

	private final ImmutableList<Transition> schedule;

	private final int cost, estimate;
}
