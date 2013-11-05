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

public class Panel extends JPanel
{
    Cell[][] cell = new Cell[10][10]; 
	int Larg, Alt, Dx, Dy; 
	BufferedImage grama, terra, agua; 
	
	int[][] Terreno = { 
		{ 0, 0, 0, 1, 2, 2, 2, 2, 1, 1},
		 { 0, 0, 1, 1, 2, 2, 2, 2, 1, 1},
		{ 0, 0, 1, 2, 2, 2, 0, 2, 1, 1},
		 { 0, 0, 1, 1, 1, 2, 2, 2, 2, 2},
		{ 0, 0, 0, 0, 1, 2, 2, 2, 2, 2},
		 { 0, 0, 0, 1, 0, 2, 2, 2, 2, 2},
		{ 0, 0, 1, 1, 0, 0, 0, 2, 2, 1},
		 { 0, 0, 1, 1, 0, 0, 2, 2, 2, 1},
		{ 1, 1, 1, 1, 0, 0, 0, 1, 1, 1},
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
	};

	Panel(int L, int W, int H) {
		Dx = (int) (2 * L * Math.sin(2 * Math.PI / 6)); 
		Dy = 3* L/2;
		Larg = W; Alt = H;

		try {
			grama = ImageIO.read(this.getClass().getResource("../data/img/Grass.png"));
		}
		catch (Exception e) {
		    System.out.println("Image not found!");
			System.exit(1);
		}

		BufferedImage[] types = {grama}; 

		int Δ  = 0;
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) 
			{
				cell[i][j] = new Cell(Δ  + L + i*Dx, 
				                       L + j*Dy, L,
				                       types[0]); 
				Δ  = Δ  == 0 ? Dx/2 : 0;				
			}
	}
}
