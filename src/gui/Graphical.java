package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.TexturePaint;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

import parameters.*;
import arena.Map;



public class Graphical extends JPanel implements GUI,Game
{
    public void createWindow()
    {
        JFrame frame = new JFrame();
        frame.setTitle("A Robot Game"); 
        
	    frame.setSize(800, 800);
	    
        frame.addWindowListener(new WindowAdapter() 
        { 
		    public void windowClosing(WindowEvent e) 
	        { 
				System.exit(0); 
		    }
	    });
	    Container contentPane = frame.getContentPane();
	    contentPane.add(new Graphical());
	    frame.setVisible(true);     
    }
    
    public void printMap()
    {
        
    }
    
    public void paint()
    {
        try { Thread.sleep(SPEED); } 
        catch (Exception e) 
        {
            System.out.println("Thread Error");
        }
        
        try { printMap(); } 
        catch (Exception e) 
        {
            System.out.println("Print Error");
        }
        
        System.out.println("Saiu!!\n\n\n");
    }
    
    public void printMiniMap(){}

}



