package model;

import javafx.scene.control.Alert;

public class PccAlert extends Alert {

    private static PccAlert myAlert;

    private PccAlert(AlertType alertType) {
        super(alertType);
    }


    public static PccAlert getInstance() {
        if(myAlert == null) {
            myAlert = new PccAlert(Alert.AlertType.INFORMATION);
        }
        return myAlert;
    }

    //-------------------------------------------------
    public void displayErrorMessage(String message)
    {

    }

    //--------------------------------------------------
    public void displayMessage(String message) {

    }
}
