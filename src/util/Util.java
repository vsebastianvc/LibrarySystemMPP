package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.domain.CheckoutRecordEntry;
import model.domain.LibraryMember;
import model.domain.User;

public class Util {
	private static User user;
	private static CheckoutRecordEntry checkOut;
	public static void showAlert(String message, String titleMessage, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(titleMessage);
		alert.setContentText(message);
		alert.show();
	}
	public static void persistUser(User u) {
		user=u;
	}
	public static User getInstanceUser() {
		return user;
	}
	public static void persistRecord(CheckoutRecordEntry c) {
		checkOut=c;
	}
	public static CheckoutRecordEntry getInstanceRecord() {
		return checkOut;
	}

}
