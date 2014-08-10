package de.lumpn.util.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public class ImmutableArrayList<T> implements ImmutableList<T>, RandomAccess {

	private final List<T> items;

	/**
	 * Constructs an immutable list by copying the collection of items.
	 * @param source Source collection of items.
	 */
	public ImmutableArrayList(Collection<T> source) {
		this.items = new ArrayList<T>(source);
	}

	/**
	 * Constructs an immutable list from an existing immutable list without copying.
	 * @param source Immutable list of items. Must not be modified ever.
	 * @param isImmutable Dummy parameter for disambiguation. Must be <code>true</code>.
	 */
	private ImmutableArrayList(List<T> source, boolean isImmutable) {
		assert isImmutable;
		this.items = source;
	}

	@Override
	public ImmutableListIterator<T> iterator() {
		return iterator(0);
	}

	@Override
	public ImmutableListIterator<T> iterator(int index) {
		return new DefaultImmutableListIterator<T>(this, index);
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public boolean contains(T item) {
		return items.contains(item);
	}

	@Override
	public boolean containsAll(Collection<T> c) {
		return items.containsAll(c);
	}

	@Override
	public T get(int index) {
		return items.get(index);
	}

	@Override
	public int indexOf(T item) {
		return items.indexOf(item);
	}

	@Override
	public int lastIndexOf(T item) {
		return items.lastIndexOf(item);
	}

	@Override
	public ImmutableArrayList<T> subList(int fromIndex, int toIndex) {
		return new ImmutableArrayList<T>(items.subList(fromIndex, toIndex), true);
	}

	@Override
	public List<T> toList() {
		return new ArrayList<T>(items);
	}

	@Override
	public List<T> toUnmodifiableList() {
		return Collections.unmodifiableList(items);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof ImmutableArrayList<?>)) return false;
		ImmutableArrayList<?> other = (ImmutableArrayList<?>) obj;
		if (!items.equals(other.items)) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return items.hashCode();
	}
}
