// specify the package
package userinterface;

// system imports

import impresario.IModel;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import model.PccAlert;

import java.util.Vector;

// project imports

/**
 * The class containing the Transaction Choice View for the PCC application
 */
//==============================================================
public class ReceptionistView extends View {

    // GUI components
    private PccButton addArticleTypeButton;
    private PccButton updateArticleTypeButton;
    private PccButton removeArticleTypeButton;
    private PccButton addColorButton;
    private PccButton updateColorButton;
    private PccButton removeColorButton;
    private PccButton addClothingItemButton;
    private PccButton updateClothingItemButton;
    private PccButton removeClothingItemButton;
    private PccButton logRequestButton;
    private PccButton fulfillRequestButton;
    private PccButton removeRequestButton;

    private PccButton checkoutClothingItemButton;
    private PccButton listAvailableInventoryButton;

    private PccButton cancelButton;

    private MessageView statusLog;

    // constructor for this class -- takes a model object
    ReceptionistView(IModel receptionist) {
        super(receptionist, "ReceptionistView");

        // BorderPane for clean display of containers (full width color fill)
        bp.setTop(createBanner());

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
        return "What do you wish to do today?";
    }

    // Create the navigation buttons
    private VBox createFormContent() {

        VBox container = new VBox(15);
        container.setAlignment(Pos.CENTER);
        GridPane gridContainer = new GridPane();
        gridContainer.setAlignment(Pos.CENTER);
        gridContainer.setHgap(32);
        gridContainer.setVgap(12);
        gridContainer.setPadding(new Insets(0, 10, 0, 10));

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.RIGHT);
        gridContainer.getColumnConstraints().add(columnConstraints);

        // create the buttons, listen for events, add them to the container
        HBox checkoutCont = new HBox(10);
        checkoutCont.setAlignment(Pos.CENTER);
        checkoutClothingItemButton = new PccButton("Checkout Clothing Item");
        checkoutClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("CheckoutClothingItem", null));
        checkoutCont.getChildren().add(checkoutClothingItemButton);

        gridContainer.add(checkoutCont, 0, 0, 4, 1);
        gridContainer.add(new Pane(), 0, 1, 4, 1);
        // Article type choices
        PccText atLabel = new PccText("Article Types: ");
        addArticleTypeButton = new PccButton("Add");
        addArticleTypeButton.setOnAction(e -> myModel.stateChangeRequest("AddArticleType", null));

        updateArticleTypeButton = new PccButton("Update");
        updateArticleTypeButton.setOnAction(e -> myModel.stateChangeRequest("UpdateArticleType", null));

        removeArticleTypeButton = new PccButton("Remove");
        removeArticleTypeButton.setOnAction(e -> myModel.stateChangeRequest("RemoveArticleType", null));

        gridContainer.add(atLabel, 0, 2);
        gridContainer.add(addArticleTypeButton, 1, 2);
        gridContainer.add(updateArticleTypeButton, 2, 2);
        gridContainer.add(removeArticleTypeButton, 3, 2);

        // Color choices
        PccText colorLabel = new PccText("Colors: ");
        addColorButton = new PccButton("Add");
        addColorButton.setOnAction(e -> myModel.stateChangeRequest("AddColor", null));

        updateColorButton = new PccButton("Update");
        updateColorButton.setOnAction(e -> myModel.stateChangeRequest("UpdateColor", null));

        removeColorButton = new PccButton("Remove");
        removeColorButton.setOnAction(e -> myModel.stateChangeRequest("RemoveColor", null));

        gridContainer.add(colorLabel, 0, 3);
        gridContainer.add(addColorButton, 1, 3);
        gridContainer.add(updateColorButton, 2, 3);
        gridContainer.add(removeColorButton, 3, 3);

        // Clothing item choices
        PccText ciLabel = new PccText("Clothing Items: ");
        addClothingItemButton = new PccButton("Add");
        addClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("AddClothingItem", null));

        updateClothingItemButton = new PccButton("Update");
        updateClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("ModifyClothingItem", null));

        removeClothingItemButton = new PccButton("Remove");
        removeClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("RemoveClothingItem", null));

        gridContainer.add(ciLabel, 0, 4);
        gridContainer.add(addClothingItemButton, 1, 4);
        gridContainer.add(updateClothingItemButton, 2, 4);
        gridContainer.add(removeClothingItemButton, 3, 4);

        // Clothing item request
        PccText reqLabel = new PccText("Requests: ");
        logRequestButton = new PccButton("Log");
        logRequestButton.setOnAction(e -> myModel.stateChangeRequest("LogRequest", null));

        fulfillRequestButton = new PccButton("Fulfill");
        fulfillRequestButton.setOnAction(e -> processAction());

        removeRequestButton = new PccButton("Remove");
        removeRequestButton.setOnAction(e -> myModel.stateChangeRequest("RemoveRequest", null));

        gridContainer.add(reqLabel, 0, 5);
        gridContainer.add(logRequestButton, 1, 5);
        gridContainer.add(fulfillRequestButton, 2, 5);
        gridContainer.add(removeRequestButton, 3, 5);

        gridContainer.add(new Pane(), 0, 6, 4, 1);

        HBox listAvailCont = new HBox(10);
        listAvailCont.setAlignment(Pos.CENTER);
        listAvailableInventoryButton = new PccButton("List Available Inventory");
        listAvailableInventoryButton.setOnAction(e -> myModel.stateChangeRequest("ListAvailableInventory", null));
        listAvailCont.getChildren().add(listAvailableInventoryButton);

        gridContainer.add(listAvailCont, 0, 7, 4, 1);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        cancelButton = new PccButton("Exit System");
        cancelButton.setOnAction(e -> myModel.stateChangeRequest("ExitSystem", null));
        doneCont.getChildren().add(cancelButton);

        gridContainer.add(new Pane(), 0, 8);

        container.getChildren().add(gridContainer);
        container.getChildren().add(doneCont);

        return container;
    }

    MessageView createStatusLog(String initialMessage) {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }


    public void populateFields() {
        Vector requests = (Vector) myModel.getState("Requests");
        if (requests.size() > 0) {
            fulfillRequestButton.setText("Fulfill (" + requests.size() + ")");
        }
    }

    private void processAction() {
        Vector myRequests = (Vector) myModel.getState("Requests");
        if (myRequests.size() > 0) {
            PccAlert myAlert = PccAlert.getInstance();
            myAlert.setAlertType(Alert.AlertType.CONFIRMATION);
            myAlert.setHeaderText("Request Fulfillment");
            myAlert.setTitle("Brockport Professional Clothes Closet Info");
            myAlert.setContentText("You have " + ((Vector)
                    myModel.getState("Requests")).size() +
                    " requests ready to be fulfilled.\nWould you like to view them?");
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            myAlert.getButtonTypes().setAll(yesButton, noButton);
            myAlert.showAndWait().ifPresent(type -> {
                if (type.getText().equals("Yes")) {
                    myModel.stateChangeRequest("FulfillRequestSpecific", myRequests);
                } else
                    myModel.stateChangeRequest("FulfillRequest", null);
            });
        } else
            myModel.stateChangeRequest("FulfillRequest", null);
    }

    public void updateState(String key, Object value) {
        if (key.equals("TransactionError")) {
            // display the passed text
            displayErrorMessage((String) value);
        }
    }

    /**
     * Display error message
     */

    public void displayErrorMessage(String message) {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Clear error message
     */

    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }
}
