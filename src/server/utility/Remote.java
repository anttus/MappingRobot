package server.utility;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;

public class Remote {

	public EV3IRSensor sensor;

	public Remote() {
		sensor = new EV3IRSensor(SensorPort.S2);
	}

	public int getRemote() {
		int remote = sensor.getRemoteCommand(0);
		int value;
		switch(remote)
		{
		case 1: // Top left
			value = 1;
			break;
		case 2: // Bottom left
			value = 2;
			break;
		case 3: // Top right
			value = 3;
			break;
		case 4: // Bottom right
			value = 4;
			break;
		case 5: // Top left + top right
			value = 5;
			break;
		case 6: // Top left + bottom right
			value = 6;
			break;
		case 8: // Bottom left + bottom right
			value = 8;
			break;
		case 9: // Centre/beacon (switching)
			value = 9;
			break;
		default:
			value = 0;
			break;
		}
		return value;
	}

}
