package ui.comp3111;

import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.EnvirHandler;
import core.comp3111.LoadCSV;
import core.comp3111.SaveCSV;
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
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	 * 
	 * @author xzhaoar
	 *
	 */
public class Import3111Scene {
	/**
	 * Scene for import 3111 button
	 * @author xzhaoar
	 * @param Stage
	 *            - original main stage
	 * @param dtName
	 *            - the DataTable you want to save
	 * @return import3111Scene
	 */
	public static Scene import3111(Stage primaryStage, String dtName) {
			
		// TextField
		TextField filename = new TextField ();
		filename.setPromptText("Enter a .comp3111 filepath");
		filename.relocate(150, 80);
		
		// file chooser button
		FileChooser fc = new FileChooser();
		Button filechooser = new Button("Choose from local file");
		filechooser.setOnAction(e->{
			fc.setTitle("Subsititude Localfile .comp3111");
			File file = fc.showOpenDialog(primaryStage);
			if (file != null) {
				filename.clear();
				filename.appendText(file.getAbsolutePath());
			}
		});
		filechooser.relocate(150,110);
		
		
		
		// OK button
		Button OK= new Button("OK");
		OK.setOnAction(e->{
			DataCollection temp = new DataCollection();
			if (!filename.getText().endsWith(".comp3111")) {
				Alert alert = new Alert(AlertType.WARNING,"Such filename is not supported, please end with .comp3111");
				alert.showAndWait();
			} else {
				try {
					temp = EnvirHandler.envirHandler(Main.dtcl, filename.getText(), "L");
				} catch (ClassNotFoundException | IOException e1) {
					Alert alert = new Alert(AlertType.WARNING,"Wrong Content in the file!");
					alert.showAndWait();
					e1.printStackTrace();
				}
				Main.dtcl = temp;
				Main.dtcl.chartCollection = Main.reconstruct(Main.dtcl.chartParams);
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
		
		Label label2= new Label("file format should be xxx.comp3111");
		label2.setStyle("-fx-font-weight: bold");
		label2.relocate(150, 140);
		
		
	    Pane ics1 = new Pane();
	    ics1.getChildren().addAll(label1, label2);
	    ics1.getChildren().addAll(filename);
		ics1.getChildren().addAll(OK,back,filechooser);
		
		
		Scene import3111Scene = new Scene(ics1,500,350,Color.WHITE);
		
		return import3111Scene;
	}

}
