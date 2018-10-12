package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.media.sound.FFT;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.dataaccess.DataAccess;
import model.dataaccess.DataAccessFacade;
import model.domain.Address;
import model.domain.Author;
import model.domain.Book;
import model.domain.LibraryMember;
import util.Util;
import util.ValException;

public class BookController implements Initializable {
	
	private String viewType;
	// New book screen items
	@FXML // fx:id="fieldNewBookISBN"
	private TextField fieldNewBookISBN;
	@FXML // fx:id="fieldNewBookTitle"
	private TextField fieldNewBookTitle;
	@FXML // fx:id="fieldNewBookAuthors"
	private TableView<Author> fieldNewBookAuthors;
	@FXML // fx:id="choiceNewBookMaxCheckout"
	private ComboBox<String> choiceNewBookMaxCheckout;
	@FXML // fx:id="fieldNewBookNumCopies"
	private TextField fieldNewBookNumCopies;
	@FXML // fx:id="btnCreateNewAuthor"
	private Button btnCreateNewAuthor;

	@FXML // fx:id="createNewBook"
	private Button btnCreateNewBook;

	// New book's copy screen items
	@FXML // fx:id="fieldNewCopyISBN"
	private TextField fieldNewCopyISBN;

	@FXML // fx:id="btnCreateNewBookCopy"
	private Button btnCreateNewBookCopy;

	@FXML
	private AnchorPane panelAddCopy;
	
	public BookController(String viewType) {
		// TODO Auto-generated constructor stub
		this.viewType=viewType;
	}
	
	@FXML
	TableColumn<Author, String> firstName;
	@FXML
	TableColumn<Author, String> lastName;
	@FXML
	TableColumn<Author, String> phone;
	@FXML
	TableColumn<Author, String> street;
	@FXML
	TableColumn<Author, String> city;
	@FXML
	TableColumn<Author, String> state;
	@FXML
	TableColumn<Author, String> columZip;
	@FXML
	TableColumn<Author, String> biography;
	
	ObservableList<Author> data;
	
	List<Author> authors = new ArrayList<>();

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

	@FXML
	void createNewBook(ActionEvent event) {

		try {
			valNoBookEmpty();
			valNumCopies();
		} catch (ValException e) {
			Util.showAlert(e.getMessage(), "Error", AlertType.ERROR);
		}
//		fieldNewBookAuthors.setEditable(true);
		DataAccess db = new DataAccessFacade();
		Book book = new Book(fieldNewBookISBN.getText(), fieldNewBookTitle.getText(), Integer.parseInt(choiceNewBookMaxCheckout.getValue().toString()), authors);
		db.saveAbook(book);
		
		
		DataAccess dbTest = new DataAccessFacade();
		HashMap<String, Book> books = dbTest.readBooksMap();
		Book book1 = books.get(fieldNewBookISBN.getText());
		System.out.println("BOOK " + book1);
        
	}
	@FXML
	void createNewAuthor(ActionEvent event) {
		Address a= new Address("", "", "", "");
        authors.add(new Author("", "", "", a, ""));
        data=FXCollections.observableArrayList(authors);
        fieldNewBookAuthors.setItems(data);
	}

	public void valNoBookEmpty() throws ValException {
		if (fieldNewBookISBN.getText() == null || fieldNewBookTitle.getText() == null
		// || fieldNewBookAuthors.getText() == null
				|| choiceNewBookMaxCheckout.getSelectionModel().getSelectedItem() == null
				|| fieldNewBookNumCopies.getText() == null)
			throw new ValException("Book fields cannot be empty");
		if (fieldNewBookISBN.getText().isEmpty() || fieldNewBookTitle.getText().isEmpty()
		// || fieldNewBookAuthors.getText().isEmpty()
				|| fieldNewBookNumCopies.getText().isEmpty())
			throw new ValException("Book fields cannot be empty");
	}

	public void valNumCopies() throws ValException {
		if (!fieldNewBookNumCopies.getText().matches("[0-9]{1,5}"))
			throw new ValException("Invalid number of copies");
	}

	public void valISBN(String isbn) throws ValException {
		if (isbn == null || isbn.isEmpty())
			throw new ValException("Invalid Book's ISBN");
	}
	class EditingCell extends TableCell<Author, String> {
		 
        private TextField textField;
 
        public EditingCell() {
        }
 
        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
 
