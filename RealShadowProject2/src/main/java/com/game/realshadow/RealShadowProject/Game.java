package com.game.realshadow.RealShadowProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {
	
	private int secondsPassed = 0;
	private long delay = 25 ;
	private Timer timer = new Timer();
	private TimerTask timerTask = new TimerTask() {
		@Override
		public void run() {
			playerMovement();
		}

	};
	
	private JFrame application;
	
	private static final int applicationWidth = 600;
	private static final int applicationHeight = applicationWidth;
	private static final int ballSize = 30;
	private static int ballX = (applicationWidth/2)-(ballSize/2);
	private final static int distance = ballSize - 5;
	private static int ballY = applicationHeight/2-ballSize/2;
	
	private static final int playerWidth = 120;
	private static final int playerHeight = 30;
	private static int playerX = (applicationWidth/2)-(playerWidth/2);
	private static final int playerY = applicationHeight-applicationHeight/10;
	private static char playerDirection;
	
	private static final int enemyWidth = 120;
	private static final int enemyHeight = 30;
	private static int enemyX = (applicationWidth/2)-(playerWidth/2);
	private static final int enemyY = (applicationHeight -(applicationHeight-applicationHeight/100))+applicationHeight/ballSize;	
	
	
	Game(JFrame application){
		
		this.application = application;
		this.setPreferredSize(new Dimension(applicationWidth,applicationHeight));
		this.setBackground(Color.black);
		this.setLayout(null);
		
		start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGame(g);
	}
	
	private void drawGame(Graphics g) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image ball = toolkit.getImage("src/main/java/RealShadow-DataImages/ballRes/ball.png");
		Image player = toolkit.getImage("src/main/java/RealShadow-DataImages/player/player.png");
		Image enemy = toolkit.getImage("src/main/java/RealShadow-DataImages/enemy/enemy.png");
		Image map = toolkit.getImage("src/main/java/RealShadow-DataImages/map/map.png");
		g.drawImage(map,0,0,this);
		g.drawImage(ball,ballX,ballY,ballSize,ballSize,this);
		g.drawImage(player,playerX,playerY,playerWidth,playerHeight,this);
		g.drawImage(enemy,enemyX,enemyY,enemyWidth,enemyHeight,this);
		
		repaint();
	}
	
	private void start() {
		timer.scheduleAtFixedRate(timerTask, delay, delay);
		playerDirection();
		keys();
	}
	
	private void playerDirection() {
		
		Random random = new Random();
		int direction = random.nextInt(2);
		if(direction==0)
			playerDirection = 'L';
		else
			playerDirection = 'R';
	}

	private void playerMovement() {
		if(playerDirection=='L' && playerX >= 0)
			playerX -=3;
		else
			playerX +=3;
		if(playerDirection=='R' && playerX <=applicationWidth-playerWidth)
			playerX +=3;
		else
			playerX -=3;
		if(playerX==0)
			playerDirection='R';
		if(playerX==applicationWidth-playerWidth)
			playerDirection='L';
	}
	
	private void keys() {
		application.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_LEFT && playerX>0)
					playerDirection='L';
				if(e.getKeyCode()==KeyEvent.VK_RIGHT && playerX<applicationWidth-playerWidth)
					playerDirection='R';
			}
		});
	}
}
