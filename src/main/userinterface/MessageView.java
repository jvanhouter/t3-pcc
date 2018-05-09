//************************************************************
//	COPYRIGHT 2010/2015 Sandeep Mitra and Students, The
//    College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
package userinterface;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.PccAlert;

public class MessageView extends Text {

    private PccAlert myAlert;

    // Class constructor
    public MessageView(String initialMessage) {
        super(initialMessage);
        myAlert = myAlert.getInstance();
        setFont(Font.font(View.APP_FONT, FontWeight.BOLD, 16));
        setFill(Color.BLUE);
        setTextAlignment(TextAlignment.LEFT);
    }

    /**
     * Display ordinary message
     */
    public void displayMessage(String message) {
        //System.out.println("heremsg");
        //Exception e = new Exception();
        //e.printStackTrace();
        // display the passed text in blue
        if (message.equals("Loading...") || message.length() == 0) {
            setFill(Color.web("#ffc726"));
            setText(message);
        } else {
            myAlert.getInstance().displayMessage(message);
        }
    }

    /**
     * Display error message
     */
    public void displayErrorMessage(String message) {
        // display the passed text in red
        myAlert.getInstance().displayErrorMessage(message);
    }

    /**
     * Clear error message
     */
    public void clearErrorMessage() {
        setText("                           ");
    }

    public void display2ErrorMessage(String message) {
        if (!myAlert.getInstance().isShowing()) {
            myAlert.getInstance().setAlertType(Alert.AlertType.ERROR);
            myAlert.getInstance().setTitle("Error Dialog");
            myAlert.getInstance().setHeaderText("An error has occurred ");
            myAlert.getInstance().setContentText(message);
            myAlert.getInstance().getButtonTypes().clear();
            DialogPane dialogPane = myAlert.getInstance().getDialogPane();
// root
            dialogPane.setStyle("-fx-background-color: #647585;");

// 1. Grid
            // remove style to customize header
//        dialogPane.getStyleClass().remove("alert");

            GridPane grid = (GridPane) dialogPane.lookup(".header-panel");
            grid.setStyle("-fx-background-color: #647585;");

// 2. ContentText with just a Label
            dialogPane.lookup(".content.label").setStyle("-fx-font-size: 16px; "
                    + "-fx-font-weight: bold; -fx-text-fill: #c20012;");

// 3- ButtonBar
            ButtonBar buttonBar = (ButtonBar) myAlert.getInstance().getDialogPane().lookup(".button-bar");
            buttonBar.setStyle("-fx-background-color: #647585;");
            buttonBar.getButtons().forEach(b -> {
                b.setStyle("-fx-font-family: \"Roboto\";");
                b.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
                b.setOnMouseEntered(e -> b.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #007a58; -fx-text-fill: #ffc726"));
                b.setOnMouseExited(e -> b.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726"));
                b.setOnMousePressed(e -> b.setStyle("-fx-border-color: #8d8d8d; -fx-border-width: 1px; -fx-background-color: #8d8d8d; -fx-text-fill: #ffc726"));
                b.setOnMouseReleased(e -> b.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726"));

            });
            myAlert.getInstance().showAndWait();
        }
    }

}
