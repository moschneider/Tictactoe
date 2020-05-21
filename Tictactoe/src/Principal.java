/**
 * Sistema Tic Tac Toe 1.0 
 * Criado 2016 por Marvin Schneider
 * Freeware
 */


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Classe Principal
 * 
 * @author marvin
 *
 */

public class Principal {
	
	int vez = Configuracao.VERMELHO; // Jogador na vez
	int primeiroJogador = Configuracao.JOGADOR, segundoJogador = Configuracao.COMPUTADOR; // Jogadores, que devem jogar a partida
	Color ganhador = Color.GRAY; // Cinza = ninguém ainda ganhou
	boolean tabuleiroAberto = true; // Ainda pode jogar?
	Tabuleiro tabuleiro; // Classe com rotinas referentes ao tabuleiro
	
	Buttons b;
	
	/**
	 * Variáveis para montagem da janela
	 */
	
	public JFrame janelaPrincipal;
	public JMenuBar menuBar;
	public JMenu arquivo, jogo;
	public JMenuItem arquivoExit, jogoReiniciarJogadorComputador, jogoReiniciarComputadorJogador, jogoReiniciarJogadorJogador, jogoReiniciarComputadorComputador;
	
	/**
	 * Construtor com montagem da janela
	 */
	
	public Principal()
	{
		b = new Buttons();
		
		janelaPrincipal = new JFrame("Tic Tac Toe");
		janelaPrincipal.setSize(400, 400);
		janelaPrincipal.setLayout(new GridLayout(3,3));
		janelaPrincipal.setLocationRelativeTo(null);	// centralizar a janela
		
		menuBar = new JMenuBar();
		arquivo = new JMenu("Arquivo");
		jogo = new JMenu("Jogo");
		menuBar.add(arquivo);
		menuBar.add(jogo);
		arquivoExit = new JMenuItem("Exit");
		arquivo.add(arquivoExit);
		jogoReiniciarJogadorComputador = new JMenuItem("Novo Jogo - Jogador vs. Computador");
		jogoReiniciarComputadorJogador = new JMenuItem("Novo Jogo - Computador vs. Jogador");
		jogoReiniciarJogadorJogador = new JMenuItem("Novo Jogo - Jogador vs. Jogador");
		jogoReiniciarComputadorComputador = new JMenuItem("Novo Jogo - Computador vs. Computador");
		jogo.add(jogoReiniciarJogadorJogador);
		jogo.add(jogoReiniciarJogadorComputador);
		jogo.add(jogoReiniciarComputadorJogador);
		jogo.add(jogoReiniciarComputadorComputador);
		jogoReiniciarJogadorJogador.addActionListener(new ReiniciarListener(Configuracao.JOGADOR,Configuracao.JOGADOR));
		jogoReiniciarJogadorComputador.addActionListener(new ReiniciarListener(Configuracao.JOGADOR,Configuracao.COMPUTADOR));
		jogoReiniciarComputadorJogador.addActionListener(new ReiniciarListener(Configuracao.COMPUTADOR,Configuracao.JOGADOR));
		jogoReiniciarComputadorComputador.addActionListener(new ReiniciarListener(Configuracao.COMPUTADOR,Configuracao.COMPUTADOR));
		arquivoExit.addActionListener(new ExitListener());
		
		janelaPrincipal.setJMenuBar(menuBar);
		
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				janelaPrincipal.add(b.getButton(i, j));
				b.getButton(i, j).addActionListener(new ButtonListener(i,j));
			}
		
