package sortedList;

public interface SortedList<E extends Comparable<? super E>> {

	/**
	 * Add a new value to the sorted list in the appropriate position
	 * to preserve the order.
	 * 
	 * @param e Value to be added to the list
	 * @throws IllegalArgumentException If the value e is null
	 */
	void add(E e) throws IllegalArgumentException;

	/**
	 * Remove the first occurrence of a value from the list.
	 * 
	 * @param e  Value to be removed from the list
	 * @return   True if the value was removed, false otherwise
	 */
	boolean remove(E e);
	
	/**
	 * Remove the value from the list at the specified index.
	 * 
	 * @param index List index from which the value should be removed
	 * @return      The value that was removed from the list
	 * @throws IndexOutOfBoundsException If index < 0 or index >= list size
	 */
	E removeIndex(int index) throws IndexOutOfBoundsException;
	
	/**
	 * Returns the index of the first occurrence of a value in the list,
	 * or -1 if the value is not in the list.
	 * 
	 * @param e  The value to search for
	 * @return   The index of the first occurrence of e, or -1 if not in the list
	 */
	int firstIndex(E e);
	
	/**
	 * Return the value at a specific index in the list.
	 * 
	 * @param index  The index from which the value should be retrieved
	 * @return       The value at the specified index
	 * @throws IndexOutOfBoundsException If index < 0 or index >= list size
	 */
	E get(int index) throws IndexOutOfBoundsException;
	
	/**
	 * Determine whether a particular value is in the list.
	 * 
	 * @param e The value being searched for
	 * @return  True if the value is in the list, false otherwise.
	 */
	boolean contains(E e);

	/**
	 * Return the amount of values in the list.
	 * 
	 * @return The amount of values in the list
	 */
	int size();
	
	/**
	 * Determine whether the list is empty.
	 * 
	 * @return True if the list is empty, false otherwise.
	 */
	boolean isEmpty();
	
	/**
	 * Remove all of the values from the list.
	 */
	void clear();
	
	/**
	 * Temporary method to return the list as an array, for testing and debugging.
	 * 
	 * @return Array with a copy of the elements
	 */
	E[] toArray();


}