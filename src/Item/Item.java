package Item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class Item 
{
	protected String Type;
	protected String guid;
	protected String title;
	protected String author;
	protected Information DetailedInfo;
	

	public String getType() {return Type;}
	public void setType(String type) {Type = type;}
	public String getGuid() {return guid;}
	public void setGuid(String guid) {this.guid = guid;}
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public String getAuthor() {return author;}
	public void setAuthor(String author) {this.author = author;}
	
	public Information getInfo() {return DetailedInfo;}
	public void setInfo(String Line) {DetailedInfo = new Information(Line);}
	public void setExpiry(String Time) {DetailedInfo.setTime(CompareTimes(Time));}
	public void setDescrip(String Descrip) {DetailedInfo.setDescrip(Descrip);}
	
	private String CompareTimes(String Time)
	{
		StringTokenizer ST = new StringTokenizer(Time, ":");
		int ExpiryHour = Integer.parseInt(ST.nextToken());
		int ExpiryMinute = Integer.parseInt(ST.nextToken());
		int ExpirySecond = Integer.parseInt(ST.nextToken());
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		int CurrentTimeHour = Integer.parseInt(dateFormat.format(cal.getTime()).toString().substring(0, 2)) + 2; //For the timezone
		int CurrentTimeMinute = Integer.parseInt(dateFormat.format(cal.getTime()).toString().substring(3, 5)); 
		int CurrentTimeSecond = Integer.parseInt(dateFormat.format(cal.getTime()).toString().substring(6, 8)); 

		int TimeLeftHour;
		int TimeLeftMinute;
		int TimeLeftSecond;
		
		if(ExpirySecond >= CurrentTimeSecond) //Supposed to be
		{
			TimeLeftSecond = ExpirySecond - CurrentTimeSecond;
		}
		else
		{
			ExpirySecond = ExpirySecond + 60;
			TimeLeftSecond = ExpirySecond - CurrentTimeSecond;
			CurrentTimeMinute--;
		}
		
		if(ExpiryMinute >= CurrentTimeMinute) //Supposed to be
		{
			TimeLeftMinute = ExpiryMinute - CurrentTimeMinute;
		}
		else
		{
			ExpiryMinute = ExpiryMinute + 60;
			TimeLeftMinute = ExpiryMinute - CurrentTimeMinute;
			CurrentTimeHour--;
		}
		
		TimeLeftHour = ExpiryHour - CurrentTimeHour;
		
		String Result = "";

		if(TimeLeftHour > 0) {Result = Result + " " + TimeLeftHour + "h";}
		if(TimeLeftMinute > 0) {Result = Result + " " + TimeLeftMinute + "m";}
//		if(TimeLeftSecond > 0) {Result = Result + " " + TimeLeftSecond + "s";}
		
//		System.out.println(Result);
		
		return Result;
	}
	
	
}
