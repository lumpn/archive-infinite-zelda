package de.lumpn.zelda.layout;

/**
 * Immutable bounds.
 */
public class Bounds {

	public Bounds(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.minZ = minZ;
		this.maxZ = maxZ;
	}

	public boolean contains(Position position) {
		if (position.getX() < minX || position.getX() > maxX) return false;
		if (position.getY() < minY || position.getY() > maxY) return false;
		if (position.getZ() < minZ || position.getZ() > maxZ) return false;
		return true;
	}

	private final int minX, maxX, minY, maxY, minZ, maxZ;
}
