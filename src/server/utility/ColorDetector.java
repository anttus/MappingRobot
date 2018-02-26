package server.utility;
import java.util.ArrayList;
import java.util.Collections;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

public class ColorDetector {
	private Port port;
	private SensorModes sensor;
	private SampleProvider colorProvider;
	private ArrayList<Color> calibratedColors = new ArrayList<Color>();
	private ArrayList<Float> comparisonList = new ArrayList<Float>(); //List to hold comparison values.

	public ColorDetector()
	{
		port = LocalEV3.get().getPort("S3");
		sensor = new EV3ColorSensor(port);
		colorProvider = ((EV3ColorSensor)sensor).getRGBMode();
	}

	/**
	 * Returns a Color object which includes the r, g and b values of the color
	 * @return Color color
	 */
	public Color getSample()
	{
		float[] sample = new float[colorProvider.sampleSize()];

		colorProvider.fetchSample(sample, 0);
		sample[0] = Math.round(sample[0] * 765); //Change to RGB values.
		sample[1] = Math.round(sample[1] * 765);
		sample[2] = Math.round(sample[2] * 765);

		Color color = new Color(sample[0], sample[1], sample[2]);
		return color;
	}

	/**
	 * Creates the base color to compare to.
	 */
	public void calibrate()
	{
		calibratedColors.add(getSample());
	}

	/**
	 * Takes a sample and compares it to the calibrated base color.
	 * If the color is close enough to the calibrated color, returns true.
	 * @param Color sample
	 * @return boolean
	 */
	public boolean compare(Color sample)
	{
		comparisonList = getComparisonList(sample);

		//Find the index of the lowest value.
		int minIndex = comparisonList.indexOf(Collections.min(comparisonList));

		//If the lowest index is 0, measured color is closest to the first calibrated.
		//If the lowest value is below 50, measured color is close enough to calibrated color.
		if(minIndex == 0 && comparisonList.get(minIndex) < 50) return true;
		else return false;
	}

	/**
	 * Detects if there is a large enough change from the calibrated background color.
	 * @param Color sample
	 * @return boolean
	 */
	public boolean detect(Color sample)
	{
		comparisonList = getComparisonList(sample);
		int minIndex = comparisonList.indexOf(Collections.min(comparisonList));

		if(minIndex == 0 && comparisonList.get(minIndex) > 30) return true;
		else return false;
	}

	public ArrayList<Float> getComparisonList(Color sample)
	{
		comparisonList.clear();										//Clear the comparisonList.
		for(Color calibratedColor : calibratedColors)				//Calculate difference between measurement and calibrated colors
		{
			float r = sample.getR() - calibratedColor.getR();
			float g = sample.getG() - calibratedColor.getG();
			float b = sample.getB() - calibratedColor.getB();

			r *= r;
			g *= g;
			b *= b;

			double comparisonLine = Math.sqrt(r + g + b);
			comparisonList.add((float)comparisonLine);				//Add values to  the comparison list.
		}
		return comparisonList;
	}

	/**
	 * Takes a sample and returns the samples R, G and B values as a string
	 * @return String
	 */
	public String getSampleString()
	{
		return getSample().toString();
	}

	/**
	 * Clears the calibrated colors list.
	 */
	public void resetCalibration()
	{
		calibratedColors.clear();
	}
}
