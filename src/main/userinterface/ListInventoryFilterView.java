// specify the package
package userinterface;

// system imports

import Utilities.UiConstants;
import impresario.IModel;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.Properties;
// project imports

/**
 * The class containing the Add Article Type View  for the Professional Clothes
 * Closet application
 */
//==============================================================
public class ListInventoryFilterView extends View {

    // GUI components
    protected PccButton submitButton;
    protected Button cancelButton;

    protected CheckBox selectAllDonated;
    protected ComboBox status;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object

    ListInventoryFilterView(IModel clothingItem) {
        super(clothingItem, "ListInventoryFilterView");

        // create a container for showing the contents
        VBox container = getParentContainer();

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();
    }

    @Override
    protected String getActionText() {
        return "List Inventory Filter";
    }

    // Create the main form content

    private VBox createFormContent() {
        VBox vbox = new VBox(10);

        PccText prompt = new PccText("Please select desired filters.\nLeave all filters unchecked to list all items.");
        prompt.setWrappingWidth(WRAPPING_WIDTH);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.web(APP_TEXT_COLOR));
        prompt.setFont(Font.font(APP_FONT, FontWeight.BOLD, 14));
        vbox.getChildren().add(prompt);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 25, 0, 25));

        submitButton = new PccButton("Submit");
        submitButton.setOnAction(this::processAction);

        cancelButton = new PccButton("Return");
        cancelButton.setOnAction(e -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelInventory", null);
        });

        selectAllDonated = new CheckBox("Status: ");
        status = new ComboBox();
        status.getItems().addAll("Donated", "Received", "Removed");
        status.getSelectionModel().select(0);

        grid.add(selectAllDonated, 0, 0);
        grid.add(status, 1, 0);


        vbox.getChildren().add(grid);
        vbox.getChildren().add(submitButton);
        vbox.getChildren().add(cancelButton);

        return vbox;
    }

    private void processAction(ActionEvent actionEvent) {
        String query = "SELECT * FROM inventory WHERE 1 ";
        if(selectAllDonated.isSelected())
        {
            query = query + "AND (Status = '" + status.getValue() + "') ";
        }
        query = query + ";";
        myModel.stateChangeRequest("Filter", query);
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
//                displayErrorMessage(val);
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