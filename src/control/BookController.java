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
import model.domain.Author;
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
		try {
			valISBN(fieldNewCopyISBN.getText());
			if (book == null) {
				Util.showAlert("Book not found, please check ", "Error", AlertType.ERROR);
			} else {
				System.out.println(book.addCopy());
				db.saveAbook(book);
				this.panelAddCopy.getChildren().clear();
				System.out.printf("Add book copy with: ISBN: %s \n", fieldNewCopyISBN.getText());
//				for (BookCopy bookCopy : book.getCopies()) {
//					System.out.println("Numero" + bookCopy);
//				}
			}
		} catch (ValException e) {
			Util.showAlert(e.getMessage(), "Error", AlertType.ERROR);
			return;
		}

	}

	public void valISBN(String isbn) throws ValException {
		if (isbn == null || isbn.isEmpty())
			throw new ValException("Invalid Book's ISBN");
	}

	@FXML
	void createNewBook(ActionEvent event) {

		try {
			valNoBookEmpty();
			valNumCopies();
		} catch (ValException e) {
			Util.showAlert(e.getMessage(), "Error", AlertType.ERROR);
		}
//		DataAccess db = new DataAccessFacade();
//		Author author = new 
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
