package gui;

// Default libraries
import java.awt.*;
import java.awt.event.*;
import java.awt.TexturePaint;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

// Libraries
import parameters.*;
import arena.Map;

public class Graphical extends Frame implements GUI,Game
{
    private boolean firstTime = true;
    private Map map;

    public Graphical(Map map)
    {
        this.map = map;
        setTitle("Polygon");
		setSize(600, 600);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		add(new Panel(30, 600, 600));
		setVisible(true);
    }
    
    public void printMap()
    {
        if(firstTime)
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    //Graphical ar = this
                    //ar.setVisible(true);
                    setVisible(true);
                }
            });
            firstTime = false;
        }
    }
    
    public void paint()
    {
        printMap();
    }
    
    public void printMiniMap(){}
}



