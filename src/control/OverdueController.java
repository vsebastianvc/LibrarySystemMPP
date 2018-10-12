package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.domain.Book;
import model.domain.BookCopy;

public class OverdueController implements Initializable {

	@FXML // fx:id="fieldCheckRecBookGrid
	private TableView<BookCopy> tableOverdue;
	@FXML
	TableColumn<BookCopy, String> ISBN;
	@FXML
	TableColumn<BookCopy, String> BookName;
	@FXML
	TableColumn<BookCopy, String> numberCopy;
	@FXML
	TableColumn<BookCopy, String> isAvailable;
	@FXML
	TableColumn<BookCopy, String> overdue;
	@FXML
	TableColumn<BookCopy, String> possesion;

	ObservableList<BookCopy> data;

	private Book book;

	@FXML // fx:id="btnCheckRecDone"
	private Button btnCheckRecDone;

	@FXML
	private AnchorPane panelOverdue;

	public OverdueController(Book newSelection) {
		// TODO Auto-generated constructor stub
		this.book = newSelection;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		ISBN.setCellValueFactory(new PropertyValueFactory<BookCopy, String>("isbn"));
		BookName.setCellValueFactory(new PropertyValueFactory<BookCopy, String>("bookName"));
		numberCopy.setCellValueFactory(new PropertyValueFactory<BookCopy, String>("copyNum"));
		isAvailable.setCellValueFactory(new PropertyValueFactory<BookCopy, String>("exists"));
		overdue.setCellValueFactory(new PropertyValueFactory<BookCopy, String>("overdue"));
		possesion.setCellValueFactory(new PropertyValueFactory<BookCopy, String>("possesion"));
//		tableOverdue.getItems().setAll(checkoutRecord);

		data = FXCollections.observableArrayList();

		if (book.hasCopies()) {
			for (BookCopy bookCopy : book.getCopies()) {

				data.add(bookCopy);

			}

		}

		tableOverdue.setItems(data);
	}

	public void btnCheckRecDone() {
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/showBook.fxml"));
			this.panelOverdue.getChildren().clear();
			this.panelOverdue.getChildren().add(page);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
