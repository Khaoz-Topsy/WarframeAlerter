package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import ADTs.HashTable;
import Item.Item;

public class AlertPanel extends JPanel
{
	private Painter ItemPainter = new Painter();
	private HashTable Table = new HashTable(50);
	private int Width = 500;
	private int Height = 500;
	private int TimeLeft = 1000;
	
	public AlertPanel()
	{
		
	}
	
	public void Repaint()
	{
		Width = this.getWidth();
		Height = this.getHeight();
		repaint();
	}
	
	public void SetItems(HashTable Table)
	{
		this.Table = Table;
		Width = this.getWidth();
		Height = this.getHeight();
		repaint();
	}
	
	public void SecRefresh(int Time)
	{
		TimeLeft = Time+1;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		ItemPainter.setGraphics(g);
		ItemPainter.paint(Table, Width, Height);
		ItemPainter.paint(TimeLeft); 
	}
}
