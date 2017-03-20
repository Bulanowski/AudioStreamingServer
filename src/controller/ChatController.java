package controller;

import java.io.IOException;
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
		Date date = new Date();
		String fullMsg = "["+date.toString()+"] "+c.getName() +": "+msg;
		try {
			clientList.sendAll(PackageType.CHAT.getByte(), fullMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
