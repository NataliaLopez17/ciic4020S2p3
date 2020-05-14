package map;

public interface KeyExtractor<K, V> {

	K getKey(V value);
}