		janelaPrincipal.setVisible(true);
		janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabuleiro = new Tabuleiro(b);
	}
	
	/**
	 * Ligar ou desligar os botões do tabuleiro
	 * 
	 * @param switchOn
	 */
	
	public void changeAllButtons(boolean switchOn)
	{
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(switchOn)
					b.setEnabled(i,j,true); else
						b.setEnabled(i,j,false);
	}
	
	/**
	 * Mostrar uma mensagem de aviso na tela
	 * 
	 * @param texto
	 */
	
	public void mensagem(String texto)
	{
		JOptionPane.showMessageDialog(null, texto,
	             "Aviso", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Checa se o jogo foi ganho por alguém e devolve a respectiva cor (cinza = ninguém ganhou)
	 * @return
	 */
	
	public Color jogoGanho()
	{
		for(int i=0;i<3;i++)
		{
			if(b.getCor(i, 0)!=Color.GRAY)
			{
				// Vertical
				
				if((b.getCor(i,0)==b.getCor(i,1))
					&& (b.getCor(i,1)==b.getCor(i,2)))
						return b.getCor(i,0);
				
				// Horizontal
				
				if((b.getCor(0,i)==b.getCor(1,i))
						&& (b.getCor(1,i)==b.getCor(2,i)))
							return b.getCor(0,i);

			}
		}
		
		// Diagonal descendo
		
		if((b.getCor(0,0)==b.getCor(1,1))
				&& (b.getCor(1,1)==b.getCor(2,2)))
					return b.getCor(0,0);
		
		// Diagonal subindo
		
		if((b.getCor(0,2)==b.getCor(1,1))
				&& (b.getCor(1,1)==b.getCor(2,0)))
					return b.getCor(0,2);

		return Color.GRAY;
	}
	
	// Checa se o tabuleiro está cheia 
	
	public boolean empate()
	{
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				if(b.getCor(i,j)==Color.GRAY)
					return false;
			}
		
		return true;
		
	}
	
	// Reinicia o tabuleiro com as respectivas configurações
	
	class ReiniciarListener implements ActionListener
	{
		int j1, j2;
		
		// Guarda pimeiro e segundo jogar para uso posterior
		
		public ReiniciarListener(int j1, int j2)
		{
			this.j1=j1;
			this.j2=j2;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)
				{
					b.setCor(i,j,Color.GRAY);
					b.setText(i, j, " ");
				}
			
			tabuleiro.sincronizaDados();
			
			tabuleiroAberto=true;
			
			vez=Configuracao.VERMELHO;
			
			primeiroJogador=j1;
			segundoJogador=j2;
			
			System.out.println(primeiroJogador + " " + segundoJogador);
			
			jogue();
		}
		
	}
	
	/**
	 * Listener para saída do jogo
	 * 
	 * @author marvin
	 *
	 */
	
	class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {

			System.exit(0);
		}
		
	}
	
	/**
	 * Rotina que gerencia jogos de dois computadores e quando o computador começa o jogo
	 */
	
	public void jogue()
	{
		if(primeiroJogador==Configuracao.COMPUTADOR && segundoJogador==Configuracao.COMPUTADOR) // IA contra IA
		{
		
			changeAllButtons(false);
		
			while(empate()==false && jogoGanho()==Color.GRAY)
			{
				tabuleiro.executeMovimento(Configuracao.VERMELHO);
		
				if(empate()==false && jogoGanho()==Color.GRAY)
				{
					tabuleiro.executeMovimento(Configuracao.AZUL);
				}
			}
			
			changeAllButtons(true);
			
		} else 
			if(primeiroJogador==Configuracao.COMPUTADOR)	// IA começa a jogar
			{
				changeAllButtons(false);
				tabuleiro.executeMovimento(Configuracao.VERMELHO);
				changeAllButtons(true);
				
				vez=Configuracao.AZUL;
			}
		
		processaFimDeJogo();
	}
	
	/**
	 * Avalia final de jogo
	 */
	
	public void processaFimDeJogo()
	{
		ganhador=jogoGanho();
		
		if(ganhador!=Color.GRAY)
		{
			tabuleiroAberto=false;
			
			if(ganhador==Color.BLUE)
				mensagem("AZUL ganhou o jogo"); else
					mensagem("VERMELHO ganhou o jogo");
			
			return;
		}
		
		if(empate())
		{
			mensagem("Jogo empatado");
			tabuleiroAberto=false;
		}
		
	}
	
	/**
	 * Funcionalidade de cada botão de tabuleiro
	 * 
	 * @author marvin
	 *
	 */
	
	class ButtonListener implements ActionListener
	{
		int x, y;

		public ButtonListener(int x, int y)	// O botão conhece sua posição
		{
			this.x=x;
			this.y=y;
		}
		
		public void actionPerformed(ActionEvent arg0) {

			if(b.getCor(x,y)==Color.GRAY && tabuleiroAberto)	// Posição ainda vazia e jogo ainda não finalizado
			{
				if(vez==Configuracao.VERMELHO)		// Vermelho na vez (primeiro jogador)
				{
					b.setCor(x,y,Color.RED);
					b.setText(x,y,"X");
					tabuleiro.sincronizaDados();
					
					if(segundoJogador==Configuracao.COMPUTADOR && empate()==false && jogoGanho()==Color.GRAY)
					{
						changeAllButtons(false);
						tabuleiro.executeMovimento(Configuracao.AZUL);
						changeAllButtons(true);
					}
					else 
						vez=Configuracao.AZUL;
					
				} else
				{
					b.setCor(x,y,Color.BLUE);		// Azul na vez (segundo jogador)
					b.setText(x,y,"O");
					tabuleiro.sincronizaDados();
					
					if(primeiroJogador==Configuracao.COMPUTADOR && empate()==false && jogoGanho()==Color.GRAY)
					{
						changeAllButtons(false);
						tabuleiro.executeMovimento(Configuracao.VERMELHO);
						changeAllButtons(true);
					} else 		
						vez=Configuracao.VERMELHO;
				}
				
				processaFimDeJogo();
				
			}
		}
		
	}

	public static void main(String[] args) {
		
		new Principal();
	}

}
