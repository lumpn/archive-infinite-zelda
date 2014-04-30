package de.lumpn.util.list;

public final class DefaultImmutableListIterator<T> implements ImmutableListIterator<T> {

	public DefaultImmutableListIterator(ImmutableList<T> list, int index) {
		this.index = index;
		this.list = list;
	}

	@Override
	public boolean hasNext() {
		return index < list.size();
	}

	@Override
	public T next() {
		index++;
		return list.get(index);
	}

	@Override
	public boolean hasPrevious() {
		return index > 0;
	}

	@Override
	public T previous() {
		index--;
		return list.get(index);
	}

	@Override
	public int nextIndex() {
		return index + 1;
	}

	@Override
	public int previousIndex() {
		return index - 1;
	}

	private int index;

	private final ImmutableList<T> list;
}
