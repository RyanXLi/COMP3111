package ui.comp3111;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScatterDataChart extends DataChart implements Serializable {

	private static final long serialVersionUID = 1L;
	private ScatterChart<Number, Number> scatterChart;

	/**
	 * 
	 * @param dataTable
	 * @param xColName
	 * @param yColName
	 * @param cateColName
	 * @throws DataTableException
	 */
	public ScatterDataChart(DataTable dataTable, String xColName, String yColName, String cateColName, String chartTitle) throws DataTableException {
		super();
		
		if (dataTable == null) { 
			throw new DataTableException("dataTable is a null reference");
		} 
		
		DataColumn xCol = dataTable.getCol(xColName);
		DataColumn yCol = dataTable.getCol(yColName);
		DataColumn cateCol = dataTable.getCol(cateColName);
		
		// Ensure both columns exist and the type is number
		if (xCol == null || yCol == null || cateCol == null 
				|| !xCol.getTypeName().equals(DataType.TYPE_NUMBER)
				|| !yCol.getTypeName().equals(DataType.TYPE_NUMBER)
				|| !cateCol.getTypeName().equals(DataType.TYPE_STRING)) {
			throw new DataTableException("Selected data column cannot be processed");
		}

		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		xAxis.setLabel(xColName);
		yAxis.setLabel(yColName);
		
		scatterChart = new ScatterChart<>(xAxis, yAxis);
		scatterChart.setTitle(chartTitle);
		
		// Find categories
		ArrayList<String> categories = new ArrayList<>();
		ArrayList<XYChart.Series<Number, Number>> serieses = new ArrayList<>();
		String[] cateColStrArray = Arrays.copyOf(cateCol.getData(), cateCol.getData().length, String[].class);
		for (int i = 0; i < cateCol.getSize(); i++) {
			if (!categories.contains(cateColStrArray[i])) {
				// init case
				categories.add(cateColStrArray[i]);
				XYChart.Series<Number, Number> series = new XYChart.Series<>();
				series.setName(cateColStrArray[i]);
				series.getData().add(
						new XYChart.Data<Number, Number>(
								(Number)xCol.getData()[i], 
								(Number)yCol.getData()[i])
						);
				serieses.add(series);
				
			} else {
				// non-init case
				for (XYChart.Series<Number, Number> series : serieses) {
					if (series.getName().equals(cateColStrArray[i])) {
						series.getData().add(
								new XYChart.Data<Number, Number>(
										(Number)xCol.getData()[i], 
										(Number)yCol.getData()[i])
								);
					}
				}
			}
		}
		
		// clear all previous series
		scatterChart.getData().clear();

		// add the new series as the only one series for this line chart
		for (XYChart.Series<Number, Number> series : serieses) {
			scatterChart.getData().add(series);
		}
	}

	@Override
	public void draw(final Stage primaryStage) {

		// Layout the UI components
		VBox container = new VBox(20);
		container.getChildren().add(scatterChart);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);
        Scene scatterChartScene = new Scene(pane, 600, 600);
        
        primaryStage.setScene(scatterChartScene);

        //// New window (Stage)
        //Stage scatterChartWindow = new Stage();
        //scatterChartWindow.setTitle("Second Stage");
        //scatterChartWindow.setScene(scatterChartScene);
        //
        //// Set position of second window, related to primary window.
        //scatterChartWindow.setX(primaryStage.getX() + 200);
        //scatterChartWindow.setY(primaryStage.getY() + 100);
        //
        //scatterChartWindow.show();
        
	}


}
