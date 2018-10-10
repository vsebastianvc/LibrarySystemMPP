package control;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.dataaccess.DataAccess;
import model.dataaccess.DataAccessFacade;
import model.domain.Address;
import model.domain.LibraryMember;
import model.domain.User;

public class MemberController {
	// New member's screen items
	@FXML // fx:id="fieldNewMemberId"
	private TextField fieldNewMemberId;
	@FXML // fx:id="newFirstName"
	private TextField fieldNewFirstName;
	@FXML // fx:id="newLastName"
	private TextField fieldNewLastName;
	@FXML // fx:id="newTelNumber"
	private TextField fieldNewTelNumber;
	@FXML // fx:id="newStreet"
	private TextField fieldNewStreet;
	@FXML // fx:id="newState"
	private TextField fieldNewState;
	@FXML // fx:id="newCity"
	private TextField fieldNewCity;
	@FXML // fx:id="newZip"
	private TextField fieldNewZip;

	@FXML // fx:id="createNewMember"
	private Button btnCreateNewMember;
	
	@FXML
	private AnchorPane panelNewMember;

	@FXML
	void createNewMember(ActionEvent event) {
		DataAccess db = new DataAccessFacade();
		
		Address add = new Address(fieldNewStreet.getText(), fieldNewCity.getText(), fieldNewState.getText(),
				fieldNewZip.getText());
		LibraryMember lm = new LibraryMember(fieldNewMemberId.getText(), fieldNewFirstName.getText(),
				fieldNewLastName.getText(), fieldNewTelNumber.getText(), add);
		db.saveNewMember(lm);

		DataAccess dbTest = new DataAccessFacade();
		HashMap<String, LibraryMember> users = dbTest.readMemberMap();
		LibraryMember user = users.get(fieldNewMemberId.getText());
		System.out.println("Check User: "+user);

//		System.out.printf(
//				"Add member with: ID: %s, First name: %s, Last name: %s, Tel number: %s, Street: %s, State: %s, City: %s, Zip: %s \n",
//				fieldNewMemberId.getText(), fieldNewFirstName.getText(), fieldNewLastName.getText(),
//				fieldNewTelNumber.getText(), fieldNewStreet.getText(), fieldNewState.getText(), fieldNewCity.getText(),
//				fieldNewZip.getText());
		  this.panelNewMember.getChildren().clear();
		
	}
}
