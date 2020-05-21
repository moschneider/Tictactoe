/**
 * Sistema Tic Tac Toe 1.0 
 * Criado 2016 por Marvin Schneider
 * Freeware
 */

/**
 * Classe abrigando funcionalidades básicas dos botões de tabuleiro e algumas rotinas "atalho"
 */

import java.awt.Color;

import javax.swing.JButton;

public class Buttons {

	JButton[][] button;
	
	public Buttons()
	{
		button = new JButton[3][3];
		
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				button[i][j]=new JButton(" ");
				button[i][j].setBackground(Color.GRAY);
			}
	}
	
	public Color getCor(int x, int y)
	{
		return button[x][y].getBackground();
	}
	
	public void setCor(int x, int y, Color c)
	{
		button[x][y].setBackground(c);
	}
	
	public String getText(int x, int y)
	{
		return button[x][y].getText();
	}
	
	public void setText(int x, int y, String texto)
	{
		button[x][y].setText(texto);
	}
	
	public JButton getButton(int x, int y)
	{
		return button[x][y];
	}
	
	public void setEnabled(int x, int y, boolean e)
	{
		button[x][y].setEnabled(e);
	}
}
