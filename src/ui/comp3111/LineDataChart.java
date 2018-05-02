package ui.comp3111;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

import com.sun.xml.internal.ws.api.server.Container;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LineDataChart extends DataChart implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private LineChart<Number, Number> lineChart;
	public boolean isFirstFrame = false;
	public double origLowerBound;
	public double origUpperBound;
	public double curLowerBound;
	public double curUpperBound;
	
	final public double ERR = 0.00001;
	final public double timeOfOnePlay = 5; // in seconds
	final public double fps = 30;
	final public double shownRatio = 0.5;
	
	public DataTable dataTable;
	public String xColName;
	public String yColName;
	public String chartTitle;
	
	/**
	 * constructor - a copy construct with the change of the 
	 * x-axis lower and upper bound of the chart. 
	 * 
	 * @param another
	 * 			- the copied LineDataChart
	 * @param lowerBound
	 * 			- the new lower bound of x axis
	 * @param upperBound
	 * 			- the new upper bound of x axis
	 */
	public LineDataChart(LineDataChart another, double lowerBound, double upperBound) {
		this.xAxis = another.xAxis;
		this.yAxis = another.yAxis;
		this.lineChart = another.lineChart;
		this.isFirstFrame = another.isFirstFrame;
		
		this.origLowerBound = another.origLowerBound;
		this.origUpperBound = another.origUpperBound;
		this.curLowerBound = another.curLowerBound; 
		this.curUpperBound= another.curUpperBound; 


	}

	/**
	 * constructor - constructs a LineDataChart that is
	 * ready to be drawn
	 * 
	 * @param dataTable
	 * 			- the reference of the data table to create a chart from
	 * @param xColName
	 * 			- name of the column to be the x-axis of the chart.
	 * 				It has to denote a numeric column
	 * @param yColName
	 * 			- name of the column to be the y-axis of the chart.
	 * 				It has to denote a numeric column
	 * @param chartTitle
	 * 			- the title of the chart to be created
	 * @param animate
	 * 			- whether the line chart should be animated
	 * @param update
	 * 			- whether the construction is a update of a
	 * 				original data chart
	 * @param lowerBound
	 * 			- the lower bound of the x-axis
	 * @param upperBound
	 * 			- the upper bound of the x-axis
	 * @throws DataTableException
	 */
	public LineDataChart(DataTable dataTable, String xColName, String yColName, String chartTitle, boolean animate,
			boolean update, double lowerBound, double upperBound) throws DataTableException {
		super();
		
		this.dataTable = dataTable;
		this.xColName = xColName;
		this.yColName = yColName;
		this.chartTitle = chartTitle;
		
		if (dataTable == null) { 
			throw new DataTableException("dataTable is a null reference");
		} 
		
		DataColumn xCol = dataTable.getCol(xColName);
		DataColumn yCol = dataTable.getCol(yColName);
		
		// Ensure both columns exist and the type is number
		if (xCol == null || yCol == null || !xCol.getTypeName().equals(DataType.TYPE_NUMBER)
				|| !yCol.getTypeName().equals(DataType.TYPE_NUMBER)) {
			throw new DataTableException("Selected data column cannot be processed");
		}
		
		isFirstFrame = animate;

		if (!update) {
			xAxis = new NumberAxis();			
		} else {
			xAxis = new NumberAxis(lowerBound, upperBound, 0.1 * (upperBound - lowerBound));
		}
		xAxis.setForceZeroInRange(false);
		yAxis = new NumberAxis();
		
		xAxis.setLabel(xColName);
		yAxis.setLabel(yColName);
		
		xAxis.setAnimated(false);
		yAxis.setAnimated(false);
		
		lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle(chartTitle);
		
		// defining a series
		
		@SuppressWarnings("rawtypes")
		XYChart.Series series = new XYChart.Series();

		series.setName(xColName + " - " + yColName);

		// populating the series with data		
		Number[] xValues = Arrays.copyOf(xCol.getData(), xCol.getData().length, Number[].class);
		Number[] yValues = Arrays.copyOf(yCol.getData(), yCol.getData().length, Number[].class);
		
		Double[] doubleXValues = new Double[xValues.length];
		for (int i = 0; i < xValues.length; i++) {
			doubleXValues[i] = xValues[i].doubleValue();
		}
		origLowerBound = Collections.min(Arrays.asList(doubleXValues));
		origUpperBound = Collections.max(Arrays.asList(doubleXValues));		
		curLowerBound = origLowerBound;
		curUpperBound = origUpperBound;

		// In DataTable structure, both length must be the same
		int len = xValues.length;

		for (int i = 0; i < len; i++) {
			series.getData().add(new XYChart.Data(xValues[i], yValues[i]));
		}

		// clear all previous series
		lineChart.getData().clear();

		// add the new series as the only one series for this line chart
		lineChart.getData().add(series);
		//}


	}
	
	public LineChart<Number, Number> getLineChart() {
		return lineChart;
	}

	@Override
	public void draw(final Stage primaryStage) {
        drawChartScene(primaryStage, lineChart);
        if (isFirstFrame) {
        	LineDataChart thisRef = this;
        	
    		Main.timeline = new Timeline(new KeyFrame(Duration.millis(1000/fps), new EventHandler<ActionEvent>() {  

    		    @Override
    		    public void handle(ActionEvent event) {
    		        Main.animate(primaryStage, thisRef);
    		    }
    		}));
    		Main.timeline.setCycleCount(Animation.INDEFINITE);
    		Main.timeline.play();
        } 
	}
	
	public void drawChartScene(final Stage primaryStage, LineChart lineChart) {
        
		// Layout the UI components
		Main.container = new VBox(20);
		Main.container.getChildren().add(lineChart);
		Main.container.setAlignment(Pos.CENTER);
		
		Button back= new Button("back");
		back.setOnAction(e->{
			if (Main.isAnimated) {
				Main.isAnimated = false;
				Main.aniCache = null;
				Main.container = null;
				Main.timeline.stop();
			}
			primaryStage.setScene(Main.primaryScene(primaryStage));
			}
		);
		back.relocate(550, 400);
	
		Main.container.getChildren().add(back);

		BorderPane pane = new BorderPane();
		pane.setCenter(Main.container);
	
        Scene lineChartScene = new Scene(pane, 600, 450);
        
        primaryStage.setScene(lineChartScene);
	}
}