            setText((String) getItem());
            setGraphic(null);
        }
 
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, 
                    Boolean arg1, Boolean arg2) {
                        if (!arg2) {
                            commitEdit(textField.getText());
                        }
                }
            });
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(!viewType.equals("copy")) {
		Callback<TableColumn<Author, String>, TableCell<Author, String>> cellFactory =
	             new Callback<TableColumn<Author, String>, TableCell<Author, String>>() {
	                 public TableCell call(TableColumn p) {
	                    return new EditingCell();
	                 }
	             };
	             
	        firstName.setCellValueFactory(
	                new PropertyValueFactory<Author, String>("firstName"));
	        firstName.setCellFactory(cellFactory);
	        firstName.setOnEditCommit(
	                new EventHandler<CellEditEvent<Author, String>>() {
	                    @Override
	                    public void handle(CellEditEvent<Author, String> t) {
	                        ((Author) t.getTableView().getItems().get(
	                            t.getTablePosition().getRow())
	                            ).setFirstName(t.getNewValue());
	                    }
	                 }
	            );
	        lastName.setCellValueFactory(
	                new PropertyValueFactory<Author, String>("lastName"));
	        lastName.setCellFactory(cellFactory);
	        lastName.setOnEditCommit(
	                new EventHandler<CellEditEvent<Author, String>>() {
	                    @Override
	                    public void handle(CellEditEvent<Author, String> t) {
	                        ((Author) t.getTableView().getItems().get(
	                            t.getTablePosition().getRow())
	                            ).setLastName(t.getNewValue());
	                    }
	                 }
	            );
	        phone.setCellValueFactory(
	                new PropertyValueFactory<Author, String>("telephone"));
	        phone.setCellFactory(cellFactory);
	        phone.setOnEditCommit(
	                new EventHandler<CellEditEvent<Author, String>>() {
	                    @Override
	                    public void handle(CellEditEvent<Author, String> t) {
	                        ((Author) t.getTableView().getItems().get(
	                            t.getTablePosition().getRow())
	                            ).setTelephone(t.getNewValue());
	                    }
	                 }
	            );
	        street.setCellValueFactory(
	                new PropertyValueFactory<Author, String>("street"));
	        street.setCellFactory(cellFactory);
	        street.setOnEditCommit(
	                new EventHandler<CellEditEvent<Author, String>>() {
	                    @Override
	                    public void handle(CellEditEvent<Author, String> t) {
	                        ((Author) t.getTableView().getItems().get(
	                            t.getTablePosition().getRow())
	                            ).getAddress().setStreet(t.getNewValue());
	                    }
	                 }
	            );
	        city.setCellValueFactory(
	                new PropertyValueFactory<Author, String>("city"));
	        city.setCellFactory(cellFactory);
	        city.setOnEditCommit(
	                new EventHandler<CellEditEvent<Author, String>>() {
	                    @Override
	                    public void handle(CellEditEvent<Author, String> t) {
	                        ((Author) t.getTableView().getItems().get(
	                            t.getTablePosition().getRow())
	                            ).getAddress().setCity(t.getNewValue());
	                    }
	                 }
	            );
	        state.setCellValueFactory(
	                new PropertyValueFactory<Author, String>("state"));
	        state.setCellFactory(cellFactory);
	        state.setOnEditCommit(
	                new EventHandler<CellEditEvent<Author, String>>() {
	                    @Override
	                    public void handle(CellEditEvent<Author, String> t) {
	                        ((Author) t.getTableView().getItems().get(
	                            t.getTablePosition().getRow())
	                            ).getAddress().setState(t.getNewValue());
	                    }
	                 }
	            );
	        columZip.setCellValueFactory(
	                new PropertyValueFactory<Author, String>("zip"));
	        columZip.setCellFactory(cellFactory);
	        columZip.setOnEditCommit(
	                new EventHandler<CellEditEvent<Author, String>>() {
	                    @Override
	                    public void handle(CellEditEvent<Author, String> t) {
	                        ((Author) t.getTableView().getItems().get(
	                            t.getTablePosition().getRow())
	                            ).getAddress().setZip(t.getNewValue());
	                    }
	                 }
	            );
	        biography.setCellValueFactory(
	                new PropertyValueFactory<Author, String>("biography"));
	        biography.setCellFactory(cellFactory);
	        biography.setOnEditCommit(
	                new EventHandler<CellEditEvent<Author, String>>() {
	                    @Override
	                    public void handle(CellEditEvent<Author, String> t) {
	                        ((Author) t.getTableView().getItems().get(
	                            t.getTablePosition().getRow())
	                            ).setBiography(t.getNewValue());
	                    }
	                 }
	            );
	        
	        
//	        Address a= new Address("101 S. Main", "Fairfield", "IA", "52556");
//	        authors.add(new Author("Joe", "Thomas", "641-445-2123", a, "A happy man is he."));
	        Address a= new Address("", "", "", "");
	        authors.add(new Author("", "", "", a, ""));
	        data=FXCollections.observableArrayList(authors);
	        fieldNewBookAuthors.setItems(data);
		
	}
	}
}
