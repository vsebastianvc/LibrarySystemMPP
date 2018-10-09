package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Start extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Library System");
				
		VBox topContainer = new VBox();
		MenuBar mainMenu = new MenuBar();

		HBox labelBox = new HBox(10);
		labelBox.setAlignment(Pos.CENTER);
		topContainer.getChildren().add(mainMenu);
		topContainer.getChildren().add(labelBox);

		//Actions menu
		Menu operMenu = new Menu("Actions");
		MenuItem checkOutBook = new MenuItem("Check out...");
		MenuItem checkInBook = new MenuItem("Check in...");
		MenuItem queryOverdue = new MenuItem("Query overdue...");
		MenuItem newBookSep = new SeparatorMenuItem();
		operMenu.getItems().addAll(checkOutBook);
		operMenu.getItems().addAll(checkInBook);
		operMenu.getItems().addAll(queryOverdue);
		operMenu.getItems().addAll(newBookSep);
		MenuItem exitApp = new MenuItem("Exit");
		operMenu.getItems().addAll(exitApp);
		exitApp.setOnAction(evt -> Platform.exit());
		
		//Member menu
		Menu custMenu = new Menu("Member");
		MenuItem newMember = new MenuItem("New...");
		MenuItem printCheckoutRecord = new MenuItem("Print checkout record...");
		newMember.setOnAction(evt -> {	
			//CatalogListWindow catalogs = CatalogListWindow.INSTANCE;
			//catalogs.setStage(primaryStage);
	        //catalogs.setData(DefaultData.CATALOG_LIST_DATA);
	        //catalogs.show();  
	        primaryStage.hide();
					
		});
		custMenu.getItems().addAll(newMember);
		custMenu.getItems().addAll(printCheckoutRecord);

		//Book menu
		Menu adminMenu = new Menu("Book");
		MenuItem newBook = new MenuItem("New...");
		MenuItem newBookCopy = new MenuItem("New book copy...");
		adminMenu.getItems().addAll(newBook);
		adminMenu.getItems().addAll(newBookCopy);
		
		mainMenu.getMenus().addAll(custMenu, adminMenu, operMenu);
		
		primaryStage.setScene(new Scene(topContainer, 800, 400));
		primaryStage.show();
	}
	
}