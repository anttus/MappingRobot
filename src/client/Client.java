package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.Waypoint;
import lejos.utility.Delay;
import server.utility.Data;

public class Client {

	private Socket s;
	private DataOutputStream dos;
	private DataInputStream dis;
	ClientController control;

	/**
	 * Creates the connection and opens the streams.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Client(ClientController control) throws UnknownHostException, IOException {
		this.control = control;
		s = new Socket("10.0.1.1", 1111);
		dos = new DataOutputStream(s.getOutputStream());
		dis = new DataInputStream(s.getInputStream());
	}



	public String readMessage()	{
		String message = "";
		try {
			message = dis.readUTF();
			Delay.msDelay(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * Reads data objects from the robot.
	 * @return Data object
	 */
	public Data readData()
	{
		Data data = new Data(control);
		try {
			data.loadObject(dis);
			data.setTime();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;

	}

	/**
	 * Closes the datastreams.
	 */
	public void close() {
		try {
			dos.close();
			dis.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
