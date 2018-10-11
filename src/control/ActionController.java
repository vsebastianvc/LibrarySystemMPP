package control;

import java.time.LocalDate;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.dataaccess.DataAccess;
import model.dataaccess.DataAccessFacade;
import model.domain.Book;
import model.domain.BookCopy;
import model.domain.CheckoutRecordEntry;
import model.domain.LibraryMember;
import javafx.scene.control.Alert.AlertType;
import util.Util;
import util.ValException;

public class ActionController {
	// Checkout screen items
	@FXML // fx:id="fieldCheckoutISBN"
	private TextField fieldCheckoutISBN;
	@FXML // fx:id="fieldCheckoutMemberId"
	private TextField fieldCheckoutMemberId;

	@FXML // fx:id="btnCheckout"
	private Button btnCheckout;

	@FXML
	private AnchorPane panelCheckOut;

	@FXML
	void checkoutBook(ActionEvent event) {
		try {
			valMemberID(fieldCheckoutMemberId.getText());
			valISBN(fieldCheckoutISBN.getText());
		} catch (ValException e) {
			Util.showAlert(e.getMessage(), "Error", AlertType.ERROR);
		}
		DataAccess db = new DataAccessFacade();
		HashMap<String, LibraryMember> list_members = db.readMemberMap();
		HashMap<String, Book> books = db.readBooksMap();

		if (list_members.get(fieldCheckoutMemberId.getText()) == null) {
			Util.showAlert("Member Id Not found", "Not data found", AlertType.WARNING);
			return;
		}
		Book temp_book = books.get(fieldCheckoutISBN.getText());

		if (temp_book == null) {
			Util.showAlert("Book isbn No found", "Not data found", AlertType.WARNING);
			return;
		} else {
			if (temp_book.isAvailable()) {
				BookCopy bc = temp_book.getNextAvailableCopy();

				CheckoutRecordEntry cre = list_members.get(fieldCheckoutMemberId.getText()).addCheckoutRecordEntry(bc,
						LocalDate.now(), LocalDate.now().plusDays(temp_book.getMaxCheckoutLength()));
				bc.changeAvailability();
				db.saveNewMember(list_members.get(fieldCheckoutMemberId.getText()));
				db.saveAbook(temp_book);
				this.panelCheckOut.getChildren().clear();
				System.out.println("HIZO CHECKOUT !!!!");
				try {
					// checkoutSuccess( cre);

					// Gooooooooooooo hizo checkout
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return;
			} else {
				Util.showAlert("No available copy for the book", "Not data found", AlertType.ERROR);
				return;
			}
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
}
