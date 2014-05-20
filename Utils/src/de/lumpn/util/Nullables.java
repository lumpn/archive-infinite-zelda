package de.lumpn.util;

public final class Nullables {

	public static <T> int hashCode(T obj) {
		if (obj == null) return 0;
		return obj.hashCode();
	}

	public static <T> boolean equals(T a, T b) {
		if (a == null) return (b == null);
		return a.equals(b);
	}
}
