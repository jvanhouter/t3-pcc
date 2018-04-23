package model;

public class Alert extends javafx.scene.control.Alert{

    public Alert(AlertType alertType)
    {
        super(alertType);
    }

    //-------------------------------------------------
    public void displayErrorMessage(String message)
    {
        setHeaderText("Clothes Closet");
        setTitle("Error!");
        setContentText(message);
        show();
    }

    //--------------------------------------------------
    public void displayMessage(String message) {
        setHeaderText("Clothes Closet");
        setTitle("Message");
        setContentText(message);
        show();
    }
}
