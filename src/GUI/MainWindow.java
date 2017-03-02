package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ADTs.HashTable;
import ADTs.ItemEntry;
import ADTs.Pos;
import ADTs.PositionList;
import Email.SendEmailThread;
import Files.DataFile;
import Files.UserFileReader;
import Item.AlertItem;
import Item.Item;
import Item.ItemInfoGrabber;

public class MainWindow extends JFrame implements ComponentListener
{
	private ItemInfoGrabber ItemInfo;// = new ItemInfoGrabber();
	private AlertPanel AlertPnl = new AlertPanel();
	private boolean bAutoUpdate = true;
	private JScrollPane scroll;
	private Timer MyTimer;
	private int TimeMilli = 10000;
	private int TimeLeft = 10000;

	private ArrayList<String> SortList = new ArrayList<String>();

	private JMenuItem Test = new JMenuItem("Test");
	private JCheckBoxMenuItem AutoUpdate = new JCheckBoxMenuItem("Auto Refresh");
	private JMenuItem SetFreq = new JMenuItem("Set Update Frequency");
	private JMenuItem ManualUpdate = new JMenuItem("Manual Update");
	private JMenuItem SendTestEmail = new JMenuItem("Send Test Email");
	private JMenuItem SetEmailPref = new JMenuItem("Set Email Preferences");
	private JCheckBoxMenuItem SendEmailOnRewards = new JCheckBoxMenuItem("Send Email on Rewards");

	private JMenuItem AllItems = new JMenuItem("All Items");
	private JMenuItem AlertItems = new JMenuItem("Alert Items");
	private JMenuItem InvasionItems = new JMenuItem("Invasion Items");
	private JMenuItem InfestationItems = new JMenuItem("Infestation Items");
	private JMenuItem AlertInvasion = new JMenuItem("(Alert + Invasion)");
	
	public MainWindow()
	{
		ItemInfo = new ItemInfoGrabber();
		SetupWindow();
		System.out.println("Setup Complete (MainWindow)");
		SetupTimer(TimeMilli);
		SetupSecTimer();
	}

