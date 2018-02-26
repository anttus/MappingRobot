package server.utility;

import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;

public class Movement {

	private MovePilot pilot;
	private NewChassis newChassis;
	private int linSpeed = 6;
	private int angSpeed = 650;

	public Movement() {
		newChassis = new NewChassis();
		pilot = new MovePilot(newChassis.getChassis());
		pilot.setLinearSpeed(linSpeed);
		pilot.setAngularSpeed(angSpeed);
	}

	public void setPose(Pose pose)
	{
		newChassis.getChassis().getPoseProvider().setPose(pose);
	}

	/**
	 * Returns the current position as a Pose object
	 * @return Pose pose
	 */
	public Pose getPose() {
		return newChassis.getChassis().getPoseProvider().getPose();
	}

	public void moveStraight(double distanceCm) {
		pilot.travel(distanceCm, true);
	}

	public void turn(String direction) {
		if (direction.equals("right")) {
//			pilot.rotate(270);
			pilot.rotate(90);
			pilot.rotate(90);
			pilot.rotate(90);
		}
		if (direction.equals("left")) {
			pilot.rotate(90);

		}
	}

	public void randomTurn() {
		String direction;
		double dir = Math.round(Math.random());
		if (dir == 1) {direction = "left";} else {direction = "right";}
		turn(direction);
	}

	public void turnHalf() {
//		pilot.rotate(180);
		pilot.rotate(90);
		pilot.rotate(90);
	}

	public void stop() {
		pilot.stop();

	}

	/**
	 * Returns the infrared's spinning motor's angle
	 * @return int angle
	 */
	public int getTacho() {
		return newChassis.getMSpin().getTachoCount();
	}

	/**
	 * Spins the infrared sensor and returns the forward and side distances as an array
	 * @param Infrared ir
	 * @return float[] distances
	 */
	public float[] getSpinDistance(Infrared ir) {
		int limitAngle = 90; //Right
		float[] distances = new float[3];

		distances[0] = ir.getDistance(); //Forwards
		newChassis.getMSpin().rotateTo(limitAngle);
		distances[1] = ir.getDistance(); //Right
		newChassis.getMSpin().rotateTo(-limitAngle);
		distances[2] = ir.getDistance();; //Left
		newChassis.getMSpin().rotateTo(0);
		return distances;
	}

	public void waitForStop() {
		newChassis.waitForStop();
	}

}

