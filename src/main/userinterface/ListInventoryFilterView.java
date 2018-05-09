// specify the package
package userinterface;

// system imports

import Utilities.UiConstants;
import impresario.IModel;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.lang.StringBuilder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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

    protected CheckBox selectStatus;
    protected ComboBox status;
    protected CheckBox selectDonateDate;
    protected TextField donateDate;
    protected CheckBox selectRecDate;
    protected TextField recDate;
    DatePicker datePicker = new DatePicker();

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object

    ListInventoryFilterView(IModel clothingItem) {
        super(clothingItem, "ListInventoryFilterView");

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
    }

    @Override
    protected String getActionText() {
        return "List Inventory";
    }

    // Create the main form content

    private VBox createFormContent() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        PccText prompt = new PccText("Please select desired filters.\nLeave all filters unchecked to list all items.");
        prompt.setWrappingWidth(WRAPPING_WIDTH);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.web(APP_TEXT_COLOR));
        prompt.setFont(Font.font(APP_FONT, 18));
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

        //These checkboxes provide filter options

        PccText statusLabel = new PccText("Status: ");
        //selectStatus = new CheckBox("Status: ");
        selectStatus = new CheckBox();
        selectStatus.selectedProperty().addListener(
                (observable, oldValue, newValue) -> status.setDisable(!newValue));
        status = new ComboBox();
        status.getItems().addAll("Donated", "Received", "Removed");
        status.setDisable(true);
        status.getSelectionModel().select(0);


        PccText donateLabel = new PccText("Donated Date: ");
        selectDonateDate = new CheckBox();
        selectDonateDate.selectedProperty().addListener(
                (observable, oldValue, newValue) -> datePicker.setDisable(!newValue));
        donateDate = new TextField();
        datePicker.setDisable(true);
        donateDate.setPromptText("Items older than...");




        grid.add(statusLabel, 0, 0);
        grid.add(selectStatus, 1, 0);
        grid.add(status, 2, 0);

        grid.add(donateLabel, 0, 1);
        grid.add(selectDonateDate, 1, 1);
//        grid.add(donateDate, 2, 1);
        grid.add(datePicker, 2, 1);


        vbox.getChildren().add(grid);
        vbox.getChildren().add(submitButton);
        vbox.getChildren().add(cancelButton);

        return vbox;
    }

    private void processAction(ActionEvent actionEvent) {

        //String query = "SELECT * FROM inventory WHERE 1 ";
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM inventory WHERE 1 ");

//        if(selectAllDonated.isSelected())
//        {
//            query = query + "AND (Status = '" + status.getValue() + "') ";
//        }
//        query = query + ";";

        //Build the queries here, based on selected checkboxes
        // - Search by Status
        if(selectStatus.isSelected())
            query.append("AND (Status = '" + status.getValue() + "') ");

        // - Search by DonatedDate
        if (selectDonateDate.isSelected()){
//            String searchDate;

            //Check for null in the textfield
//            if (searchDate == null || searchDate.trim().isEmpty()) {
                //Assume today's date if the field is empty
//                Calendar currDate = Calendar.getInstance();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
               String searchDate = datePicker.getValue().format(dateTimeFormatter);
            System.out.println(searchDate);
//            }

            query.append("AND (DateDonated <= '" + searchDate + "') ");
        }


        //Close the search query string
        query.append(";");
        myModel.stateChangeRequest("Filter", query.toString());
    }

    // Create the status log field
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    public void populateFields() {
        clearErrorMessage();
        datePicker.setValue(LocalDate.now());
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
