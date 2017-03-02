package GUI;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import Item.Item;

public class PriorityPanel extends JPanel
{
	private Painter ItemPainter = new Painter();
	private ArrayList<Item> Items = new ArrayList<Item>();
	private int Width = 500;
	private int Height = 500;
	
	public void SetItems(ArrayList<Item> items)
	{
		Items = items;
		Width = this.getWidth();
		Height = this.getHeight();
		repaint();
	}
	
	public void Repaint()
	{
		Width = this.getWidth();
		Height = this.getHeight();
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		ItemPainter.setGraphics(g);
		ItemPainter.paint(Items, Width, Height);
	}
	
}
