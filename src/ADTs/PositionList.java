package ADTs;

import java.util.Iterator;

public class PositionList<T> implements iList<T>
{
	private Node<T> Header = null;
	private Node<T> Footer = null;
	private int size = 0;
	
	public PositionList()
	{
		Header = new Node<T>(null, null, null);
		Footer = new Node<T>(null, null, null);
		Header.setNext(Footer);
		Footer.setPrev(Header);
	}

	@Override
	public void addAfter(Pos<T> p, T item) 
	{
		Node<T> CurrentElem = checkPos(p);
		if(CurrentElem != null)
		{
			Node<T> NewNode = new Node<T>(CurrentElem.getNext(), item, CurrentElem);
			CurrentElem.getNext().setPrev(NewNode);
			CurrentElem.setNext(NewNode);
			size++;
		}
	}

	@Override
	public void addBefore(Pos<T> p, T item)
	{
		Node<T> CurrentElem = checkPos(p);
		if(CurrentElem != null)
		{
			Node<T> NewNode = new Node<T>(CurrentElem, item, CurrentElem.getPrev());
			CurrentElem.getPrev().setNext(NewNode);
			CurrentElem.setPrev(NewNode);
			size++;
		}
	}

	@Override
	public void addFirst(T item)
	{
		addAfter(Header, item);
	}

	@Override
	public void addLast(T item)
	{
		addBefore(Footer, item);
	}

	@Override
	public T Remove(Pos<T> p)
	{
		Node<T> CurrentElem = checkPos(p);
		
		T element = CurrentElem.element();
		CurrentElem.getPrev().setNext(CurrentElem.getNext());
		CurrentElem.getNext().setPrev(CurrentElem.getPrev());
		CurrentElem.setNext(null);
		CurrentElem.setPrev(null);
		size--;
		return element;
	}

	@Override
	public Pos<T> Search(T p) 
	{
		Node<T> currentNode = Header.getNext();
		while (currentNode != Footer) 
		{
			if (currentNode.element().equals(p))
			{
				return currentNode;
			}
			currentNode = currentNode.getNext();
		}
		//If it does not find the correct one, it returns null
		return null;
	}

	@Override
	public Pos<T> Next(Pos<T> p) 
	{
		Node<T> CurrentNode = checkPos(p);
		return CurrentNode.getNext();
	}

	@Override
	public Pos<T> Prev(Pos<T> p) 
	{
		Node<T> CurrentNode = checkPos(p);
		return CurrentNode.getPrev();
	}

	@Override
	public Pos<T> First() 
	{
		if (Header.getNext() == Footer) 
		{
			return null;
		}
		else
		{
			return Header.getNext();
		}
	}

	@Override
	public Pos<T> Last()
	{
		if (Footer.getPrev() == Header) 
		{
			return null;
		}
		else
		{
			return Footer.getPrev();
		}
	}
	
	private Node<T> checkPos(Pos<T> pos)
	{
		if (pos instanceof Node<?>)
		{
			Node<T> newNode = (Node<T>)pos;
			return newNode;
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public Iterator<T> iterator() 
	{
		return new PosListIterator<T>(this);
	}

	@Override
	public boolean isEmpty() 
	{
		if(Header.getNext().equals(Footer))//Empty
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public int size() 
	{
		return size;
	}

}
