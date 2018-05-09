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
public class SearchClothingItemView extends View {

    // GUI components
    protected TextField barcode;
    protected TextField articleType;
    protected TextField gender;

    protected PccButton submitButton;
    protected PccButton cancelButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public SearchClothingItemView(IModel at) {
        super(at, "SearchClothingView");

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

    //-------------------------------------------------------------
    @Override
    protected String getActionText() {
        return "Search for Clothing";
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        PccText prompt1 = new PccText("Enter Barcode:");
        prompt1.setWrappingWidth(WRAPPING_WIDTH);
        prompt1.setTextAlignment(TextAlignment.CENTER);
        prompt1.setFill(Color.web(APP_TEXT_COLOR));
        prompt1.setFont(Font.font(APP_FONT, 20));
        vbox.getChildren().add(prompt1);

        GridPane grid0 = new GridPane();
        grid0.setAlignment(Pos.CENTER);
        grid0.setHgap(10);
        grid0.setVgap(10);
        grid0.setPadding(new Insets(5, 30, 20, 0));

        PccText barcodeLabel = new PccText(" Barcode: ");
        Font myFont = Font.font(APP_FONT, 12);
        barcodeLabel.setFont(myFont);
        barcodeLabel.setWrappingWidth(150);
        barcodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid0.add(barcodeLabel, 0, 1);

        barcode = new TextField();
        barcode.setOnAction(e -> {
            clearErrorMessage();
            Properties props = new Properties();
            String barcodeString = barcode.getText();
            if (barcodeString.length() > 0) {
                props.setProperty("Barcode", barcodeString);
                myModel.stateChangeRequest("SearchClothingItem", props);
            }
        });
        grid0.add(barcode, 1, 1);

        vbox.getChildren().add(grid0);
        //--------------------------------------------------------------------------//
        PccText prompt2 = new PccText(" Or Search by Article Type and/or Gender ");
        prompt2.setWrappingWidth(WRAPPING_WIDTH);
        prompt2.setTextAlignment(TextAlignment.CENTER);
        prompt2.setFill(Color.web(APP_TEXT_COLOR));
        prompt2.setFont(Font.font(APP_FONT, 20));
        vbox.getChildren().add(prompt2);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(12);
        grid.setPadding(new Insets(5, 30, 20, 0));

        PccText atLabel = new PccText("Article Type : ");
        atLabel.setFont(myFont);
        atLabel.setWrappingWidth(150);
        atLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(atLabel, 0, 1);

        articleType = new TextField();
        articleType.setOnAction(e -> {
            clearErrorMessage();
            Properties props = new Properties();

            String atString = articleType.getText();
            props.setProperty("ArticleType", atString);
            String genderString = gender.getText();
            props.setProperty("Gender", genderString);
            myModel.stateChangeRequest("SearchClothingItem", props);

        });
        grid.add(articleType, 1, 1);

        PccText colorLabel = new PccText("Gender  : ");
        colorLabel.setFont(myFont);
        colorLabel.setWrappingWidth(150);
        colorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(colorLabel, 0, 2);

        gender = new TextField();
        grid.add(gender, 1, 2);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        submitButton = new PccButton("Submit");
        submitButton.setOnAction(e -> {
            clearErrorMessage();
            Properties props = new Properties();
            String bcString = barcode.getText();
            if (bcString.length() > 0) {
                props.setProperty("Barcode", bcString);
                myModel.stateChangeRequest("SearchClothingItem", props);
            } else {
                String atString = articleType.getText();
                props.setProperty("ArticleType", atString);

                String genderString = gender.getText();
                props.setProperty("Gender", genderString);
                myModel.stateChangeRequest("SearchClothingItem", props);
            }
        });
        doneCont.getChildren().add(submitButton);

        cancelButton = new PccButton("Return");
        cancelButton.setOnAction(e -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelSearchClothingItem", null);
        });
        doneCont.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }


    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields() {

    }

    /**
     * Update method
     */
    //---------------------------------------------------------
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
    //----------------------------------------------------------
    public void displayErrorMessage(String message) {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message) {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }

}

//---------------------------------------------------------------
//	Revision History:
//
