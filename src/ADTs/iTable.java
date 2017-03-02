package ADTs;

import java.util.Iterator;

import Item.Item;

public interface iTable
{
	public void Clear();
	public Item Remove(String Key);
	public boolean ContainsKey(String Key);
	public Item Get(String Key);
	public void put(String Key, Item Item);

	public Iterator<String> Keys();
	public Iterator<Item> Items();

	public int size();
	public boolean isEmpty();
}
