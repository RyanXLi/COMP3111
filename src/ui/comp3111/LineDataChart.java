package ui.comp3111;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataType;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LineDataChart extends DataChart {
	
	private LineChart<Number, Number> lineChart;
	public LineDataChart() {
		super();
	}

	public LineDataChart(DataTable dataTable, String xColName, String yColName) {
		super();
		
		if (dataTable == null) { return; } // Do nothing if null table provided
			
		DataColumn xCol = dataTable.getCol(xColName);
		DataColumn yCol = dataTable.getCol(yColName);
		
		// Ensure both columns exist and the type is number
		if (xCol != null && yCol != null && xCol.getTypeName().equals(DataType.TYPE_NUMBER)
				&& yCol.getTypeName().equals(DataType.TYPE_NUMBER)) {

			lineChart.setTitle(xColName + " - " + yColName + " chart");
			xAxis.setLabel(xColName);
			yAxis.setLabel(yColName);

			
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
		}
	}

	@Override
	public void draw(final Stage primaryStage) {
		// TODO Auto-generated method stub
        Label secondLabel = new Label("I'm a Label on new Window");
        
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 230, 100);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);

        newWindow.show();
	}

}
