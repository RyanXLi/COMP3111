package ui.comp3111;

import core.comp3111.DataTable;
import core.comp3111.DataTableException;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class NumericFilterScene {
	public static Scene numericFilter(Stage primaryStage, String dtName) {
		
		DataTable dt = Main.dtcl.getDataTable(dtName); 
		
		// Find out all numeric columns 
		ArrayList<String> numColName= new ArrayList<>();		
		if(dt.getNumCol()>0) {
		    for(String colName:dt.getDataTable().keySet()) {
			    if(dt.getCol(colName).getTypeName()=="java.lang.Number")
				    numColName.add(colName);
		    }
		}
		
		// column box
		ChoiceBox<String> colBox = new ChoiceBox<>();
		colBox.setItems(FXCollections.observableArrayList(numColName));
		colBox.setPrefWidth(170);
		colBox.relocate(240, 85);

		
		
		// operator choice box
		ChoiceBox<String> opBox = new ChoiceBox<>();
		opBox.setItems(FXCollections.observableArrayList(">","<",">=","<=","=="));
		opBox.setPrefWidth(100);
		opBox.relocate(240, 130);
		
		// Indicate target number
		TextField setNumber = new TextField();
		setNumber.setPrefWidth(60);
		setNumber.relocate(350, 130);
				
		
		// Radio button
		ToggleGroup group = new ToggleGroup();

		RadioButton rb1 = new RadioButton("Replace the original data table");
		rb1.setToggleGroup(group);
		rb1.setSelected(true);
		rb1.relocate(150, 250);
		
		RadioButton rb2 = new RadioButton("Create a new data table");
		rb2.setToggleGroup(group);
		rb2.relocate(150, 270);
		
		
		// OK button
		Button OK= new Button("OK");
	    OK.relocate(400, 310);
	    
	    OK.disableProperty().bind(colBox.getSelectionModel().selectedItemProperty().isNull()
	    		.or(opBox.getSelectionModel().selectedItemProperty().isNull()
	    				.or(setNumber.textProperty().isEmpty())));
		
	    String colName=colBox.getSelectionModel().getSelectedItem();
	    String operator = opBox.getSelectionModel().getSelectedItem();
	    String num = setNumber.getText();
	    boolean handleMode = rb1.isSelected();
	    OK.setOnAction(e->{try {
			Main.dtcl.numFilter(dtName, colName,operator,num, handleMode);}
	        catch (DataTableException e1) {}
            primaryStage.setScene(Main.primaryScene(primaryStage));});
		
		// Back button
		Button back= new Button("back");
		back.setOnAction(e->{primaryStage.setScene(Main.primaryScene(primaryStage));});
		back.relocate(440, 310);
		
		
		// labels
		Label label1= new Label("Numeric filter:");
		label1.setStyle("-fx-font-weight: bold");
		label1.relocate(30, 30);
		
		Label label2= new Label("Base on column:");
		label2.relocate(70, 90);
		
		Label label3= new Label("Leave only the rows that are:");
		label3.relocate(70, 135);
		
		Label label4= new Label("What do you want to do with the result data table?");
		label4.setStyle("-fx-font-weight: bold");
        label4.relocate(30, 210);
		
	    Pane numFilterPane1 = new Pane();
	    numFilterPane1.getChildren().addAll(label1,label2,label3,label4);
		numFilterPane1.getChildren().addAll(colBox,opBox,setNumber,rb1,rb2,OK,back);
		
		
		Scene numFilterScene = new Scene(numFilterPane1,500,350,Color.WHITE);
		
		return numFilterScene;
	}

}
