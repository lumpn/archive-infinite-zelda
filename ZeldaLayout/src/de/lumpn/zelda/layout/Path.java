package de.lumpn.zelda.layout;

import de.lumpn.util.Pair;

public class Path {

	public static int length(Path path) {
		if (!path.hasNext()) return 0;
		return length(path.next()) + 1;
	}

	public static Pair<Path> split(Path path, int splitPosition) {
		Path iterator = path;
		
		Path first = new Path(iterator.position);
		Path current = first;
		for (int i = 0; i < splitPosition; i++) {
			iterator = iterator.next();
			Path next = new Path(iterator.position);
			current.next = next;
			current = next;
		}

		Path second = new Path(iterator.position);
		current = second;
		while (iterator.hasNext()) {
			iterator = iterator.next();
			Path next = new Path(iterator.position);
			current.next = next;
			current = next;
		}

		return new Pair<Path>(first, second);
	}

	public Path(Position position) {
		this.position = position;
	}

	public boolean hasNext() {
		return (next != null);
	}

	public Path next() {
		return next;
	}

	public Position getPosition() {
		return position;
	}

	private final Position position;
	private Path next;
}
