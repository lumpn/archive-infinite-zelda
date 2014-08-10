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

	/**
	 * Compares the specified object with this list for equality.  Returns
	 * <tt>true</tt> if and only if the specified object is also a list, both
	 * lists have the same size, and all corresponding pairs of elements in
	 * the two lists are <i>equal</i>. In other words, two lists are defined to
	 * be equal if they contain the same elements in the same order.
	 */
	@Override
	boolean equals(Object o);

	/**
	 * Returns the hash code value for this list.  The hash code of a list
	 * is defined to be the result of the following calculation:
	 * <pre>{@code
	 *     int hashCode = 1;
	 *     for (E e : list)
	 *         hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
	 * }</pre>
	 */
	@Override
	int hashCode();

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
