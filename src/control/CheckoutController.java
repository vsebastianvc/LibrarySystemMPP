package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.domain.CheckoutRecordEntry;

public class CheckoutController implements Initializable{
	
	private StringProperty memberIdString = new SimpleStringProperty();
    private StringProperty memberNameString = new SimpleStringProperty();
    
	
	@FXML // fx:id="fieldCheckRecMemberID
	private TextField fieldCheckRecMemberID;
	@FXML // fx:id="fieldCheckRecMemberName
	private TextField fieldCheckRecMemberName;
	//@FXML // fx:id="fieldCheckRecBookISBN
	//private TextField fieldCheckRecBookISBN;
	//@FXML // fx:id="fieldCheckRecBookName
	//private TextField fieldCheckRecBookName;
	//@FXML // fx:id="fieldCheckRecBookDateOut
	//private TextField fieldCheckRecBookDateOut;
	@FXML // fx:id="fieldCheckRecBookGrid
	private TableView<CheckoutRecordEntry> fieldCheckRecBookGrid;
	@FXML
	TableColumn<ObservableList<String>, String> ISBN;
	@FXML
	TableColumn<ObservableList<String>, String> BookName;
	@FXML
	TableColumn<ObservableList<String>, String> DateOut;
	@FXML
	TableColumn<ObservableList<String>, String> DueDate;
	
	@FXML // fx:id="btnCheckRecDone"
	private Button btnCheckRecDone;
	
	public CheckoutController(String memberId, String memberName) {
		memberIdString.set(memberId);
		memberNameString.set(memberName);
	}
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	    
		fieldCheckRecMemberID.setText(memberIdString.get());
		fieldCheckRecMemberName.setText(memberNameString.get());

        //fieldCheckRecBookGrid.getItems().setAll(parseCheckoutEntries());
        fieldCheckRecBookGrid.getColumns().get(0).setText("1001");
        fieldCheckRecBookGrid.getColumns().get(1).setText("Mi vida secreta");
        fieldCheckRecBookGrid.getColumns().get(2).setText("10-oct-2018");
        fieldCheckRecBookGrid.getColumns().get(0).setText("31-oct-2018");
    }

}
