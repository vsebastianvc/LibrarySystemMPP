package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.dataaccess.Auth;
import model.dataaccess.DataAccess;
import model.dataaccess.DataAccessFacade;
import model.domain.Address;
import model.domain.Author;
import model.domain.Book;
import model.domain.CheckoutRecordEntry;
import model.domain.LibraryMember;
import util.Util;
import util.ValException;

public class SystemController {

	@FXML // fx:id="fieldFormName"
	private Label fieldFormName;
	@FXML // fx:id="fieldFormDesc"
	private Label fieldFormDesc;
	@FXML // fx:id="fieldFormImage"
	private ImageView fieldFormImage;
	
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
	
	@FXML // fx:id="fieldCheckRecMemberID
	private TextField fieldCheckRecMemberID;
	@FXML // fx:id="fieldCheckRecMemberName
	private TextField fieldCheckRecMemberName;
	@FXML // fx:id="fieldCheckRecBookISBN
	private TextField fieldCheckRecBookISBN;
	@FXML // fx:id="fieldCheckRecBookName
	private TextField fieldCheckRecBookName;
	@FXML // fx:id="fieldCheckRecBookDateOut
	private TextField fieldCheckRecBookDateOut;
	@FXML // fx:id="fieldCheckRecBookGrid
	private TableView fieldCheckRecBookGrid;
	
	@FXML // fx:id="btnCheckRecDone"
	private Button btnCheckRecDone;

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

	// Checkout screen items
	@FXML // fx:id="fieldCheckoutISBN"
	private TextField fieldCheckoutISBN;
	@FXML // fx:id="fieldCheckoutMemberId"
	private TextField fieldCheckoutMemberId;

	@FXML // fx:id="btnCheckout"
	private Button btnCheckout;

	@FXML
	void checkoutBook(ActionEvent event) {
		try {
			valMemberID(fieldCheckoutMemberId.getText());
			valISBN(fieldCheckoutISBN.getText());
		} catch (ValException e) {
			Util.showAlert(e.getMessage(), "Error", AlertType.ERROR);
		}
		System.out.printf("Checkout book with: ISBN: %s for member id: %s \n", fieldCheckoutISBN.getText(),
				fieldCheckoutMemberId.getText());
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
		//setFormInfo("Main Menu","Choose a menu option or use shortcuts (ctrl+key) to access options.","/view/Book.PNG");
		System.out.println("Initializing");
	}

	void setFormInfo(String title, String desc, String image) {
		fieldFormName.setText(title);
		fieldFormDesc.setText(desc);
		fieldFormImage.setImage(new Image(image));
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
			setFormInfo("Check Out Form","Use to check out a book from Library. Enter Member Id and Book's ISBN.","/view/CheckOut.PNG");
			System.out.println("Agregado el panel Check Out");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@FXML
	void showCheckOutRecord() {
		List<Author> aut1 = new ArrayList<Author>();
		aut1.add(new Author( "Carlos", "Hernandez", "+57 3004633523", new Address("1000 N 4St","Fairfield","IOWA","52557"), "Lealo"));
		LibraryMember lm1 = new LibraryMember("1001", "Carlos", "Hernandez", "+57 3004633523", new Address("1000 N 4St","Fairfield","IOWA","52557"));
		Book b1 = new Book("BK-001", "Mi vida secreta",21,aut1);
		
		System.out.println("Show Check Out Record");
		try {
			FXMLLoader loader =  new FXMLLoader(getClass().getResource("/view/CheckoutRecord.fxml"));
			CheckoutController controller = new CheckoutController(lm1.getMemberId(), lm1.getFullName());
	        // Set it in the FXMLLoader
	        loader.setController(controller);
			AnchorPane page = (AnchorPane)loader.load();
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			setFormInfo("Check Out Record","Book check out done!. Press Return button.","/view/Reading.PNG");
			System.out.println("Agregado el panel Check Out Record");
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
			System.out.println("Agregado el panel Check In");
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
