package ui.comp3111;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.print.attribute.standard.MediaSize.Other;

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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LineDataChart extends DataChart {
	
	private static final long serialVersionUID = 1L;
	private LineChart<Number, Number> lineChart;
	public boolean isAnimated = false;
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
	
	Timeline timeline;
	
	public LineDataChart(LineDataChart another, double lowerBound, double upperBound) {
		this.xAxis = another.xAxis;
		this.yAxis = another.yAxis;
		this.lineChart = another.lineChart;
		this.isAnimated = another.isAnimated;
		
		this.origLowerBound = another.origLowerBound;
		this.origUpperBound = another.origUpperBound;
		this.curLowerBound = another.curLowerBound; 
		this.curUpperBound= another.curUpperBound; 


	}

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
		
		isAnimated = animate;

		if (!update) {
			xAxis = new NumberAxis();
		} else {
			xAxis = new NumberAxis(lowerBound, upperBound, 0.1);
		}
		xAxis.setForceZeroInRange(false);
		yAxis = new NumberAxis();
		
		xAxis.setLabel(xColName);
		yAxis.setLabel(yColName);
		
		
		lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle(chartTitle);
		
		// defining a series
		
		@SuppressWarnings("rawtypes")
		XYChart.Series series = new XYChart.Series();

		series.setName(xColName + " - " + yColName);

		// populating the series with data
		// As we have checked the type, it is safe to downcast to Number[]
		Number[] xValues = (Number[]) xCol.getData();
		Number[] yValues = (Number[]) yCol.getData();
		
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
	
	//private void drawHelper(final Stage primaryStage) {
	//	//Main.aniCache = new LineDataChart(this);
	//	
	//	// calculate new bounds
	//	double xRangeLen = (origUpperBound - origLowerBound) / fps / timeOfOnePlay;
	//	if ((Math.abs(origLowerBound - curLowerBound) <= ERR && Math.abs(origLowerBound - curLowerBound) <= ERR)
	//			|| (curUpperBound + xRangeLen >= origUpperBound)) {
	//		// first frame of animation OR next frame over bound
	//		curLowerBound = origLowerBound;
	//		curUpperBound = shownRatio * origUpperBound;
	//	} else {
	//		curLowerBound += xRangeLen;
	//		curUpperBound += xRangeLen;
	//	}
	//	
	//	((NumberAxis)(lineChart.getXAxis())).setLowerBound(curLowerBound);
	//	((NumberAxis)(lineChart.getXAxis())).setUpperBound(curUpperBound);
	//	//draw(primaryStage);
	//	
	//	try {
	//		Thread.sleep((long)(1000/fps));
	//	} catch (InterruptedException e) {
	//		e.printStackTrace();
	//	}
    //
	//	// recurse
	//	drawHelper(primaryStage);
	//}

	@Override
	public void draw(final Stage primaryStage) {
        
        if (isAnimated) {
        	LineDataChart thisRef = this;
        	
    		timeline = new Timeline(new KeyFrame(Duration.millis(1000/fps), new EventHandler<ActionEvent>() {  

    		    @Override
    		    public void handle(ActionEvent event) {
    		        Main.animate(primaryStage, thisRef);
    		    }
    		}));
    		timeline.setCycleCount(Animation.INDEFINITE);
    		timeline.play();
 	
        } else {
    		// Layout the UI components
    		VBox container = new VBox(20);
    		container.getChildren().add(lineChart);
    		container.setAlignment(Pos.CENTER);

    		BorderPane pane = new BorderPane();
    		pane.setCenter(container);
            Scene lineChartScene = new Scene(pane, 600, 600);
            
            primaryStage.setScene(lineChartScene);
            
            //// New window (Stage)
            //Stage lineChartWindow = new Stage();
            //lineChartWindow.setTitle("Second Stage");
            //lineChartWindow.setScene(lineChartScene);
            //
            //// Set position of second window, related to primary window.
            //lineChartWindow.setX(primaryStage.getX() + 200);
            //lineChartWindow.setY(primaryStage.getY() + 100);
            //
            //lineChartWindow.show();

        		//// Apply CSS to style the GUI components
        		//pane.getStyleClass().add("screen-background");
        }
            

	}

}
