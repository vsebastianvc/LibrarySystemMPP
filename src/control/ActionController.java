package control;

import java.time.LocalDate;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

	// Print checkout record
	@FXML // fx:id="fieldPrintCheckoutMemberID"
	private TextField fieldPrintCheckoutMemberID;

	@FXML // fx:id="btnPrintCheckoutRecord"
	private Button btnPrintCheckoutRecord;
	
	
	
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
		Book temp_book = books.get(fieldCheckoutISBN.getText());
			if (temp_book.isAvailable()) {
				BookCopy bc = temp_book.getNextAvailableCopy();

				CheckoutRecordEntry cre = list_members.get(fieldCheckoutMemberId.getText()).addCheckoutRecordEntry(bc,
						LocalDate.now(), LocalDate.now().plusDays(temp_book.getMaxCheckoutLength()));
				bc.changeAvailability();
				db.saveNewMember(list_members.get(fieldCheckoutMemberId.getText()));
				db.saveAbook(temp_book);
				Util.persistRecord(cre);
				this.panelCheckOut.getChildren().clear();
				System.out.println("HIZO CHECKOUT !!!!");
				try {
					
					AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/CheckOutRecord.fxml"));
					this.panelCheckOut.getChildren().clear();
					this.panelCheckOut.getChildren().add(page);
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

	@FXML
	void checkinBook(ActionEvent event) {
//		try {
////			valISBN(fieldCheckinISBN.getText());
//		} catch (ValException e) {
//			Util.showAlert(e.getMessage(), "Error", AlertType.ERROR);
//		}
////		System.out.printf("Checkin book with: ISBN: %s \n", fieldCheckinISBN.getText());
	}


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

	public void valISBN(String isbn) throws ValException {
		if (isbn == null || isbn.isEmpty())
			throw new ValException("Invalid Book's ISBN");
	}

	public void valMemberID(String memberID) throws ValException {
		if (memberID == null || memberID.isEmpty())
			throw new ValException("Invalid Member ID");
	}
}
