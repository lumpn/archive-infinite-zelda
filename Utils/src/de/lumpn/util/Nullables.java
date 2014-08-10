package de.lumpn.util;

public final class Nullables {

	public static <T> int hashCode(T obj) {
		return (obj == null) ? 0 : obj.hashCode();
	}
	
	public static <T> boolean equals(T a, T b) {
		return (a == null) ? (b == null) : a.equals(b);
	}
}
