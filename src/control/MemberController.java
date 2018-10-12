package control;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.dataaccess.DataAccess;
import model.dataaccess.DataAccessFacade;
import model.domain.Address;
import model.domain.LibraryMember;
import util.Util;
import util.ValException;

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
		try {
			valNoMemberEmpty();
			valZip();
			valPhone();
			
			DataAccess db = new DataAccessFacade();

			Address add = new Address(fieldNewStreet.getText(), fieldNewCity.getText(), fieldNewState.getText(),
					fieldNewZip.getText());
			LibraryMember lm = new LibraryMember(fieldNewMemberId.getText(), fieldNewFirstName.getText(),
					fieldNewLastName.getText(), fieldNewTelNumber.getText(), add);
			db.saveNewMember(lm);

//			DataAccess dbTest = new DataAccessFacade();
//			HashMap<String, LibraryMember> users = dbTest.readMemberMap();
//			LibraryMember user = users.get(fieldNewMemberId.getText());
//			System.out.println("Check User: " + user);
			this.panelNewMember.getChildren().clear();
//			System.out.printf(
//					"Add member with: ID: %s, First name: %s, Last name: %s, Tel number: %s, Street: %s, State: %s, City: %s, Zip: %s \n",
//					fieldNewMemberId.getText(), fieldNewFirstName.getText(), fieldNewLastName.getText(),
//					fieldNewTelNumber.getText(), fieldNewStreet.getText(), fieldNewState.getText(), fieldNewCity.getText(),
//					fieldNewZip.getText());		
		} catch (ValException e) {
			// TODO Auto-generated catch block
			Util.showAlert(e.getMessage(), "Error", AlertType.ERROR);
		}
		

	}

	public void valNoMemberEmpty() throws ValException {
		if (fieldNewMemberId.getText() == null || fieldNewFirstName.getText() == null
				|| fieldNewLastName.getText() == null || fieldNewTelNumber.getText() == null
				|| fieldNewStreet.getText() == null || fieldNewState.getText() == null || fieldNewCity.getText() == null
				|| fieldNewZip.getText() == null)
			throw new ValException("Member fields cannot be empty");
		if (fieldNewMemberId.getText().isEmpty() || fieldNewFirstName.getText().isEmpty()
				|| fieldNewLastName.getText().isEmpty() || fieldNewTelNumber.getText().isEmpty()
				|| fieldNewStreet.getText().isEmpty() || fieldNewState.getText().isEmpty()
				|| fieldNewCity.getText().isEmpty() || fieldNewZip.getText().isEmpty())
			throw new ValException("Member fields cannot be empty");
	}

	public void valZip() throws ValException {
		if (!fieldNewZip.getText().matches("[0-9]{5}"))
			throw new ValException("Invalid Zip code");
	}

	public void valPhone() throws ValException {
		if (!fieldNewTelNumber.getText().matches("[0-9]*"))
			throw new ValException("Invalid phone number");
	}
}
