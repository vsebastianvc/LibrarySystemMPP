package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Util {
	public static void showAlert(String message, String titleMessage, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(titleMessage);
		alert.setContentText(message);
		alert.show();
	}

}
