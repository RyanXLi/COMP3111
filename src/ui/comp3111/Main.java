package ui.comp3111;

import core.comp3111.DataCollection;
import core.comp3111.DataChart;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataType;
import core.comp3111.SampleDataGenerator;
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

/**
 * The Main class of this GUI application
 * 
 * @author cspeter
 *
 */
public class Main extends Application {
 
	
    static DataCollection dtcl= new DataCollection();
    private static ListView<String> dataTableList = new ListView<String>();
	private static ListView<String> chartList = new ListView<String>();


		  @Override
	public void start(Stage primaryStage) {
			 
        DataTable dt1 = new DataTable();
		dtcl.addDataTable(dt1);
		DataChart dc1 = new DataChart();
		dtcl.addDataChart(dc1);

	    primaryStage.setScene(primaryScene(primaryStage));
	    primaryStage.setTitle("PlaYFuL BluE MoOn");
        primaryStage.show();    
    }
  
  
    public static void main(String[] args) {
        launch(args);
	    }
  
  
    public static Scene primaryScene(Stage primaryStage) {
		//The root pane contains the menu bar at the top  

		BorderPane root = new BorderPane();
		MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
		root.setTop(menuBar);
		
		
		
		
		
		//The stack1 pane contains the list is for dataTables
		StackPane stack1 = new StackPane();			    
		ObservableList<String> dataTableItems =FXCollections.observableArrayList (
		    dtcl.getTableCollection().keySet());
		dataTableList.setItems(dataTableItems);	    
		stack1.getChildren().add(dataTableList);
		stack1.setLayoutX(60);
		stack1.setLayoutY(80);
		stack1.setPrefSize(200, 300);
		    


		//The stack2 pane contains the list is for charts
		StackPane stack2 = new StackPane();
		ObservableList<String> chartItems =FXCollections.observableArrayList (
			dtcl.getChartCollection().keySet());
		chartList.setItems(chartItems);	    
		stack2.getChildren().add(chartList);
		stack2.setLayoutX(310);
		stack2.setLayoutY(80);
		stack2.setPrefSize(200, 300);
			    
		
		//"View chart" button, enabled only when a chart is selected.
		Button button = new Button("View chart");
		button.disableProperty().bind(chartList.getSelectionModel().selectedItemProperty().isNull());
		StackPane stack3=new StackPane();
		stack3.getChildren().add(button);
		stack3.setLayoutX(480);
		stack3.setLayoutY(400);



		// ****Detailed Implementation of the menu bar
		
		//The file menu
		Menu fileMenu = new Menu("File");
		MenuItem importMenuItem = new MenuItem("import from CSV");
		MenuItem saveMenuItem = new MenuItem("save as 3111");
		MenuItem loadMenuItem = new MenuItem("import from 3111");
		
		fileMenu.getItems().addAll(importMenuItem, loadMenuItem,
		     saveMenuItem);
		
		//The filter menu, enabled only when a datatable is selected
		Menu filterMenu = new Menu("Filter");
		MenuItem filterNumMenuItem = new MenuItem("Numeric");
		MenuItem filterTextMenuItem = new MenuItem("Text");
		filterMenu.getItems().addAll(filterNumMenuItem,filterTextMenuItem);
		filterMenu.disableProperty().bind(dataTableList.getSelectionModel().selectedItemProperty().isNull());
		
		
		importMenuItem.setOnAction(e->{primaryStage.setScene(
				ImportCsvScene.importCsv(primaryStage,dataTableList.getSelectionModel().getSelectedItem()));});

		
		filterNumMenuItem.setOnAction(e->{primaryStage.setScene(
				NumericFilterScene.numericFilter(primaryStage,dataTableList.getSelectionModel().getSelectedItem()));});
		
		filterTextMenuItem.setOnAction(e->{primaryStage.setScene(
				TextFilterScene.textFilter(primaryStage,dataTableList.getSelectionModel().getSelectedItem()));});


		//The draw menu, enabled only when a datatable is selected
		Menu drawMenu = new Menu("Draw");
		MenuItem lineItem = new MenuItem("line chart");
		MenuItem scatterItem = new MenuItem("scatter chart");
		drawMenu.getItems().addAll(lineItem, scatterItem);
		drawMenu.disableProperty().bind(dataTableList.getSelectionModel().selectedItemProperty().isNull());
		
		menuBar.getMenus().addAll(fileMenu, filterMenu, drawMenu);
		
		
		//Summing things up, the whole scene
	    Pane box= new Pane();
	    box.getChildren().addAll(root,stack1,stack2,stack3);
	    Scene scene = new Scene(box, 600, 450, Color.WHITE);
	    			    
	    return scene;
	    }		
    
    }

