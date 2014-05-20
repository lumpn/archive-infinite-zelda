package de.lumpn.util.map;

import de.lumpn.util.Nullables;

public class DefaultImmutableMapEntry<K, V> implements ImmutableMap.Entry<K, V> {

	private final K key;
	private final V value;

	/**
	 * Creates an entry representing a mapping from the specified
	 * key to the specified value.
	 *
	 * @param key the key represented by this entry
	 * @param value the value represented by this entry
	 */
	public DefaultImmutableMapEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Creates an entry representing the same mapping as the
	 * specified entry.
	 *
	 * @param entry the entry to copy
	 */
	public DefaultImmutableMapEntry(ImmutableMap.Entry<? extends K, ? extends V> entry) {
		this.key = entry.getKey();
		this.value = entry.getValue();
	}

	/**
	 * Returns the key corresponding to this entry.
	 *
	 * @return the key corresponding to this entry
	 */
	@Override
	public K getKey() {
		return key;
	}

	/**
	 * Returns the value corresponding to this entry.
	 *
	 * @return the value corresponding to this entry
	 */
	@Override
	public V getValue() {
		return value;
	}

	/**
	 * Compares the specified object with this entry for equality.
	 * Returns {@code true} if the given object is also a map entry and
	 * the two entries represent the same mapping.  More formally, two
	 * entries {@code e1} and {@code e2} represent the same mapping
	 * if<pre>
	 *   (e1.getKey()==null ?
	 *    e2.getKey()==null :
	 *    e1.getKey().equals(e2.getKey()))
	 *   &amp;&amp;
	 *   (e1.getValue()==null ?
	 *    e2.getValue()==null :
	 *    e1.getValue().equals(e2.getValue()))</pre>
	 * This ensures that the {@code equals} method works properly across
	 * different implementations of the {@code Map.Entry} interface.
	 *
	 * @param o object to be compared for equality with this map entry
	 * @return {@code true} if the specified object is equal to this map
	 *         entry
	 * @see    #hashCode
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ImmutableMap.Entry)) return false;
		ImmutableMap.Entry<?, ?> e = (ImmutableMap.Entry<?, ?>) o;
		return Nullables.equals(key, e.getKey()) && Nullables.equals(value, e.getValue());
	}

	/**
	 * Returns the hash code value for this map entry.  The hash code
	 * of a map entry {@code e} is defined to be: <pre>
	 *   (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^
	 *   (e.getValue()==null ? 0 : e.getValue().hashCode())</pre>
	 * This ensures that {@code e1.equals(e2)} implies that
	 * {@code e1.hashCode()==e2.hashCode()} for any two Entries
	 * {@code e1} and {@code e2}, as required by the general
	 * contract of {@link Object#hashCode}.
	 *
	 * @return the hash code value for this map entry
	 * @see    #equals
	 */
	@Override
	public int hashCode() {
		return Nullables.hashCode(key) ^ Nullables.hashCode(value);
	}

	/**
	 * Returns a String representation of this map entry.  This
	 * implementation returns the string representation of this
	 * entry's key followed by the equals character ("<tt>=</tt>")
	 * followed by the string representation of this entry's value.
	 *
	 * @return a String representation of this map entry
	 */
	@Override
	public String toString() {
		return key + "=" + value;
	}

}
