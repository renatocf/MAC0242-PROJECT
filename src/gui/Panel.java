package gui;

// Graphical Libraries
import java.awt.*;
import java.awt.event.*;
import java.awt.TexturePaint;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;
import java.io.IOException;

// Libraries
import arena.Map;
import parameters.*;

public class Panel extends JPanel 
    implements Game
{
    int MAP_SIZE = 16;
    private Cell[][] cell = new Cell[MAP_SIZE][MAP_SIZE];
    private int width, height, Dx, Dy; 
    
    Panel(int R, int width, int height) 
    {
        this.width = width; 
        this.height = height;
        
        Dx = (int) (2 * R * Math.sin(2 * Math.PI / 6)); 
        Dy = 3* R/2;

        /* TODO: Create an interface for images addresses/names */
        BufferedImage grass = load("/img/Grass.png");
        BufferedImage[] types = { grass }; 

        int Δ  = 0;
        for (int i = 0; i < MAP_SIZE; i++)
            for (int j = 0; j < MAP_SIZE; j++) 
            {
                cell[i][j] = new Cell(
                    Δ + R + i*Dx, R + j*Dy, R, types[0]
                ); 
                Δ = (Δ == 0) ? Dx/2 : 0;
            }
    }
    
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < MAP_SIZE; i++) 
            for (int j = 0; j < MAP_SIZE; j++)
                cell[i][j].draw(g); 
    }
    
    private BufferedImage load(String path)
    {
        try { return ImageIO.read(this.getClass().getResource(path)); 
        
        } catch (IOException e) {
            System.err.println("Error while reading!");
            e.printStackTrace();
        
        } catch (IllegalArgumentException e) {
            System.err.println("Image not found!");
            e.printStackTrace();
        }
        return null;
    }
}
