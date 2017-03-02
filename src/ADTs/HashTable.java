package ADTs;

import java.util.ArrayList;
import java.util.Iterator;

import Item.AlertItem;
import Item.Item;

public class HashTable implements iTable
{
	private Object[] HashArray;
	private int AlertSize = 0;
	private int size;
	private int TotalSize;
	
	public HashTable(int Capacity)
	{
		TotalSize = Capacity;
		GenArray(TotalSize);
		
	}
	
	public void GenArray(int createCap)
	{
		Object[] NewArray = new Object[createCap];
		
		for(int a=0; a<createCap; a++)
		{
			NewArray[a] = new PositionList<ItemEntry>();
		}
		HashArray = NewArray;
	}
	
	public void ResizeArray(int newCap)
	{
		Object[] NewArray = new Object[newCap];
		
		for(int a=0; a<newCap; a++)//Creating ItemEntry's in the array
		{
			NewArray[a] = new PositionList<ItemEntry>();
		}
		
		for(int b=0; b<size; b++)//Populating new Array with old Array data
		{
			NewArray[b] = HashArray[b];
		}
		HashArray = NewArray;
	}
	
	private int hash(String str)
	{
		int hash = 0;
		byte[] bytes = str.getBytes();
		
		for (int i = 0; i < str.length(); i++) 
		{
		    hash = hash*25 + str.charAt(i);
		}

		if(hash < 0){hash = -hash;}

//		System.out.println(str + "  =  " +hash % TotalSize);
		return hash % TotalSize;
	}
	
	@Override
	public void Clear() 
	{
		for(int a=0; a<HashArray.length; a++)
		{
			HashArray[a] = null;
		}
		HashArray = null;
		GenArray(TotalSize);
		AlertSize = 0;
		size = 0;
	}

	public HashTable Clone() 
	{
		HashTable NewHashTable = new HashTable(50);
		for(int a=0; a<HashArray.length; a++)
		{
			if(HashArray[a] != null)//Check if the ItemList is null
			{
				PositionList<ItemEntry> TempList = (PositionList<ItemEntry>) HashArray[a];
				if(!TempList.isEmpty())
				{
					Pos<ItemEntry> Current = TempList.First();
					for(int ListCount = 0; ListCount<TempList.size(); ListCount++)//For each ItemEntry in the ItemEntryList
					{
						NewHashTable.put(Current.element().getItem().getGuid(), Current.element().getItem());
						Current = TempList.Next(Current);
					}
				}
			}
		}
		
//		System.out.println(NewHashTable.size());
		
		return NewHashTable;
	}

	public boolean EqualTo(HashTable Old) 
	{
		if(this.AlertSize != Old.AlertSize)
		{
			return false;
		}
		else
		{
			Iterator<String> Keys = this.Keys();
			Iterator<String> OldKeys = Old.Keys();
			while (Keys.hasNext()) 
			{
				if(Keys.next().equals(OldKeys.next()) == false)
				{
					return false;
				}
			}
		}
		
		//Passes all tests, they are equal
		return true;
	}

	@Override
	public Item Remove(String Key) {
		// TODO Auto-generated method stub

//		Keys.remove(Key);
		return null;
	}

