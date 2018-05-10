// specify the package
package userinterface;

// system imports

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Properties;

// project imports

/**
 * The class containing the Add Article Type View  for the Professional Clothes
 * Closet application
 */
//==============================================================
public class AddArticleTypeView extends View {

    // GUI components
    protected TextField barcodePrefix;
    protected TextField description;
    protected TextField alphaCode;

    protected PccButton submitButton;
    protected PccButton cancelButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public AddArticleTypeView(IModel at) {
        super(at, "AddArticleTypeView");

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
    }

    @Override
    protected String getActionText() {
        return " Add an Article Type ";
    }

    // Create the main form content
    //-------------------------------------------------------------
    VBox createFormContent() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);



        PccText prompt = new PccText("Please Enter Article Type Information:");
        prompt.setWrappingWidth(WRAPPING_WIDTH);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.web(APP_TEXT_COLOR));
        prompt.setFont(Font.font(APP_FONT, 20));
        vbox.getChildren().add(prompt);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(12);
        grid.setPadding(new Insets(5, 25, 20, 0));

        PccText barcodePrefixLabel = new PccText(" Barcode Prefix : ");
        Font myFont = Font.font(APP_FONT, 16);
        barcodePrefixLabel.setFont(myFont);
        barcodePrefixLabel.setWrappingWidth(150);
        barcodePrefixLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(barcodePrefixLabel, 0, 1);

        barcodePrefix = new TextField();
        barcodePrefix.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]{0,5}")) {
                barcodePrefix.setText(oldValue);
            }
        });
        grid.add(barcodePrefix, 1, 1);

        PccText descripLabel = new PccText(" Description : ");
        descripLabel.setFont(myFont);
        descripLabel.setFill(Color.web(APP_TEXT_COLOR));
        descripLabel.setWrappingWidth(150);
        descripLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(descripLabel, 0, 2);

        description = new TextField();
        description.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9 -]{0,30}")) {
                description.setText(oldValue);
            }
        });
        grid.add(description, 1, 2);

        PccText alphaCodeLabel = new PccText(" Alpha Code : ");
        alphaCodeLabel.setFont(myFont);
        alphaCodeLabel.setFill(Color.web(APP_TEXT_COLOR));
        alphaCodeLabel.setWrappingWidth(150);
        alphaCodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(alphaCodeLabel, 0, 3);

        alphaCode = new TextField();
        alphaCode.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]{0,5}")) {
                alphaCode.setText(oldValue);
            }
            if (newValue.matches("[A-Za-z]{0,5}")) {
                alphaCode.setText(newValue.toUpperCase());
            }
        });
        grid.add(alphaCode, 1, 3);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        submitButton = new PccButton("Submit");
        submitButton.setOnAction(this::processAction);
        doneCont.getChildren().add(submitButton);

        cancelButton = new PccButton("Return");
        cancelButton.setOnAction(e -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelAddAT", null);
        });
        doneCont.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    private void processAction(ActionEvent e) {
        clearErrorMessage();
        Properties properties = new Properties();
        String barcodePrefixText = barcodePrefix.getText();
        if (barcodePrefixText.length() > 0) {
            properties.setProperty("BarcodePrefix", barcodePrefixText);
            String descriptionText = description.getText();
            if (descriptionText.length() > 0) {
                properties.setProperty("Description", descriptionText);
                String alphaCodeText = alphaCode.getText();
                if (alphaCodeText.length() > 0) {
                    properties.setProperty("AlphaCode", alphaCodeText);
                    myModel.stateChangeRequest("ArticleTypeData", properties);
                    myModel.stateChangeRequest("OK", null);
                } else {
                    displayErrorMessage("ERROR: Please enter a valid alpha code!");
                }
            } else {
                displayErrorMessage("ERROR: Please enter a valid description!");
            }

        } else {
            displayErrorMessage("ERROR: Please enter a barcode prefix!");

        }
    }

    // Create the status log field
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    public void populateFields() {

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