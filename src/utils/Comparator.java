package utils;

public interface Comparator<E> {
	/**
	 * 
	 * @param v1 First value to compare
	 * @param v2 Second value to compare
	 * @return -1 if v1 < v2, 0 if v1 == v2, 1 if v1 > v2
	 */
	int compare(E v1, E v2);

}