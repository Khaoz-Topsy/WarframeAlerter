package ADTs;

public class Node<T> implements Pos<T> 
{
	private T Element;
	private Node<T> Next;
	private Node<T> Prev;
	
	public Node(Node<T> Next, T Element, Node<T> Prev)
	{
		this.Next = Next;
		this.Prev = Prev;
		this.Element = Element;
	}
	
		
	public T getElement(){return Element;}
	public void setElement(T element){Element = element;}

	public Node<T> getNext() {return Next;}
	public void setNext(Node<T> next) {Next = next;}

	public Node<T> getPrev() {return Prev;}
	public void setPrev(Node<T> prev) {Prev = prev;}

	
	@Override
	public T element() {return Element;}

}
