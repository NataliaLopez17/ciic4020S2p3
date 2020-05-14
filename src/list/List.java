package list;

public interface List<E> extends Iterable<E> {

	public void add(E obj);

	public void add(int index, E obj);

	public boolean remove(E obj);

	public boolean remove(int index);

	public int removeAll(E obj);

	public E get(int index);

	public E set(int index, E obj);

	public E first();

	public E last();

	public int firstIndex(E obj);

	public int lastIndex(E obj);

	public int size();

	public boolean isEmpty();

	public boolean contains(E obj);

	public void clear();

	public Object[] toArray();
}