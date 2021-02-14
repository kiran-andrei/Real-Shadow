package com.game.realshadow.RealShadowProject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameLauncher {
	
	public static void main(String[] args) throws IOException {
		
		ServerSocket serverSocket = new ServerSocket(6555);
		Socket socket = serverSocket.accept();
		
		new Application(socket);
		
	}

}
