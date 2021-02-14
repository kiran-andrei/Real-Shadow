package com.game.realshadow.RealShadowProject;

import javax.swing.JFrame;

public class Application {
	
	private JFrame application = new JFrame("RealShadow");
	
	Application(){
		
		application.add(new Game(application));
		application.setResizable(false);
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.pack();
		application.setLocationRelativeTo(null);
		application.setVisible(true);
		
	}

}
