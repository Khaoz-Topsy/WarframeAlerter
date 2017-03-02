package Item;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import ADTs.HashTable;

public class ItemInfoGrabber 
{
//	private HashTable OldGuidTable = new HashTable(50);
	private HashTable GuidTable = new HashTable(50);
	
	private boolean IsRunning = false;

	private ArrayList<AlertItem> AlertItems = new ArrayList<AlertItem>();
	private ArrayList<AlertItem> NewAlertItems = new ArrayList<AlertItem>();
	private ArrayList<AlertItem> OldAlertItems = new ArrayList<AlertItem>();
	
	private boolean HasChanged = false;
	
	public ItemInfoGrabber()
	{		
		
	}
	
	public int getNumberItems()
	{
		int TempSize = GuidTable.size();
		if(TempSize < 1)
		{
			int Count = 0;
			try 
			{
				URL url = new URL("http://content.warframe.com/dynamic/rss.php");
			
				InputStream input = url.openStream();
				
				XMLEvent Event = null;
				XMLInputFactory InputFactory = XMLInputFactory.newInstance();
				XMLEventReader EventReader = InputFactory.createXMLEventReader(input);
			
				while(EventReader.hasNext()) 
				{	//Read each line of the XML file
					Event = EventReader.nextEvent();
					
					if(Event.toString().toLowerCase().equals("<item>"))
					{ 
						Count++;
					}
				}
			}
			catch (XMLStreamException e) {} 
			catch (MalformedURLException e) {}
			catch (IOException e) {}
			
			return Count;
		}
		else
		{
			return TempSize;
		}
	}

