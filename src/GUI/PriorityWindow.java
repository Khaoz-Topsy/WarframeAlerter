package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ADTs.HashTable;
import ADTs.ItemEntry;
import ADTs.Pos;
import ADTs.PositionList;
import Item.Item;

public class PriorityWindow extends JFrame implements ComponentListener
{
	private HashTable OldTable = new HashTable(50);
	private HashTable MissionTable = new HashTable(50);
	private ArrayList<String> SortList = new ArrayList<String>();
	private PriorityPanel PriorityPnl = new PriorityPanel();

	private ArrayList<Item> AlertsArray = new ArrayList<Item>();
	private ArrayList<Item> InvasionsArray = new ArrayList<Item>();
	private ArrayList<Item> OutbreaksArray = new ArrayList<Item>();
	private ArrayList<Item> BigOrderedList = new ArrayList<Item>();
	
	private JMenuItem All = new JMenuItem("All");
	private JMenuItem Alerts = new JMenuItem("Alerts");
	private JMenuItem Invasions = new JMenuItem("Invasions");
	private JMenuItem Outbreaks = new JMenuItem("Outbreaks");
	
	public PriorityWindow()
	{
		
	}
	
	public void initialize(HashTable Old, ArrayList<String> SortList)
	{
		OldTable = Old;
		this.SortList = SortList;
		
		this.setTitle("Warframe Alert Tracker (Prioritised Missions)");
		this.setSize(800, 780);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		JMenuBar MenuBar = new JMenuBar();
		JMenu Select = new JMenu("Select");
		
		Select.add(All);
		Select.add(Alerts);
		Select.add(Invasions);
		Select.add(Outbreaks);
		
		MenuBar.add(Select);
		
		SetupMenuBar();
		
		SortHashTableByMission(Old);
		GenerateOrderedItemList(SortList);

		this.add(MenuBar, BorderLayout.NORTH);
		this.add(PriorityPnl, BorderLayout.CENTER);

		this.setVisible(true);	
		PriorityPnl.SetItems(BigOrderedList);
	}

	private void SetupMenuBar()
	{
		All.addActionListener(new ActionListener() 
		{			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				PriorityPnl.SetItems(BigOrderedList);
			}
		});

		Alerts.addActionListener(new ActionListener() 
		{			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				PriorityPnl.SetItems(AlertsArray);
			}
		});

		Invasions.addActionListener(new ActionListener() 
		{			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				PriorityPnl.SetItems(InvasionsArray);
			}
		});

		Outbreaks.addActionListener(new ActionListener() 
		{			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				PriorityPnl.SetItems(OutbreaksArray);
			}
		});
	}
	
	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) 
	{
		PriorityPnl.Repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {}
	
	private void SortHashTableByMission(HashTable Old)
	{
		Iterator<String> strKeys = Old.Keys();
		
		while (strKeys.hasNext())
		{
			String Key = strKeys.next();
			Item Temp = Old.Get(Key);
			MissionTable.put(Temp.getType(), Temp);
		}
	}
	
	private void GenerateOrderedItemList(ArrayList<String> SortList)
	{						
		for(int a=0; a<SortList.size(); a++)
		{
			if(SortList.get(a).contains("Alert")) 
			{
				PositionList<ItemEntry> MissionTableAlertList = MissionTable.GetList("Alert");
				
				Pos<ItemEntry> CurrentMissionItem = MissionTableAlertList.First();
				for(int b=0; b<MissionTableAlertList.size(); b++)
				{
					AlertsArray.add(CurrentMissionItem.element().getItem());
					BigOrderedList.add(CurrentMissionItem.element().getItem());
					CurrentMissionItem = MissionTableAlertList.Next(CurrentMissionItem);
				}
			}
			if(SortList.get(a).contains("Invasion")) 
			{
				PositionList<ItemEntry> MissionTableAlertList = MissionTable.GetList("Invasion");
				
				Pos<ItemEntry> CurrentMissionItem = MissionTableAlertList.First();
				for(int b=0; b<MissionTableAlertList.size(); b++)
				{
					InvasionsArray.add(CurrentMissionItem.element().getItem());
					BigOrderedList.add(CurrentMissionItem.element().getItem());
					CurrentMissionItem = MissionTableAlertList.Next(CurrentMissionItem);
				}
			}
			if(SortList.get(a).contains("Infestation")) 
			{
				PositionList<ItemEntry> MissionTableAlertList = MissionTable.GetList("Outbreak");
				
				Pos<ItemEntry> CurrentMissionItem = MissionTableAlertList.First();
				for(int b=0; b<MissionTableAlertList.size(); b++)
				{
					OutbreaksArray.add(CurrentMissionItem.element().getItem());
					BigOrderedList.add(CurrentMissionItem.element().getItem());
					CurrentMissionItem = MissionTableAlertList.Next(CurrentMissionItem);
				}
			}
		}
	}
}
