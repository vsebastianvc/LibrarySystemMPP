package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.domain.Book;
import model.domain.CheckoutRecordEntry;
import model.domain.LibraryMember;
import util.Util;

public class CheckoutController implements Initializable {

	@FXML // fx:id="fieldCheckRecMemberID
	private TextField fieldCheckRecMemberID;
	@FXML // fx:id="fieldCheckRecMemberName
	private TextField fieldCheckRecMemberName;

	@FXML // fx:id="fieldCheckRecBookGrid
	private TableView<CheckoutRecordEntry> fieldCheckRecBookGrid;
	@FXML
	TableColumn<CheckoutRecordEntry, String> ISBN;
	@FXML
	TableColumn<CheckoutRecordEntry, String> BookName;
	@FXML
	TableColumn<CheckoutRecordEntry, String> DateOut;
	@FXML
	TableColumn<CheckoutRecordEntry, String> DueDate;

	ObservableList<CheckoutController> data;

	@FXML // fx:id="btnCheckRecDone"
	private Button btnCheckRecDone;
	
	@FXML
	private AnchorPane panelCheckOutRecord;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		fieldCheckRecMemberID.setText(Util.getInstanceRecord().getMember().getMemberId());
		fieldCheckRecMemberName.setText(Util.getInstanceRecord().getMember().getFullName());

		LibraryMember mem = Util.getInstanceRecord().getMember();
		List<CheckoutRecordEntry> checkoutRecord = mem.getCheckoutRecordEntries();
		ISBN.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntry, String>("bookIsbn"));
		BookName.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntry, String>("bookName"));
		DateOut.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntry, String>("checkoutDateString"));
		DueDate.setCellValueFactory(new PropertyValueFactory<CheckoutRecordEntry, String>("duedateString"));

		fieldCheckRecBookGrid.getItems().setAll(checkoutRecord);

	}
	public void btnCheckRecDone() {
		this.panelCheckOutRecord.getChildren().clear();
	}

}
