package sortedList;

/**
 * @author Juan O. López
 *
 */
public class SortedArrayList<E extends Comparable<? super E>> extends AbstractSortedList<E> {

	private E[] elements;

	@SuppressWarnings("unchecked")
	public SortedArrayList(int initialCapacity) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException("Capacity must be at least 1");
		elements = (E[]) new Comparable[initialCapacity]; // Cannot use Object here
		currentSize = 0;
	}

	@Override
	public void add(E e) {
		if (size() == elements.length)
			reAllocate();
		int index = getIndex(e); // Index where e should be added
		/* Shift elements down to make room for new element */
		for (int j = size(); j > index; j--)
			elements[j] = elements[j - 1];
		elements[index] = e;
		currentSize++;
	}

	/**
	 * Return the index of the first occurrence of a value. If not in the list,
	 * return the index where it would be inserted.
	 * 
	 * @param e The value being searched for
	 * @return The index where the value is or belongs
	 */
	private int getIndex(E e) {
		if (isEmpty())
			return 0;
		/* We perform binary search recursively to find the result */
		return binarySearch(e, 0, size() - 1);
	}

	/**
	 * This version of binary search specifically looks for the index of the first
	 * occurrence of a value. If the value is not in the list, then we return the
	 * index where it would be inserted, preserving the order.
	 *
	 * @param e     The value being searched for
	 * @param first First index of the array portion being searched
	 * @param last  Last index of the array portion being searched
	 * @return The index where the value is or belongs
	 */
	private int binarySearch(E e, int first, int last) {
		if (first == last) {
			/* We have only one element, so e belongs either before or after it */
			if (elements[first].compareTo(e) >= 0)
				return first;
			else
				return first + 1;
		}

		int mid = (first + last) / 2;
		/*
		 * To find the position, we need to find a value greater than or equal to e,
		 * either at the beginning of the list or with a lower value preceding
		 */
		if (elements[mid].compareTo(e) >= 0 && (mid == first || elements[mid - 1].compareTo(e) < 0))
			return mid;
		else if (elements[mid].compareTo(e) < 0)
			return binarySearch(e, mid + 1, last);
		else
			return binarySearch(e, first, mid - 1);
	}

	/**************************************************************************
	 * Exercise 3
	 * 
	 * Q: Could we also use binary search in SortedLinkedList? If you answered no,
	 * why not? If you answered yes, why didn't/shouldn't we do it?
	 * 
	 * A: Yes, we can use it, but it's not a good idea. When using an array we have
	 * random access to the elements in O(1), so that we may quickly access any of
	 * the values at any index when performing the binary search algorithm. This
	 * makes binary search run in O(log n). However, in a linked list we must
	 * traverse the list whenever we need to access a particular index, so binary
	 * search would take O(n log n), which is worse than doing a sequential search
	 * (which is O(n)).
	 */

	@SuppressWarnings("unchecked")
	private void reAllocate() {
		E[] newElements = (E[]) new Comparable[2 * size()]; // Cannot use Object here
		System.arraycopy(elements, 0, newElements, 0, size());
		elements = newElements;
	}

	@Override
	public boolean remove(E e) {
		int index = firstIndex(e);
		if (index != -1) { // Found it!
			removeIndex(index);
			return true;
		}
		return false;
	}

	@Override
	public E removeIndex(int index) {
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		E result = elements[index];
		/* Shift elements up to avoid gaps in the array */
		for (int j = index; j < size() - 1; j++)
			elements[j] = elements[j + 1];

		elements[--currentSize] = null;
		return result;
	}

	@Override
	public int firstIndex(E e) {
		int index = getIndex(e); // Index of where it is, or where it belongs
		if (index < size() && elements[index].equals(e)) // Found it!
			return index;
		return -1; // Didn't find it
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		return elements[index];
	}

	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray() {
		E[] asArray = (E[]) new Comparable[size()]; // Cannot use Object here
		System.arraycopy(elements, 0, asArray, 0, size());
		return asArray;
	}

}