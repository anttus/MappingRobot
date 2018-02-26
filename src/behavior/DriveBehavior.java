package behavior;

import lejos.robotics.subsumption.Behavior;
import server.ServerController;
import server.utility.Color;
import server.utility.Data;

public class DriveBehavior implements Behavior {

	private volatile boolean suppressed = false;
	private volatile boolean stopSpinning = false;
	private boolean first = true;

	private ServerController control;

	/**
	 * Thread to handle distance measurements.
	 * Creates a data object and sends it to the client.
	 */
	private Thread tDistance = new Thread(new Runnable() {
		public void run() {
			while (true) {
				while (!stopSpinning) {
					float[] array = control.getSidesDistance();
					Data data = control.getLocation();
					data.setDistanceForward(array[0]);
					data.setDistanceRight(array[1]);
					data.setDistanceLeft(array[2]);
					control.writeData(data);
				}
			}
		}

	});

	/**
	 * Thread to handle color measurements.
	 * If the color is markedly different from the ground,
	 * creates a data object and sends it to the client.
	 */
	private Thread tColor = new Thread(new Runnable() {
		public void run() {
			while (true) {
				while (!stopSpinning) {
					Color color = control.getColor();
					if (control.detectColor(color)) {
						Data data = control.getLocation();
						data.setR(color.getR());
						data.setG(color.getG());
						data.setB(color.getB());
						control.writeData(data);
					}
				}
			}
		}
	});

	public DriveBehavior(ServerController control) {
		this.control = control;
	}

	//Default behavior, so always true.
	public boolean takeControl() {
		return true;
	}

	public void action() {
		suppressed = false;
		stopSpinning = false;
		//If this is the first time, start the distance and color threads.
		if (first) {
			tDistance.start();
			tColor.start();
			first = false;
		}
		control.travel(2000);

		while (!suppressed) {
			Thread.yield();
		}
		control.stop();

	}

	public void suppress() {
		suppressed = true;
		stopSpinning = true;
	}
}
