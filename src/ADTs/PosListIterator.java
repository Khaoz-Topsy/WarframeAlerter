package ADTs;

import java.util.Iterator;

public class PosListIterator<T> implements Iterator<T>
{
	iList<T> MyList;
	Pos<T> PlaceHolder;
	boolean valid;
	
	public PosListIterator(PositionList<T> list) 
	{
		this.MyList = list;
		valid = true;
		if (list.isEmpty() == false) 
		{
			this.PlaceHolder = list.First();
		}
	}
	
	@Override
	public boolean hasNext() 
	{
		if(MyList.isEmpty() == true)
		{
			return false;
		}
		
		if(MyList.Next(PlaceHolder) == null)
		{
			return false;
		}
		
		if(valid == false)
		{
			return false;
		}
		
		return true;
	}

	@Override
	public T next() 
	{
		T Element = PlaceHolder.element();
		PlaceHolder = MyList.Next(PlaceHolder);
		return Element;
	}

	@Override
	public void remove() {}

}
