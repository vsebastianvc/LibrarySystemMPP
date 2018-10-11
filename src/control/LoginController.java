package control;

import java.io.IOException;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.dataaccess.DataAccess;
import model.dataaccess.DataAccessFacade;
import model.domain.User;
import util.Util;

public class LoginController extends Application {

	@FXML
	private TextField userId;
	@FXML
	private PasswordField password;
	@FXML
	private Button btnLogin;
	private static Stage primaryStage;

	public static void main(String[] args) {
		Application.launch(LoginController.class, args);
	}

	@Override
	public void start(Stage primary) throws Exception {
		primaryStage = primary;
		Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		primaryStage.setTitle("Welcome to Library System");
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
		String css = this.getClass().getResource("/view/login.css").toExternalForm(); 
		primaryStage.setScene(scene);
		scene.getStylesheets().add(css);
		primaryStage.show();

	}

	public void btnLoginOnAction() {
		DataAccess db = new DataAccessFacade();
		HashMap<String, User> users = db.readUserMap();
		User user = users.get(userId.getText());
//		System.out.println(userId.getText());
//		System.out.println(password.getText());
		if (user == null) {
			Util.showAlert("User id or password Wrong ", "Error login", AlertType.ERROR);

		} else if (user.authenticate(userId.getText(), password.getText())) {
			Util.persistUser(user);
			AnchorPane page=null;
			try {
				page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/MainWindow.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Library System");
            primaryStage.show();
//            System.out.println("Persist USER: "+Util.getInstanceUser());
			System.out.println("LOGIN SUCCESS");
		
		} else {
			Util.showAlert("User id or password Wrong ", "Error login", AlertType.ERROR);
		}

	}
	
	public void btnLoginOnExit() {
		Platform.exit();
	}
	
}