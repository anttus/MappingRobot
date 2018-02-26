package client.gui;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import client.ClientController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientGUI extends Application implements Initializable {

	@FXML private TextArea dataText;
	@FXML private TextArea rgbText;
	@FXML private Canvas mapCanvas;
	@FXML private Canvas colorCanvas;
	@FXML private Button startButton;
	@FXML private Button stopButton;
	private GraphicsContext gc;
	private GraphicsContext gc2;
	private PixelWriter pw;
	private ClientController control;
	private int zoom = 3;

	//Start button
	@FXML protected void handleSubmitButtonAction(ActionEvent event) {
		mapCanvas.setScaleX(zoom);
		mapCanvas.setScaleY(zoom);
		control.connect();
		control.start();
    }

	//Stop button
	@FXML protected void handleStopButtonAction(ActionEvent event) {
		control.disconnect();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("GuiXML.fxml"));
		stage.setTitle("MapperBot 2000");
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
	}

    public static void main(String[] args) throws UnknownHostException, IOException {
        launch(args);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gc = mapCanvas.getGraphicsContext2D();
		gc2 = colorCanvas.getGraphicsContext2D();
		pw = gc.getPixelWriter();
		control = new ClientController(this);
	}

	/**
	 * Gets the width of the map canvas
	 * @return double width
	 */
	public double getMaxWidth() {
		return mapCanvas.getWidth();
	}

	/**
	 * Gets the width of the map canvas
	 * @return double width
	 */
	public double getMaxHeight() {
		return mapCanvas.getHeight();
	}

	/**
	 * Draw a square of pixels
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @param c - color of the pixels
	 */
	public void drawPixel(int x, int y, Color c) {
		pw.setColor(x, y, c);
		pw.setColor(x + 1, y, c);
		pw.setColor(x, y + 1, c);
		pw.setColor(x + 1, y + 1, c);
	}

	/**
	 * Draws n amount of pixels in the x-axis direction
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @param c - color of the pixels
	 */
	public void drawPixelsX(int x, int y, Color c) {
		int len = 12;
		for (int i = 0; i < len; i++) {
			pw.setColor(x + i, y, c);
		}
	}

	/**
	 * Draws n amount of pixels in the y-axis direction
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @param c - color of the pixels
	 */
	public void drawPixelsY(int x, int y, Color c) {
		int n = 12;
		for (int i = 0; i < n; i++) {
			pw.setColor(x, y + i, c);
		}
	}

	/**
	 * Draws an x-axis direction line according to the length parameter
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @param c - color of the pixels
	 * @param length = length of the line
	 */
	public void drawPixelsLineX(int x, int y, Color c, int length) {
		for (int i = 0; i < length; i++) {
			pw.setColor(x, y + i , c);
		}
	}

	/**
	 * Draws a y-axis direction line according to the length parameter
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @param c - color of the pixels
	 * @param length = length of the line
	 */
	public void drawPixelsLineY(int x, int y, Color c, int length) {
		for (int i = 0; i < length; i++) {
			pw.setColor(x + i, y , c);
		}
	}

	/**
	 * Appends the data information to the text field
	 * @param String data
	 */
	public void setData(String data) {
		dataText.appendText(data);
	}

	public void readData() {
		control.readData();
	}

	/**
	 * Sets the colof of the color canvas
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void setColor(int red, int green, int blue) {
		gc2.strokeRect(0, 0, colorCanvas.getWidth(), colorCanvas.getHeight());
		gc2.setFill(Color.rgb(red, green, blue));
		gc2.fillRect(0, 0, colorCanvas.getWidth(), colorCanvas.getHeight());
	}

	public Color getColor() {
		return (Color) gc2.getFill();
	}

	public String getColorString() {
		return (getColor().getRed() + ", " + getColor().getGreen() + ", " + getColor().getBlue());
	}

	public void setRgbText(String text) {
//		rgbText.setStyle("-fx-text-inner-color: rgb(" + getColorString() + ");");
		rgbText.appendText(text);
	}

}
