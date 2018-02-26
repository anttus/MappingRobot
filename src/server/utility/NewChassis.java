package server.utility;

import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;

public class NewChassis {

	private RegulatedMotor mR;
	private RegulatedMotor mL;
	private RegulatedMotor mSpin;
	private Wheel wheelL, wheelR;
	private Chassis chassis;
	private double diameter = 3.2;
	private double offset = 8.75;
	private double DELTA = 0.08;

	public NewChassis() {
		mSpin = new EV3MediumRegulatedMotor(BrickFinder.getDefault().getPort("B"));
		mSpin.setSpeed((int)mSpin.getMaxSpeed());
		mR = new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("D"));
        mL = new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("A"));
        wheelL = WheeledChassis.modelWheel(mL, diameter + DELTA).offset(-offset);
        wheelR = WheeledChassis.modelWheel(mR, diameter + DELTA).offset(offset);

        chassis = new WheeledChassis(
        		new Wheel[] { wheelL, wheelR }, WheeledChassis.TYPE_DIFFERENTIAL);
	}

	/**
	 * Returns the chassis object
	 * @return Chassis chassis
	 */
	public Chassis getChassis() {
		return chassis;
	}

	/**
	 * Returns the medium motor as a RegulatedMotor object
	 * @return RegulatedMotor object
	 */
	public RegulatedMotor getMSpin() {
		return mSpin;
	}

	public void waitForStop() {
		mR.waitComplete();
		mL.waitComplete();
	}

}
