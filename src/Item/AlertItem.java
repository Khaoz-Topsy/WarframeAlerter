package Item;

public class AlertItem 
{
	private String Type = "N/A";
	private String Title = "N/A";
	private String GUID = "N/A";
	
	public AlertItem()
	{
		
	}
	
	public AlertItem(String Type, String Title, String GUID)
	{
		this.Type = Type;
		this.Title = Title;
		this.GUID = GUID;
	}
	
	
	
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getGUID() {
		return GUID;
	}
	public void setGUID(String guid) {
		Title = guid;
	}
	
	public boolean IsEqualTo(AlertItem Other) 
	{
		if(this.Type.equals(Other.getType()) == true)
		{
			if(this.Title.equals(Other.getTitle()) == true)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public String toString()
	{
		return "[Type: " + Type + "] [Title: " + Title + "]";
	}
	
	
}
