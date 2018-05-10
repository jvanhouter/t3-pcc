// specify the package
package userinterface;

// system imports

import Utilities.UiConstants;
import impresario.IModel;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import model.PccAlert;

import java.util.Properties;
// project imports

/**
 * The class containing the Add Article Type View  for the Professional Clothes
 * Closet application
 */
//==============================================================
public class BarcodeScannerView extends View {

    // GUI components
    protected TextField barcodeField;
    protected TextField description;
    protected TextField alphaCode;

    protected PccText searchPrompt;

    protected PccButton submitButton;
    protected PccButton cancelButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object

    BarcodeScannerView(IModel clothingItem) {
        super(clothingItem, "BarcodeScannerView");

        // create a container for showing the contents
        container.getChildren().add(createActionArea());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());
        container.getChildren().add(createStatusLog(""));

        //Add container to our BorderPane
        bp.setCenter(container);

        // Add BorderPane to our view
        getChildren().add(bp);

        populateFields();

        myModel.subscribe("TransactionError", this);
        myModel.subscribe("HandleBarcodeProblems", this);
    }

    @Override
    protected String getActionText() {
        return "Barcode Search";
    }


    // Create the main form content

    private VBox createFormContent() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        PccText prompt = new PccText("Please scan or manually enter \nclothing item barcode:");
        prompt.setWrappingWidth(WRAPPING_WIDTH);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.web(APP_TEXT_COLOR));
        prompt.setFont(Font.font(APP_FONT, 20));
        vbox.getChildren().add(prompt);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(25);
        grid.setPadding(new Insets(5, 25, 0, 25));

        barcodeField = new TextField();
        barcodeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]{0,8}")) {
                barcodeField.setText(oldValue);
            }
        });
        barcodeField.setOnAction(this::processAction);
        barcodeField.setMaxWidth(Double.MAX_VALUE);
        grid.add(barcodeField, 0, 1, 2, 1);
        GridPane.setFillWidth(barcodeField, true);
        submitButton = new PccButton("Submit");
        submitButton.setOnAction(this::processAction);

        cancelButton = new PccButton("Return");
        cancelButton.setOnAction(e -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelBarcodeSearch", null);
        });

        searchPrompt = new PccText("", 14);
        GridPane.setHalignment(searchPrompt, HPos.CENTER);
        grid.add(searchPrompt, 0, 2, 2, 1);

        HBox donCont = new HBox(100);
        donCont.setAlignment(Pos.CENTER);
        donCont.getChildren().add(submitButton);
        donCont.getChildren().add(cancelButton);
        grid.add(donCont, 0, 3, 2, 1);
//        grid.add(submitButton, 0, 3);
//        grid.add(cancelButton, 1, 3);

        vbox.getChildren().add(grid);

        return vbox;
    }

    private void processAction(ActionEvent actionEvent) {
        clearErrorMessage();
        Properties props = new Properties();
        String barcode = barcodeField.getText();
        if ((barcode.length() > 0) && (barcode.length() <= UiConstants.BARCODE_MAX_LENGTH)) {
            if (barcode.substring(0, 1).equals("0") || barcode.substring(0, 1).equals("1") || barcode.substring(0, 1).equals("2")) {
                PauseTransition pause = new PauseTransition(Duration.millis(100));
                props.setProperty("Barcode", barcode);
                displayMessage("Loading...");
                barcodeField.setText("");
                pause.setOnFinished(event -> myModel.stateChangeRequest("ProcessBarcode", props));
                pause.play();
            } else {
                displayErrorMessage("ERROR: Barcode does not begin with 0, 1, or 2!");
            }
        } else if ((boolean) myModel.getState("ListAll")) {
            myModel.stateChangeRequest("ProcessBarcode", props);
        } else {
            displayErrorMessage("ERROR: Please enter a valid barcode!");
        }

    }

    // Create the status log field
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    public void populateFields() {
        clearErrorMessage();
        if ((boolean) myModel.getState("ListAll")) {
            searchPrompt.setText("(enter nothing to list all clothing items)");
        }
    }

    /**
     * Update method
     */
    public void updateState(String key, Object value) {
        clearErrorMessage();

        if (key.equals("TransactionError")) {
            String val = (String) value;
            if (val.startsWith("ERR")) {
//                displayErrorMessage(val);
            } else {
                displayMessage(val);
            }

        } else if (key.equals("HandleBarcodeProblems")) {
            String val = (String) myModel.getState("BarcodeError");
            String barcodeError = "The clothing item associated with barcode " + val + " This clothing item will not be added to the checkout cart.";
            PccAlert alert = PccAlert.getInstance();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Barcode Error");
            alert.setContentText(barcodeError);
            alert.setHeaderText("There is a problem with the item you wish to checkout.");
            alert.show();
        }
    }

    /**
     * Display error message
     */
    public void displayErrorMessage(String message) {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    public void displayMessage(String message) {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }

}
