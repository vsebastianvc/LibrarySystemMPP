package control;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import model.dataaccess.DataAccess;
import model.dataaccess.DataAccessFacade;
import model.domain.Book;
import model.domain.BookCopy;
import model.domain.LibraryMember;
import util.Util;
import util.ValException;

public class BookController {
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

	// New book's copy screen items
	@FXML // fx:id="fieldNewCopyISBN"
	private TextField fieldNewCopyISBN;

	@FXML // fx:id="btnCreateNewBookCopy"
	private Button btnCreateNewBookCopy;
	
	@FXML
	private AnchorPane panelAddCopy;

	@FXML
	void createNewBookCopy(ActionEvent event) {
		DataAccess db = new DataAccessFacade();
		HashMap<String, Book> books = db.readBooksMap();
		Book book = books.get(fieldNewCopyISBN.getText());
		if (book == null) {
			Util.showAlert("Book not found, please check ", "Error login", AlertType.ERROR);
		} else {
			book.addCopy();
			db.saveAbook(book);
			DataAccess dbTest = new DataAccessFacade();
			HashMap<String, Book> booksTest = dbTest.readBooksMap();
			Book bookTest = books.get(fieldNewCopyISBN.getText());
			System.out.println("Check User: " + booksTest);
			this.panelAddCopy.getChildren().clear();
			System.out.printf("Add book copy with: ISBN: %s \n", fieldNewCopyISBN.getText());
		}
	}

	@FXML
	void createNewBook(ActionEvent event) {

		try {
			valNoBookEmpty();
			valNumCopies();
		} catch (ValException e) {
			Util.showAlert(e.getMessage(), "Error", AlertType.ERROR);
		}
	}

	public void valNoBookEmpty() throws ValException {
		if (fieldNewBookISBN.getText() == null || fieldNewBookTitle.getText() == null
				|| fieldNewBookAuthors.getText() == null
				|| choiceNewBookMaxCheckout.getSelectionModel().getSelectedItem() == null
				|| fieldNewBookNumCopies.getText() == null)
			throw new ValException("Book fields cannot be empty");
		if (fieldNewBookISBN.getText().isEmpty() || fieldNewBookTitle.getText().isEmpty()
				|| fieldNewBookAuthors.getText().isEmpty() || fieldNewBookNumCopies.getText().isEmpty())
			throw new ValException("Book fields cannot be empty");
	}

	public void valNumCopies() throws ValException {
		if (!fieldNewBookNumCopies.getText().matches("[0-9]{1,5}"))
			throw new ValException("Invalid number of copies");
	}
}
