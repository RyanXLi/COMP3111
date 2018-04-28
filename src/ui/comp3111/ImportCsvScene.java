package ui.comp3111;

import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.LoadCSV;
import core.comp3111.DataCollection;

import java.util.ArrayList;
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
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.processing.FilerException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImportCsvScene {
	/**
	 * Scene for import .csv button
	 * 
	 * @param Stage
	 *            - original main stage
	 * @param dtName
	 *            - the DataTable you want to save
	 */
	public static Scene importCsv(Stage primaryStage, String dtName) {
		
		DataTable dt = new DataTable(); 
		
		
		// Radio button
		ToggleGroup group = new ToggleGroup();

		RadioButton rb1 = new RadioButton("default filling");
		rb1.setToggleGroup(group);
		rb1.setSelected(true);
		rb1.relocate(150, 180);
				
		RadioButton rb2 = new RadioButton("median filling");
		rb2.setToggleGroup(group);
		rb2.relocate(150, 210);
		
		RadioButton rb3 = new RadioButton("mean filling");
		rb3.setToggleGroup(group);
		rb3.relocate(150, 240);
		
		
		
		// TextField
		TextField filename = new TextField ();
		filename.setPromptText("Enter a .csv filename");
		filename.relocate(150, 80);
		
		// file chooser button
		FileChooser fc = new FileChooser();
		Button filechooser = new Button("Choose from local file");
		filechooser.setOnAction(e->{
			fc.setTitle("Open Localfile .csv");
			File file = fc.showOpenDialog(primaryStage);
			if (file != null) {
				filename.clear();
				filename.appendText(file.getName());
			}
		});
		filechooser.relocate(150,110);
		
		
		
		// OK button
		Button OK= new Button("OK");
		OK.setOnAction(e->{
			DataTable result = new DataTable();
			
			String handleType = "Default";
			if(rb2.isSelected()) {
				handleType = "Median";
			} else if(rb3.isSelected()) {
				handleType = "Mean";
			}
			
			try {
				if (!filename.getText().endsWith(".csv")) {
					Alert alert = new Alert(AlertType.WARNING,"Such filename is not supported");
					alert.showAndWait();
					primaryStage.setScene(Main.primaryScene(primaryStage));
				} else {	
					result = LoadCSV.loadCSV(filename.getText(), handleType);
				}
				
				if (result.getNumCol() == 0) {
					Alert alert = new Alert(AlertType.WARNING,"Given file cannot be processed");
					alert.showAndWait();
				} else {
					Main.dtcl.addDataTable(result);
				}
			} catch (IOException | DataTableException e1) {
				e1.printStackTrace();
			}
			primaryStage.setScene(Main.primaryScene(primaryStage));
		});
	    OK.relocate(400, 310);
	    
		
		// Back button
		Button back= new Button("back");
		back.setOnAction(e->{primaryStage.setScene(Main.primaryScene(primaryStage));});
		back.relocate(440, 310);
		
		
		// labels
		Label label1= new Label("Filename");
		label1.setStyle("-fx-font-weight: bold");
		label1.relocate(80, 80);
		
		Label label2= new Label("Choose your missing data handle type");
		label2.relocate(80, 150);
		
		
	    Pane ics1 = new Pane();
	    ics1.getChildren().addAll(label1,label2);
	    ics1.getChildren().addAll(filename);
		ics1.getChildren().addAll(rb1,rb2,rb3,OK,back,filechooser);
		
		
		Scene importCsvScene = new Scene(ics1,500,350,Color.WHITE);
		
		return importCsvScene;
	}

}
