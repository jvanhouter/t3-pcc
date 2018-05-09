// specify the package
package userinterface;

// system imports

import impresario.IModel;
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

import java.util.Properties;

// project imports

/**
 * The class containing the Add Article Type View  for the Professional Clothes
 * Closet application
 */
//==============================================================
public class SearchArticleTypeView extends View {

    // GUI components
    protected TextField barcodePrefix;
    protected TextField description;
    protected TextField alphaCode;

    protected PccButton submitButton;
    protected PccButton cancelButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object

    public SearchArticleTypeView(IModel at) {
        super(at, "SearchArticleTypeView");

        // create a container for showing the contents
        VBox container = getParentContainer();
        container.setAlignment(Pos.CENTER);

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
        return "Article Type Search";
    }

    // Create the main form content
    private VBox createFormContent() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        PccText prompt1 = new PccText("Please Enter Article Type Barcode Prefix:");
        prompt1.setWrappingWidth(WRAPPING_WIDTH);
        prompt1.setTextAlignment(TextAlignment.CENTER);
        prompt1.setFill(Color.web(APP_TEXT_COLOR));
        prompt1.setFont(Font.font(APP_FONT, 20));
        vbox.getChildren().add(prompt1);

        PccText blankText3 = new PccText(" ");
        blankText3.setFont(Font.font(APP_FONT, 2));
        blankText3.setFill(Color.WHITE);
        vbox.getChildren().add(blankText3);

        GridPane grid0 = new GridPane();
        grid0.setAlignment(Pos.CENTER);
        grid0.setHgap(10);
        grid0.setVgap(10);
        grid0.setPadding(new Insets(5, 30, 20, 0));

        PccText barcodePrefixLabel = new PccText(" Barcode Prefix : ");
        Font myFont = Font.font(APP_FONT, 16);
        barcodePrefixLabel.setFont(myFont);
        barcodePrefixLabel.setWrappingWidth(150);
        barcodePrefixLabel.setTextAlignment(TextAlignment.RIGHT);
        grid0.add(barcodePrefixLabel, 0, 1);

        barcodePrefix = new TextField();
        barcodePrefix.setOnAction(e -> {
            clearErrorMessage();
            Properties props = new Properties();
            String bcPrfx = barcodePrefix.getText();
            if (bcPrfx.length() > 0) {
                props.setProperty("BarcodePrefix", bcPrfx);
                myModel.stateChangeRequest("SearchArticleType", props);
            }
        });
        grid0.add(barcodePrefix, 1, 1);

        vbox.getChildren().add(grid0);

        PccText blankText4 = new PccText(" ");
        blankText4.setFont(Font.font(APP_FONT, 2));
        blankText4.setFill(Color.WHITE);
        vbox.getChildren().add(blankText4);


        PccText prompt2 = new PccText("Or Enter a Description and/or an Alphacode:");
        prompt2.setWrappingWidth(WRAPPING_WIDTH);
        prompt2.setTextAlignment(TextAlignment.CENTER);
        prompt2.setFill(Color.web(APP_TEXT_COLOR));
        prompt2.setFont(Font.font(APP_FONT, 20));
        vbox.getChildren().add(prompt2);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 30, 20, 0));

        PccText blankText5 = new PccText(" ");
        blankText5.setFont(Font.font(APP_FONT, 2));
        blankText5.setFill(Color.WHITE);
        vbox.getChildren().add(blankText5);


        PccText descripLabel = new PccText(" Description : ");
        descripLabel.setFont(myFont);
        descripLabel.setWrappingWidth(150);
        descripLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(descripLabel, 0, 1);

        description = new TextField();
        description.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9 -]{0,30}")) {
                description.setText(oldValue);
            }
        });
        description.setOnAction(e -> {
            clearErrorMessage();
            Properties props = new Properties();

            String descrip = description.getText();
            props.setProperty("Description", descrip);
            String alfaC = alphaCode.getText();
            props.setProperty("AlphaCode", alfaC);
            myModel.stateChangeRequest("SearchArticleType", props);

        });
        grid.add(description, 1, 1);

        PccText alphaCodeLabel = new PccText(" Alpha Code : ");
        alphaCodeLabel.setFont(myFont);
        alphaCodeLabel.setWrappingWidth(150);
        alphaCodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(alphaCodeLabel, 0, 2);

        alphaCode = new TextField();
        grid.add(alphaCode, 1, 2);



        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        submitButton = new PccButton("Submit");
        submitButton.setOnAction(e -> {
            clearErrorMessage();
            Properties props = new Properties();
            String bcPrfx = barcodePrefix.getText();
            if (bcPrfx.length() > 0) {
                props.setProperty("BarcodePrefix", bcPrfx);
                myModel.stateChangeRequest("SearchArticleType", props);
            } else {
                String descrip = description.getText();
                props.setProperty("Description", descrip);
                String alfaC = alphaCode.getText();
                props.setProperty("AlphaCode", alfaC);
                myModel.stateChangeRequest("SearchArticleType", props);
            }
        });
        doneCont.getChildren().add(submitButton);

        cancelButton = new PccButton("Return");
        cancelButton.setOnAction(e -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelSearchArticleType", null);
        });
        doneCont.getChildren().add(cancelButton);


        PccText prompt3 = new PccText("(enter nothing to see a list of all article types)");
        prompt3.setWrappingWidth(WRAPPING_WIDTH);
        prompt3.setTextAlignment(TextAlignment.CENTER);
        prompt3.setFill(Color.web(APP_TEXT_COLOR));
        prompt3.setFont(Font.font(APP_FONT, 15));

        vbox.getChildren().add(grid);
        vbox.getChildren().add(prompt3);

        PccText blankText6 = new PccText(" ");
        blankText6.setFont(Font.font(APP_FONT, 4));
        blankText6.setFill(Color.WHITE);
        vbox.getChildren().add(blankText6);
        vbox.getChildren().add(doneCont);

        return vbox;
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


//	Revision History:
//
