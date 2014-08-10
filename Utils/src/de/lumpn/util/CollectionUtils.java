package de.lumpn.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class CollectionUtils {

	public static <T> List<T> immutable(List<T> items) {
		// unmodifiable defensive copy
		return Collections.unmodifiableList(new ArrayList<T>(items));
	}

	public static <K, V> Map<K, V> immutable(Map<K, V> items) {
		// unmodifiable defensive copy
		return Collections.unmodifiableMap(new HashMap<K, V>(items));
	}

	/**
	 * Distributes the items of both collections randomly. Does NOT preserve
	 * order!
	 */
	public static <T> Pair<List<T>> distribute(Collection<T> x, Collection<T> y, Random random) {

		Pair<List<T>> pair1 = split(shuffle(x, random), random);
		Pair<List<T>> pair2 = split(shuffle(y, random), random);

		return new Pair<List<T>>(concat(pair1.first(), pair2.second()), concat(pair1.second(), pair2.first()));
	}

	public static <T> List<T> shuffle(Collection<T> items, Random random) {
		List<T> result = new ArrayList<T>(items);
		Collections.shuffle(result, random);
		return result;
	}

	public static <T> Pair<List<T>> split(List<T> items, Random random) {
		if (items.isEmpty()) {
			return new Pair<List<T>>(Collections.<T> emptyList(), Collections.<T> emptyList());
		}

		int split = random.nextInt(items.size());
		return new Pair<List<T>>(items.subList(0, split), items.subList(split, items.size()));
	}

	public static <T> List<T> concat(Collection<T> a, Collection<T> b) {
		List<T> result = new ArrayList<T>();
		result.addAll(a);
		result.addAll(b);
		return result;
	}

	public static <T> List<Pair<T>> zip(Collection<T> a, Collection<T> b) {
		assert (a.size() == b.size());

		List<Pair<T>> result = new ArrayList<Pair<T>>();
		Iterator<T> i = a.iterator();
		Iterator<T> j = b.iterator();
		while (i.hasNext() && j.hasNext()) {
			result.add(new Pair<T>(i.next(), j.next()));
		}
		return result;
	}

	public static <T, TResult> List<TResult> select(Iterable<T> items, Func<T, TResult> func) {
		List<TResult> result = new ArrayList<TResult>();
		for (T item : items) {
			result.add(func.invoke(item));
		}
		return result;
	}

}
