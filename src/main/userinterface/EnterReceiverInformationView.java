package userinterface;

// system imports

import Utilities.UiConstants;
import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import model.ClothingItem;
import model.InventoryItemCollection;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports


public class EnterReceiverInformationView extends View {

    // GUI components
    protected TextField netId;
    protected TextField fName;
    protected TextField lName;

    protected PccButton submitButton;
    protected PccButton cancelButton;

    protected TableView<InventoryTableModel> InventoryTable;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object

    public EnterReceiverInformationView(IModel at) {
        super(at, "EnterReceiverInformationView");

        // create a container for showing the contents
        VBox container = getParentContainer();

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        String initialMessage = "";
        container.getChildren().add(createStatusLog(initialMessage));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);

    }


    @Override
    protected String getActionText() {
        return "Checkout a Clothing Item";
    }

    protected void getEntryTableModelValues() {
        ObservableList<InventoryTableModel> tableData = FXCollections.observableArrayList();
        try {
            InventoryItemCollection inventoryItemCollection =
                    (InventoryItemCollection) myModel.getState("InventoryList");

            Vector entryList = (Vector) inventoryItemCollection.getState("InventoryItems");

            if (entryList.size() > 0) {
                Enumeration entries = entryList.elements();

                while (entries.hasMoreElements()) {
                    ClothingItem nextCI = (ClothingItem) entries.nextElement();
                    Vector<String> view = nextCI.getEntryListView();

                    // add this list entry to the list
                    InventoryTableModel nextTableRowData = new InventoryTableModel(view);
                    tableData.add(nextTableRowData);

                }
            } else {
//                displayMessage("No matching entries found!");
            }

            InventoryTable.setItems(tableData);
        } catch (Exception e) {//SQLException e) {
            // Need to handle this exception
        }
    }

    // Create the main form content

    private VBox createFormContent() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        
        PccText prompt = new PccText("Enter Recipient Information");
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

        InventoryTable = new TableView<InventoryTableModel>();
        InventoryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn barcodeColumn = new TableColumn("Barcode");
        barcodeColumn.setMinWidth(50);
        barcodeColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("barcode"));

        TableColumn genderColumn = new TableColumn("Gender");
        genderColumn.setMinWidth(150);
        genderColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("gender"));

        TableColumn sizeColumn = new TableColumn("Size");
        sizeColumn.setMinWidth(50);
        sizeColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("size"));

        TableColumn articleTypeColumn = new TableColumn("Article Type");
        articleTypeColumn.setMinWidth(50);
        articleTypeColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("articleType"));

        TableColumn color1Column = new TableColumn("Color1");
        color1Column.setMinWidth(50);
        color1Column.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("color1"));

        TableColumn color2Column = new TableColumn("Color2");
        color2Column.setMinWidth(50);
        color2Column.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("color2"));

        TableColumn brandColumn = new TableColumn("Brand");
        brandColumn.setMinWidth(50);
        brandColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("brand"));

        TableColumn notesColumn = new TableColumn("Notes");
        notesColumn.setMinWidth(50);
        notesColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("notes"));

        TableColumn statusColumn = new TableColumn("Status");
        statusColumn.setMinWidth(50);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("status"));

        TableColumn donorFirstNameColumn = new TableColumn("Donor First Name");
        donorFirstNameColumn.setMinWidth(50);
        donorFirstNameColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("donorFirstName"));

        TableColumn donorLastNameColumn = new TableColumn("Donor Last Name");
        donorLastNameColumn.setMinWidth(50);
        donorLastNameColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("donorLastName"));

        TableColumn donorPhoneColumn = new TableColumn("Donor Phone");
        donorPhoneColumn.setMinWidth(50);
        donorPhoneColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("donorPhone"));

        TableColumn donorEmailColumn = new TableColumn("Donor Email");
        donorEmailColumn.setMinWidth(50);
        donorEmailColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("donorEmail"));

        TableColumn receiverNetIdColumn = new TableColumn("Receiver Net ID");
        receiverNetIdColumn.setMinWidth(50);
        receiverNetIdColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("receiverNetId"));

        TableColumn receiverFirstNameColumn = new TableColumn("Receiver First Name");
        receiverFirstNameColumn.setMinWidth(50);
        receiverFirstNameColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("receiverFirstName"));

        TableColumn receiverLastNameColumn = new TableColumn("Receiver Last Name");
        receiverLastNameColumn.setMinWidth(50);
        receiverLastNameColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("receiverLastName"));

        TableColumn dateDonatedColumn = new TableColumn("Date Donated");
        dateDonatedColumn.setMinWidth(50);
        dateDonatedColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("dateDonated"));

        TableColumn dateTakenColumn = new TableColumn("Date Taken");
        dateTakenColumn.setMinWidth(50);
        dateTakenColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("dateTaken"));


        InventoryTable.getColumns().addAll(barcodeColumn, genderColumn, sizeColumn,
                articleTypeColumn, color1Column, color2Column, brandColumn, notesColumn, statusColumn,
                donorLastNameColumn, donorFirstNameColumn, donorPhoneColumn, donorEmailColumn,
                receiverNetIdColumn, receiverLastNameColumn, receiverFirstNameColumn,
                dateDonatedColumn, dateTakenColumn);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(150, 150);
        scrollPane.setContent(InventoryTable);

        PccText netIdLabel = new PccText(" Net ID : ");
        Font myFont = Font.font(APP_FONT, 16);
        netIdLabel.setFont(myFont);
        netIdLabel.setFill(Color.web(APP_TEXT_COLOR));
        netIdLabel.setWrappingWidth(150);
        netIdLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(netIdLabel, 0, 1);

        netId = new TextField();
        grid.add(netId, 1, 1);

        PccText fNameLabel = new PccText(" First Name : ");
        fNameLabel.setFont(myFont);
        fNameLabel.setFill(Color.web(APP_TEXT_COLOR));
        fNameLabel.setWrappingWidth(150);
        fNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(fNameLabel, 0, 2);

        fName = new TextField();
        grid.add(fName, 1, 2);

        PccText lNameLabel = new PccText(" Last Name : ");
        lNameLabel.setFont(myFont);
        lNameLabel.setFill(Color.web(APP_TEXT_COLOR));
        lNameLabel.setWrappingWidth(150);
        lNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(lNameLabel, 0, 3);

        lName = new TextField();
        grid.add(lName, 1, 3);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        submitButton = new PccButton("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                Properties props = new Properties();
                String netIdReceiver = netId.getText();
                if (netIdReceiver.length() > 0) {
                    props.setProperty("ReceiverNetid", netIdReceiver);
                    String fNameReceiver = fName.getText();
                    if (fNameReceiver.length() > 0 && fNameReceiver.length() < UiConstants.RECEIVER_FIRST_NAME_MAX_LENGTH) {
                        props.setProperty("ReceiverFirstName", fNameReceiver);
                        String lNameReceiver = lName.getText();
                        if (lNameReceiver.length() > 0 && lNameReceiver.length() < UiConstants.RECEIVER_LAST_NAME_MAX_LENGTH) {
                            props.setProperty("ReceiverLastName", lNameReceiver);
                            myModel.stateChangeRequest("ReceiverData", props);
                        } else {
                            displayErrorMessage("Last name incorrect size!");
                        }
                    } else {
                        displayErrorMessage("First name incorrect size!");
                    }

                } else {
                    displayErrorMessage("NetId incorrect size!");

                }

            }
        });
        doneCont.getChildren().add(submitButton);

        cancelButton = new PccButton("Done");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelCheckoutCI", null);
            }
        });
        doneCont.getChildren().add(cancelButton);

        vbox.getChildren().add(scrollPane);
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
        getEntryTableModelValues();
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
