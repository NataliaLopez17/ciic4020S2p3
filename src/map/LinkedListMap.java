package map;

import java.io.PrintStream;

import list.LinkedList;
import list.List;

public class LinkedListMap<K, V> implements Map<K, V> {

	// private fields
	private List<V> elements;
	private KeyExtractor<K, V> extractor;

	public LinkedListMap(KeyExtractor<K, V> extractor) {
		if (extractor == null)
			throw new IllegalArgumentException("Parameter cannot be null.");
		this.extractor = extractor;
		elements = new LinkedList<V>();
	}

	@Override
	public V get(K key) {
		if (key == null)
			throw new IllegalArgumentException("Parameter cannot be null.");
		if (isEmpty())
			return null;

		/* Iterate over the elements until we find one with the given key */
		for (V v : elements) { // Recall that our List extends Iterable
			K tempKey = extractor.getKey(v);
			if (tempKey.equals(key)) // Found it!
				return v;
		}
		return null;
	}

	@Override
	public void put(K key, V value) {
		if (key == null || value == null)
			throw new IllegalArgumentException("Parameter cannot be null.");

		/*
		 * We could test whether containsKey returns true, but it would iterate over the
		 * list, only to iterate again when we invoke remove. remove doesn't throw an
		 * exception if the key isn't there, so just call it.
		 */
		remove(key); // key might not exist in the map, but this would just return null
		elements.add(0, value);
	}

	@Override
	public V remove(K key) {
		if (key == null)
			throw new IllegalArgumentException("Parameter cannot be null.");
		int pos = 0;
		for (V v : elements) {
			K tempKey = extractor.getKey(v);
			if (tempKey.equals(key)) {
				elements.remove(pos); // Would break the iteration, but we'll exit now
				return v;
			} else
				pos++;
		}
		return null; // Did not remove anything
	}

	@Override
	public boolean containsKey(K key) {
		return get(key) != null;
	}

	@Override
	public List<K> getKeys() {
		List<K> result = new LinkedList<K>();
		for (V v : elements)
			result.add(0, extractor.getKey(v)); // Add at the beginning; faster
		return result;
	}

	@Override
	public List<V> getValues() {
		List<V> result = new LinkedList<V>();
		for (V v : elements)
			result.add(0, v); // Add at the beginning; faster
		return result;
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public void clear() {
		elements.clear();
	}

	@Override
	public void print(PrintStream out) {
		for (V v : elements)
			out.println("(" + extractor.getKey(v) + ", " + v + ")");
	}

}