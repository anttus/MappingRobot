package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lejos.utility.Delay;
import server.utility.Data;

public class Server {

	private ServerSocket srv;
	private Socket s;
	private DataInputStream dis;
	private DataOutputStream dos;

	public Server() {
		try {
			srv = new ServerSocket(1111);
			s = srv.accept();
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Receives the messages as string from the client
	 * @return String message
	 */
	public String readMessage() {
		String message = "";
		try {
			message = dis.readUTF();
			Delay.msDelay(100);
			System.out.println("Message received");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * Writes data objects to the client
	 * @param data
	 */
	public void writeData(Data data) {
		try {
			System.out.println("Sending Data");
			data.dumpObject(dos);
			Delay.msDelay(100);
			System.out.println("Data sent");
		} catch (IOException e) {
			System.out.println("Data failed");
		}
	}

}