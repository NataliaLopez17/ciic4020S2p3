package list;

/*These 2 imports came with the class itself provided by the professor*/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E> {

	// private fields
	private E elements[];

	private int currentSize;

	private static final int DEFAULT_SIZE = 11;

	private class ListIterator implements Iterator<E> {
		private int currentPosition;

		public ListIterator() {
			this.currentPosition = 0;
		}

		@Override
		public boolean hasNext() {
			return this.currentPosition < size();
		}

		@Override
		public E next() {
			if (this.hasNext()) {
				return (E) elements[this.currentPosition++];
			} else
				throw new NoSuchElementException();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList(int initialCapacity) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException("Capacity must be at least 1.");
		this.currentSize = 0;
		this.elements = (E[]) new Object[initialCapacity];
	}

	@SuppressWarnings("unchecked")
	public ArrayList() {

		this.currentSize = 0;
		this.elements = (E[]) new Object[DEFAULT_SIZE];
	}

	@Override
	public void add(E obj) {
		if (this.size() == this.elements.length)
			reAllocate();
		this.elements[this.currentSize++] = obj;
	}

	@SuppressWarnings("unchecked")
	private void reAllocate() {
		/* create a new array with twice the size */
		E newElements[] = (E[]) new Object[2 * this.elements.length];
		for (int i = 0; i < this.size(); i++)
			newElements[i] = this.elements[i];
		/*
		 * replace old elements with newElements We could call elements.clear() first,
		 * but let the GC handle that
		 */
		this.elements = newElements;
	}

	@Override
	public void add(int index, E obj) {
		// if (obj == null)
		// throw new IllegalArgumentException("Object cannot be null.");
		if (index == this.currentSize)
			this.add(obj); // Use other method to "append"
		else {
			if (index >= 0 && index < this.currentSize) {
				if (this.currentSize == this.elements.length)
					reAllocate();
				// move everybody one spot to the back
				for (int i = this.currentSize; i > index; i--)
					this.elements[i] = this.elements[i - 1];
				// add element at position index
				this.elements[index] = obj;
				this.currentSize++;
			} else
				throw new ArrayIndexOutOfBoundsException();
		}
	}

	@Override
	public boolean remove(E obj) {
		if (obj == null)
			throw new IllegalArgumentException("Object cannot be null.");
		// first find obj in the array
		int position = this.firstIndex(obj);
		if (position >= 0) // found it
			return this.remove(position);
		else
			return false;
	}

	@Override
	public boolean remove(int index) {
		if (index >= 0 && index < this.currentSize) {
			// move everybody one spot to the front
			for (int i = index; i < this.currentSize - 1; i++)
				this.elements[i] = this.elements[i + 1];
			this.elements[--this.currentSize] = null;
			return true;
		} else
			return false;
	}

	@Override
	public int removeAll(E obj) {
		int counter = 0;
		while (this.remove(obj))
			counter++;
		return counter;
	}

	@Override
	public E get(int index) {
		if (index >= 0 && index < this.size())
			return this.elements[index];
		else
			throw new ArrayIndexOutOfBoundsException();
	}

	@Override
	public E set(int index, E obj) {
		if (obj == null)
			throw new IllegalArgumentException("Object cannot be null.");
		if (index >= 0 && index < this.size()) {
			E temp = this.elements[index];
			this.elements[index] = obj;
			return temp;
		} else
			throw new ArrayIndexOutOfBoundsException();
	}

	@Override
	public E first() {
		if (this.isEmpty())
			return null;
		else
			return this.elements[0];
	}

	@Override
	public E last() {
		if (this.isEmpty())
			return null;
		else
			return this.elements[this.currentSize - 1];
	}

	@Override
	public int firstIndex(E obj) {
		for (int i = 0; i < this.size(); i++)
			if (this.elements[i].equals(obj))
				return i;
		return -1;
	}

	@Override
	public int lastIndex(E obj) {
		for (int i = this.size() - 1; i >= 0; i--)
			if (this.elements[i].equals(obj))
				return i;
		return -1;
	}

	@Override
	public int size() {
		return this.currentSize;
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public boolean contains(E obj) {
		return this.firstIndex(obj) >= 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.currentSize; i++)
			this.elements[i] = null;
		this.currentSize = 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new ListIterator();
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