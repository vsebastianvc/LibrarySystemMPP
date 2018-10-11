package control;

import java.io.IOException;
import java.util.HashMap;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.dataaccess.Auth;
import model.dataaccess.DataAccess;
import model.dataaccess.DataAccessFacade;
import model.domain.CheckoutRecordEntry;
import model.domain.LibraryMember;
import util.Util;
import util.ValException;

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


	@FXML
	void newBookFired(ActionEvent event) {
		System.out.println("New Book");
		if (Util.getInstanceUser().getAuthorization().equals(Auth.LIBRARIAN)) {
			Util.showAlert("Librarian cannot add a Book", "Permission denied", AlertType.ERROR);
			return;
		}
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

	@FXML
	void newBookCopyFired(ActionEvent event) {
		System.out.println("New Book Copy");
		if (Util.getInstanceUser().getAuthorization().equals(Auth.LIBRARIAN)) {
			Util.showAlert("Librarian cannot add a Book Copy", "Permission denied", AlertType.ERROR);
			return;
		}
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



	public void valISBN(String isbn) throws ValException {
		if (isbn == null || isbn.isEmpty())
			throw new ValException("Invalid Book's ISBN");
	}

	public void valMemberID(String memberID) throws ValException {
		if (memberID == null || memberID.isEmpty())
			throw new ValException("Invalid Member ID");
	}
	
	// Checkin screen items
	@FXML // fx:id="fieldCheckinISBN"
	private TextField fieldCheckinISBN;

	@FXML // fx:id="btnCheckin"
	private Button btnCheckin;

	@FXML
	void checkinBook(ActionEvent event) {
		try {
			valISBN(fieldCheckinISBN.getText());
		} catch (ValException e) {
			Util.showAlert(e.getMessage(), "Error", AlertType.ERROR);
		}
		System.out.printf("Checkin book with: ISBN: %s \n", fieldCheckinISBN.getText());
	}

	// Query overdue
	@FXML // fx:id="fieldQueryOverdueISBN"
	private TextField fieldQueryOverdueISBN;

	@FXML // fx:id="btnQueryOverdue"
	private Button btnQueryOverdue;

	@FXML
	void queryOverdue(ActionEvent event) {
		try {
			valISBN(fieldQueryOverdueISBN.getText());
		} catch (ValException e) {
			Util.showAlert(e.getMessage(), "Error", AlertType.ERROR);
		}
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
		try {
			valMemberID(fieldPrintCheckoutMemberID.getText());
		} catch (ValException e) {
			Util.showAlert(e.getMessage(), "Error", AlertType.ERROR);
			return;
		}
		LibraryMember member = users.get(fieldPrintCheckoutMemberID.getText());
		System.out.printf("Print checkout record of member id: %s \n", fieldPrintCheckoutMemberID.getText());
		System.out.println("Sending to printer ....");
		System.out.println("=====================================================");
		System.out.println("Copy number | ISBN | Title"+" | Checkout date | Due date");
		System.out.println("=====================================================");
		if(member != null && member.getCheckoutRecordEntries()!=null) {
			for (CheckoutRecordEntry entry : member.getCheckoutRecordEntries()) {
				System.out.println(entry.getBookcopy()+" | "+entry.getCheckoutDateString()+" | "+entry.getDuedateString());
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
		System.out.println("Initializing");
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
		if (Util.getInstanceUser().isSuperUser()) {
			Util.showAlert("Admin can not checkout Book", "Permission denied", AlertType.ERROR);
			return;
		}
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