 	public void ReadXML()
	{	
		try 
		{
			URL url = new URL("http://content.warframe.com/dynamic/rss.php");
			
			InputStream input = url.openStream();
			
			XMLEvent Event = null;
			XMLInputFactory InputFactory = XMLInputFactory.newInstance();
			XMLEventReader EventReader = InputFactory.createXMLEventReader(input);
			
			//Delete and recreate the HashTable
			GuidTable.Clear();
			
			while(EventReader.hasNext()) 
			{	//Read each line of the XML file
				Event = EventReader.nextEvent();
				
				if(Event.toString().toLowerCase().equals("<item>"))
				{ 	//Start of an Item
					String guid = null;
					String title = null;
					String author = null;
					boolean ItemRunning = true;
					
					do
					{	//Load the base information before determining what type of Item it is
						Event = EventReader.nextEvent();
						if(Event.toString().toLowerCase().equals("<guid>"))
						{
							guid = EventReader.nextEvent().toString();
						}
						if(Event.toString().toLowerCase().equals("<title>"))
						{
							title = EventReader.nextEvent().toString();
						}
						if(Event.toString().toLowerCase().equals("<author>"))
						{
							author = EventReader.nextEvent().toString();
//***Alert**********************
							if(author.toLowerCase().equals("alert"))
							{
								String desc = "";
								String pubDate = "";
								String faction = "";
								String expiry = "";
								int ErrorCounter = 0;
								boolean AlertRunning = true;
								
								do
								{
									ErrorCounter++;
									Event = EventReader.nextEvent();
									
									if(Event.toString().toLowerCase().equals("<description>"))
									{
										desc = EventReader.nextEvent().toString();
									}
									if(Event.toString().toLowerCase().contains("<pubdate>"))
									{
										pubDate = EventReader.nextEvent().toString();
									}
									if(Event.toString().toLowerCase().contains("<['http://warframe.com/rss/v1']:wf:faction>"))
									{
										faction = EventReader.nextEvent().toString();
									}
									if(Event.toString().toLowerCase().contains("<['http://warframe.com/rss/v1']:wf:expiry>"))
									{
										expiry = EventReader.nextEvent().toString();
									}

									if(Event.toString().toLowerCase().equals("</item>")){AlertRunning = false;}
									if(ErrorCounter > 50){AlertRunning = false;}
								}while(AlertRunning);
								
//								System.out.println("Alert Added");
								GuidTable.put(guid, new Alert("Alert", guid, title, author, desc, pubDate, faction, expiry));
							}
//***Invasion********************
							if(author.toLowerCase().equals("invasion"))
							{
								String PubDate = null;
								int ErrorCounter = 0;
								boolean InvasionRunning = true;
								
								do
								{
									Event = EventReader.nextEvent();
									if(Event.toString().toLowerCase().equals("<title>"))
									{
										title = EventReader.nextEvent().toString();
									}

									if(Event.toString().toLowerCase().equals("<pubdate>"))
									{
										PubDate = EventReader.nextEvent().toString();
									}
									
									if(Event.toString().toLowerCase().equals("</item>")){InvasionRunning = false;}
									if(ErrorCounter > 50){InvasionRunning = false;}
								}while(InvasionRunning);

//								System.out.println("Invasion Added");
								GuidTable.put(guid, new Invasion("Invasion", guid, title, author, PubDate));
							}
//***Outbreak********************
							if(author.toLowerCase().equals("outbreak"))
							{
								String PubDate = null;
								int ErrorCounter = 0;
								boolean OutbreakRunning = true;
								
								do
								{
									Event = EventReader.nextEvent();
									if(Event.toString().toLowerCase().equals("<title>"))
									{
										title = EventReader.nextEvent().toString();
									}
									if(Event.toString().toLowerCase().equals("<pubdate>"))
									{
										PubDate = EventReader.nextEvent().toString();
									}
									
									if(Event.toString().toLowerCase().equals("</item>")){OutbreakRunning = false;}
									if(ErrorCounter > 50){OutbreakRunning = false;}
								}while(OutbreakRunning);
								
//								System.out.println("Outbreak Added");
								GuidTable.put(guid, new Outbreak("Outbreak",guid, title, author, PubDate));
							}
						}

						if(Event.toString().toLowerCase().equals("</item>")){ItemRunning = false;}
						if(Event.toString().toLowerCase().equals("</channel>")){ItemRunning = false;}
						if(Event.toString().toLowerCase().equals("</rss>")){ItemRunning = false;}
					}while(ItemRunning);
				}
			}
			//Check if there are any new updates
			
			
			if(IsRunning) 
			{
				AlertItems.clear();
				if (IsNewAlerts()) 
				{
					HasChanged = true;
				} 
				else 
				{
					HasChanged = false;
				}
			}
			else
			{
				IsRunning = true;
			}
		} 
		catch (UnknownHostException ex) {System.out.println("Unable to reach Host");}
		catch (IOException ex) {System.out.println("No internet Access");}
		catch (Exception ex){System.out.println("Exception101");}
	}
 	
	public boolean IsNewAlerts()
	{
		boolean Result = false;
		OldAlertItems = NewAlertItems;
		NewAlertItems = GuidTable.GetEmailAlerts();
		
		for(int a=0; a<NewAlertItems.size(); a++) //For each item in the NEW Array
		{
			boolean Found = false;
			for(int b=0; b<OldAlertItems.size(); b++) //Compare to all elements in the OLD array
			{
				if(NewAlertItems.get(a).IsEqualTo(OldAlertItems.get(b)))
				{
					Found = true;
				}
			}
			if(!Found)
			{
				AlertItems.add(NewAlertItems.get(a));
				Result = true;
				System.out.println("New Item Found: " + NewAlertItems.get(a).getTitle());
			}
		}
		
		return Result;
	}
 	
	public boolean HasChanged()
	{
		return HasChanged;
	}
 	
	public int getAlertsSize()
	{
		return AlertItems.size();
	}
 	
	public ArrayList<AlertItem> getAlerts()
	{
		return AlertItems;
	}
 	
	public HashTable getHashTable()
	{
		return GuidTable;
	}
}
