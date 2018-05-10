// specify the package
package userinterface;

// system imports

import Utilities.UiConstants;
import Utilities.Utilities;
import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.StringConverter;
import model.ArticleType;
import model.ClothingItem;
import model.ClothingRequest;
import model.Color;

import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Pattern;

// project imports

/**
 * The class containing the Add Clothing Item View  for the Professional Clothes
 * Closet application
 */
//==============================================================
public class FulfillRequestView extends View {

    // For showing error message
    protected MessageView statusLog;
    // GUI components
    protected TextField gender;
    protected TextField sizeText;
    protected TextField articleType;
    protected TextField primaryColor;
    protected TextField secondaryColor;
    protected TextField brandText;
    protected TextField notesText;

    protected TextField genderReq;
    protected TextField sizeTextReq;
    protected TextField articleTypeReq;
    protected TextField primaryColorReq;
    protected TextField secondaryColorReq;
    protected TextField brandTextReq;
    protected TextField notesTextReq;
    private PccButton submitButton;
    private PccButton cancelButton;

    // constructor for this class -- takes a model object
    public FulfillRequestView(IModel clothingItem) {
        super(clothingItem, "FulfillRequestView");

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
        return "Fulfil Request";
    }

    // Create the main form content
    private VBox createFormContent() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        PccText prompt = new PccText("Please Input Clothing Item Information:");
        prompt.setWrappingWidth(WRAPPING_WIDTH);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFont(Font.font(APP_FONT, 20));
        vbox.getChildren().add(prompt);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 25, 20, 0));

        PccText myItem = new PccText("Clothing Item");
        myItem.setWrappingWidth(150);
        myItem.setTextAlignment(TextAlignment.CENTER);
        grid.add(myItem, 1, 0);

        PccText myRequest = new PccText("Requested Item");
        myRequest.setWrappingWidth(150);
        myRequest.setTextAlignment(TextAlignment.CENTER);
        grid.add(myRequest, 3, 0);

        // Gender UI items ==================================================
        PccText genderLabel = new PccText(" Gender : ");
        genderLabel.setWrappingWidth(150);
        genderLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(genderLabel, 0, 1);

        gender = new TextField();
        gender.setEditable(false);

        grid.add(gender, 1, 1);
        // =================================================================
        // Size UI Items ===================================================
        PccText sizeLabel = new PccText(" Size : ");
        sizeLabel.setWrappingWidth(150);
        sizeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(sizeLabel, 0, 2);

        sizeText = new TextField();
        sizeText.setEditable(false);
        grid.add(sizeText, 1, 2);

        // =================================================================
        // Article Type UI Items ===========================================
        PccText articleTypeLabel = new PccText(" Article Type : ");
        articleTypeLabel.setWrappingWidth(150);
        articleTypeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(articleTypeLabel, 0, 3);

        articleType = new TextField();
        articleType.setEditable(false);

        grid.add(articleType, 1, 3);
        // =================================================================
        // Primary Color UI Items ==========================================
        PccText primaryColorLabel = new PccText(" Primary Color : ");
        primaryColorLabel.setWrappingWidth(150);
        primaryColorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(primaryColorLabel, 0, 4);

        primaryColor = new TextField();
        primaryColor.setEditable(false);

        grid.add(primaryColor, 1, 4);
        // =================================================================
        // Secondary Color UI Items ========================================
        PccText secondaryColorLabel = new PccText(" Secondary Color : ");
        secondaryColorLabel.setWrappingWidth(150);
        secondaryColorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(secondaryColorLabel, 0, 5);

        secondaryColor = new TextField();
        secondaryColor.setEditable(false);

        grid.add(secondaryColor, 1, 5);
        // =================================================================
        // Brand UI Items ==================================================
        PccText brandLabel = new PccText(" Brand : ");
        brandLabel.setWrappingWidth(150);
        brandLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(brandLabel, 0, 6);

        brandText = new TextField();
        brandText.setEditable(false);
        grid.add(brandText, 1, 6);

        // =================================================================
        // Notes UI Items ==================================================
        PccText notesLabel = new PccText(" Notes : ");
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 0, 7);

        notesText = new TextField();
        notesText.setEditable(false);
        grid.add(notesText, 1, 7);

        //------------------------------------------------
        // request fields
        // Gender UI items ==================================================
        PccText genderLabel2 = new PccText(" Gender : ");
        genderLabel2.setWrappingWidth(150);
        genderLabel2.setTextAlignment(TextAlignment.RIGHT);
        grid.add(genderLabel2, 2, 1);

        genderReq = new TextField();
        genderReq.setEditable(false);

        grid.add(genderReq, 3, 1);
        // =================================================================
        // Size UI Items ===================================================
        PccText sizeLabel2 = new PccText(" Size : ");
        sizeLabel2.setWrappingWidth(150);
        sizeLabel2.setTextAlignment(TextAlignment.RIGHT);
        grid.add(sizeLabel2, 2, 2);

        sizeTextReq = new TextField();
        sizeTextReq.setEditable(false);
        grid.add(sizeTextReq, 3, 2);

        // =================================================================
        // Article Type UI Items ===========================================
        PccText articleTypeLabel2 = new PccText(" Article Type : ");
        articleTypeLabel2.setWrappingWidth(150);
        articleTypeLabel2.setTextAlignment(TextAlignment.RIGHT);
        grid.add(articleTypeLabel2, 2, 3);

        articleTypeReq = new TextField();
        articleTypeReq.setEditable(false);

        grid.add(articleTypeReq, 3, 3);
        // =================================================================
        // Primary Color UI Items ==========================================
        PccText primaryColorLabel2 = new PccText(" Primary Color : ");
        primaryColorLabel2.setWrappingWidth(150);
        primaryColorLabel2.setTextAlignment(TextAlignment.RIGHT);
        grid.add(primaryColorLabel2, 2, 4);

        primaryColorReq = new TextField();
        primaryColorReq.setEditable(false);

        grid.add(primaryColorReq, 3, 4);
        // =================================================================
        // Secondary Color UI Items ========================================
        PccText secondaryColorLabel2 = new PccText(" Secondary Color : ");
        secondaryColorLabel2.setWrappingWidth(150);
        secondaryColorLabel2.setTextAlignment(TextAlignment.RIGHT);
        grid.add(secondaryColorLabel2, 2, 5);

        secondaryColorReq = new TextField();
        secondaryColorReq.setEditable(false);

        grid.add(secondaryColorReq, 3, 5);
        // =================================================================
        // Brand UI Items ==================================================
        PccText brandLabel2 = new PccText(" Brand : ");
        brandLabel2.setWrappingWidth(150);
        brandLabel2.setTextAlignment(TextAlignment.RIGHT);
        grid.add(brandLabel2, 2, 6);

        brandTextReq = new TextField();
        brandTextReq.setEditable(false);
        grid.add(brandTextReq, 3, 6);


        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        submitButton = new PccButton("Submit");
        submitButton.setOnAction(this::processAction);
        doneCont.getChildren().add(submitButton);

        cancelButton = new PccButton("Return");
        cancelButton.setOnAction(e -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelRequest", null);
        });
        doneCont.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }


    // Create the status log field
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    public void populateFields() {
        clearErrorMessage();
        ClothingItem ci = (ClothingItem) myModel.getState("ClothingItem");
        ClothingRequest cr = (ClothingRequest) myModel.getState("ClothingRequest");

        gender.setText((String) ci.getState("Gender"));
        if(((String) ci.getState("Size")).equals("999")) {
            sizeText.setText("");
        } else
            sizeText.setText((String) ci.getState("Size"));
        articleType.setText((String) Utilities.collectArticleTypeHash().get(((String) ci.getState("ArticleType"))).getState("Description"));
        if(Utilities.collectColorHash().get((String) ci.getState("Color1")) != null)
            primaryColor.setText((String) Utilities.collectColorHash().get((String) ci.getState("Color1")).getState("Description"));
        if(Utilities.collectColorHash().get((String) ci.getState("Color2")) != null)
            secondaryColor.setText((String) Utilities.collectColorHash().get((String) ci.getState("Color2")).getState("Description"));
        brandText.setText((String) ci.getState("Brand"));
        notesText.setText((String) ci.getState("Notes"));

        genderReq.setText((String) cr.getState("RequestedGender"));
        if(((String) cr.getState("RequestedSize")).equals("999")) {
            sizeTextReq.setText("");
        } else
            sizeTextReq.setText((String) cr.getState("RequestedSize"));
        articleTypeReq.setText((String) Utilities.collectArticleTypeHash().get(((String) cr.getState("RequestedArticleType"))).getState("Description"));
        if(Utilities.collectColorHash().get((String) cr.getState("RequestedColor1")) != null)
            primaryColorReq.setText((String) Utilities.collectColorHash().get((String) cr.getState("RequestedColor1")).getState("Description"));
        if(Utilities.collectColorHash().get((String) cr.getState("RequestedColor2")) != null)
            secondaryColorReq.setText((String) Utilities.collectColorHash().get((String) cr.getState("RequestedColor2")).getState("Description"));
        brandTextReq.setText((String) ci.getState("RequestedBrand"));

    }

    private void processAction(ActionEvent e) {
        clearErrorMessage();
        myModel.stateChangeRequest("ProcessRequest", null);
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
            } else if (val.length() > 0) {
                displayMessage(val);
                myModel.stateChangeRequest("CancelAddClothingItem", null);
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
