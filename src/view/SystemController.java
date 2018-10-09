package view;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SystemController {

	@FXML // fx:id="checkOut"
	private MenuItem checkOut;

	@FXML // fx:id="checkIn"
	private MenuItem checkIn;

	@FXML // fx:id="queryOverdue"
	private MenuItem queryOverdue;

	@FXML // fx:id="newMember"
	private MenuItem systemExit;

	@FXML // fx:id="newBook"
	private MenuItem newBook;

	@FXML // fx:id="newBookCopy"
	private MenuItem newBookCopy;

	// @FXML // fx:id="deleteIssue"
	// private Button deleteIssue; // Value injected by FXMLLoader

	@FXML // fx:id="newIssue"
	private Button newIssue; // Value injected by FXMLLoader

	@FXML // fx:id="saveIssue"
	private Button saveIssue; // Value injected by FXMLLoader

	@FXML // fx:id="titleLine"
	private AnchorPane contentPanel; // Value injected by FXMLLoader

	@FXML // fx:id="newMemberID"
	private TextField newMemberID; // Value injected by FXMLLoader

	@FXML
	private AnchorPane mainPanel;

	/**
	 * Initializes the controller class.
	 */
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {

	}

	/**
	 * Called when the NewIssue button is fired.
	 *
	 * @param event the action event.
	 */
	@FXML
	void newIssueFired(ActionEvent event) {

		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(MainMenuController.class.getResource("Test.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Called when the DeleteIssue button is fired.
	 *
	 * @param event the action event.
	 */
	@FXML
	void deleteIssueFired(ActionEvent event) {

	}

	/**
	 * Called when the SaveIssue button is fired.
	 *
	 * @param event the action event.
	 */
	@FXML
	void saveIssueFired(ActionEvent event) {
		this.contentPanel.getChildren().clear();
	}

	FadeTransition messageTransition = null;

	public void displayMessage(String message) {

	}

	@FXML
	void systemExitFired(ActionEvent event) {
//    	System.out.println("Exit");
//    	Platform.exit();
		Stage stage = new Stage();
		stage.setTitle("Welcome MUM Library System");
		Pane myPane = null;
		try {
			myPane = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		stage.show();

		Stage st = (Stage) this.contentPanel.getScene().getWindow();
		st.close();
	}

	@FXML
	void checkOutFired(ActionEvent event) {
		System.out.println("Check Out");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(MainMenuController.class.getResource("CheckOut.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void checkInFired(ActionEvent event) {
		System.out.println("Check In");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(MainMenuController.class.getResource("CheckIn.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void queryOverdueFired(ActionEvent event) {
		System.out.println("Query Overdue");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(MainMenuController.class.getResource("queryOverdue.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void newMemberFired(ActionEvent event) {
		System.out.println("New Member");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(MainMenuController.class.getResource("addNewMember.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void newBookFired(ActionEvent event) {
		System.out.println("New Book");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(MainMenuController.class.getResource("addBook.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void newBookCopyFired(ActionEvent event) {
		System.out.println("New Book Copy");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(MainMenuController.class.getResource("addBookCopy.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void printCheckoutRecordFired(ActionEvent event) {
		System.out.println("Print checkout");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader
					.load(MainMenuController.class.getResource("printCheckoutRecord.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
