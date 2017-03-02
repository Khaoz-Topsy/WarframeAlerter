package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import ADTs.HashTable;
import Item.Item;

public class Painter
{
	private Graphics MyG;

//	private int HeadingSpace = 10;
	private int HeadingSpace = 40;
	private int HeightofBlock = 100;
	private int HeightToImage = 5;
	private int WidthToImage = 5;
	private int Width = 0;
	private int Height = 0;
	private Image AlertImage;
	private Image OutbreakImage;
	private Image InvasionImage;
	private Image TempImage;
	
	public Painter()
	{
		AlertImage = new ImageIcon(this.getClass().getResource("/resources/Alert.png")).getImage();
		OutbreakImage = new ImageIcon(this.getClass().getResource("/resources/Infestation.png")).getImage();
		InvasionImage = new ImageIcon(this.getClass().getResource("/resources/Invasion.png")).getImage();
	}
		
	public void setGraphics(Graphics G)
	{
		MyG = G;
	}
	
	private void ColourBackground()
	{
		MyG.setColor(Color.BLACK);
		MyG.fillRect(0, 0, Width, Height);
	}
	
	public void paint(HashTable Table, int Width, int Height) //Unordered Items
	{
		this.Width = Width;
		this.Height = Height;
		
		ColourBackground();
		
		Iterator<String> strKeys = Table.Keys();
		int a = -1;
		
		while (strKeys.hasNext())
		{
			a++;
			String Key = strKeys.next();
			switch(Table.Get(Key).getType())
			{
			case "Alert":
				MyG.setColor(Color.RED);
				TempImage = AlertImage;
				break;
			case "Invasion":
				MyG.setColor(Color.decode("#079BFF"));
				TempImage = InvasionImage;
				break;
			case "Outbreak":
				MyG.setColor(Color.decode("#238F39"));
				TempImage = OutbreakImage;
				break;
			}
			
			if((a%2) == 0) 
			{ //even... 
				MyG.drawRect(10, ((a/2)*HeightofBlock+(a*10))+HeadingSpace, (Width/2)-20, HeightofBlock);
				MyG.drawImage(TempImage, 10+WidthToImage, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+HeightToImage, null);
				if(Table.Get(Key).getTitle().length() < (Width/2)/7)
				{
					MyG.drawString(Table.Get(Key).getTitle(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+15);
					
					if(Table.Get(Key).getType().equals("Alert"))
					{
						MyG.setColor(Color.WHITE);
						MyG.drawString("Description: " + Table.Get(Key).getInfo().getDescrip(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+30);
						MyG.setColor(Color.RED);
					}
					
				}
				else
				{
					String TooLong = Table.Get(Key).getTitle();
					StringTokenizer ST = new StringTokenizer(TooLong, " ");
					int Total = ST.countTokens();
					int HalfTotal = (Total/2);
					String TopLine = "";
					String BottomLine = "";
					for(int b=0; b<HalfTotal; b++)
					{
						TopLine += ST.nextToken() + " ";
					}
					for(int b=HalfTotal; b<Total; b++)
					{
						BottomLine += ST.nextToken() + " ";
					}
					MyG.drawString(TopLine, 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+15);
					MyG.drawString(BottomLine, 65, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+30);
				}

				
//				MyG.setColor(Color.WHITE);
				
				
				if(Table.Get(Key).getType().equals("Invasion"))
				{
					MyG.drawString("Location: " + Table.Get(Key).getInfo().getLocation(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+50);
					StringTokenizer ST = new StringTokenizer(Table.Get(Key).getInfo().getVersus().toString(),"VS.");
					String Fac1 = ST.nextToken();
					String Fac2 = ST.nextToken();
					
					if(Fac1.contains("Grineer")) {MyG.setColor(Color.decode("#E2FF2B"));}//Yellow
					else {MyG.setColor(Color.decode("#00FFFF"));}//Light Blue
					MyG.drawString("" + Fac1, 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+80);
					
					if(Fac2.contains("Grineer")) {MyG.setColor(Color.decode("#E2FF2B"));}//Yellow
					else {MyG.setColor(Color.decode("#00FFFF"));}//Light Blue
					MyG.drawString("" + Fac2.substring(1), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+95);
					
					MyG.setColor(Color.decode("#079BFF"));
				}
				
				if(Table.Get(Key).getType().equals("Outbreak"))
				{
					if(!(Table.Get(Key).getInfo().getMoney().equals("N/A")))
					{
						MyG.drawString("Money: " + Table.Get(Key).getInfo().getMoney(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+50);
					}
					else if(!(Table.Get(Key).getInfo().getReward().equals("N/A")))
						{
							MyG.drawString("Resource: " + Table.Get(Key).getInfo().getReward(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+50);
						}
					MyG.drawString("Location: " + Table.Get(Key).getInfo().getLocation(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+65);
				}
				
				if(Table.Get(Key).getType().equals("Alert"))
				{
					MyG.drawString("Money: " + Table.Get(Key).getInfo().getMoney(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+50);
					MyG.drawString("Time Left: " + Table.Get(Key).getInfo().getTime(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+65);
					MyG.drawString("Location: " + Table.Get(Key).getInfo().getLocation(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+80);
					if(!(Table.Get(Key).getInfo().getReward().equals("N/A")))
					{
						MyG.drawString("Special Reward: " + Table.Get(Key).getInfo().getReward(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+95);
					}
				}
				


			} 
			else 
			{ //odd... 
				MyG.drawRect(Width/2, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace, (Width/2)-20, HeightofBlock);
				MyG.drawImage(TempImage, (Width/2)+WidthToImage, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+HeightToImage, null);
				if(Table.Get(Key).getTitle().length() < (Width/2)/7)
				{
					MyG.drawString(Table.Get(Key).getTitle(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+15);
					
					if(Table.Get(Key).getType().equals("Alert"))
					{
						MyG.setColor(Color.WHITE);
						MyG.drawString("Description: " + Table.Get(Key).getInfo().getDescrip(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+30);
						MyG.setColor(Color.RED);
					}
				}
				else
				{
					String TooLong = Table.Get(Key).getTitle();
					StringTokenizer ST = new StringTokenizer(TooLong, " ");
					int Total = ST.countTokens();
					int HalfTotal = (Total/2);
					String TopLine = "";
					String BottomLine = "";
					for(int b=0; b<HalfTotal; b++)
					{
						TopLine += ST.nextToken() + " ";
					}
					for(int b=HalfTotal; b<Total; b++)
					{
						BottomLine += ST.nextToken() + " ";
					}
					MyG.drawString(TopLine, (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+15);
					MyG.drawString(BottomLine, (Width/2)+45, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+30);
				}

				
//				MyG.setColor(Color.WHITE);
				
				
				if(Table.Get(Key).getType().equals("Invasion"))
				{
					MyG.drawString("Location: " + Table.Get(Key).getInfo().getLocation(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+50);
					StringTokenizer ST = new StringTokenizer(Table.Get(Key).getInfo().getVersus().toString(),"VS.");
					String Fac1 = ST.nextToken();
					String Fac2 = ST.nextToken();
					
					if(Fac1.contains("Grineer")) {MyG.setColor(Color.decode("#E2FF2B"));}//Yellow
					else {MyG.setColor(Color.decode("#00FFFF"));}//Light Blue
					MyG.drawString("" + Fac1, (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+80);
					
					if(Fac2.contains("Grineer")) {MyG.setColor(Color.decode("#E2FF2B"));}//Yellow
					else {MyG.setColor(Color.decode("#00FFFF"));}//Light Blue
					MyG.drawString("" + Fac2.substring(1), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+95);
				}
				
				if(Table.Get(Key).getType().equals("Outbreak"))
				{
					if(!(Table.Get(Key).getInfo().getMoney().equals("N/A")))
					{
						MyG.drawString("Money: " + Table.Get(Key).getInfo().getMoney(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+50);
					}
					else if(!(Table.Get(Key).getInfo().getReward().equals("N/A")))
						{
							MyG.drawString("Resource: " + Table.Get(Key).getInfo().getReward(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+50);
						}
					MyG.drawString("Location: " + Table.Get(Key).getInfo().getLocation(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+65);
				}
				
				if(Table.Get(Key).getType().equals("Alert"))
				{
					MyG.drawString("Money: " + Table.Get(Key).getInfo().getMoney(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+50);
					MyG.drawString("Time Left: " + Table.Get(Key).getInfo().getTime(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+65);
					MyG.drawString("Location: " + Table.Get(Key).getInfo().getLocation(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+80);
					if(!(Table.Get(Key).getInfo().getReward().equals("N/A")))
					{
						MyG.drawString("Special Reward: " + Table.Get(Key).getInfo().getReward(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+95);
					}
				}
			}
		}
		
	}
	
	public void paint(ArrayList<Item> MyItems, int Width, int Height)  //Ordered Items
	{
		this.Width = Width;
		this.Height = Height;
		
		ColourBackground();
		
		for(int a=0; a<MyItems.size(); a++)
		{
			switch(MyItems.get(a).getType())
			{
			case "Alert":
				MyG.setColor(Color.RED);
				TempImage = AlertImage;
				break;
			case "Invasion":
				MyG.setColor(Color.decode("#079BFF"));
				TempImage = InvasionImage; 
				break;
			case "Outbreak":
				MyG.setColor(Color.decode("#238F39"));
				TempImage = OutbreakImage;
				break;
			}
			
			if((a%2) == 0) 
			{ //even... 
				MyG.drawRect(10, ((a/2)*HeightofBlock+(a*10))+HeadingSpace, (Width/2)-20, HeightofBlock);
				MyG.drawImage(TempImage, 10+WidthToImage, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+HeightToImage, null);
				if(MyItems.get(a).getTitle().length() < (Width/2)/7)
				{
					MyG.drawString(MyItems.get(a).getTitle() + " - " + MyItems.get(a).getType(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+15);
				}
				else
				{
					String TooLong = MyItems.get(a).getTitle();
					StringTokenizer ST = new StringTokenizer(TooLong, " ");
					int Total = ST.countTokens();
					int HalfTotal = (Total/2);
					String TopLine = "";
					String BottomLine = "";
					for(int b=0; b<HalfTotal; b++)
					{
						TopLine += ST.nextToken() + " ";
					}
					for(int b=HalfTotal; b<Total; b++)
					{
						BottomLine += ST.nextToken() + " ";
					}
					MyG.drawString(TopLine, 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+15);
					MyG.drawString(BottomLine + " - " + MyItems.get(a).getType(), 65, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+30);
				}
				


				
//				MyG.setColor(Color.WHITE);
				
				
				if(MyItems.get(a).getType().equals("Invasion"))
				{
					MyG.drawString("Location: " + MyItems.get(a).getInfo().getLocation(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+50);
					MyG.drawString("Conflict: " + MyItems.get(a).getInfo().getVersus(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+65);
				}
				
				if(MyItems.get(a).getType().equals("Outbreak"))
				{
					if(!(MyItems.get(a).getInfo().getMoney().equals("N/A")))
					{
						MyG.drawString("Money: " + MyItems.get(a).getInfo().getMoney(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+50);
					}
					else if(!(MyItems.get(a).getInfo().getReward().equals("N/A")))
						{
							MyG.drawString("Resource: " + MyItems.get(a).getInfo().getReward(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+50);
						}
					MyG.drawString("Location: " + MyItems.get(a).getInfo().getLocation(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+65);
				}
				
				if(MyItems.get(a).getType().equals("Alert"))
				{
					MyG.drawString("Money: " + MyItems.get(a).getInfo().getMoney(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+50);
					MyG.drawString("Time Left: " + MyItems.get(a).getInfo().getTime(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+65);
					MyG.drawString("Location: " + MyItems.get(a).getInfo().getLocation(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+80);
					if(!(MyItems.get(a).getInfo().getReward().equals("N/A")))
					{
						MyG.drawString("Special Reward: " + MyItems.get(a).getInfo().getReward(), 50, ((a/2)*HeightofBlock+(a*10))+HeadingSpace+95);
					}
				}
			} 
			else 
			{ //odd... 
				MyG.drawRect(Width/2, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace, (Width/2)-20, HeightofBlock);
				MyG.drawImage(TempImage, (Width/2)+WidthToImage, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+HeightToImage, null);
				if(MyItems.get(a).getTitle().length() < (Width/2)/7)
				{
					MyG.drawString(MyItems.get(a).getTitle() + " - " + MyItems.get(a).getType(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+15);
				}
				else
				{
					String TooLong = MyItems.get(a).getTitle();
					StringTokenizer ST = new StringTokenizer(TooLong, " ");
					int Total = ST.countTokens();
					int HalfTotal = (Total/2);
					String TopLine = "";
					String BottomLine = "";
					for(int b=0; b<HalfTotal; b++)
					{
						TopLine += ST.nextToken() + " ";
					}
					for(int b=HalfTotal; b<Total; b++)
					{
						BottomLine += ST.nextToken() + " ";
					}
					MyG.drawString(TopLine, (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+15);
					MyG.drawString(BottomLine + " - " + MyItems.get(a).getType(), (Width/2)+45, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+30);
				}
				
				
				if(MyItems.get(a).getType().equals("Invasion"))
				{
					MyG.drawString("Location: " + MyItems.get(a).getInfo().getLocation(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+50);
					MyG.drawString("Conflict: " + MyItems.get(a).getInfo().getVersus(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+65);
				}
				
				if(MyItems.get(a).getType().equals("Outbreak"))
				{
					if(!(MyItems.get(a).getInfo().getMoney().equals("N/A")))
					{
						MyG.drawString("Money: " + MyItems.get(a).getInfo().getMoney(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+50);
					}
					else if(!(MyItems.get(a).getInfo().getReward().equals("N/A")))
						{
							MyG.drawString("Resource: " + MyItems.get(a).getInfo().getReward(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+50);
						}
					MyG.drawString("Location: " + MyItems.get(a).getInfo().getLocation(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+65);
				}
				
				if(MyItems.get(a).getType().equals("Alert"))
				{
					MyG.drawString("Money: " + MyItems.get(a).getInfo().getMoney(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+50);
					MyG.drawString("Time Left: " + MyItems.get(a).getInfo().getTime(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+65);
					MyG.drawString("Location: " + MyItems.get(a).getInfo().getLocation(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+80);
					if(!(MyItems.get(a).getInfo().getReward().equals("N/A")))
					{
						MyG.drawString("Special Reward: " + MyItems.get(a).getInfo().getReward(), (Width/2)+40, (((a-1)/2)*HeightofBlock+((a-1)*10))+HeadingSpace+95);
					}
				}
			}
		}
		
	}

	public void paint(int Time)  
	{
		MyG.setColor(Color.YELLOW);
		MyG.drawRect(10, 6, 170, 25);
		MyG.drawString(SecondsToString(Time) + " Seconds before refresh", 15, 23);
	}
	
	private String SecondsToString(int Time)
	{
		String Result = "";
		String Spacer = " ";
		if(Time < 1000)
		{
			Result += Spacer;
		}
		if(Time < 100)
		{
			Result += Spacer;
		}
		if(Time < 10)
		{
			Result += Spacer;
		}
		
		return Result + Time;
	}
	
	public int getHeadingSpace() {
		return HeadingSpace;
	}

	public void setHeadingSpace(int headingSpace) {
		HeadingSpace = headingSpace;
	}

	public int getHeightofBlock() {
		return HeightofBlock;
	}

	public void setHeightofBlock(int heightofBlock) {
		HeightofBlock = heightofBlock;
	}

	public int getWidth() {
		return Width;
	}

	public void setWidth(int width) {
		Width = width;
	}

	public int getHeight() {
		return Height;
	}

	public void setHeight(int height) {
		Height = height;
	}
	
	
}
