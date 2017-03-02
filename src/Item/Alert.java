package Item;

import java.util.StringTokenizer;

public class Alert extends Item
{
	private String Description;
	private String PubDate;
	private String Faction;
	private String Expiry;
	
	public Alert(String Type, String guid, String title, String author, String Description, String PubDate, String faction, String expiry)
	{
		this.Type = Type;
		this.guid = guid;
		this.title = title;
		this.author = author;
		this.Description = Description;
		this.PubDate = PubDate;
		this.Faction = faction;
		this.Expiry = expiry;
		
		StringTokenizer ST = new StringTokenizer(expiry, " ");
		boolean TimeFound = false;
		String Time = null;
		while(!TimeFound)
		{
			String Temp = ST.nextToken();
			if(Temp.contains(":") == true)
			{
				Time = Temp;
				TimeFound = true;
			}
		}
				
		this.setInfo(title);
		this.setDescrip(Description);
		this.setExpiry(Time);
	}
		
	public String getPubDate() {return PubDate;}
	public void setPubDate(String pubDate) {PubDate = pubDate;}
	public String getFaction() {return Faction;}
	public void setFaction(String faction) {this.Faction = faction;}
	
	
}
