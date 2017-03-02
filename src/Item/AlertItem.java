package Item;

public class AlertItem 
{
	private String Type = "N/A";
	private String Title = "N/A";
	
	public AlertItem()
	{
		
	}
	
	public AlertItem(String Type, String Title)
	{
		this.Type = Type;
		this.Title = Title;
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
