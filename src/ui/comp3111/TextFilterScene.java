package ui.comp3111;

import java.util.ArrayList;

import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TextFilterScene {
public static Scene textFilter(Stage primaryStage, String dtName) {
			
	    DataTable dt = Main.dtcl.getDataTable(dtName); 
	
		// Find out all numeric columns 
		ArrayList<String> textColName= new ArrayList<>();		
		if(dt.getNumCol()>0) {
		    for(String colName:dt.getDataTable().keySet()) {
			    if(dt.getCol(colName).getTypeName()=="java.lang.String")
				    textColName.add(colName);
		    }
		}
		
		// column box
		ChoiceBox<String> colBox = new ChoiceBox<>();
		colBox.setItems(FXCollections.observableArrayList(textColName));
		colBox.setPrefWidth(170);
		colBox.relocate(240, 85);

		

		
		// Indicate target string
		TextField setText = new TextField();
		setText.setPrefWidth(80);
		setText.relocate(330, 130);
		
		
		
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
				.or(setText.textProperty().isEmpty()));
		
		String colName = colBox.getSelectionModel().getSelectedItem();
		String text = setText.getText();
		boolean handleMode = rb1.isSelected();
		OK.setOnAction(e->{try {Main.dtcl.textFilter(dtName,colName , text, handleMode);}
		               catch (DataTableException e1) {}
		               primaryStage.setScene(Main.primaryScene(primaryStage));});
		
		// Back button
		Button back= new Button("back");
		back.setOnAction(e->{primaryStage.setScene(Main.primaryScene(primaryStage));});
		back.relocate(440, 310);
		
		
		// labels
		Label label1= new Label("Textual filter:");
		label1.setStyle("-fx-font-weight: bold");
		label1.relocate(30, 30);
		
		Label label2= new Label("Base on column:");
		label2.relocate(70, 90);
		
		Label label3= new Label("Leave the rows that have and only have text:");
		label3.relocate(70, 135);
		
		Label label4= new Label("What do you want to do with the result data table?");
		label4.setStyle("-fx-font-weight: bold");
        label4.relocate(30, 210);
		
	    Pane numFilterPane1 = new Pane();
	    numFilterPane1.getChildren().addAll(label1,label2,label3,label4);
		numFilterPane1.getChildren().addAll(colBox,setText,rb1,rb2,OK,back);
		
		
		Scene numFilterScene = new Scene(numFilterPane1,500,350,Color.WHITE);
		
		return numFilterScene;
	}

}
