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
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A wrapper around the JavaFX scatter chart to support drawing.
 * 
 * @author RyanX
 *
 */
public class ScatterDataChart extends DataChart implements Serializable {

	private static final long serialVersionUID = 1L;
	private ScatterChart<Number, Number> scatterChart;
	public DataTable dataTable;

	/**
	 * constructor - create a new ScatterDataChart ready to be drawn
	 *
	 * @param dataTable
	 * 			- the reference of the data table to create a chart from
	 * @param xColName
	 * 			- name of the column to be the x-axis of the chart.
	 * 				It has to denote a numeric column
	 * @param yColName
	 * 			- name of the column to be the y-axis of the chart.
	 * 				It has to denote a numeric column
	 * @param cateColName
	 * 			- name of the column to determine the category of the chart.
	 * 				It has to denote a text column
	 * @param chartTitle
	 * 			- the title of the chart to be created
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
	
	

	/**
	 * Draw the scatter chart to the stage
	 * 
	 * @param primaryStage
	 * 			- the primary stage of the application
	 */
	@Override
	public void draw(final Stage primaryStage) {

		// Layout the UI components
		VBox container = new VBox(20);
		container.getChildren().add(scatterChart);
		container.setAlignment(Pos.CENTER);
		
		Button back= new Button("back");
		back.setOnAction(e->{
			primaryStage.setScene(Main.primaryScene(primaryStage));
		});
		back.relocate(550, 400);
		container.getChildren().add(back);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);
        Scene scatterChartScene = new Scene(pane, 600, 450);
        
        primaryStage.setScene(scatterChartScene);
        
	}
}
