package server;

import behavior.DriveBehavior;
import behavior.IRBehavior;
import behavior.StopBehavior;
import lejos.hardware.Sound;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import server.utility.Color;
import server.utility.ColorDetector;
import server.utility.Data;
import server.utility.Infrared;
import server.utility.Movement;
import server.utility.Remote;

public class ServerController {
	private static Movement mov;
	private static Server srv;
	private static Infrared ir;
	private static Remote remote;
	private static ColorDetector detect;
	private static Behavior stopB;
	private static Behavior driveB;
	private static Behavior irB;
	private static ServerController srvControl;

	public static void main(String[] args) {
		srvControl = new ServerController();
		stopB = new StopBehavior(srvControl);
		driveB = new DriveBehavior(srvControl);
		irB = new IRBehavior(srvControl);

		Behavior[] behaviors = { driveB, irB, stopB };

		Arbitrator arbitrator = new Arbitrator(behaviors);
		arbitrator.go();

	}

	public ServerController() {
		detect = new ColorDetector();
		System.out.println("Detector launched");
		remote = new Remote();
		System.out.println("Remote launched");
		ir = new Infrared();
		System.out.println("IR launched");
		mov = new Movement();
		System.out.println("Mov launched");
		Sound.setVolume(25);
		Sound.playNote(Sound.PIANO, 330, 30);
		Sound.playNote(Sound.PIANO, 400, 30);
		Sound.playNote(Sound.PIANO, 500, 30);
		detect.calibrate();
		System.out.println("Waiting client...");
		srv = new Server();
		System.out.println("Server launched");
	}

	/**
	 * @see Movement#getTacho()
	 */
	public int getIRDirection() {
		return mov.getTacho();
	}

	/**
	 * @see ColorDetector#getSample()
	 */
	public Color getColor() {
		return detect.getSample();
	}

	/**
	 * @see ColorDetector#detect(Color)
	 */
	public boolean detectColor(Color color) {
		return detect.detect(color);
	}

	/**
	 * @see Movement#moveStraight(double)
	 */
	public void travel(double distance) {
		mov.moveStraight(distance);
	}

	public void turn(String direction) {
		mov.turn(direction);
	}

	public void randTurn() {
		mov.randomTurn();
	}

	public void turnHalf() {
		mov.turnHalf();
	}

	public void stop() {
		mov.stop();
	}

	/**
	 * @see Movement#getSpinDistance(Infrared)
	 */
	public float[] getSidesDistance() {
		return mov.getSpinDistance(ir);
	}

	/**
	 * @see Infrared#getDistance()
	 */
	public double getDistance() {
		return ir.getDistance();
	}

	public int getRemoteComm() {
		return remote.getRemote();
	}

	/**
	 * Gets the current location of the robot, creates a data object from the location.
	 * @return Data object
	 */
	public Data getLocation() {
		Pose pose = mov.getPose();
		Data data = new Data(pose.getX(), pose.getY(), pose.getHeading());
		return data;
	}

	/**
	 * @see Server#writeData(Data)
	 */
	public void writeData(Data data) {
		srv.writeData(data);
	}

	public void waitForStop() {
		mov.waitForStop();
	}
}
