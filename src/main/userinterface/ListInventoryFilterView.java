// specify the package
package userinterface;

// system imports
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.lang.StringBuilder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// project imports
import Utilities.UiConstants;
import Utilities.Utilities;

import impresario.IModel;
import model.ArticleType;
import model.Color;

/**
 * The class containing the List Inventory Filter View  for the Professional Clothes
 * Closet application
 */
//==============================================================
public class ListInventoryFilterView extends View {

    // GUI components
    protected PccButton submitButton;
    protected PccButton cancelButton;

    protected CheckBox selectStatus;
    protected ComboBox status;
    protected CheckBox selectDonateDate;
    protected TextField donateDate;
    protected CheckBox selectArticle;
    protected ComboBox<ArticleType> articleChoice;
    protected CheckBox selectColor;
    protected ComboBox<Color> colorChoice;

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
//        prompt.setFill(Color.web(APP_TEXT_COLOR));
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

        PccText articleLabel = new PccText("Article Type: ");
        selectArticle = new CheckBox();
        selectArticle.selectedProperty().addListener(
                (observable, oldValue, newValue) -> articleChoice.setDisable(!newValue));

        //Borrowed from AddClothingItemView
        articleChoice = new ComboBox<>();
        articleChoice.setConverter(new StringConverter<ArticleType>() {
            @Override
            public String toString(ArticleType object) {
                return (String) object.getState("Description");
            }

            @Override
            public ArticleType fromString(String string) {
                return articleChoice.getItems().stream().filter(at ->
                        at.getState("Description").equals(string)).findFirst().orElse(null);
            }
        });
        articleChoice.valueProperty().addListener((obs, oldval, newval) -> {
            /*if (newval != null) {
                System.out.println("Selected article type: " + newval.getState("Description")
                        + " ID: " + newval.getState("ID"));
            }*/
        });
        articleChoice.setDisable(true);

        //Add all of the Filter widgets to the grid container
        grid.add(statusLabel, 0, 0);
        grid.add(selectStatus, 1, 0);
        grid.add(status, 2, 0);

        grid.add(donateLabel, 0, 1);
        grid.add(selectDonateDate, 1, 1);
//        grid.add(donateDate, 2, 1);
        grid.add(datePicker, 2, 1);

        grid.add(articleLabel, 0, 2);
        grid.add(selectArticle, 1, 2);
        grid.add(articleChoice, 2, 2);


        vbox.getChildren().add(grid);
        vbox.getChildren().add(submitButton);
        vbox.getChildren().add(cancelButton);

        return vbox;
    }

    private void processAction(ActionEvent actionEvent) {
        Properties props = new Properties();

        //Build the queries here, based on selected checkboxes
        // - Search by Status
        if (selectStatus.isSelected()) {
            props.put("status", status.getValue());
        }

        // - Search by DonatedDate
        if (selectDonateDate.isSelected()){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String searchDate = datePicker.getValue().format(dateTimeFormatter);
            System.out.println(searchDate);

            props.put("date", searchDate);
        }

        if (selectArticle.isSelected()) {
            props.put("article", articleChoice.getValue().getState("ID"));
        }
//
//        if (selectColor.isSelected()) {
//            props.put("article", colorChoice.getValue().getState("ID"));
//
//        }

        myModel.stateChangeRequest("Filter", props);
    }

    // Create the status log field
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    public void populateFields() {
        clearErrorMessage();
        datePicker.setValue(LocalDate.now());

        //Instantiate the article type and color values
        Iterator articles = Utilities.collectArticleTypeHash().entrySet().iterator();
        ObservableList<ArticleType> articleTypes = FXCollections.observableArrayList();
        Comparator<ArticleType> compareAT = (o1, o2) ->
                ((String) o1.getState("Description")).compareToIgnoreCase(((String) o2.getState("Description")));
        while (articles.hasNext()) {
            Map.Entry pair = (Map.Entry)articles.next();
            articleTypes.add((ArticleType) pair.getValue());
        }
        articleTypes.sort(compareAT);
        articleChoice.setItems(articleTypes);

//        Iterator colors = Utilities.collectColorHash().entrySet().iterator();
//        ObservableList<Color> colorItems = FXCollections.observableArrayList();
//        Comparator<Color> compareCT = new Comparator<Color>() {
//            @Override
//            public int compare(Color o1, Color o2) {
//                return ((String) o1.getState("Description")).compareToIgnoreCase(((String) o2.getState("Description")));
//            }
//        };
//        while (colors.hasNext()) {
//            Map.Entry pair = (Map.Entry)colors.next();
//            colorItems.add((Color) pair.getValue());
//        }
//        colorItems.sort(compareCT);
//        primaryColorCombo.setItems(colorItems);
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
