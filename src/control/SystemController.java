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
			setFormInfo("Add New Book","Use to add a new Book. Enter Book information.","/view/AddBook.PNG");
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
			setFormInfo("Add New Book Copy","Use to add another copy of a Book. Enter Book's ISBN.","/view/NewBookCopy.PNG");
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
		if (Util.getInstanceUser().isSuperUser()) {
			Util.showAlert("Admin can not checkout Books", "Permission denied", AlertType.ERROR);
			return;
		}
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/checkOut.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			setFormInfo("Check Out Form","Use to check out a book from Library. Enter Member Id and Book's ISBN.","/view/CheckOut.PNG");
			System.out.println("Agregado el panel Check Out");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void checkInFired(ActionEvent event) {
		System.out.println("Check In");
		if (Util.getInstanceUser().isSuperUser()) {
			Util.showAlert("Admin can not checkin Books", "Permission denied", AlertType.ERROR);
			return;
		}
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/checkIn.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			setFormInfo("Check In Form","Use to check in a book from Library. Enter Book's ISBN.","/view/CheckIn.PNG");
			System.out.println("Agregado el panel Check In");
		} catch (IOException e) {
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
	void queryOverdueFired(ActionEvent event) {
		System.out.println("Query Overdue");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/queryOverdue.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			setFormInfo("Query Overdue","Use to check for books already had to be returned to library. Enter Book's ISBN.","/view/QueryOverdue.PNG");
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
			setFormInfo("New Member","Use add a Library Member. Enter Member information.","/view/NewMember.PNG");
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
			setFormInfo("Print Checkout Record","Use to print checkout's member history. Enter Member ID.","/view/CheckoutHistory.PNG");
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
