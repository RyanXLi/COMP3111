package ui.comp3111;

import java.io.Serializable;

import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Parent class for different types of data chart,
 * essentially a wrapper around different JavaFX charts.
 * 
 * @author RyanX
 *
 */
public class DataChart implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructor - an empty constructor
	 */
	public DataChart() {}
	
	
	
	protected NumberAxis xAxis = null;
	protected NumberAxis yAxis = null;
	

	/**
	 * The method of drawing a data chart
	 * @param primaryStage
	 * 			- the primary stage of the application
	 */
	public void draw(Stage primaryStage) {}

}
