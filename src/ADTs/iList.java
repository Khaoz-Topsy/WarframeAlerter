package ADTs;

public interface iList<T> extends Iterable<T> 
{
	public void addAfter(Pos<T> p, T item);
	public void addBefore(Pos<T> p, T item);
	public void addFirst(T item);
	public void addLast(T item);
	public T Remove(Pos<T> p);
	public Pos<T> Search(T p);
	
	public Pos<T> Next(Pos<T> p);
	public Pos<T> Prev(Pos<T> p);
	public Pos<T> First();
	public Pos<T> Last();
	
	public boolean isEmpty();
	public int size();
}