package list;

/*These 2 imports came with the class itself provided by the professor*/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements List<E> {

	private class Node {
		private E value;
		private Node next;

		public Node(E value, Node next) {
			this.value = value;
			this.next = next;
		}

		public Node(E value) {
			this(value, null); // Delegate to other constructor
		}

		public Node() {
			this(null, null); // Delegate to other constructor
		}

		public E getValue() {
			return value;
		}

		public void setValue(E value) {
			this.value = value;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		public void clear() {
			value = null;
			next = null;
		}
	} // End of Node class

	private class ListIterator implements Iterator<E> {

		private Node nextNode;

		public ListIterator() {
			nextNode = header.getNext();
		}

		@Override
		public boolean hasNext() {
			return nextNode != null;
		}

		@Override
		public E next() {
			if (hasNext()) {
				E val = nextNode.getValue();
				nextNode = nextNode.getNext();
				return val;
			} else
				throw new NoSuchElementException();
		}

	} // End of ListIterator class

	// private fields
	private Node header;
	private int currentSize;

	public LinkedList() {
		header = new Node();
		currentSize = 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new ListIterator();
	}

	@Override
	public void add(E obj) {
		Node curNode, newNode;
		// Need to find the last node
		for (curNode = header; curNode.getNext() != null; curNode = curNode.getNext())
			;
		// Now curNode is the last node
		// Create a new Node and make curNode point to it
		newNode = new Node(obj);
		curNode.setNext(newNode);
		currentSize++;
	}

	@Override
	public void add(int index, E obj) {
		Node curNode, newNode;

		// First confirm index is a valid position
		// We allow for index == size() and delegate to add(object).
		if (index < 0 || index > size())
			throw new IndexOutOfBoundsException();
		if (index == size())
			add(obj); // Use our "append" method
		else {
			// Get predecessor node (at position index - 1)
			curNode = get_node(index - 1);
			// The new node must be inserted between curNode and curNode's next
			// Note that if index = 0, curNode will be header node
			newNode = new Node(obj, curNode.getNext());
			curNode.setNext(newNode);
			currentSize++;
		}
	}

	@Override
	public boolean remove(E obj) {
		Node curNode = header;
		Node nextNode = curNode.getNext();

		// Traverse the list until we find the element or we reach the end
		while (nextNode != null && !nextNode.getValue().equals(obj)) {
			curNode = nextNode;
			nextNode = nextNode.getNext();
		}

		// Need to check if we found it
		if (nextNode != null) { // Found it!
			// If we have A -> B -> C and want to remove B, make A point to C
			curNode.setNext(nextNode.getNext());
			nextNode.clear(); // free up resources
			currentSize--;
			return true;
		} else
			return false;
	}

	@Override
	public boolean remove(int index) {
		Node curNode, rmNode;
		// First confirm index is a valid position
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		curNode = get_node(index - 1);
		rmNode = curNode.getNext();
		// If we have A -> B -> C and want to remove B, make A point to C
		curNode.setNext(rmNode.getNext());
		rmNode.clear();
		currentSize--;

		return true;
	}

	/* Private method to return the node at position index */
	private Node get_node(int index) {
		Node curNode;

		/*
		 * First confirm index is a valid position Allow -1 so that header node may be
		 * returned
		 */
		if (index < -1 || index >= size())
			throw new IndexOutOfBoundsException();
		curNode = header;
		// Since first node is pos 0, let header be position -1
		for (int curPos = -1; curPos < index; curPos++)
			curNode = curNode.getNext();
		return curNode;
	}

	@Override
	public int removeAll(E obj) {
		int counter = 0;
		Node curNode = header;
		Node nextNode = curNode.getNext();

		/*
		 * We used the following in ArrayList, and it would also work here, but it would
		 * have running time of O(n^2).
		 * 
		 * while (remove(obj)) counter++;
		 */

		// Traverse the entire list
		while (nextNode != null) {
			if (nextNode.getValue().equals(obj)) { // Remove it!
				curNode.setNext(nextNode.getNext());
				nextNode.clear();
				currentSize--;
				counter++;
				/*
				 * Node that was pointed to by nextNode no longer exists so reset it such that
				 * it's still the node after curNode
				 */
				nextNode = curNode.getNext();
			} else {
				curNode = nextNode;
				nextNode = nextNode.getNext();
			}
		}
		return counter;
	}

	@Override
	public E get(int index) {
		// get_node allows for index to be -1, but we don't want get to allow that
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		return get_node(index).getValue();
	}

	@Override
	public E set(int index, E obj) {
		// get_node allows for index to be -1, but we don't want set to allow that
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		Node theNode = get_node(index);
		E theValue = theNode.getValue();
		theNode.setValue(obj);
		return theValue;
	}

	@Override
	public E first() {
		return get(0);
	}

	@Override
	public E last() {
		return get(size() - 1);
	}

	@Override
	public int firstIndex(E obj) {
		Node curNode = header.getNext();
		int curPos = 0;
		// Traverse the list until we find the element or we reach the end
		while (curNode != null && !curNode.getValue().equals(obj)) {
			curPos++;
			curNode = curNode.getNext();
		}
		if (curNode != null)
			return curPos;
		else
			return -1;
	}

	@Override
	public int lastIndex(E obj) {
		int curPos = 0, lastPos = -1;
		// Traverse the list
		for (Node curNode = header.getNext(); curNode != null; curNode = curNode.getNext()) {
			if (curNode.getValue().equals(obj))
				lastPos = curPos;
			curPos++;
		}
		return lastPos;
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean contains(E obj) {
		return firstIndex(obj) != -1;
	}

	@Override
	public void clear() {
		// Avoid throwing an exception if the list is already empty
		while (size() > 0)
			remove(0);
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[this.size()];
		for (int i = 0; i < this.size(); ++i) {
			result[i] = this.get(i);
		}
		return result;
	}
}