	private void SetupWindow()
	{
		this.setTitle("Warframe Alert Tracker");
		this.setSize(850, 790);
//		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		addWindowListener(new java.awt.event.WindowAdapter() //On Clicking the top right X
		{
			@Override public void windowClosing(java.awt.event.WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});
		
	    scroll = new JScrollPane(AlertPnl);
	    AlertPnl.setPreferredSize(new Dimension(500,1200)); 
	    setScrollBar(ItemInfo.getNumberItems());
		
		JMenuBar MenuBar = new JMenuBar();
		JMenu File = new JMenu("File");
		JMenu Update = new JMenu("Update");
		JMenu Sort = new JMenu("Sort");
		JMenu Email = new JMenu("Email");

		File.add(Test);
//		File.addSeparator();
		Update.add(AutoUpdate);
		Update.add(SetFreq);
		Update.add(ManualUpdate);

		Sort.add(AllItems);
		Sort.addSeparator();
		Sort.add(AlertInvasion);
		Sort.addSeparator();
		Sort.add(AlertItems);
		Sort.add(InvasionItems);
		Sort.add(InfestationItems);

		Email.add(SendTestEmail);
		Email.add(SetEmailPref);
		Email.add(SendEmailOnRewards);
		
		SetupMenuBar();
		
		MenuBar.add(File);
		MenuBar.add(Update);
		MenuBar.add(Sort);
		MenuBar.add(Email);
		
		this.add(MenuBar, BorderLayout.NORTH);
		this.add(scroll, BorderLayout.CENTER);

		this.setVisible(true);	
		this.addComponentListener(this);
	}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) 
	{
		AlertPnl.Repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {}

	private int ExtractNum(String TimeString)
	{
		StringTokenizer ST = new StringTokenizer(TimeString, " ");
		int Result = Integer.parseInt(ST.nextToken());
		if(Result == 121)
		{
			return TimeMilli/1000;
		}
		return Result;
	}
	
	private void setScrollBar(int Size)
	{
		if(Size > 12)
	    {
		    AlertPnl.setPreferredSize(new Dimension(500,1200)); 
		    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    }
	    else
	    {
			scroll.getVerticalScrollBar().setValue(0);
	    	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//	    	SetNewTimerTime(TimeMilli*1000); //TODO
	    }
	}
	
	private void SetupMenuBar()
	{				
		SortList.clear();
		SortList.add("Alert");
		SortList.add("Invasion");
		SortList.add("Infestation");
		
		Test.setSelected(true);
		Test.addActionListener(new ActionListener()
		{
			@Override 
			public void actionPerformed(ActionEvent e)
			{
//				SendEmail.SendEmail("topsykretness@gmail.com", "Subject", "Text");
				
//				String Test = "T " + "\n" + "E" + "\n" + "S" + "\n" + "T";
//				System.out.println(Test);
			}
		});
		
		AutoUpdate.setSelected(true);
		AutoUpdate.addActionListener(new ActionListener()
		{
			@Override 
			public void actionPerformed(ActionEvent e)
			{
				if(AutoUpdate.isSelected())//Must Auto Update
					setUpdate(true);
				else
					setUpdate(false);
			}
		});
		
		SetFreq.addActionListener(new ActionListener() 
		{
			@Override 
			public void actionPerformed(ActionEvent e)
			{
				SetNewTimerTime(ExtractNum(MyGUIDialog.RefreshRateDialog())*1000);
			}
		});
			
		ManualUpdate.addActionListener(new ActionListener() 
		{
			@Override 
			public void actionPerformed(ActionEvent e)
			{				
				Refresh();
			}
		});
		
		AllItems.addActionListener(new ActionListener() 
		{
			@Override 
			public void actionPerformed(ActionEvent e)
			{
				SortList.clear();
				SortList.add("Alert");
				SortList.add("Invasion");
				SortList.add("Infestation");

				HashTable TempHash = GenerateOrderedItemList(SortHashTableByMission(ItemInfo.getHashTable()));
			    setScrollBar(TempHash.size());
				AlertPnl.SetItems(TempHash);
			}
		});
		
		AlertItems.addActionListener(new ActionListener() 
		{
			@Override 
			public void actionPerformed(ActionEvent e)
			{
				SortList.clear();
				SortList.add("Alert");

				HashTable TempHash = GenerateOrderedItemList(SortHashTableByMission(ItemInfo.getHashTable()));
			    setScrollBar(TempHash.size());
				AlertPnl.SetItems(TempHash);
			}
		});
		
		InvasionItems.addActionListener(new ActionListener() 
		{
			@Override 
			public void actionPerformed(ActionEvent e)
			{
				SortList.clear();
				SortList.add("Invasion");

				HashTable TempHash = GenerateOrderedItemList(SortHashTableByMission(ItemInfo.getHashTable()));
			    setScrollBar(TempHash.size());
				AlertPnl.SetItems(TempHash);
			}
		});
		
		InfestationItems.addActionListener(new ActionListener() 
		{
			@Override 
			public void actionPerformed(ActionEvent e)
			{
				SortList.clear();
				SortList.add("Infestation");

				HashTable TempHash = GenerateOrderedItemList(SortHashTableByMission(ItemInfo.getHashTable()));
			    setScrollBar(TempHash.size());
				AlertPnl.SetItems(TempHash);
			}
		});
		
		AlertInvasion.addActionListener(new ActionListener() 
		{
			@Override 
			public void actionPerformed(ActionEvent e)
			{
				SortList.clear();
				SortList.add("Alert");
				SortList.add("Invasion");

				HashTable TempHash = GenerateOrderedItemList(SortHashTableByMission(ItemInfo.getHashTable()));
			    setScrollBar(TempHash.size());
				AlertPnl.SetItems(TempHash);
			}
		});
		
		SendTestEmail.addActionListener(new ActionListener()
		{
			@Override 
			public void actionPerformed(ActionEvent e)
			{						
				String EmailAddress = JOptionPane.showInputDialog("Enter Test Email address");
				if(EmailAddress == null || (EmailAddress != null && ("".equals(EmailAddress))))   
				{
					System.out.println("Pressed cancel on SendTestEmail");
				}
				else
				{
					SendMail(EmailAddress, "Warfame Alerter Test Email", "Test Mail");
				}
			}
		});
		
		SetEmailPref.addActionListener(new ActionListener()
		{
			@Override 
			public void actionPerformed(ActionEvent e)
			{	
				MyGUIDialog.setEmailPreferences();	
			}
		});
		
		SendEmailOnRewards.addActionListener(new ActionListener()
		{
			@Override 
			public void actionPerformed(ActionEvent e)
			{	//TODO Load Email preferences
				
			}
		});

	}
	
	private void SendMail(String EmailAddress, String Subject, String Text)
	{
		SendEmailThread SendEmail = new SendEmailThread(EmailAddress, Subject, Text);
		SendEmail.run();
	}
	
	private HashTable GenerateOrderedItemList(HashTable MissionTable)
	{	
		HashTable SortedHashTable = new HashTable(50);
		
		for(int a=0; a<SortList.size(); a++)
		{
			if(SortList.get(a).contains("Alert")) 
			{
				PositionList<ItemEntry> MissionTableAlertList = MissionTable.GetList("Alert");
				
				Pos<ItemEntry> CurrentMissionItem = MissionTableAlertList.First();
				for(int b=0; b<MissionTableAlertList.size(); b++)
				{
					SortedHashTable.put(CurrentMissionItem.element().getItem().getGuid(), CurrentMissionItem.element().getItem());
					CurrentMissionItem = MissionTableAlertList.Next(CurrentMissionItem);
				}
			}
			if(SortList.get(a).contains("Invasion")) 
			{
				PositionList<ItemEntry> MissionTableAlertList = MissionTable.GetList("Invasion");
				
				Pos<ItemEntry> CurrentMissionItem = MissionTableAlertList.First();
				for(int b=0; b<MissionTableAlertList.size(); b++)
				{
					SortedHashTable.put(CurrentMissionItem.element().getItem().getGuid(), CurrentMissionItem.element().getItem());
					CurrentMissionItem = MissionTableAlertList.Next(CurrentMissionItem);
				}
			}
			if(SortList.get(a).contains("Infestation")) 
			{
				PositionList<ItemEntry> MissionTableAlertList = MissionTable.GetList("Outbreak");
				
				Pos<ItemEntry> CurrentMissionItem = MissionTableAlertList.First();
				for(int b=0; b<MissionTableAlertList.size(); b++)
				{
					SortedHashTable.put(CurrentMissionItem.element().getItem().getGuid(), CurrentMissionItem.element().getItem());
					CurrentMissionItem = MissionTableAlertList.Next(CurrentMissionItem);
				}
			}
		}
		return SortedHashTable;
	}
	
	private HashTable SortHashTableByMission(HashTable Old)
	{
		HashTable MissionTable = new HashTable(50);
		Iterator<String> strKeys = Old.Keys();
		
		while (strKeys.hasNext())
		{
			String Key = strKeys.next();
			Item Temp = Old.Get(Key);
			MissionTable.put(Temp.getType(), Temp);
		}
		return MissionTable;
	}
	
	public void EmailBasedOnAlerts(ArrayList<AlertItem> AlertItems)
	{
		DataFile dTemp = UserFileReader.readEmailFile(System.getProperty("user.home") + "\\Warframe Alert.txt");
		
		for(int a=0; a<AlertItems.size(); a++)
		{
			if(AlertItems.get(a).getType().equals("Item"))			
			{
				SendMail(dTemp.getEmail(), "Warfame Alerter has found an Item of Interest for you", AlertItems.get(a).getTitle());
			}
			if(AlertItems.get(a).getType().equals("Mod")) 		
			{
				if(dTemp.getMod())
				{
					SendMail(dTemp.getEmail(), "Warfame Alerter has found an Item of Interest for you", AlertItems.get(a).getTitle());
				}
			}
			if(AlertItems.get(a).getType().equals("Key"))		
			{
				if(dTemp.getKey())
				{
					SendMail(dTemp.getEmail(), "Warfame Alerter has found an Item of Interest for you", AlertItems.get(a).getTitle());
				}
			}
			if(AlertItems.get(a).getType().equals("Aura"))			
			{
				if(dTemp.getAura())
				{
					SendMail(dTemp.getEmail(), "Warfame Alerter has found an Item of Interest for you", AlertItems.get(a).getTitle());
				}
			}
			if(AlertItems.get(a).getType().equals("Vauban"))		
			{
				if(dTemp.getVauban())
				{
					SendMail(dTemp.getEmail(), "Warfame Alerter has found an Item of Interest for you", AlertItems.get(a).getTitle());
				}
			}
			if(AlertItems.get(a).getType().equals("Resource")) 		
			{
				if(dTemp.getResource())
				{
					SendMail(dTemp.getEmail(), "Warfame Alerter has found an Item of Interest for you", AlertItems.get(a).getTitle());
				}
			}
			if(AlertItems.get(a).getType().equals("Blueprint"))		
			{
				if(dTemp.getBlueprint())
				{
					SendMail(dTemp.getEmail(), "Warfame Alerter has found an Item of Interest for you", AlertItems.get(a).getTitle());
				}
			}
		}
	}
	
	public void SetNewTimerTime(int Time)
	{
		MyTimer.stop();
		TimeMilli = Time;
		SetupTimer(TimeMilli);
	}

	public void setUpdate(boolean bool)
	{
		bAutoUpdate = bool;
		if(bool == true)
		{
			SetNewTimerTime(TimeMilli); //So that it starts updating immediately
		}
	}

 	public void SetupTimer(int Time)
	{
 		MyTimer = new Timer(Time, new ActionListener() {public void actionPerformed(ActionEvent e) 
 		{
 			TimeLeft = (TimeMilli/1000);
 			Refresh();
 		}});
		MyTimer.setInitialDelay(0);
		MyTimer.start();
	}

 	public void SetupSecTimer()
	{
 		Timer SecTimer = new Timer(1000, new ActionListener() {public void actionPerformed(ActionEvent e) 
 		{
 			if(bAutoUpdate)
			{
 	 			TimeLeft--;
 	 			AlertPnl.SecRefresh(TimeLeft);
 	 			repaint(); 	
			}
 			else
 			{
 	 			AlertPnl.SecRefresh(-1);
 			}
 		}});
 		SecTimer.setInitialDelay(0);
 		SecTimer.start();
	}
 	
 	public void Refresh()
 	{
		if(bAutoUpdate)
		{
 			ItemInfo.ReadXML();
 			
 			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
 			Date date = new Date();
 			System.out.println("Refresh " + dateFormat.format(date));

			HashTable TempHash = GenerateOrderedItemList(SortHashTableByMission(ItemInfo.getHashTable()));
		    setScrollBar(TempHash.size());
			AlertPnl.SetItems(TempHash);
//	 		AlertPnl.Repaint();
			if(SendEmailOnRewards.isSelected())
			{
				if(ItemInfo.HasChanged())
				{
	 				EmailBasedOnAlerts(ItemInfo.getAlerts());
				}
			}
		}
 	}
}
