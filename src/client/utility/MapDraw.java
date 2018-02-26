package client.utility;

import client.gui.ClientGUI;
import javafx.scene.paint.Color;
import server.utility.Data;

public class MapDraw {

    private ClientGUI gui;
    private final int WIDTH = 30;
    private Color c = Color.RED;
    private Color c2 = Color.BLACK;
    private final int MAXDIST = 50;
    private final int WALLDIST = 20;
    private final int OFFSET = 10;

    public MapDraw(ClientGUI gui) {
        this.gui = gui;
    }

    public void draw(Data data) {
        int x = (int) data.getX() + ((int)(gui.getMaxWidth() / 2.5));
        int y = (int) data.getY() + ((int)(gui.getMaxHeight() / 1.75));

        int heading = (int)data.getHeading();
        	switch (heading) {
    		case 0:
    			//Positive X
    			gui.drawPixelsX(x, y, c2);
    			if (data.getDistanceLeft() <= MAXDIST) {
    				gui.drawPixelsX(x, y + data.getDistanceLeft(), c);
    			}
    			if (data.getDistanceRight() <= MAXDIST) {
    				gui.drawPixelsX(x, y - data.getDistanceRight(), c);
    			}
    			if (data.getDistanceForward() <= WALLDIST) {
    				gui.drawPixelsLineX((x + data.getDistanceForward() + OFFSET), y - (WIDTH/2),
    						c, WIDTH);
    			}
    			break;
    		case -180:
    		case 180:
    			//Negative X
    			gui.drawPixelsX(x, y, c2);
    			if (data.getDistanceLeft() <= MAXDIST) {
    				gui.drawPixelsX(x, y - data.getDistanceLeft(), c);
    			}
    			if (data.getDistanceRight() <= MAXDIST) {
    				gui.drawPixelsX(x, y + data.getDistanceRight(), c);
    			}
    			if (data.getDistanceForward() <= WALLDIST) {
    				gui.drawPixelsLineX((x - data.getDistanceForward() - OFFSET), y - (WIDTH/2),
    						c, WIDTH);
    			}
    			break;
    		case -90:
    			//Negative Y
    			gui.drawPixelsY(x, y, c2);
    			if (data.getDistanceLeft() <= MAXDIST) {
    				gui.drawPixelsY(x + data.getDistanceLeft(), y, c);
    			}
    			if (data.getDistanceRight() <= MAXDIST) {
    				gui.drawPixelsY(x - data.getDistanceRight(), y, c);
    			}
    			if (data.getDistanceForward() <= WALLDIST) {
    				gui.drawPixelsLineY((x - (WIDTH/2)), (y - data.getDistanceForward() - OFFSET),
    						c, WIDTH);
    			}
    			break;
    		case 90:
    			//Positive Y
    			gui.drawPixelsY(x, y, c2);
    			if (data.getDistanceLeft() <= MAXDIST) {
    				gui.drawPixelsY(x - data.getDistanceLeft(), y, c);
    			}
    			if (data.getDistanceRight() <= MAXDIST) {
    				gui.drawPixelsY(x + data.getDistanceRight(), y, c);
    			}
    			if (data.getDistanceForward() <= WALLDIST) {
    				gui.drawPixelsLineY((x - (WIDTH/2)), (y + data.getDistanceForward() + OFFSET),
    						c, WIDTH);
    			}
    			break;
    		default:
    			break;
    		}

    }

}
