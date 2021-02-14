package com.game.realshadow.RealShadowProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {
	
	private long delay = 25 ;
	private long ballDelay = 25;
	private Timer timer = new Timer();
	private Timer ballTimer = new Timer();
	private TimerTask timerTask = new TimerTask() {
		@Override
		public void run() {
			playerMovement();
		}

	};
	private TimerTask ballTimerTask = new TimerTask() {

		@Override
		public void run() {
				
		}
		
	};
	
	private JFrame application;
	private Socket socket;
	
	private static final int applicationWidth = 600;
	private static final int applicationHeight = applicationWidth;
	private static final int ballSize = 20;
	private static int ballX = (applicationWidth/2)-(ballSize/2);
	private static int ballY = applicationHeight/2-ballSize/2;
	private static char ballMove = 'D';
	private static char ballSideMove = 'L';
	private static boolean ballDymanicMovement = true;
	private static final int ballSpeed = 1;
	
	private static final int player1Width = 120;
	private static final int player1Height = 30;
	private static int player1X = (applicationWidth/2)-(player1Width/2);
	private static final int player1Y = applicationHeight-applicationHeight/7;
	private static char player1Direction;
	
	private static final int player2Width = 120;
	private static final int player2Height = 30;
	private static int player2X;
	private static final int player2Y = 53;
	private static char player2Direction;
	
	Game(JFrame application,Socket socket) {
		
		this.socket = socket;
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
		Image player1 = toolkit.getImage("src/main/java/RealShadow-DataImages/player/player.png");
		Image player2 = toolkit.getImage("src/main/java/RealShadow-DataImages/player2/player2.png");
		Image map = toolkit.getImage("src/main/java/RealShadow-DataImages/map/map.png");
		g.drawImage(map,0,0,applicationWidth,applicationHeight,this);
		g.drawImage(ball,ballX,ballY,ballSize,ballSize,this);
		g.drawImage(player1,player1X,player1Y,player1Width,player1Height,this);
		g.drawImage(player2,player2X,player2Y,player2Width,player2Height,this);
		
		g.setColor(Color.yellow);
		g.drawLine(462, 0, 462, 60);
		
		ballMovement();
		
		try {
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        player2X = dis.readInt();
        player2Direction = dis.readChar();
        dos.writeInt(player1X);
        dos.writeInt(ballY);
        dos.writeInt(ballX);
        dos.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		repaint();
	}
	
	private void start() {
		ballTimer.scheduleAtFixedRate(ballTimerTask, ballDelay, ballDelay);
		timer.scheduleAtFixedRate(timerTask, delay, delay);
		player1Direction();
		keys();
	}
	
	private void player1Direction() {
		
		Random random = new Random();
		int direction1 = random.nextInt(2);
		if(direction1==0)
			player1Direction = 'L';
		else
			player1Direction = 'R';
	}

	private void playerMovement() {
		if(player1Direction=='L' && player1X >= 0)
			player1X -=3;
		else
			player1X +=3;
		if(player1Direction=='R' && player1X <=applicationWidth-player1Width)
			player1X +=3;
		else
			player1X -=3;
		if(player1X==0)
			player1Direction='R';
		if(player1X==applicationWidth-player1Width)
			player1Direction='L';
	}
	
	private void keys() {
		application.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_LEFT && player1X>0)
					player1Direction='L';
				if(e.getKeyCode()==KeyEvent.VK_RIGHT && player1X<applicationWidth-player1Width)
					player1Direction='R';
			}
		});
	}
	
	private void ballMovement() {
		
		if(ballDymanicMovement==true) {
			if(ballSideMove=='L')
				ballX-=ballSpeed;
			if(ballSideMove=='R')
				ballX+=ballSpeed;
			if(ballX==0) {
				ballSideMove='R';
			}
			if(ballX==applicationWidth-ballSize) {
				ballSideMove='L';
			}
		}
		
		if(ballMove=='D' && ballY<=applicationHeight-ballSize) {
			
			if(ballY==applicationHeight-ballSize)
				ballMove='U';
			
			ballY++;
			
		}
		if(ballMove=='U' && ballY>=0) {
			
			if(ballY==0)
				ballMove='D';
			
			ballY--;
			
		}
		
		playerCollision();

	}
		
	private void playerCollision() {

		
		if(ballY==player1Y-ballSize/2 && ballMove=='D' && ballX>player1X-10 && ballX<player1X+player1Width) {
			ballMove='U';
			ballSideMove=player1Direction;
		}
		if(ballY==player2Y+ballSize/2 && ballMove=='U' && ballX>player2X-10 && ballX<player2X+player2Width) {
			ballSideMove=player2Direction;
			ballMove='D';
		}
		
		targetBall();
		System.out.println("X:" + ballX);
		System.out.println("Y:" + ballY);
		System.out.println("DIR:" + ballMove);
	}
	
	private void targetBall() {
		
		if(ballSideMove=='R' && ballY<=60 && ballX==111)
			ballSideMove='L';
		if(ballSideMove=='L' && ballY<=60 && ballX==472)
			ballSideMove='R';
		if(ballSideMove=='R' && ballY>=540 && ballX==111)
			ballSideMove='L';
		if(ballSideMove=='L' && ballY>=540 && ballX==472)
			ballSideMove='R';
		if(ballSideMove=='L' && ballY<=60 && ballX==140)
			ballSideMove='R';
		
		if(ballSideMove=='L' && ballY<=540 && ballX==140)
			ballSideMove='R';
		

		
	}
	
}
