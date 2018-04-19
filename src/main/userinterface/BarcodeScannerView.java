// specify the package
package userinterface;

// system imports

import impresario.IModel;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.Properties;
import Utilities.UiConstants;
// project imports

/**
 * The class containing the Add Article Type View  for the Professional Clothes
 * Closet application
 */
//==============================================================
public class BarcodeScannerView extends View {

    // GUI components
    protected TextField barcodePrefix;
    protected TextField description;
    protected TextField alphaCode;

    protected PccButton submitButton;
    protected Button cancelButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object

    BarcodeScannerView(IModel clothingItem) {
        super(clothingItem, "BarcodeScannerView");

        // create a container for showing the contents
        VBox container = getParentContainer();

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);
    }

    @Override
    protected String getActionText() {
        return "Barcode Search";
    }

    // Create the main form content

    private VBox createFormContent() {
        VBox vbox = new VBox(10);

        Text prompt = new Text("Scan or manually enter clothing item barcode");
        prompt.setWrappingWidth(WRAPPING_WIDTH);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        prompt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(prompt);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 25, 10, 0));

        barcodePrefix = new TextField();
        barcodePrefix.setOnAction(this::processAction);
        grid.add(barcodePrefix, 0, 1, 4, 1);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        submitButton = new PccButton("Submit");
        submitButton.setOnAction(this::processAction);
        doneCont.getChildren().add(submitButton);

        cancelButton = new PccButton("Return");
        cancelButton.setOnAction(e -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelBarcodeSearch", null);
        });
        doneCont.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    private void processAction(ActionEvent actionEvent) {
        clearErrorMessage();
        Properties props = new Properties();
        String barcode = barcodePrefix.getText();
        if ((barcode.length() > 0) && (barcode.length() <= UiConstants.BARCODE_MAX_LENGTH)) {
            if (barcode.substring(0, 1).equals("0") || (barcode.substring(0, 1).equals("1"))) {
                PauseTransition pause = new PauseTransition(Duration.millis(100));
                props.setProperty("Barcode", barcode);
                displayMessage("Loading...");
                barcodePrefix.setText("");
                pause.setOnFinished(event -> myModel.stateChangeRequest("ProcessBarcode", props));
                pause.play();

            } else {
                displayErrorMessage("ERROR: Barcode does not begin with 0 or 1!");
            }
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
    }

    /**
     * Update method
     */
    public void updateState(String key, Object value) {
        clearErrorMessage();

        if (key.equals("TransactionError")) {
            String val = (String) value;
            if (val.startsWith("ERR")) {
                displayErrorMessage(val);
            } else {
                displayMessage(val);
            }

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
