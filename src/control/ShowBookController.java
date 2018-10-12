package control;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.dataaccess.DataAccess;
import model.dataaccess.DataAccessFacade;
import model.domain.Book;
import model.domain.BookCopy;
import model.domain.CheckoutRecordEntry;

public class ShowBookController implements Initializable {

	@FXML // fx:id="fieldCheckRecBookGrid
	private TableView<Book> tableShowBook;
	@FXML
	TableColumn<Book, String> ISBN; 
	@FXML
	TableColumn<Book, String> BookName;
	@FXML
	TableColumn<Book, String> maxCheckoutDate;

	ObservableList<Book> data;

	@FXML // fx:id="btnCheckRecDone"
	private Button btnCheckRecDone;

	@FXML
	private AnchorPane panelShowBook;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		ISBN.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
		BookName.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		maxCheckoutDate.setCellValueFactory(new PropertyValueFactory<Book, String>("maxCheckoutLength"));
		data = FXCollections.observableArrayList();

		DataAccess db = new DataAccessFacade();

		HashMap<String, Book> books = db.readBooksMap();
		for (Entry<String, Book> entry : books.entrySet()) {
			data.add(entry.getValue());

		}

		tableShowBook.setItems(data);

		tableShowBook.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {

				try {
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/queryOverdue.fxml"));
					 OverdueController overdue= new OverdueController(newSelection);
					 loader.setController(overdue);
					AnchorPane page = (AnchorPane) loader.load();
					this.panelShowBook.getChildren().clear();
					this.panelShowBook.getChildren().add(page);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		
	}
	public void btnCheckRecDone() {
		this.panelShowBook.getChildren().clear();
	}
}
