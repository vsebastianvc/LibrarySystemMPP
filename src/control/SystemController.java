package control;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import view.Main;

public class SystemController {

	@FXML  // fx:id="menuCheckOut"
	private MenuItem menuCheckOut;
	
	@FXML  // fx:id="menuCheckIn"
	private MenuItem menuCheckIn;
	
	@FXML  // fx:id="menuQueryOverdue"
	private MenuItem menuQueryOverdue;
	
	@FXML  // fx:id="printCheckoutRecord"
	private MenuItem printCheckoutRecord;
	
	@FXML  // fx:id="menuSystemExit"
	private MenuItem menuSystemExit;
	
	@FXML  // fx:id="menuNewMember"
	private MenuItem menuNewMember;

	@FXML  // fx:id="menuNewBook"
	private MenuItem menuNewBook;
	
	@FXML  // fx:id="newBookCopy"
	private MenuItem menuBookCopy;
	
	//New member's screen items
	
	@FXML  // fx:id="newMemberId"
	private TextField fieldNewMemberId;
	@FXML  // fx:id="newFirstName"
	private TextField fieldNewFirstName;
	@FXML  // fx:id="newLastName"
	private TextField fieldNewLastName;
	@FXML  // fx:id="newTelNumber"
	private TextField fieldNewTelNumber;
	@FXML  // fx:id="newStreet"
	private TextField fieldNewStreet;
	@FXML  // fx:id="newState"
	private TextField fieldNewState;
	@FXML  // fx:id="newCity"
	private TextField fieldNewCity;
	@FXML  // fx:id="newZip"
	private TextField fieldNewZip;

	@FXML  // fx:id="createNewMember"
	private Button btnCreateNewMember;
	
    @FXML
    void createNewMember(ActionEvent event) {
    	String strMemberId;
    	
    	strMemberId = fieldNewMemberId.getText();
    	System.out.printf("Add member with: ID: %s, First name: %s, Last name: %s, Tel number: %s, Street: %s, State: %s, City: %s, Zip: %s \n",
    			fieldNewMemberId.getText(), fieldNewFirstName.getText(), fieldNewLastName.getText(), fieldNewTelNumber.getText(), 
    			fieldNewStreet.getText(), fieldNewState.getText(), fieldNewCity.getText(), fieldNewZip.getText());
    }

	@FXML //  fx:id="newIssue"
    private Button btnNewIssue; // Value injected by FXMLLoader

    @FXML //  fx:id="saveIssue"
    private Button btnSaveIssue; // Value injected by FXMLLoader
    
    @FXML //  fx:id="titleLine"
    private AnchorPane contentPanel; // Value injected by FXMLLoader

    /**
     * Initializes the controller class.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        
    }

    /**
     * Called when the NewIssue button is fired.
     *
     * @param event the action event.
     */
    @FXML
    void newIssueFired(ActionEvent event) {
    	
    		try {
    			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("Test.fxml"));
    			this.contentPanel.getChildren().clear();
				this.contentPanel.getChildren().add(page);
				System.out.println("Agregado el panel");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
        
    }

    /**
     * Called when the DeleteIssue button is fired.
     *
     * @param event the action event.
     */
    @FXML
    void deleteIssueFired(ActionEvent event) {
       
    }

    /**
     * Called when the SaveIssue button is fired.
     *
     * @param event the action event.
     */
    @FXML
    void saveIssueFired(ActionEvent event) {
    		this.contentPanel.getChildren().clear();
    }
    
    FadeTransition messageTransition = null;

    public void displayMessage(String message) {
        
    }
    
    @FXML
    void systemExitFired(ActionEvent event) {
    	//System.out.println("Exit");
    	Platform.exit();
    }

    @FXML
    void checkOutFired(ActionEvent event) {
    	System.out.println("Check Out");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("CheckOut.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void checkInFired(ActionEvent event) {
    	System.out.println("Check In");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("CheckIn.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void queryOverdueFired(ActionEvent event) {
    	System.out.println("Query Overdue");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("queryOverdue.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    @FXML
    void newMemberFired(ActionEvent event) {
    	System.out.println("New Member");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("addNewMember.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void newBookFired(ActionEvent event) {
    	System.out.println("New Book");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("addBook.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    @FXML
    void newBookCopyFired(ActionEvent event) {
    	System.out.println("New Book Copy");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("addBookCopy.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    }
   }
    
    @FXML
    void printCheckoutRecordFired(ActionEvent event) {
    	System.out.println("Print checkout");
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("printCheckoutRecord.fxml"));
			this.contentPanel.getChildren().clear();
			this.contentPanel.getChildren().add(page);
			System.out.println("Agregado el panel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   

}