	@Override
	public boolean ContainsKey(String Key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Item Get(String Key) 
	{
		int HashKey = (int) hash(Key);
		PositionList<ItemEntry> TempList = new PositionList<ItemEntry>();
		TempList = (PositionList<ItemEntry>) HashArray[HashKey];
		if(TempList.size() > 1) //More than one Item in the ItemEntry List
		{
			Pos<ItemEntry> Current = TempList.First(); //Look at first Item
			for(int a=0; a<TempList.size(); a++)//Go through all the Items in the list
			{
				if(Current.element().getGuidKey().equals(Key))//If the item is found
				{
					return Current.element().getItem();
				}
				else
				{
					Current = TempList.Next(Current);
				}
			}
			
			//If the correct Key is not found
			return null;
		}
		else
		{
			return TempList.First().element().getItem();
		}
	}
	
	public PositionList<ItemEntry> GetList(String Key) 
	{
		int HashKey = (int) hash(Key);
		return (PositionList<ItemEntry>) HashArray[HashKey];
	}

	@Override
	public void put(String Key, Item NewItem) 
	{
		int HashKey = (int) hash(Key);
		PositionList<ItemEntry> TempList = new PositionList<ItemEntry>();
		TempList = (PositionList<ItemEntry>) HashArray[HashKey];
		TempList.addLast(new ItemEntry(Key, NewItem));
		HashArray[HashKey] = TempList;
		size++;
		
		if(NewItem.getType().equals("Alert"))
		{
			AlertSize++;
		}
	}

	@Override
	public Iterator<String> Keys() 
	{
		PositionList<String> BigList = new PositionList<String>();
		for(int ArrCount = 0; ArrCount<HashArray.length; ArrCount++)//For each ItemList in the HashArray
		{
			if(HashArray[ArrCount] != null)//Check if the ItemList is null
			{
				PositionList<ItemEntry> ItemEntryList = (PositionList<ItemEntry>) HashArray[ArrCount]; //Make a variable of the list
				if(!ItemEntryList.isEmpty())
				{
					Pos<ItemEntry> Current = ItemEntryList.First();
					for(int ListCount = 0; ListCount<ItemEntryList.size(); ListCount++)//For each ItemEntry in the ItemEntryList
					{
						BigList.addLast(Current.element().getGuidKey());
						Current = ItemEntryList.Next(Current);
					}
				}
			}
		}
		return BigList.iterator();
	}
	
	public ArrayList<AlertItem> GetEmailAlerts() 
	{
		ArrayList<AlertItem> Result = new ArrayList<AlertItem>();

		for(int a=0; a<HashArray.length; a++)//For each ItemList in the HashArray
		{
			if(HashArray[a] != null)//Check if the ItemList is null
			{
				PositionList<ItemEntry> ItemEntryList = (PositionList<ItemEntry>) HashArray[a]; //Make a variable of the list
				if(!ItemEntryList.isEmpty())
				{
					Pos<ItemEntry> Current = ItemEntryList.First();
					for(int ListCount = 0; ListCount<ItemEntryList.size(); ListCount++)//For each ItemEntry in the ItemEntryList
					{
						if(Current.element().getItem().getInfo().isEmailAlert())
						{
//							System.out.println(Current.element().getItem().getTitle());
							Result.add(new AlertItem(Current.element().getItem().getInfo().getEmailAlertType(), Current.element().getItem().getTitle()));
						}
						Current = ItemEntryList.Next(Current);
					}
				}
			}
		}
		
		return Result;
	}

	@Override
	public Iterator<Item> Items() 
	{
		PositionList<Item> BigList = new PositionList<Item>();
		for(int ArrCount = 0; ArrCount<HashArray.length; ArrCount++)//For each ItemList in the HashArray
		{
			if(HashArray[ArrCount] != null)//Check if the ItemList is null
			{
				PositionList<ItemEntry> ItemEntryList = (PositionList<ItemEntry>) HashArray[ArrCount]; //Make a variable of the list
				if(!ItemEntryList.isEmpty())
				{
					Pos<ItemEntry> Current = ItemEntryList.First();
					for(int ListCount = 0; ListCount<ItemEntryList.size(); ListCount++)//For each ItemEntry in the ItemEntryList
					{
						BigList.addLast(Current.element().getItem());
						Current = ItemEntryList.Next(Current);
					}
				}
			}
		}
		return BigList.iterator();
	}

	@Override
	public int size() 
	{
		return size;
	}
	
	public int GetAlertSize() 
	{
		return AlertSize;
	}

	@Override
	public boolean isEmpty() 
	{
		if(size == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

//	public ArrayList<String> getKeys(){return Keys;}
}
