package Item;

public class Invasion extends Item 
{
	protected String PubDate;
	
	public Invasion(String Type, String guid, String title, String author, String PubDate)
	{
		this.Type = Type;
		this.guid = guid;
		this.title = title;
		this.author = author;
		this.PubDate = PubDate;
		
		this.setInfo(title);
	}

	public String getPubDate() {return PubDate;}
	public void setPubDate(String pubDate) {PubDate = pubDate;}
}
