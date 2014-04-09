package de.lumpn.util;

public interface Func<T, TResult> {

	public TResult invoke(T item);
}
