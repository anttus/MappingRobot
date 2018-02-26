package server.utility;

public class Color {

	private float r, g, b;
	private int locationX, locationY;

	public Color(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public float getR()
	{
		return r;
	}
	public void setR(float r)
	{
		this.r = r;
	}

	public float getG()
	{
		return g;
	}
	public void setG(float g)
	{
		this.g = g;
	}

	public float getB()
	{
		return b;
	}
	public void setB(float b)
	{
		this.b = b;
	}


	public int getX()
	{
		return locationX;
	}

	public void setX(int x)
	{
		locationX = x;
	}

	public int getY()
	{
		return locationY;
	}

	public void setY(int y) {
		locationY = y;
	}

	public String toString()
	{
		return "R: " + r + " G: " + g + " B:" + b;
	}
}
