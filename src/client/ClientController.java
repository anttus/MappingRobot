package client;

import java.io.IOException;
import java.util.ArrayList;

import client.gui.ClientGUI;
import client.utility.MapDraw;
import javafx.application.Platform;
import server.utility.Data;

public class ClientController {
	private ClientGUI gui;
	private Client client;
	private MapDraw mapDraw;

	//List to store data objects received from the robot.
	private ArrayList<Data> measurements = new ArrayList<Data>();

	//Synchronizing index for getting data from the measurements list.
	private int index = 0;

	public ClientController(ClientGUI gui) {
		this.gui = gui;
		mapDraw = new MapDraw(gui);
	}

	/**
	 * Reads the data object received from the robot.
	 * @return Data object
	 */
	public Data readData() {
		Data data = client.readData();
		return data;
	}

	/**
	 * Thread handling the reading of data object from the robot
	 * and adding them to the measurements list.
	 */
	Thread readThread = new Thread(() -> {
		while (true) {
			Data data = readData();
			measurements.add(data);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}
	});

	/**
	 * Thread handling the updating of received data objects to the GUI
	 */
	Thread updateThread = new Thread(() -> {
		while (true) {
			Platform.runLater(() -> {
				updateGUI();
			});
			try {
				Thread.sleep(10);

			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}
	});

	/**
	 * Starts the threads for reading and updating data objects.
	 */
	public void start() {
		readThread.start();
		updateThread.start();
	}

	public void updateGUI() {

		//Synchronization, if processed data objects
		if (measurements.size() > index) {
			Data data = measurements.get(index);

			//Checks if the data object contains a color or a distance measurement.
			if (checkColor(data))
			{
				gui.setColor(data.getR(), data.getG(), data.getB());
				gui.setRgbText(data.colorToString() + "\n");
			}
			else if(checkDistance(data)){
				mapDraw.draw(data);
				gui.setData(data.toString() + "\n");
			}
			index++;
		}
	}

	/**
	 * Creates a new Client object, which opens a connection to the robot.
	 */
	public void connect() {
		try {
			client = new Client(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Checks if the given data objects rgb values are within acceptable rgb limits.
	 * If the values are -1, the data object is not a color measurement.
	 * @param data
	 * @return true if a color measurement, false if not.
	 */
	public boolean checkColor(Data data) {
		return (data.getR() >= 0 && data.getR() <= 255
				&& data.getG() >= 0 && data.getG() <= 255
				&& data.getB() >= 0 && data.getB() <= 255);
	}

	/**
	 * Checks if the given data objects distance values are within acceptable limits.
	 * If the values are -1, the data object is not a distance measurement.
	 * @param data
	 * @return true if a distance measurement, false if not.
	 */
	public boolean checkDistance(Data data) {
		return (data.getDistanceForward() >= 0 && data.getDistanceForward() <= 99
				&& data.getDistanceLeft() >= 0 && data.getDistanceLeft() <= 99
				&& data.getDistanceRight() >= 0 && data.getDistanceRight() <= 99);
	}

	/**
	 * Closes the connection with the robot, ends the read and update threads.
	 */
	public void disconnect() {
		client.close();
		readThread.interrupt();
		updateThread.interrupt();
	}
}
