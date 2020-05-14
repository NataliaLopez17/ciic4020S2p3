package utils;

public class IntegerComparator implements Comparator<Integer> {

	@Override
	public int compare(Integer v1, Integer v2) {
		return v1.compareTo(v2);
	}

}