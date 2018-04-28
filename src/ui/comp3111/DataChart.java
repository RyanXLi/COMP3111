package ui.comp3111;

import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;

public abstract class DataChart {
	
	protected NumberAxis xAxis = null;
	protected NumberAxis yAxis = null;

	public DataChart() {}
	public abstract void draw(Stage primaryStage);
}
