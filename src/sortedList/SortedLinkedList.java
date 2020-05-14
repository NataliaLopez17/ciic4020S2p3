package sortedList;

/**
 * 
 * @author Fernando J. Bermudez
 * 
 */
public class SortedLinkedList<E extends Comparable<? super E>> extends AbstractSortedList<E> {

	@SuppressWarnings("unused")
	private static class Node<E> {

		private E value;
		private Node<E> next;

		public Node(E value, Node<E> next) {
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

		public Node<E> getNext() {
			return next;
		}

		public void setNext(Node<E> next) {
			this.next = next;
		}

		public void clear() {
			value = null;
			next = null;
		}
	} // End of Node class

	private Node<E> head; // First DATA node (This is NOT a dummy header node)

	public SortedLinkedList() {
		head = null;
		currentSize = 0;
	}

	@Override
	public void add(E e) {
		/* TODO ADD CODE HERE */
		/* Special case: Be careful when the new value is the smallest */
		Node<E> newNode = new Node<>(e);
		Node<E> curNode;

		if (head == null) { // list is empty
			head = newNode;

		} else if (head.getValue().compareTo(newNode.getValue()) >= 0) { // newNode is smallest value
			newNode.setNext(head);
			head = newNode;

		} else {
			curNode = head;
			while (curNode.getNext() != null && !(newNode.getValue().compareTo(curNode.getNext().getValue()) < 0
					&& newNode.getValue().compareTo(curNode.getValue()) >= 0)) {
				// We find where the node has to go in the list other than at the beginning
				curNode = curNode.getNext();
			}

			if (curNode.getNext() == null) { // newNode goes at the end
				curNode.setNext(newNode);
				newNode.setNext(null);

			} else { // newNode goes in between 2 values
				newNode.setNext(curNode.getNext());
				curNode.setNext(newNode);
			}
		}

		currentSize++;
	}

	@Override
	public boolean remove(E e) {
		/* TODO ADD CODE HERE */
		/* Special case: Be careful when the value is found at the head node */
		Node<E> rmNode, curNode;
		if (head == null)
			return false;

		if (head.getValue() == e) {
			rmNode = head;
			head = head.getNext();
			rmNode.clear();
			currentSize--;
			return true;
		}

		for (curNode = head; curNode != null; curNode = curNode.getNext()) {
			if (curNode.getNext().getValue() == e) {
				rmNode = curNode.getNext();
				curNode.setNext(rmNode.getNext());
				rmNode.clear();
				currentSize--;
				return true;

			}
		}
		return false;
	}

	@Override
	public E removeIndex(int index) {
		/* TODO ADD CODE HERE */
		/* Special case: Be careful when index = 0 */
		Node<E> rmNode, curNode;
		int i = 0;
		E value = null;
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();

		if (size() != 0) {
			if (index == 0) { // if index == 0, it means were going to remove the head
				rmNode = head;
				value = rmNode.getValue();
				head = head.getNext();
				rmNode.clear();
				currentSize--;
			} else { // if not, we got to remove an element in between 2 elements in the list
				for (curNode = head; i < index - 1; i++, curNode = curNode.getNext())
					;
				rmNode = curNode.getNext();
				value = rmNode.getValue();
				curNode.setNext(rmNode.getNext());
				rmNode.clear();
				currentSize--;

			}
		}
		return value;
	}

	@Override
	public int firstIndex(E e) {
		/* TODO ADD CODE HERE */
		int target = -1;
		int index = 0;
		Node<E> curNode = head;
		while (index < size()) {
			if (curNode.getValue() == e) {
				target = index;
				break;
			} else {
				curNode = curNode.getNext();
			}
			index++;
		}
		return target;
	}

	@Override
	public E get(int index) {
		/* TODO ADD CODE HERE */
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		Node<E> curNode = head;
		for (int i = 0; i != index; i++, curNode = curNode.getNext())
			;
		return curNode.getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray() {
		int index = 0;
		E[] theArray = (E[]) new Comparable[size()]; // Cannot use Object here
		for (Node<E> curNode = this.head; index < size() && curNode != null; curNode = curNode.getNext(), index++) {
			theArray[index] = curNode.getValue();
		}
		return theArray;
	}

}