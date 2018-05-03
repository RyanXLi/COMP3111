package ui.comp3111;

import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import core.comp3111.LoadCSV;
import core.comp3111.SaveCSV;
import core.comp3111.DataCollection;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.experimental.theories.Theories;

import com.sun.org.apache.xml.internal.dtm.ref.DTMChildIterNodeList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class for drawing the scene to generate charts
 * 
 * @author RyanX
 *
 */
public class DrawConfigurationScene {
	
	public static String TYPE_LINE = "line";
	public static String TYPE_SCATTER = "scatter";
	public static String TYPE_ANI_LINE = "animated line";
	
	/**
	 * Draw the scene for the chart generation configuration.
	 * 
	 * @param primaryStage
	 * 			 primary stage for the application
	 * @param dtName
	 * 			 name of the data table chosen to draw chart
	 * @param chartType
	 * 			 the type of chart to be generated
	 * @return The scene for the chart generation configuration
	 */
	public static Scene configureDrawing(Stage primaryStage, String dtName, String chartType) {
		
		DataTable dt = Main.dtcl.getDataTable(dtName); 		
		
		int top = 50;
		int deltaVerticle = 50;
		int horizontal1 = 70;
		int horizontal2 = 220;
		
		int yPos = top;
		
		// TextField
		Label label0= new Label("Table title:");
		label0.relocate(horizontal1, yPos);
		TextField tableTitle = new TextField();
		//tableTitle.setPromptText("Table Title: ");
		tableTitle.relocate(horizontal2, yPos);
		yPos += deltaVerticle;
				
		// Find out all numeric and text columns 
		ArrayList<String> numericColName = new ArrayList<>();		
		if(dt.getNumCol()>0) {
		    for(String colName : dt.getDataTable().keySet()) {
			    if(dt.getCol(colName).getTypeName().equals(DataType.TYPE_NUMBER))
			    	numericColName.add(colName);
		    }
		}
		
		ArrayList<String> textColName= new ArrayList<>();		
		if(dt.getNumCol()>0) {
		    for(String colName:dt.getDataTable().keySet()) {
			    if(dt.getCol(colName).getTypeName().equals(DataType.TYPE_STRING))
				    textColName.add(colName);
		    }
		}
		
		// column box
		Label label1= new Label("Column on X axis:");
		label1.relocate(horizontal1, yPos);
		ChoiceBox<String> xColBox = new ChoiceBox<>();
		xColBox.setItems(FXCollections.observableArrayList(numericColName));
		xColBox.setPrefWidth(170);
		xColBox.relocate(horizontal2, yPos);
		yPos += deltaVerticle;
		
		Label label2= new Label("Column on Y axis:");
		label2.relocate(horizontal1, yPos);
		ChoiceBox<String> yColBox = new ChoiceBox<>();
		yColBox.setItems(FXCollections.observableArrayList(numericColName));
		yColBox.setPrefWidth(170);
		yColBox.relocate(horizontal2, yPos);
		yPos += deltaVerticle;
		
		Label label3= new Label("Column of categories:");
		label3.relocate(horizontal1, yPos);
		ChoiceBox<String> cateBox = new ChoiceBox<>();
		cateBox.setItems(FXCollections.observableArrayList(textColName));
		cateBox.setPrefWidth(170);
		cateBox.relocate(horizontal2, yPos);
		yPos += deltaVerticle;
		cateBox.setDisable(!chartType.equals(TYPE_SCATTER));		

		
		// OK button
		Button OK= new Button("OK");
		OK.setOnAction(e->{
			DataChart result;
			try {
				if (chartType.equals(TYPE_LINE)) {
					Main.dtcl.chartParams.add(
							new ArrayList<String>(
									Arrays.asList(
										TYPE_LINE,
										dtName, 
										xColBox.getSelectionModel().getSelectedItem(), 
										yColBox.getSelectionModel().getSelectedItem(),
										tableTitle.getText(),
										"")
									)
							);
					result = new LineDataChart(Main.dtcl.getDataTable(dtName), 
							xColBox.getSelectionModel().getSelectedItem(), 
							yColBox.getSelectionModel().getSelectedItem(),
							tableTitle.getText(),
							false,
							false,
							0,
							2);
					
				} else if (chartType.equals(TYPE_SCATTER)) {
					Main.dtcl.chartParams.add(
							new ArrayList<String>(
									Arrays.asList(
										TYPE_SCATTER,
										dtName,
										xColBox.getSelectionModel().getSelectedItem(), 
										yColBox.getSelectionModel().getSelectedItem(),
										cateBox.getSelectionModel().getSelectedItem(),
										tableTitle.getText()
										)
									)
							);
					result = new ScatterDataChart(Main.dtcl.getDataTable(dtName), 
							xColBox.getSelectionModel().getSelectedItem(), 
							yColBox.getSelectionModel().getSelectedItem(),
							cateBox.getSelectionModel().getSelectedItem(),
							tableTitle.getText()
							);
					
				} else {
					// (chartType.equals(TYPE_ANI_LINE)) {
					Main.dtcl.chartParams.add(
							new ArrayList<String>(
									Arrays.asList(
										TYPE_ANI_LINE,
										dtName,
										xColBox.getSelectionModel().getSelectedItem(), 
										yColBox.getSelectionModel().getSelectedItem(),
										tableTitle.getText(),
										"TRUE")
									)
							);
					result = new LineDataChart(Main.dtcl.getDataTable(dtName), 
							xColBox.getSelectionModel().getSelectedItem(), 
							yColBox.getSelectionModel().getSelectedItem(),
							tableTitle.getText(),
							true,
							false,
							0,
							0);
				}
				Main.dtcl.addDataChart(result);			
			
			} catch (DataTableException e1) {
				Alert alert = new Alert(AlertType.WARNING,"Input cannot be processed");
				alert.showAndWait();
				//e1.printStackTrace();
			}
				
			Alert alert = new Alert(AlertType.WARNING,"Successfully created chart!");
			primaryStage.setScene(Main.primaryScene(primaryStage));
			
		});
	    OK.relocate(400, 310);
	    
		
		// Back button
		Button back= new Button("back");
		back.setOnAction(e->{primaryStage.setScene(Main.primaryScene(primaryStage));});
		back.relocate(440, 310);
		
		
	    Pane ics1 = new Pane();

		ics1.getChildren().addAll(
				label0, tableTitle,
				label1, xColBox,
				label2, yColBox,
				label3, cateBox,
				OK, back
				);
		
		Scene exportCsvScene = new Scene(ics1,500,350,Color.WHITE);
		
		return exportCsvScene;
	}

}
