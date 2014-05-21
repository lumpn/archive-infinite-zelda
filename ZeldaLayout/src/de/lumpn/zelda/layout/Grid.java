package de.lumpn.zelda.layout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import de.lumpn.util.Pair;
import de.lumpn.util.map.ImmutableHashMap;
import de.lumpn.util.map.ImmutableMap;

/**
 * Collection of cells representing the state of the grid
 */
public final class Grid {

	public Grid(Boundary boundary, Map<Position, Cell> cells) {
		this.boundary = boundary;
		this.cells = new ImmutableHashMap<Position, Cell>(cells);
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
		RoomIdentifier source = transition.getSource();
		RoomIdentifier destination = transition.getDestination();

		// see if source or destination exists
		if (!containsRoom(source) && !containsRoom(destination)) {
			// both rooms do not exist -> transition can not be implemented yet
			return Collections.emptyList();
		}

		// implement script
		if (transition.isLocal()) {
			return implementLocal(transition);
		}

		// connect rooms if both source and destination exist
		if (containsRoom(source) && containsRoom(destination)) {
			return implementConnection(transition);
		}

		// implement extension to new room
		// HACK: swap source/destination if source is missing
		// TODO: fix this hack and make directed transitions printable
		Transition extension = transition;
		if (!containsRoom(source)) {
			extension = new Transition(destination, source, transition.getScript());
		}
		return implementTransition(extension);
	}

	private List<Grid> implementConnection(Transition transition) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	private List<Grid> implementLocal(Transition transition) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	private List<Grid> implementTransition(Transition transition) {

		RoomIdentifier source = transition.getSource();
		RoomIdentifier destination = transition.getDestination();
		ScriptIdentifier script = transition.getScript();

		// implement
		List<Grid> result = new ArrayList<Grid>();
		for (Cell cell : getCells(source)) {

			// implement transition in each direction
			Position position = cell.getPosition();
			for (Position neighbor : getNeighbors(position)) {

				// implement transition, link cell and extension
				Pair<Cell> extension = cell.extend(neighbor, destination, script);

				// create new grid
				Map<Position, Cell> nextCells = cells.toMap();
				nextCells.put(extension.first().getPosition(), extension.first());
				nextCells.put(extension.second().getPosition(), extension.second());

				// add to result
				result.add(new Grid(boundary, nextCells));
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
			for (Position neighbor : getNeighbors(position)) {

				// create extension, link cell and extension
				Pair<Cell> extension = cell.extend(neighbor);

				// create new grid
				Map<Position, Cell> nextCells = cells.toMap();
				nextCells.put(extension.first().getPosition(), extension.first());
				nextCells.put(extension.second().getPosition(), extension.second());

				// add to result
				result.add(new Grid(boundary, nextCells));
			}
		}

		return result;
	}

	private Collection<Cell> getCells(RoomIdentifier room) {
		List<Cell> result = new ArrayList<Cell>();
		for (Cell cell : cells.values()) {
			// skip wrong rooms
			if (!cell.hasRoom(room)) continue;
			result.add(cell);
		}
		return result;
	}

	private Collection<Position> getNeighbors(Position position) {
		List<Position> result = new ArrayList<Position>();
		for (Position neighbor : position.getNeighbors()) {

			// skip out of bounds positions
			if (!boundary.contains(neighbor)) continue;

			// skip occupied positions
			if (cells.containsKey(neighbor)) continue;

			// valid neighbor
			result.add(neighbor);
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

	@Override
	public String toString() {
		int minX = 0;
		int maxX = 0;
		int minY = 0;
		int maxY = 0;
		for (Cell cell : cells.values()) {
			Position position = cell.getPosition();
			minX = Math.min(minX, position.getX());
			maxX = Math.max(maxX, position.getX());
			minY = Math.min(minY, position.getY());
			maxY = Math.max(maxY, position.getY());
		}

		// for placing north/east walls
		minX--;
		minY--;

		// 0 +#--- ---+
		// 1 |# . . . |
		// 2 .# . . . .
		// 3 |# . . . |
		// . ##########
		// 4 +#--- ---+
		// 5

		// TODO refactor big time. this code is unreadable!
		StringBuilder result = new StringBuilder();
		int z = 0; // TODO iterate over z axis too
		for (int y = maxY; y >= minY; y--) {
			for (int line = 0; line < 4; line++) {
				for (int x = minX; x <= maxX; x++) {
					Position position = new Position(x, y, z);
					Cell cell = cells.get(position);
					if (cell == null) {
						Position north = new Position(x, y + 1, z);
						Position east = new Position(x + 1, y, z);
						if (cells.containsKey(north) && (line == 0)) {
							result.append("-------+");
						} else if (cells.containsKey(east)) {
							if (line == 0) {
								result.append("       +");
							} else {
								result.append("       |");
							}
						} else {
							result.append("        ");
						}
					} else {
						switch (line) {
							case 0:
								result.append("---");
								result.append(cell.getNorthScript().toString());
								result.append("---+");
								break;
							case 1:
								result.append("     ");
								result.append(cell.getCenterScript().toString());
								result.append(" |");
								break;
							case 2:
								result.append("   ");
								result.append(cell.getRoom().toString());
								result.append("   ");
								result.append(cell.getEastScript().toString());
								break;
							case 3:
								result.append("       |");
								break;
							default:
								assert false;
								break;
						}
					}
				}
				result.append("\n");
			}
		}

		return result.toString();
	}

	private final Boundary boundary;
	private final ImmutableMap<Position, Cell> cells;
}
