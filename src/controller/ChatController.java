package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Client;
import model.ClientList;
import model.PackageType;

public class ChatController {
	private final ClientList clientList;
	
	public ChatController(ClientList clientList) {
		this.clientList = clientList;
	}
	
	public void sendChat(String msg, Client c) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
		String fullMsg = "[" + dateFormat.format(new Date()) + "] " + c.getName() + ": " + msg;
		try {
			clientList.sendAll(PackageType.CHAT.getByte(), fullMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
