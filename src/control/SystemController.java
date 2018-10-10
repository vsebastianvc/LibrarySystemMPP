package control;

import java.io.IOException;
import java.util.HashMap;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.dataaccess.Auth;
import model.dataaccess.DataAccess;
import model.dataaccess.DataAccessFacade;
import model.domain.CheckoutRecordEntry;
import model.domain.LibraryMember;
import util.Util;

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
		
		try {
			valNoBookEmpty();
			valNumCopies();
			}
		catch(ValException e) {
			Util.showAlert(e.getMessage(), "Error", AlertType.ERROR);
		}
	}

	public void valNoBookEmpty() throws ValException {
		if (fieldNewBookISBN.getText()==null||fieldNewBookTitle.getText()==null||fieldNewBookAuthors.getText()==null||
			choiceNewBookMaxCheckout.getSelectionModel().getSelectedItem()==null||fieldNewBookNumCopies.getText()==null)
			throw new ValException("Book fields cannot be empty");
		if (fieldNewBookISBN.getText().isEmpty()||fieldNewBookTitle.getText().isEmpty()||fieldNewBookAuthors.getText().isEmpty()||
			fieldNewBookNumCopies.getText().isEmpty())
			throw new ValException("Book fields cannot be empty");
	}
	
	public void valNumCopies() throws ValException{
		if (!fieldNewBookNumCopies.getText().matches("[0-9]{1,5}"))
			throw new ValException("Invalid number of copies");
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


	
	public void valNoMemberEmpty() throws ValException {
		if (fieldNewMemberId.getText()==null||fieldNewFirstName.getText()==null||fieldNewLastName.getText()==null||
				fieldNewTelNumber.getText()==null||fieldNewStreet.getText()==null||fieldNewState.getText()==null||
				fieldNewCity.getText()==null||fieldNewZip.getText()==null)
				throw new ValException("Member fields cannot be empty");
		if (fieldNewMemberId.getText().isEmpty()||fieldNewFirstName.getText().isEmpty()||fieldNewLastName.getText().isEmpty()||
			fieldNewTelNumber.getText().isEmpty()||fieldNewStreet.getText().isEmpty()||fieldNewState.getText().isEmpty()||
			fieldNewCity.getText().isEmpty()||fieldNewZip.getText().isEmpty())
			throw new ValException("Member fields cannot be empty");
	}
	
	public void valZip() throws ValException{
		if (!fieldNewZip.getText().matches("[0-9]{5}"))
			throw new ValException("Invalid Zip code");
	}
	
	public void valPhone() throws ValException{
		if (!fieldNewTelNumber.getText().matches("[0-9]*"))
			throw new ValException("Invalid phone number");
	}
	
	@FXML // fx:id="createNewMember"
	private Button btnCreateNewMember;
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
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/addBookCopy.fxml"));
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

	// Query overdue
	@FXML // fx:id="fieldQueryOverdueISBN"
	private TextField fieldQueryOverdueISBN;

	@FXML // fx:id="btnQueryOverdue"
	private Button btnQueryOverdue;

	@FXML
	void queryOverdue(ActionEvent event) {
		System.out.printf("Query overdue with ISBN: %s \n", fieldQueryOverdueISBN.getText());
	}

	// Print checkout record
	@FXML // fx:id="fieldPrintCheckoutMemberID"
	private TextField fieldPrintCheckoutMemberID;

	@FXML // fx:id="btnPrintCheckoutRecord"
	private Button btnPrintCheckoutRecord;

	@FXML
	void printCheckoutRecord(ActionEvent event) {
		DataAccess db = new DataAccessFacade();
		HashMap<String, LibraryMember> users = db.readMemberMap();
		LibraryMember member = users.get(fieldPrintCheckoutMemberID.getText());
		System.out.printf("Print checkout record of member id: %s \n", fieldPrintCheckoutMemberID.getText());
		System.out.println("Sending to printer ....");
		System.out.println("=====================================================");
		System.out.println("Copy number | ISBN | Title"+" | Checkout date | Due date");
		System.out.println("=====================================================");
		if(member != null && member.getCheckoutRecordEntries()!=null) {
			for (CheckoutRecordEntry entry : member.getCheckoutRecordEntries()) {
				System.out.println(entry.getBookcopy().toString()+" | "+entry.getCheckoutDate().toString()+" | "+entry.getDueDate().toString());
			}
		} else {
			Util.showAlert("Member id Not found", "Error", AlertType.ERROR);
		}
		System.out.println("=====================================================");
		System.out.println("Printer - Finish");
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
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/CheckOut.fxml"));
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
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/CheckIn.fxml"));
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
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/queryOverdue.fxml"));
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
		if (Util.getInstanceUser().getAuthorization().equals(Auth.LIBRARIAN)) {
			Util.showAlert("Librarian cannot add Member", "Permission denied", AlertType.ERROR);
			return;
		}
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/addNewMember.fxml"));
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
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/printCheckoutRecord.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
