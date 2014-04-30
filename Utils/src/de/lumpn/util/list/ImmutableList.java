package de.lumpn.util.list;

import java.util.Collection;
import java.util.List;

public interface ImmutableList<T> extends Iterable<T> {

	@Override
	public ImmutableListIterator<T> iterator();

	public ImmutableListIterator<T> iterator(int index);

	public int size();

	public boolean isEmpty();

	public boolean contains(T item);

	public boolean containsAll(Collection<T> c);

	public T get(int index);

	public int indexOf(T item);

	public int lastIndexOf(T item);

	public ImmutableList<T> subList(int fromIndex, int toIndex);

	/**
	 * Returns a new modifiable list containing all items.
	 * @return List copy.
	 */
	public List<T> toList();

	/**
	 * Returns a unmodifiable view on the items.
	 * @return Unmodifiable view.
	 */
	public List<T> toUnmodifiableList();
}
