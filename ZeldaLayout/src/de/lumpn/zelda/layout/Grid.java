package de.lumpn.zelda.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.lumpn.util.Pair;

/**
 * Collection of cells representing the state of the grid
 */
public final class Grid {

	public Grid() {
		this.cells = new HashMap<Position, Cell>();
	}

	private Grid(Map<Position, Cell> cells) {
		this.cells = cells;
	}

	public boolean containsRoom(RoomIdentifier room) {
		for (Cell cell : cells.values()) {
			if (cell.hasRoom(room)) return true;
		}
		return false;
	}

	public int numRooms() {
		return cells.size();
	}

	public int numScripts() {
		int result = 0;
		for (Cell cell : cells.values()) {
			result += cell.numScripts();
		}
		return result;
	}

	public List<Grid> implement(Transition transition) {
		assert containsRoom(transition.getSource());

		// implement script
		if (transition.isLocal()) {
			return implementLocal(transition);
		}

		// connect rooms if both source and destination exist
		RoomIdentifier destination = transition.getDestination();
		if (containsRoom(destination)) {
			return implementConnection(transition);
		}

		// implement extension to new room
		return implementExtension(transition);
	}

	private List<Grid> implementConnection(Transition transition) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Grid> implementLocal(Transition transition) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Grid> implementExtension(Transition transition) {

		RoomIdentifier source = transition.getSource();
		RoomIdentifier destination = transition.getDestination();
		ScriptIdentifier script = transition.getScript();

		// implement
		List<Grid> result = new ArrayList<Grid>();
		for (Cell cell : cells.values()) {

			// skip non-source cells
			if (!cell.hasRoom(source)) continue;

			// implement transition in each direction
			Position position = cell.getPosition();
			List<Position> neighbors = position.getNeighbors();
			for (Position neighbor : neighbors) {

				// skip occupied positions
				if (cells.containsKey(neighbor)) continue;

				// implement transition, link cell and extension
				Pair<Cell> extension = cell.extend(neighbor, destination, script);

				// create new grid
				Map<Position, Cell> nextCells = new HashMap<Position, Cell>(cells);
				nextCells.put(extension.first().getPosition(), extension.first());
				nextCells.put(extension.second().getPosition(), extension.second());

				// add to result
				result.add(new Grid(nextCells));
			}
		}

		return result;
	}

	public List<Grid> extend() {

		// extend each cell
		List<Grid> result = new ArrayList<Grid>();
		for (Cell cell : cells.values()) {

			// extend in each direction
			Position position = cell.getPosition();
			List<Position> neighbors = position.getNeighbors();
			for (Position neighbor : neighbors) {

				// skip occupied positions
				if (cells.containsKey(neighbor)) continue;

				// create extension, link cell and extension
				Pair<Cell> extension = cell.extend(neighbor);

				// create new grid
				Map<Position, Cell> nextCells = new HashMap<Position, Cell>(cells);
				nextCells.put(extension.first().getPosition(), extension.first());
				nextCells.put(extension.second().getPosition(), extension.second());

				// add to result
				result.add(new Grid(nextCells));
			}
		}

		return result;
	}

	@Override
	public int hashCode() {
		return cells.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Grid)) return false;
		Grid other = (Grid) obj;
		return cells.equals(other.cells);
	}

	private final Map<Position, Cell> cells;
}
