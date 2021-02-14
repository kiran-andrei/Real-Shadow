package com.game.realshadow.RealShadowProject;

import java.net.Socket;

import javax.swing.JFrame;

public class Application {
	
	private JFrame application = new JFrame("RealShadow");
	private Socket socket;
	
	Application(Socket socket){
		
		this.socket = socket;
		application.add(new Game(application,socket));
		application.setResizable(false);
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.pack();
		application.setLocationRelativeTo(null);
		application.setVisible(true);
		
	}

}
