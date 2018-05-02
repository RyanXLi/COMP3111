package ui.comp3111;

import core.comp3111.DataCollection;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

/**
 * The Main class of this GUI application
 * 
 * @author cspeter
 *
 */
public class Main extends Application {
 
	
    static DataCollection dtcl= new DataCollection();
    public static LineDataChart aniCache = null;
    private static ListView<String> dataTableList = new ListView<String>();
	private static ListView<String> chartList = new ListView<String>();
	public static VBox container;
	public static Timeline timeline;

	private static boolean isDebugging = true;
	public static boolean isAnimated = false;

	
	/**
	 * The method of drawing a frame of the animation
	 * 
	 * @param primaryStage
	 * @param orig
	 */
	public static void animate(Stage primaryStage, LineDataChart orig) {
		
		isAnimated = true;

		// calculate new bounds
		double xRangeLen = (orig.origUpperBound - orig.origLowerBound) / orig.fps / orig.timeOfOnePlay;
		double upperBound, lowerBound;
		if (Main.aniCache == null || (Main.aniCache.curUpperBound + xRangeLen >= Main.aniCache.origUpperBound)) {
			// TODO: change "==null"
			// first frame of animation OR next frame over bound
			lowerBound = orig.origLowerBound;
			upperBound = orig.shownRatio * orig.origUpperBound;
		} else {
			lowerBound = Main.aniCache.curLowerBound + xRangeLen;
			upperBound = Main.aniCache.curUpperBound + xRangeLen;
		}
					
		
		try {
			Main.aniCache = new LineDataChart(orig.dataTable, orig.xColName, orig.yColName, orig.chartTitle, false,
					true, lowerBound, upperBound);
		} catch (DataTableException e1) {
			e1.printStackTrace();
		}
		
		Main.aniCache.curLowerBound = lowerBound;
		Main.aniCache.curUpperBound = upperBound;	

		//Main.aniCache.draw(primaryStage);
		Main.container.getChildren().set(0, Main.aniCache.getLineChart());
			
	}

		  @Override
	public void start(Stage primaryStage) {
			 
        DataTable dt1 = new DataTable();
        if (isDebugging) {
	    	DataColumn testDataColumn   = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2, 3, 4, 5,6,7});
	    	DataColumn testDoubleColumn = new DataColumn(DataType.TYPE_NUMBER, new Double[] {3.3, 2.2, 1.1, 5.5, 4.4,7.7,6.6});
	    	DataColumn testStringColumn = new DataColumn(DataType.TYPE_STRING, new String[] {"a","b","a", "a", "b","b","a"});
	    	//DataColumn testCommaColumn  = new DataColumn(DataType.TYPE_STRING, new String[] {"a","a","a","a", "a"});
	    	try {
		    	dt1.addCol("testDataColumn", testDataColumn  );
		    	dt1.addCol("testDoubleColumn", testDoubleColumn);
		    	dt1.addCol("testStringColumn", testStringColumn);
		    	//dt1.addCol("testCommaColumn", testCommaColumn );
	    	} catch (Exception e) {
			}
        }
    	
		dtcl.addDataTable(dt1);

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
		button.setOnAction(e->{
			dtcl.getDataChart(chartList.getSelectionModel().getSelectedItem()).draw(primaryStage);
		});
		StackPane stack3=new StackPane();
		stack3.getChildren().add(button);
		stack3.setLayoutX(480);
		stack3.setLayoutY(400);



		// ****Detailed Implementation of the menu bar
		
		//The file menu
		Menu fileMenu = new Menu("File");
		MenuItem importMenuItem = new MenuItem("import from CSV");
		MenuItem exportMenuItem = new MenuItem("export as CSV");
		MenuItem saveMenuItem = new MenuItem("save as 3111");
		MenuItem loadMenuItem = new MenuItem("import from 3111");
		
		fileMenu.getItems().addAll(importMenuItem,exportMenuItem, loadMenuItem,saveMenuItem
		     );
		
		exportMenuItem.disableProperty().bind(dataTableList.getSelectionModel().selectedItemProperty().isNull());
		
		//The filter menu, enabled only when a datatable is selected
		Menu filterMenu = new Menu("Filter");
		MenuItem filterNumMenuItem = new MenuItem("Numeric");
		MenuItem filterTextMenuItem = new MenuItem("Text");
		filterMenu.getItems().addAll(filterNumMenuItem,filterTextMenuItem);
		filterMenu.disableProperty().bind(dataTableList.getSelectionModel().selectedItemProperty().isNull());
		
		importMenuItem.setOnAction(e->{primaryStage.setScene(
				ImportCsvScene.importCsv(primaryStage,dataTableList.getSelectionModel().getSelectedItem()));});
		exportMenuItem.setOnAction(e->{primaryStage.setScene(
				ExportCsvScene.exportCsv(primaryStage,dataTableList.getSelectionModel().getSelectedItem()));});
		loadMenuItem.setOnAction(e->{primaryStage.setScene(
				Import3111Scene.import3111(primaryStage,dataTableList.getSelectionModel().getSelectedItem()));});
		saveMenuItem.setOnAction(e->{primaryStage.setScene(
				Export3111Scene.export3111(primaryStage,dataTableList.getSelectionModel().getSelectedItem()));});

		
		filterNumMenuItem.setOnAction(e->{primaryStage.setScene(
				NumericFilterScene.numericFilter(primaryStage,dataTableList.getSelectionModel().getSelectedItem()));});
		
		filterTextMenuItem.setOnAction(e->{primaryStage.setScene(
				TextFilterScene.textFilter(primaryStage,dataTableList.getSelectionModel().getSelectedItem()));});


		//The draw menu, enabled only when a datatable is selected
		Menu drawMenu = new Menu("Create Chart");
		MenuItem lineItem = new MenuItem("line chart");
		MenuItem scatterItem = new MenuItem("scatter chart");
		MenuItem animatedLineItem = new MenuItem("animated line chart");
		drawMenu.getItems().addAll(lineItem, scatterItem, animatedLineItem);
		drawMenu.disableProperty().bind(dataTableList.getSelectionModel().selectedItemProperty().isNull());
		
		lineItem.setOnAction(e->{
			primaryStage.setScene(
					DrawConfigurationScene.configureDrawing(
							primaryStage,
							dataTableList.getSelectionModel().getSelectedItem(),
							DrawConfigurationScene.TYPE_LINE
							)
					);
			}
		);
		
		scatterItem.setOnAction(e->{
			primaryStage.setScene(
					DrawConfigurationScene.configureDrawing(
							primaryStage,
							dataTableList.getSelectionModel().getSelectedItem(),
							DrawConfigurationScene.TYPE_SCATTER
							)
					);
			}
		);
		
		animatedLineItem.setOnAction(e->{
			primaryStage.setScene(
					DrawConfigurationScene.configureDrawing(
							primaryStage,
							dataTableList.getSelectionModel().getSelectedItem(),
							DrawConfigurationScene.TYPE_ANI_LINE
							)
					);
			}
		);
		
		
		
		menuBar.getMenus().addAll(fileMenu, filterMenu, drawMenu);
		
		
		//Summing things up, the whole scene
	    Pane box= new Pane();
	    box.getChildren().addAll(root,stack1,stack2,stack3);
	    Scene scene = new Scene(box, 600, 450, Color.WHITE);
	    			    
	    return scene;
	    }		
    
    }

