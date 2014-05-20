package de.lumpn.util.map;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ImmutableHashMap<K, V> implements ImmutableMap<K, V> {

	private final Map<K, V> items;

	public ImmutableHashMap(Map<K, V> source) {
		this.items = new HashMap<K, V>(source);
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
	public boolean containsKey(Object key) {
		return items.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return items.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return items.get(key);
	}

	@Override
	public Set<K> keySet() {
		// TODO return truly immutable set
		return Collections.unmodifiableSet(items.keySet());
	}

	@Override
	public Collection<V> values() {
		// TODO return truly immutable collection
		return Collections.unmodifiableCollection(items.values());
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		// TODO return truly immutable set
		// TODO do so without explicit conversion
		Set<Entry<K, V>> result = new HashSet<Entry<K, V>>();
		for (Map.Entry<K, V> entry : items.entrySet()) {
			result.add(new DefaultImmutableMapEntry<K, V>(entry.getKey(), entry.getValue()));
		}

		return Collections.unmodifiableSet(result);
	}

	@Override
	public Map<K, V> toMap() {
		return new HashMap<K, V>(items);
	}

	@Override
	public Map<K, V> toUnmodifiableMap() {
		return Collections.unmodifiableMap(items);
	}
}
