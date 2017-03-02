package Item;

import java.util.StringTokenizer;

public class Information 
{
	private String Money = "N/A";
	private String Reward = "N/A";
	private String RewardType = "N/A";
	private String Location = "N/A";
	private String Description = "N/A";
	private String Time = "N/A";
	private String Versus = "N/A";

	private String EmailAlertType = "N/A";
	private boolean EmailAlert = false;
	
	public Information(String Line)
	{
		AddLine(Line);
	}
	
	public void AddLine(String Line)
	{
		StringTokenizer ST = new StringTokenizer(Line," ");
		String Current = ST.nextToken();
		String Old = null;
		boolean EOL = false;
		do
		{
			if(Current.contains("cr"))
			{
				String TempMoney = Current.substring(0, Current.length()-2);
				if(isNumeric(TempMoney))
				{
					Money = TempMoney;					
				}
			}
			
			if(Current.contains("m"))
			{
				if(isNumeric(Current.substring(0, Current.length()-1)))
				{
					Time = Current;					
				}
			}

			if(Current.contains("(Mod)")) 		
			{
				StringTokenizer RewardST = new StringTokenizer(Line,"-");
				Reward = RewardST.nextToken();
				EmailAlertType = "Mod";
				RewardType = "Mod";
				EmailAlert = true;
			}
			if(Current.contains("(Key)"))		
			{
				StringTokenizer RewardST = new StringTokenizer(Line,"-");
				Reward = RewardST.nextToken();
				EmailAlertType = "Key";
				RewardType = "Key";
				EmailAlert = true;
			}
			if(Current.contains("(Aura)"))			
			{
				StringTokenizer RewardST = new StringTokenizer(Line,"-");
				Reward = RewardST.nextToken();
				EmailAlertType = "Aura";
				RewardType = "Aura";
				EmailAlert = true;
			}
			if(Current.contains("(Item)"))			
			{
				StringTokenizer RewardST = new StringTokenizer(Line,"-");
				Reward = RewardST.nextToken();
				EmailAlertType = "Item";
				RewardType = "Item";
				EmailAlert = true;
			}
			if(Current.contains("(Blueprint)"))		
			{
				StringTokenizer RewardST = new StringTokenizer(Line,"-");
				Reward = RewardST.nextToken();
				if(!RewardType.equals("Vauban"))
				{
					EmailAlertType = "Blueprint";
					RewardType = "Blueprint";
				}
				EmailAlert = true;
			}
			if(Current.contains("Vauban"))		
			{
				StringTokenizer RewardST = new StringTokenizer(Line,"-");
				Reward = RewardST.nextToken();
				EmailAlertType = "Vauban";
				RewardType = "Vauban";
				EmailAlert = true;
			}
			if(Current.contains("(Resource)")) 		
			{
				StringTokenizer RewardST = new StringTokenizer(Line,"-");
				Reward = RewardST.nextToken();
				EmailAlertType = "Resource";
				RewardType = "Resource";
				EmailAlert = true;
			}

			if(LocationCheck.Check(Current))
			{
//				StringTokenizer RewardST = new StringTokenizer(Line,"-");
//				Location = RewardST.nextToken();
				Location = Old + " " + Current;
			}
			
			if(ST.hasMoreTokens())
			{
				Old = Current;
				Current = ST.nextToken();
			}
			else
			{
				EOL = true;
			}
			
		}while(!EOL);
		
		if(Money.equals("N/A"))
		{
			StringTokenizer SecondST = new StringTokenizer(Line,"-");
			Reward = SecondST.nextToken();
		}
		
		if(Versus.equals("N/A"))
		{
			StringTokenizer SecondST = new StringTokenizer(Line,"-");
			Versus = SecondST.nextToken();
		}
	}
	
	private static boolean isNumeric(String Num)
	{
		for(int a=0; a<Num.length(); a++)
	    {
	        if(!Character.isDigit(Num.charAt(a)))
        	{
	        	return false;
        	}
	    }
	    return true;
	}

	
	public String getMoney() {return Money;}

	public void setMoney(String money) {Money = money;}

	public String getReward() {return Reward;}

	public void setReward(String reward) {Reward = reward;}

	public String getRewardType() {return RewardType;}

	public String getLocation() {return Location;}

	public void setLocation(String location) {Location = location;}

	public String getTime() {return Time;}

	public void setTime(String time) {Time = time;}

	public String getVersus() {return Versus;}

	public void setVersus(String versus) {Versus = versus;}

	public String getDescrip() {return Description;}

	public void setDescrip(String descrip) {Description = descrip;}

	public boolean isEmailAlert() {return EmailAlert;}

	public String getEmailAlertType() {return EmailAlertType;}
	
//	public void setEmailAlert(boolean emailAlert) {EmailAlert = emailAlert;}


}
