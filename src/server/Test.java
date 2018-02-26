package server;

import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import server.utility.NewChassis;

public class Test {

//THIS CLASS IS ONLY FOR TESTING PURPOSES

	public static void main(String[] args) {

		NewChassis c = new NewChassis();
		MovePilot p = new MovePilot(c.getChassis());
		int quarter = 90;
		int half = 180;

		p.setLinearSpeed(6);

//		p.travel(98);

		// 180 degrees
		for (int i = 0; i < 4; i++) {
			p.rotate(half);
		}
		Delay.msDelay(4000);
//		for (int i = 0; i < 4; i++) {
//			p.rotate(-half);
//		}

		// 90 degrees
		for (int i = 0; i < 6; i++) {
			p.rotate(quarter);
		}
		Delay.msDelay(4000);
//		for (int i = 0; i < 6; i++) {
//			p.rotate(-quarter);
//		}


	}

}
