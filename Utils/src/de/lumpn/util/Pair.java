package de.lumpn.util;

/**
 * Immutable pair of T
 */
public final class Pair<T> {

	public static <T> Pair<T> create(T a, T b) {
		return new Pair<T>(a, b);
	}

	public Pair(T a, T b) {
		this.a = a;
		this.b = b;
	}

	public T first() {
		return a;
	}

	public T second() {
		return b;
	}

	public Pair<T> swap() {
		return new Pair<T>(b, a);
	}

	private final T a, b;
}
