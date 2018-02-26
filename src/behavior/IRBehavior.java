package behavior;

import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import server.ServerController;
import server.utility.Data;

public class IRBehavior implements Behavior {

	private volatile boolean suppressed = false;
	ServerController control;

	public IRBehavior(ServerController control)
	{
		this.control = control;
	}

	/**
	 * Takes control if the infrared sensor detects distances under 15
	 */
	public boolean takeControl() {
		if(control.getIRDirection() > -20 && control.getIRDirection() < 20) {
			return control.getDistance() < 20;
		}
		else return false;
	}

	/**
	 * Writes the received distances and turns either left, right or 180 degrees depending on the side and front distances
	 */
	public void action() {
		suppressed = false;
		Sound.setVolume(20);
		Sound.playNote(Sound.XYLOPHONE, 440, 50);
		float[] array = control.getSidesDistance();
		Data data = control.getLocation();
		data.setDistanceForward(array[0]);
		data.setDistanceRight(array[1]);
		data.setDistanceLeft(array[2]);
		control.writeData(data);

		if (data.getDistanceLeft() > 30 && data.getDistanceRight() > 30) {
			control.randTurn();
			control.waitForStop();
			Delay.msDelay(10);
		}
		else if (data.getDistanceLeft() > 25 ) {
			control.turn("left");
			control.waitForStop();
			Delay.msDelay(10);
		}
		else if (data.getDistanceRight() > 25) {
			control.turn("right");
			control.waitForStop();
			Delay.msDelay(10);
		}
		else {
			control.turnHalf();
			control.waitForStop();
			Delay.msDelay(10);
		}
	}

	public void suppress() {
		suppressed = true;
	}
}
