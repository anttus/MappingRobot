package server.utility;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;

public class Infrared {

	SampleProvider distance;
	public EV3IRSensor sensor;

	public Infrared() {
		sensor = new EV3IRSensor(SensorPort.S1);
		distance = sensor.getDistanceMode();
	}

	/**
	 * Returns the measured distance as float
	 * @return float distance
	 */
	public float getDistance() {
		float[] sample = new float[distance.sampleSize()];
		distance.fetchSample(sample, 0);
		return sample[0];
	}

}