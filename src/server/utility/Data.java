package server.utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.time.LocalDateTime;

import client.ClientController;
import lejos.robotics.Transmittable;

public class Data implements Transmittable{

    private LocalDateTime time;
    private float x,y;
    private int heading, r, g, b, distanceLeft, distanceForward, distanceRight;
    ClientController control;

    public Data(){}

    public Data(ClientController control) {
    	this.control = control;
    }

    public Data(float x, float y, float heading) {
        this.x = x;
        this.y = y;
        this.heading = convertHeadingFloat(heading);
        r = -1;
        g = -1;
        b = -1;
        distanceForward = -1;
        distanceLeft = -1;
        distanceRight = -1;
    }


    public float getHeading() {
        return heading;
    }

    public void setHeading(float heading) {
        this.heading = convertHeadingFloat(heading);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(float r) {
        this.r = convertColorFloat(r);
    }

    public int getG() {
        return g;
    }

    public void setG(float g) {

        this.g = convertColorFloat(g);
    }

    public int getB() {
        return b;
    }

    public void setB(float b) {
        this.b = convertColorFloat(b);
    }

    public int getDistanceLeft() {
        return distanceRight;
    }

    public void setDistanceLeft(float distanceLeft) {
        this.distanceLeft = convertDistanceFloat(distanceLeft);
    }

    public int getDistanceForward() {
        return distanceForward;
    }

    public void setDistanceForward(float distanceForward) {
        this.distanceForward = convertDistanceFloat(distanceForward);
    }

    public int getDistanceRight() {
        return distanceLeft;
    }

    public void setDistanceRight(float distanceRight) {
        this.distanceRight = convertDistanceFloat(distanceRight);
    }

    public void setTime() {
        time = LocalDateTime.now();
    }

    /**
     * Returns the time variable as a formatted string.
     * @return String formatted time
     */
    public String getTime() {
        return "Aika: " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond();
    }

    /**
     * Casts the parameter as int, performs a sanity check for RGB values (can't be over 255),
     * returns the given float as an int.
     * @param n
     * @return int
     */
    public int convertColorFloat(float n) {
        int num = (int) n;
        if(num > 255) {
            num = 255;
        }
        return num;
    }

    /**
     * Casts the parameter as int, performs a sanity check for Distance values(infinity is now 99 cm.)
     * @param n
     * @return int
     */
    public int convertDistanceFloat(float n) {
        int num = (int) n;
        if(num > 99)
        {
            num = 99;
        }
        return num;
    }

    /**
     * Casts the float parameter to an int and rounds that int to the nearest 10.
     * eg. 179 -> 180.
     * @param n
     * @return int
     */
    public int convertHeadingFloat(float n)
    {
        int num = (int) n;
        if(num > 0) {
            num = Math.round((num + 5) / 10 ) * 10;
        }
        else {
            num = Math.round((num - 5) / 10 ) * 10;
        }
        return num;
    }

    /**
     * Forces distance values exceeding 99 to 99.
     * @param n
     * @return
     */
	public int checkDistance(int n) {
		if(n > 99) {
			n = 99;
		}
		return n;
	}

	/**
	 * Forces sane numbers for RGB values. If a value is less than -1, forces all colors to -1.
	 * If a value is greater than 255, forces that value to 255.
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void checkColor(int red, int green, int blue) {
		int[] colors = {red, green, blue};
		for(int color : colors)
		{
			if(color < -1 ) {
				colors[0] = -1;
				colors[1] = -1;
				colors[2] = -1;
			}
			if (color > 255) {
				color = 255;
			}
		}
		r = colors[0];
		g = colors[1];
		b = colors[2];
	}

	/**
	 * Forces heading values to be between -180 to 180.
	 * @param n
	 * @return checked int.
	 */
	public int checkHeading(int n) {
		if (n > 180) {
			n = 180;
		}
		else if (n < -180) {
			n = -180;
		}
		return n;
	}

	/**
	 * Forces location values to be within 300.
	 * @param float n
	 * @return checked float
	 */
	public float checkLocation(float n) {
		if(n > 300)
		{
			n = 300;
		}
		return n;

	}

    public String colorToString() {
        return "R: " + getR() + ", B: " + getB() + ", G: " + getG();
    }

    public String locationToString() {
        return "Sijainti: X: " + (int) x + " Y: " + (int) y + " H: " + heading;
    }

    public String distanceToString() {
        return "Etäisyydet: LD: " + distanceLeft + " RD: " + distanceRight + " FD: " + distanceForward;
    }

    public String toString() {
        return getTime() + "\t\t" + locationToString() + "\t\t" + distanceToString();
    }

    /**
     * Handles the reading of the object, assigns values to the variables.
     * @param DataInputStream
     */
    @Override
    public void loadObject(DataInputStream dis) throws IOException {
        try {
            x = checkLocation(dis.readFloat());
            y = checkLocation(dis.readFloat());
            heading = checkHeading(dis.readInt());
            r = dis.readInt();
            g = dis.readInt();
            b = dis.readInt();
            checkColor(r, g, b);
            distanceLeft = checkDistance(dis.readInt());
            distanceForward = checkDistance(dis.readInt());
            distanceRight = checkDistance(dis.readInt());
        } catch (EOFException e) {
            control.disconnect();
        } catch (SocketException e) {
        	System.out.println("Connection closed.");
        }
    }

    /**
     * Handles the writing of the object.
     * @param DataOutputStream
     */
    @Override
    public void dumpObject(DataOutputStream dos) throws IOException {
        dos.writeFloat(x);
        dos.writeFloat(y);
        dos.writeInt(heading);
        dos.writeInt(r);
        dos.writeInt(g);
        dos.writeInt(b);
        dos.writeInt(distanceLeft);
        dos.writeInt(distanceForward);
        dos.writeInt(distanceRight);
        dos.flush();
    }
}
