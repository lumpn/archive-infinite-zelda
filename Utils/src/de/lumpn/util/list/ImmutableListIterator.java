package de.lumpn.util.list;

import java.util.Iterator;

public interface ImmutableListIterator<T> extends Iterator<T> {

	public boolean hasPrevious();

	public T previous();

	public int nextIndex();

	public int previousIndex();
}
