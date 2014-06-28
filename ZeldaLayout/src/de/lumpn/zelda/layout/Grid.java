package de.lumpn.zelda.layout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import de.lumpn.util.Pair;
import de.lumpn.util.map.ImmutableHashMap;
import de.lumpn.util.map.ImmutableMap;

/**
 * Collection of cells representing the state of the grid
 */
public final class Grid {

	// TODO extract mutable grid builder for transition implementations
	private static void setCell(Map<Position, Cell> cells, Cell cell) {
		cells.put(cell.getPosition(), cell);
	}

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
		List<Grid> result = new ArrayList<Grid>();

		Collection<Cell> sources = getCells(transition.getSource());
		Collection<Cell> destinations = getCells(transition.getDestination());
		for (Cell source : sources) {
			for (Cell destination : destinations) {
				Grid connection = implementConnection(source, destination, transition.getScript());
				if (connection == null) continue;
				result.add(connection);
			}
		}

		return result;
	}

	private Grid implementConnection(Cell source, Cell destination,
			ScriptIdentifier transitionScript) {

		// find a path between source and destination involving only free cells
		// source and destination are both included in path
		Path path = findPath(source.getPosition(), destination.getPosition());
		if (path == null) return null;

		Map<Position, Cell> newCells = cells.toMap();

		int length = Path.length(path);
		if (length < 2) {
			// source and destination are neighbors
			Pair<Cell> fix1 = source.extend(destination.getPosition(), destination.getRoom(),
					transitionScript);
			Pair<Cell> fix2 = destination.extend(source.getPosition(), source.getRoom(),
					transitionScript);

			// NOTE: implicitly throw away duplicate cells
			setCell(newCells, fix1.first());
			setCell(newCells, fix2.first());

			return new Grid(boundary, newCells);
		}

		Cell current = source;
		Cell previous = current;
		RoomIdentifier room = source.getRoom();

		int i = 0;
		// NOTE that we start at path.next!
		for (Path step = path.next(); step != null; step = step.next()) {
			ScriptIdentifier script = ScriptIdentifier.OPEN;
			if (i == length / 2) {
				script = transitionScript;
				room = destination.getRoom();
			}
			Pair<Cell> extension = current.extend(step.getPosition(), room, script);
			setCell(newCells, extension.first());
			previous = current;
			current = extension.second();
			i++;
		}

		// fix up destination room
		// NOTE: implicitly throw away current and fixup.second!
		Pair<Cell> fixup = destination.extend(previous.getPosition());
		setCell(newCells, fixup.first());

		// build grid
		return new Grid(boundary, newCells);
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
			for (Position neighbor : getValidNeighbors(position)) {

				// implement transition, link cell and extension
				Pair<Cell> extension = cell.extend(neighbor, destination, script);

				// create new grid
				Map<Position, Cell> nextCells = cells.toMap();
				setCell(nextCells, extension.first());
				setCell(nextCells, extension.second());

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
			for (Position neighbor : getValidNeighbors(position)) {

				// create extension, link cell and extension
				Pair<Cell> extension = cell.extend(neighbor);

				// create new grid
				Map<Position, Cell> nextCells = cells.toMap();
				setCell(nextCells, extension.first());
				setCell(nextCells, extension.second());

				// add to result
				result.add(new Grid(boundary, nextCells));
			}
		}

		return result;
	}

	private Path findPath(Position source, Position destination) {

		Set<Position> closedSet = new HashSet<Position>();
		Set<Position> openSet = new HashSet<Position>();
		openSet.add(source);

		Map<Position, Integer> gScore = new HashMap<Position, Integer>();
		Map<Position, Integer> fScore = new HashMap<Position, Integer>();
		gScore.put(source, 0);
		fScore.put(source, Position.getDistance(source, destination));

		Map<Position, Position> cameFrom = new HashMap<Position, Position>();

		while (!openSet.isEmpty()) {
			Position current = getMinimum(openSet, fScore);

			// are we next to the destination and able to connect?
			if (canConnect(current, destination)) {
				return reconstructPath(current, new Path(destination), cameFrom);
			}

			openSet.remove(current);
			closedSet.add(current);

			for (Position neighbor : getValidNeighbors(current)) {
				if (closedSet.contains(neighbor)) continue;

				int tentativeScore = gScore.get(current) + 1;
				if (!openSet.contains(neighbor) || tentativeScore < gScore.get(neighbor)) {
					cameFrom.put(neighbor, current);
					gScore.put(neighbor, tentativeScore);
					fScore.put(neighbor,
							tentativeScore + Position.getDistance(neighbor, destination));
					openSet.add(neighbor);
				}
			}
		}

		return null;
	}

	public boolean canConnect(Position from, Position to) {

		// too far?
		if (Position.getDistance(from, to) > 1) return false;

		// empty cells?
		Cell source = cells.get(from);
		Cell destination = cells.get(to);
		if (source == null || destination == null) return true;

		// transition available?
		return Objects.equals(Cell.getTransitionScript(source, destination),
				ScriptIdentifier.BLOCKED);
	}

	public static Position getMinimum(Collection<Position> positions,
			Map<Position, Integer> cost) {
		Position min = null;
		int minCost = 0;
		for (Position position : positions) {
			if (min == null || cost.get(position) < minCost) {
				min = position;
				minCost = cost.get(position);
			}
		}
		return min;
	}

	public static Path reconstructPath(Position position, Path next,
			Map<Position, Position> cameFrom) {

		Path current = new Path(position, next);

		Position predecessor = cameFrom.get(position);
		if (predecessor == null) return current;

		return reconstructPath(predecessor, current, cameFrom);
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

	private List<Position> getValidNeighbors(Position position) {
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
		int minZ = 0;
		int maxZ = 0;
		for (Cell cell : cells.values()) {
			Position position = cell.getPosition();
			minX = Math.min(minX, position.getX());
			maxX = Math.max(maxX, position.getX());
			minY = Math.min(minY, position.getY());
			maxY = Math.max(maxY, position.getY());
			minZ = Math.min(minZ, position.getZ());
			maxZ = Math.max(maxZ, position.getZ());
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
		for (int z = maxZ; z >= minZ; z--) {
			for (int y = maxY; y >= minY; y--) {
				for (int line = 0; line < 4; line++) { // TODO how about 4 stringbuilders instead
																								// of this?
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
			result.append("\n");
		}

		return result.toString();
	}

	private final Boundary boundary;
	private final ImmutableMap<Position, Cell> cells;
}
