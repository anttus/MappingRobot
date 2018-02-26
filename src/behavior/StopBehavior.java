package behavior;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import server.ServerController;

public class StopBehavior implements Behavior {

	public static boolean stop = false;
	ServerController control;

	private volatile boolean suppressed  = false;

	public StopBehavior(ServerController control)
	{
		this.control = control;
	}

	public boolean takeControl() {
		return Button.ESCAPE.isDown() || control.getRemoteComm() == 1;
	}

	/**
	 * Stops the robot and plays an indicating sound
	 */
	public void action() {
		suppressed = false;
		System.out.println("CONTROLLED STOP");
		Delay.msDelay(1000);
		Sound.setVolume(5);
		Sound.playNote(Sound.FLUTE, 440, 30);
		Sound.playNote(Sound.FLUTE, 220, 30);
		System.exit(0);
		while (!suppressed) {
			Thread.yield();
		}
	}

	public void suppress() {
		suppressed = true;
	}
}
