package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class PccAlert extends Alert {

    private static PccAlert myAlert;

    private PccAlert(AlertType alertType) {
        super(alertType);
    }


    public static PccAlert getInstance() {
        if (myAlert == null) {
            myAlert = new PccAlert(Alert.AlertType.INFORMATION);
        }
        return myAlert;
    }

    //-------------------------------------------------
    public void displayErrorMessage(String message) {
        if (!myAlert.getInstance().isShowing()) {
            myAlert.getInstance().setHeaderText("Error");
            myAlert.getInstance().setAlertType(Alert.AlertType.ERROR);
            myAlert.getInstance().setTitle("Brockport Professional Clothes Closet Error");
            myAlert.getInstance().setContentText(message);
            myAlert.getInstance().getButtonTypes().clear();
            ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.YES);
            myAlert.getInstance().getButtonTypes().setAll(ok);
            myAlert.getInstance().show();
        }
    }

    //--------------------------------------------------
    public void displayMessage(String message) {
        if (!myAlert.getInstance().isShowing()) {
            myAlert.getInstance().setHeaderText("Message");
            myAlert.getInstance().setAlertType(Alert.AlertType.ERROR);
            myAlert.getInstance().setTitle("Brockport Professional Clothes Closet Error");
            myAlert.getInstance().setContentText(message);
            myAlert.getInstance().getButtonTypes().clear();
            ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.YES);
            myAlert.getInstance().getButtonTypes().setAll(ok);
            myAlert.getInstance().show();
        }
    }
}
