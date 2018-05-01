package ui.comp3111;

import javax.print.attribute.standard.MediaSize.Other;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
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

public class LineDataChart extends DataChart {
	
	private static final long serialVersionUID = 1L;
	private LineChart<Number, Number> lineChart;
	private boolean isAnimated = false;
	
	public LineDataChart(LineDataChart another) {
		this.xAxis = another.xAxis;
		this.yAxis = another.yAxis;
		this.lineChart = another.lineChart;
		this.isAnimated = another.isAnimated;
	}

	public LineDataChart(DataTable dataTable, String xColName, String yColName, String chartTitle, boolean animate) throws DataTableException {
		super();
		
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
		
		//if (xCol != null && yCol != null && xCol.getTypeName().equals(DataType.TYPE_NUMBER)
		//		&& yCol.getTypeName().equals(DataType.TYPE_NUMBER)) {

		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		
		xAxis.setLabel(xColName);
		yAxis.setLabel(yColName);
		
		lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle(chartTitle);
		
		// defining a series
		
		@SuppressWarnings("rawtypes")
		XYChart.Series series = new XYChart.Series();

		series.setName(xColName + " - " + yColName + " chart");

		// populating the series with data
		// As we have checked the type, it is safe to downcast to Number[]
		Number[] xValues = (Number[]) xCol.getData();
		Number[] yValues = (Number[]) yCol.getData();

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
	


	@Override
	public void draw(final Stage primaryStage) {

		// TODO
		if (isAnimated) {

		}
		
		// Layout the UI components
		VBox container = new VBox(20);
		container.getChildren().add(lineChart);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);
        Scene lineChartScene = new Scene(pane, 230, 100);

        // New window (Stage)
        Stage lineChartWindow = new Stage();
        lineChartWindow.setTitle("Second Stage");
        lineChartWindow.setScene(lineChartScene);

        // Set position of second window, related to primary window.
        lineChartWindow.setX(primaryStage.getX() + 200);
        lineChartWindow.setY(primaryStage.getY() + 100);

        lineChartWindow.show();

    		//// Apply CSS to style the GUI components
    		//pane.getStyleClass().add("screen-background");
	
	}

}
