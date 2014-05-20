package de.lumpn.zelda.layout;

import de.lumpn.util.Pair;

/**
 * A cell has a position and optionally a room id and scripts
 * in the cell and the three exits.
 */
public final class Cell {

	public Cell(Position position, RoomIdentifier room, ScriptIdentifier center, ScriptIdentifier north,
			ScriptIdentifier east, ScriptIdentifier up) {
		this.position = position;
		this.room = room;
		this.center = center;
		this.north = north;
		this.east = east;
		this.up = up;
	}

	public Position getPosition() {
		return position;
	}

	public boolean hasRoom(RoomIdentifier source) {
		return (source.equals(room));
	}

	public int numScripts() {
		int result = 0;
		if (!center.equals(ScriptIdentifier.EMPTY)) result++;
		if (!north.equals(ScriptIdentifier.BLOCKED)) result++;
		if (!east.equals(ScriptIdentifier.BLOCKED)) result++;
		if (!up.equals(ScriptIdentifier.BLOCKED)) result++;
		return result;
	}

	public Pair<Cell> extend(Position neighborPosition) {
		return extend(neighborPosition, room, ScriptIdentifier.OPEN);
	}

	public Pair<Cell> extend(Position neighborPosition, RoomIdentifier neighborRoom,
			ScriptIdentifier transitionScript) {

		// initialize new scripts
		ScriptIdentifier north1 = north;
		ScriptIdentifier east1 = east;
		ScriptIdentifier up1 = up;

		ScriptIdentifier north2 = ScriptIdentifier.BLOCKED;
		ScriptIdentifier east2 = ScriptIdentifier.BLOCKED;
		ScriptIdentifier up2 = ScriptIdentifier.BLOCKED;

		// find the script to replace by comparing positions
		switch (Position.getDirection(position, neighborPosition)) {
			case NORTH:
				north1 = transitionScript;
				break;
			case SOUTH:
				north2 = transitionScript;
				break;
			case EAST:
				east1 = transitionScript;
				break;
			case WEST:
				east2 = transitionScript;
				break;
			case UP:
				up1 = transitionScript;
				break;
			case DOWN:
				up2 = transitionScript;
				break;
			default:
				assert false;
		}

		// build resulting cells
		Cell cell1 = new Cell(position, room, center, north1, east1, up1);
		Cell cell2 = new Cell(neighborPosition, neighborRoom, ScriptIdentifier.EMPTY, north2, east2,
				up2);
		return new Pair<Cell>(cell1, cell2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + position.hashCode();
		result = prime * result + room.hashCode();
		result = prime * result + center.hashCode();
		result = prime * result + east.hashCode();
		result = prime * result + north.hashCode();
		result = prime * result + up.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Cell other = (Cell) obj;
		if (!position.equals(other.position)) return false;
		if (!room.equals(other.room)) return false;
		if (!center.equals(other.center)) return false;
		if (!north.equals(other.north)) return false;
		if (!east.equals(other.east)) return false;
		if (!up.equals(other.up)) return false;
		return true;
	}

	/**
	 * Position in grid.
	 */
	private final Position position;

	private final RoomIdentifier room;

	private final ScriptIdentifier center, east, north, up;
}
