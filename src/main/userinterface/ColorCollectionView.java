package userinterface;

// system imports

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import model.Color;
import model.ColorCollection;

import java.util.Enumeration;
import java.util.Vector;

// project imports

//==============================================================================
public class ColorCollectionView extends View {
    protected TableView<ColorTableModel> tableOfColors;
    protected PccButton cancelButton;
    protected PccButton submitButton;

    protected MessageView statusLog;


    //--------------------------------------------------------------------------
    public ColorCollectionView(IModel ccv) {
        super(ccv, "ColorCollectionView");

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

    //--------------------------------------------------------------------------
    protected void populateFields() {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues() {

        ObservableList<ColorTableModel> tableData = FXCollections.observableArrayList();
        try {
            ColorCollection colorCollection =
                    (ColorCollection) myModel.getState("ColorList");

            Vector entryList = (Vector) colorCollection.getState("Colors");

            if (entryList.size() > 0) {
                Enumeration entries = entryList.elements();

                while (entries.hasMoreElements()) {

                    Color nextCT = (Color) entries.nextElement();
                    Vector<String> view = nextCT.getEntryListView();

                    // add this list entry to the list
                    ColorTableModel nextTableRowData = new ColorTableModel(view);
                    tableData.add(nextTableRowData);

                }
            } else {
                displayMessage("No matching entries found!");
            }

            tableOfColors.setItems(tableData);
        } catch (Exception e) {//SQLException e) {
            // Need to handle this exception
        }
    }

    @Override
    protected String getActionText() {
        return "Color Search Results";
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        PccText prompt = new PccText("Please Select a Color:");
        prompt.setWrappingWidth(WRAPPING_WIDTH);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFont(Font.font(APP_FONT, 20));
        vbox.getChildren().add(prompt);


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(12);
        grid.setPadding(new Insets(5, 5, 5, 5));

        tableOfColors = new TableView<ColorTableModel>();
        tableOfColors.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn barcodePrefixColumn = new TableColumn("Barcode Prefix");
        barcodePrefixColumn.setMinWidth(50);
        barcodePrefixColumn.setCellValueFactory(
                new PropertyValueFactory<ColorTableModel, String>("barcodePrefix"));

        TableColumn descriptionColumn = new TableColumn("Description");
        descriptionColumn.setMinWidth(150);
        descriptionColumn.setCellValueFactory(
                new PropertyValueFactory<ColorTableModel, String>("description"));

        TableColumn alphaCodeColumn = new TableColumn("Alpha Code");
        alphaCodeColumn.setMinWidth(50);
        alphaCodeColumn.setCellValueFactory(
                new PropertyValueFactory<ColorTableModel, String>("alphaCode"));

        TableColumn statusColumn = new TableColumn("Status");
        statusColumn.setMinWidth(50);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<ColorTableModel, String>("status"));

        tableOfColors.getColumns().addAll(descriptionColumn,
                barcodePrefixColumn, alphaCodeColumn, statusColumn);

        tableOfColors.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() >= 2) {
                processColorSelected();
            }
        });

        tableOfColors.setMaxSize(800, 250);

        submitButton = new PccButton("Submit");
        submitButton.setOnAction(e -> {
            clearErrorMessage();
            // do the inquiry
            processColorSelected();

        });

        cancelButton = new PccButton("Return");
        cancelButton.setOnAction(e -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelColorList", null);
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(submitButton);
        btnContainer.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(tableOfColors);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }

    //--------------------------------------------------------------------------
    public void updateState(String key, Object value) {
    }

    //--------------------------------------------------------------------------
    protected void processColorSelected() {
        ColorTableModel selectedItem = tableOfColors.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            String selectedBarcodePrefix = selectedItem.getBarcodePrefix();

            myModel.stateChangeRequest("ColorSelected", selectedBarcodePrefix);
        }
    }

    //--------------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
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
