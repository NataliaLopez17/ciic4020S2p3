package map;

public class SimpleHashFunction<K> implements HashFunction<K> {

	@Override
	public int hashCode(K key) {
		String temp = key.toString();
		int result = 0;
		for (int i = 0; i < temp.length(); i++)
			result += temp.charAt(i);
		return result;
	}

}