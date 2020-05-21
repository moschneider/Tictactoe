/**
 * Sistema Tic Tac Toe 1.0 
 * Criado 2016 por Marvin Schneider
 * Freeware
 */

import java.awt.Color;

/**
 * Classe para administra��o de tabuleiro e an�lises
 * 
 * @author marvin
 *
 */

public class Tabuleiro {
	
	int[][] cor;
	Buttons b;
	
	/**
	 * Cria��o com bot�es
	 * 
	 * O tabuleiro possui duas dimens�es: bot�es para interface com usu�rio e matriz com os dados para c�lculo
	 * 
	 * @param b
	 */
	
	public Tabuleiro(Buttons b)
	{
		this.b=b;
		
		cor = new int[3][3];
		
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				cor[i][j]=Configuracao.VAZIO;
		
		sincronizaDados();	// Tamb�m zerar a matriz
	}
	
	/**
	 * Passar elementos de visualiza��o para a matriz
	 */
	
	public void sincronizaDados()
	{
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				switch(b.getText(i,j))
				{
				case " " : cor[i][j]=Configuracao.VAZIO; break;
				case "X" : cor[i][j]=Configuracao.VERMELHO; break;
				case "O" : cor[i][j]=Configuracao.AZUL; break;
				}
			}
	}
	
	/**
	 * Passar elementos da matriz para a visualiza��o
	 */
	
	public void sincronizaTabuleiro()
	{
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				switch(cor[i][j])
				{
				case Configuracao.VAZIO : b.setCor(i,j,Color.GRAY);
							b.setText(i, j, " ");
							break;
				case Configuracao.VERMELHO : 	b.setCor(i,j,Color.RED);
							b.setText(i, j, "X");
							break;
				case Configuracao.AZUL : b.setCor(i,j,Color.BLUE);
							b.setText(i, j, "O");
							break;
				}
			}
	}
	
	/**
	 * Colocar valor em um item de matriz e passar para visualiza��o
	 * 
	 * @param x
	 * @param y
	 * @param valor
	 */
	
	public void setItem(int x, int y, int valor)
	{
		cor[x][y]=valor;
		sincronizaTabuleiro();
	}
	
	/**
	 * Coloca valor apenas na matriz
	 * 
	 * @param x
	 * @param y
	 * @param valor
	 */
	
	public void setItemAnalise(int x, int y, int valor)
	{
		cor[x][y]=valor;
	}
	
	/**
	 * Coloca vazio em um elemento da matriz (apenas para maior legibilidade)
	 * 
	 * @param x
	 * @param y
	 */
	
	public void zeraItemAnalise(int x, int y)
	{
		cor[x][y]=Configuracao.VAZIO;
	}
	
	/**
	 * Retorna quantas linhas h� na matriz de uma determinada cor e um determinado n�mero de pe�as 
	 * 
	 * @param vez
	 * @param pecas
	 * @return
	 */
	
	public int getLinhas(int vez, int pecas)
	{
		int flag = 0, pecasProprias = 0, campoVazio = 0;
		
		for(int i=0;i<3;i++)
		{
			// Checar situa��o na vertical
			
			for(int j=0;j<3;j++)
			{
				if(j==0)
				{
					pecasProprias=0;
					campoVazio=0;
				}
				
				if(cor[i][j]==vez) pecasProprias++;
				if(cor[i][j]==Configuracao.VAZIO) campoVazio++;
				
				if(pecasProprias==pecas && campoVazio==(3-pecas))
				{
					flag++;
				}
			}
			
			// Checar situa��o na horizontal
			
			for(int j=0;j<3;j++)
			{
				if(j==0)
				{
					pecasProprias=0;
					campoVazio=0;
				}
				
				if(cor[j][i]==vez) pecasProprias++;
				if(cor[j][i]==Configuracao.VAZIO) campoVazio++;
				
				if(pecasProprias==pecas && campoVazio==(3-pecas))
				{
					flag++;
				}
			}

		}
		
		pecasProprias=0;
		campoVazio=0;
		
		// Checar na diagonal descendo
		
		for(int i=0;i<3;i++)
		{
			if(cor[i][i]==vez) pecasProprias++;
			if(cor[i][i]==Configuracao.VAZIO) campoVazio++;
		}
		
		if(pecasProprias==pecas && campoVazio==(3-pecas))
		{
			flag++;
		}
		
		pecasProprias=0;
		campoVazio=0;
		
		// Checar na diagonal subindo
		
		for(int i=0;i<3;i++)
		{
			if(cor[i][2-i]==vez) pecasProprias++;
			if(cor[i][2-i]==Configuracao.VAZIO) campoVazio++;
		}
		
		if(pecasProprias==pecas && campoVazio==(3-pecas))
		{
			flag++;
		}
		
		return flag;	// Retorna n�mero de linhas conforme configur�o
	}
	
	/**
	 * Invertir a cor da vez
	 * 
	 * @param vez
	 * @return
	 */
	
	public int inverteVez(int vez)
	{
		if(vez==Configuracao.VERMELHO)
			return Configuracao.AZUL; else
				return Configuracao.VERMELHO;
	}
	
	/**
	 * Rotina mestre de execu��o de movimento do computador. Nunca deve ser executada sem testar se o tabuleiro est� 100% cheio antes.
	 * 
	 * Para maior legibilidade n�o muito otimizada (organiza��o em passos bem distintos seguindo algoritmo do v�deo!).
	 * 
	 * @param vez
	 */
	
	public void executeMovimento(int vez)
	{
		int	melhorX = Configuracao.DUMMY, melhorY = Configuracao.DUMMY;
		int antes = 0, melhorPontos = 0, transport=0;
		int vezOriginal = vez;
		
		// Checar linhas de tr�s pe�as pr�prias
		
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(cor[i][j]==Configuracao.VAZIO)
				{
					cor[i][j]=vez;
					
					transport=getLinhas(vez, 3);
					
					if(transport>0)
					{
						cor[i][j]=vez;
						sincronizaTabuleiro();
						return;
					}
					
					cor[i][j]=Configuracao.VAZIO;
					
				}
		
		vez=inverteVez(vez);
		
		// Checar linhas de tr�s pe�as do avers�rio
		
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(cor[i][j]==Configuracao.VAZIO)
				{
					cor[i][j]=vez;
					
					transport=getLinhas(vez, 3);
					
					if(transport>0)
					{
						vez=inverteVez(vez);
						cor[i][j]=vez;
						sincronizaTabuleiro();
						return;
					}
					
					cor[i][j]=Configuracao.VAZIO;
					
				}
		
		vez=inverteVez(vez);
		
		// Checar linhas de duas pe�as pr�prias
		
		if(melhorPontos==0)
		{
			antes=getLinhas(vez, 2);
			
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)
					if(cor[i][j]==Configuracao.VAZIO)
					{
						cor[i][j]=vez;
						
						transport=getLinhas(vez, 2);
						
						if(transport>antes && transport>melhorPontos)
						{
							melhorPontos=transport;
							melhorX=i;
							melhorY=j;
						}
						
						cor[i][j]=Configuracao.VAZIO;
					}
		}
		
		vez=inverteVez(vez);
		
		// Checar linhas de duas pe�as do avers�rio
		
		if(melhorPontos==0)
		{
			antes=getLinhas(vez, 2);
			
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)
					if(cor[i][j]==Configuracao.VAZIO)
					{
						cor[i][j]=vez;
						
						transport=getLinhas(vez, 2);
						
						if(transport>antes && transport>melhorPontos)
						{
							melhorPontos=transport;
							melhorX=i;
							melhorY=j;
						}
						
						cor[i][j]=Configuracao.VAZIO;
					}
		}
		
		vez = vezOriginal;
		
		// Nada ainda? Ent�o jogue em qualquer posi��o vazia
		
		if(melhorPontos==0)
		{
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)
					if(cor[i][j]==Configuracao.VAZIO)
					{
						cor[i][j]=vez;
						sincronizaTabuleiro();
						return;
					}
			
		}
		
		System.out.println("Melhor Pontos: " + melhorPontos + " X: " + melhorX + " Y: " + melhorY);
		
		// Sen�o executar melhor jogada
		
		cor[melhorX][melhorY]=vez; 	// Usar esse m�todo em tabuleiro cheio causa uma jogada numa posi��o ilegal da matriz (-1,-1)!!
		sincronizaTabuleiro();
	}
}
