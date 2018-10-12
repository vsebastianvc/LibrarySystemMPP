package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
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

	@FXML // fx:id="titleLine"
	private AnchorPane contentPanel; // Value injected by FXMLLoader

    //Left main window panel objects, to be changed dinamically,
	//depending on the form
	@FXML // fx:id="fieldFormName"
	private Label fieldFormName;
	@FXML // fx:id="fieldFormDesc"
	private Label fieldFormDesc;
	@FXML // fx:id="fieldFormImage"
	private ImageView fieldFormImage;
	
	//Menu items
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
	
	//Checkout record entry fields
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

	// Checkin screen items
	@FXML // fx:id="fieldCheckinISBN"
	private TextField fieldCheckinISBN;
	@FXML // fx:id="btnCheckin"
	private Button btnCheckin;

	// Query overdue items
	@FXML // fx:id="fieldQueryOverdueISBN"
	private TextField fieldQueryOverdueISBN;
	@FXML // fx:id="btnQueryOverdue"
	private Button btnQueryOverdue;

	@FXML
	void newBookFired(ActionEvent event) {
//		System.out.println("New Book");
		if (Util.getInstanceUser().getAuthorization().equals(Auth.LIBRARIAN)) {
			Util.showAlert("Librarian cannot add a Book", "Permission denied", AlertType.ERROR);
			return;
		}
		try {
//			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/addBook.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addBook.fxml"));
			 BookController book= new BookController("new");
			 loader.setController(book);
			AnchorPane page = (AnchorPane) loader.load();
			this.contentPanel.getChildren().clear();
			setFormInfo("Add New Book","Use to add a new Book. Enter Book information.","/view/AddBook.PNG");
			this.contentPanel.getChildren().add(page);
//			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void newBookCopyFired(ActionEvent event) {
//		System.out.println("New Book Copy");
		if (Util.getInstanceUser().getAuthorization().equals(Auth.LIBRARIAN)) {
			Util.showAlert("Librarian cannot add a Book Copy", "Permission denied", AlertType.ERROR);
			return;
		}
		try {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addBookCopy.fxml"));
			 BookController book= new BookController("copy");
			 loader.setController(book);
			AnchorPane page = (AnchorPane) loader.load();
			
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			setFormInfo("Add New Book Copy","Use to add another copy of a Book. Enter Book's ISBN.","/view/NewBookCopy.PNG");
//			System.out.println("Agregado el panel");
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
	
	/**
	 * Initializes the controller class.
	 */
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
//		System.out.println("Initializing");
	}

	void setFormInfo(String title, String desc, String image) {
//		System.out.println("Title: "+title);
		fieldFormName.setText(title);
		fieldFormDesc.setText(desc);
		fieldFormImage.setImage(new Image(image));
	}
	
	@FXML
	void systemLogoutFired(ActionEvent event) {
//		System.out.println("Exit");
		Stage stage = new Stage();
		stage.setTitle("Welcome Library System");
		Pane myPane = null;
		try {
			myPane = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		String css = this.getClass().getResource("/view/Login.css").toExternalForm(); 
		stage.show();
		scene.getStylesheets().add(css);
		Stage st = (Stage) this.contentPanel.getScene().getWindow();
		st.close();
	}

	@FXML
	void systemExitFired(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void checkOutFired(ActionEvent event) {
//		System.out.println("Check Out");
		if (Util.getInstanceUser().getAuthorization().equals(Auth.ADMIN)) {
			Util.showAlert("Admin can not checkout Books", "Permission denied", AlertType.ERROR);
			return;
		}
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/checkOut.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			setFormInfo("Check Out Form","Use to check out a book from Library. Enter Member Id and Book's ISBN.","/view/CheckOut.PNG");
//			System.out.println("Agregado el panel Check Out");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void checkInFired(ActionEvent event) {
//		System.out.println("Check In");
		if (Util.getInstanceUser().getAuthorization().equals(Auth.ADMIN)) {
			Util.showAlert("Admin can not checkout Books", "Permission denied", AlertType.ERROR);
			return;
		}
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/checkIn.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			setFormInfo("Check In Form","Use to check in a book from Library. Enter Book's ISBN.","/view/CheckIn.PNG");
//			System.out.println("Agregado el panel Check In");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void queryOverdueFired(ActionEvent event) {
//		System.out.println("Query Overdue");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/showBook.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			setFormInfo("Query Overdue","Use to check for books already had to be returned to library. Enter Book's ISBN.","/view/QueryOverdue.PNG");
//			System.out.println("Agregado el panel");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void newMemberFired(ActionEvent event) {
//		System.out.println("New Member");
		if (Util.getInstanceUser().getAuthorization().equals(Auth.LIBRARIAN)) {
			Util.showAlert("Librarian cannot add Member", "Permission denied", AlertType.ERROR);
			return;
		}
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/addNewMember.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			setFormInfo("New Member","Use add a Library Member. Enter Member information.","/view/NewMember.PNG");
//			System.out.println("Agregado el panel");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void printCheckoutRecordFired(ActionEvent event) {
//		System.out.println("Print checkout");
		if (Util.getInstanceUser().getAuthorization().equals(Auth.ADMIN)) {
			Util.showAlert("Admin can not print checkout record", "Permission denied", AlertType.ERROR);
			return;
		}
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/printCheckoutRecord.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			setFormInfo("Print Checkout Record","Use to print checkout's member history. Enter Member ID.","/view/CheckoutHistory.PNG");
//			System.out.println("Agregado el panel");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	void queryOverdue(ActionEvent event) {
		if (Util.getInstanceUser().getAuthorization().equals(Auth.LIBRARIAN)) {
			Util.showAlert("Librarian cannot view overdue", "Permission denied", AlertType.ERROR);
			return;
		}
		try {
			valISBN(fieldQueryOverdueISBN.getText());
		} catch (ValException e) {
			Util.showAlert(e.getMessage(), "Error", AlertType.ERROR);
		}
//		System.out.printf("Query overdue with ISBN: %s \n", fieldQueryOverdueISBN.getText());
	}

}
