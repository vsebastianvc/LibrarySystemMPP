package control;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SystemController {

	@FXML // fx:id="menuCheckOut"
	private MenuItem menuCheckOut;
	@FXML // fx:id="menuCheckIn"
	private MenuItem menuCheckIn;
	@FXML // fx:id="menuQueryOverdue"
	private MenuItem menuQueryOverdue;
	@FXML // fx:id="printCheckoutRecord"
	private MenuItem printCheckoutRecord;
	@FXML // fx:id="menuSystemExit"
	private MenuItem menuSystemExit;
	@FXML // fx:id="menuNewMember"
	private MenuItem menuNewMember;
	@FXML // fx:id="menuNewBook"
	private MenuItem menuNewBook;
	@FXML // fx:id="newBookCopy"
	private MenuItem menuBookCopy;

	// New book screen items
	@FXML // fx:id="fieldNewBookISBN"
	private TextField fieldNewBookISBN;
	@FXML // fx:id="fieldNewBookTitle"
	private TextField fieldNewBookTitle;
	@FXML // fx:id="fieldNewBookAuthors"
	private TextField fieldNewBookAuthors;
	@FXML // fx:id="choiceNewBookMaxCheckout"
	private ComboBox<String> choiceNewBookMaxCheckout;
	@FXML // fx:id="fieldNewBookNumCopies"
	private TextField fieldNewBookNumCopies;

	@FXML // fx:id="createNewBook"
	private Button btnCreateNewBook;

	@FXML
	void createNewBook(ActionEvent event) {
		
		System.out.printf("Add book with: ISBN: %s, Title: %s, Authors: %s, Max Checkout: %s, num of copies: %s \n",
				fieldNewBookISBN.getText(), fieldNewBookTitle.getText(), fieldNewBookAuthors.getText(),
				choiceNewBookMaxCheckout.getSelectionModel().getSelectedItem(), fieldNewBookNumCopies.getText());
	}

	private void initMaxCheckout() {
		ObservableList<String> listMaxDays = FXCollections.observableArrayList();
		listMaxDays.removeAll(listMaxDays);
		String days7 = "7";
		String days21 = "21";
		listMaxDays.addAll(days7, days21);
		//choiceNewBookMaxCheckout.getItems().addAll(listMaxDays);
	}

	@FXML
	void newBookFired(ActionEvent event) {
		System.out.println("New Book");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/addBook.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// New member's screen items
	@FXML // fx:id="fieldNewMemberId"
	private TextField fieldNewMemberId;
	@FXML // fx:id="newFirstName"
	private TextField fieldNewFirstName;
	@FXML // fx:id="newLastName"
	private TextField fieldNewLastName;
	@FXML // fx:id="newTelNumber"
	private TextField fieldNewTelNumber;
	@FXML // fx:id="newStreet"
	private TextField fieldNewStreet;
	@FXML // fx:id="newState"
	private TextField fieldNewState;
	@FXML // fx:id="newCity"
	private TextField fieldNewCity;
	@FXML // fx:id="newZip"
	private TextField fieldNewZip;

	@FXML // fx:id="createNewMember"
	private Button btnCreateNewMember;

	@FXML
	void createNewMember(ActionEvent event) {

		System.out.printf(
				"Add member with: ID: %s, First name: %s, Last name: %s, Tel number: %s, Street: %s, State: %s, City: %s, Zip: %s \n",
				fieldNewMemberId.getText(), fieldNewFirstName.getText(), fieldNewLastName.getText(),
				fieldNewTelNumber.getText(), fieldNewStreet.getText(), fieldNewState.getText(), fieldNewCity.getText(),
				fieldNewZip.getText());
	}

	// New book's copy screen items
	@FXML // fx:id="fieldNewCopyISBN"
	private TextField fieldNewCopyISBN;

	@FXML // fx:id="btnCreateNewBookCopy"
	private Button btnCreateNewBookCopy;

	@FXML
	void createNewBookCopy(ActionEvent event) {

		System.out.printf("Add book copy with: ISBN: %s \n", fieldNewCopyISBN.getText());
	}

	@FXML
	void newBookCopyFired(ActionEvent event) {
		System.out.println("New Book Copy");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("addBookCopy.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Checkout screen items
	@FXML // fx:id="fieldCheckoutISBN"
	private TextField fieldCheckoutISBN;
	@FXML // fx:id="fieldCheckoutMemberId"
	private TextField fieldCheckoutMemberId;

	@FXML // fx:id="btnCheckout"
	private Button btnCheckout;

	@FXML
	void checkoutBook(ActionEvent event) {
		System.out.printf("Checkout book with: ISBN: %s for member id: %s \n", fieldCheckoutISBN.getText(),
				fieldCheckoutMemberId.getText());
	}

	// Checkin screen items
	@FXML // fx:id="fieldCheckinISBN"
	private TextField fieldCheckinISBN;

	@FXML // fx:id="btnCheckin"
	private Button btnCheckin;

	@FXML
	void checkinBook(ActionEvent event) {
		System.out.printf("Checkin book with: ISBN: %s \n", fieldCheckinISBN.getText());
	}

	@FXML // fx:id="newIssue"
	private Button btnNewIssue; // Value injected by FXMLLoader

	@FXML // fx:id="saveIssue"
	private Button btnSaveIssue; // Value injected by FXMLLoader

	@FXML // fx:id="titleLine"
	private AnchorPane contentPanel; // Value injected by FXMLLoader

	/**
	 * Initializes the controller class.
	 */
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		System.out.println("Entro a initialize");
		initMaxCheckout();
	}

	/**
	 * Called when the NewIssue button is fired.
	 *
	 * @param event the action event.
	 */
	@FXML
	void newIssueFired(ActionEvent event) {

		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("Test.fxml"));
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
		System.out.println("Exit");
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
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("CheckOut.fxml"));
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
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("CheckIn.fxml"));
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
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("queryOverdue.fxml"));
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
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("addNewMember.fxml"));
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
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("printCheckoutRecord.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
