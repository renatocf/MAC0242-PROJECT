package gui;

// Graphical Libraries
import java.awt.*;
import java.awt.event.*;
import java.awt.TexturePaint;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

class Cell 
{ 
	Polygon p = new Polygon();
	BufferedImage img;
	Graphics2D GImg;

	Cell(int x, int y, int r, BufferedImage t) 
	{
		img = t;

		for (int i = 0; i < 6; i++)
			p.addPoint(x + (int) (r * Math.sin(i * 2 * Math.PI / 6)),
					   y + (int) (r * Math.cos(i * 2 * Math.PI / 6)));
		
		GImg = img.createGraphics();
	}

	void draw(Graphics g) 
	{ 
		Graphics2D g2d = (Graphics2D) g;
		Rectangle r = new Rectangle(0,0,100,100);
		g2d.setPaint(new TexturePaint(img, r));
		g2d.fill(p);
	}	

	void trans(int dx, int dy) 
	{
		p.translate(dx, dy);
	}
}
