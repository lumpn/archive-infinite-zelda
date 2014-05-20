package de.lumpn.util.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public class ImmutableArrayList<T> implements ImmutableList<T>, RandomAccess {

	private final List<T> items;

	public ImmutableArrayList(Collection<T> items) {
		this.items = new ArrayList<T>(items);
	}

	/**
	 * Constructs from an existing immutable list.
	 * @param items Immutable list of items. Must not be modified externally ever.
	 * @param dummy Dummy parameter to disambiguate against standard constructor.
	 */
	@SuppressWarnings("unused")
	private ImmutableArrayList(List<T> items, int dummy) {
		this.items = items;
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
		return new ImmutableArrayList<T>(items.subList(fromIndex, toIndex), 0);
	}

	@Override
	public List<T> toList() {
		return new ArrayList<T>(items);
	}

	@Override
	public List<T> toUnmodifiableList() {
		return Collections.unmodifiableList(items);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof ImmutableArrayList)) return false;
		ImmutableArrayList other = (ImmutableArrayList) obj;
		return items.equals(other.items);
	}

	@Override
	public int hashCode() {
		return items.hashCode();
	}
}
