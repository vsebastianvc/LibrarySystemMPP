package control;

import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dataaccess.DataAccess;
import model.dataaccess.DataAccessFacade;
import model.domain.User;
import util.Util;
import view.Main;

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
		primaryStage.setTitle("Welcome MUM Library System");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void btnLoginOnAction() {
		DataAccess db = new DataAccessFacade();
		HashMap<String, User> users = db.readUserMap();
		User user = users.get(userId.getText());
		System.out.println(userId.getText());
		System.out.println(password.getText());
		if (user == null) {
			Util.showAlert("User id or password Wrong ", "Error login", AlertType.ERROR);

		} else if (user.authenticate(userId.getText(), password.getText())) {
			Util.persistUser(user);
			Main secondWindow = new Main();
			System.out.println("LOGIN SUCCESS");
			try {
				secondWindow.start(primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Util.showAlert("User id or password Wrong ", "Error login", AlertType.ERROR);
		}

	}
}