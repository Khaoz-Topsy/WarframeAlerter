package ADTs;

import Item.Item;

public class ItemEntry 
{
	private String guidKey;
	private Item Item;
	private boolean Empty = true;

	public ItemEntry()
	{
		Empty = true;
	}
	
	public ItemEntry(String Key, Item Item)
	{
		this.guidKey = Key;
		this.Item = Item;
		Empty = false;
	}
		
	public String getGuidKey() {return guidKey;}
	public void setGuidKey(String guidKey) {this.guidKey = guidKey;}
	public Item getItem() {return Item;}
	public void setItem(Item Item) {this.Item = Item;}

	public boolean isEmpty()
	{
		return Empty;
	}

}
