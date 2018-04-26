package userinterface;

// system imports
import Utilities.UiConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.Properties;
import java.util.Vector;
import java.util.Enumeration;
// project imports
import impresario.IModel;
//import model.Item;
import model.ClothingItem;
import model.InventoryItemCollection;

//==============================================================================
public class ReceiverRecentCheckoutView extends View
{
    protected TableView<InventoryTableModel> InventoryTable;
    protected Button checkoutButton;
    protected Button cancelButton;

    protected MessageView statusLog;

    //--------------------------------------------------------------------------
    public ReceiverRecentCheckoutView(IModel inv)
    {
        super(inv, "InventoryItemCollectionView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());

        container.getChildren().add(createFormContent());

        // Error message area
        String errorMessage = "";
        container.getChildren().add(createStatusLog(""));
        displayErrorMessage(errorMessage);

        getChildren().add(container);

        populateFields();

        myModel.subscribe("DisplayUpdateMessage2", this);

    }


    //--------------------------------------------------------------------------
    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues()
    {
        ObservableList<InventoryTableModel> tableData = FXCollections.observableArrayList();
        InventoryItemCollection inventoryItemCollection = (InventoryItemCollection)myModel.getState("ReceiverRecentCheckouts");
        Vector entryList = (Vector)inventoryItemCollection.getState("InventoryItems");

        if (entryList.size() > 0)
        {
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                ClothingItem nextCI = (ClothingItem)entries.nextElement();
                Vector<String> view = nextCI.getEntryListView();
                // add this list entry to the list
                InventoryTableModel nextTableRowData = new InventoryTableModel(view);
                tableData.add(nextTableRowData);
            }
        }
        else
        {
            displayMessage("No matching entries found!");
        }
        InventoryTable.setItems(tableData);
    }

    //-------------------------------------------------------------
    private Node createTitle()
    {
        VBox container = new VBox(10);
        container.setPadding(new Insets(1, 1, 1, 30));

        Text clientText = new Text(" Office of Career Services ");
        clientText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        clientText.setWrappingWidth(350);
        clientText.setTextAlignment(TextAlignment.CENTER);
        clientText.setFill(Color.DARKGREEN);
        container.getChildren().add(clientText);

        Text collegeText = new Text(" THE COLLEGE AT BROCKPORT ");
        collegeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        collegeText.setWrappingWidth(350);
        collegeText.setTextAlignment(TextAlignment.CENTER);
        collegeText.setFill(Color.DARKGREEN);
        container.getChildren().add(collegeText);

        Text titleText = new Text(" Professional Clothes Closet Management System ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(350);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        Text blankText = new Text("  ");
        blankText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        blankText.setWrappingWidth(350);
        blankText.setTextAlignment(TextAlignment.CENTER);
        blankText.setFill(Color.WHITE);
        container.getChildren().add(blankText);

        Text actionText = new Text("      ** Checkout Clothing Items **       ");
        actionText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        actionText.setWrappingWidth(350);
        actionText.setTextAlignment(TextAlignment.CENTER);
        actionText.setFill(Color.BLACK);
        container.getChildren().add(actionText);

        return container;
    }

    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        Text prompt = new Text("Receiver Recent History");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        prompt.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        vbox.getChildren().add(prompt);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 25, 10, 0));

        InventoryTable = new TableView<InventoryTableModel>();
        InventoryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn barcodeColumn = new TableColumn("Barcode") ;
        barcodeColumn.setMinWidth(50);
        barcodeColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("barcode"));

        TableColumn genderColumn = new TableColumn("Gender") ;
        genderColumn.setMinWidth(150);
        genderColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("gender"));

        TableColumn sizeColumn = new TableColumn("Size") ;
        sizeColumn.setMinWidth(50);
        sizeColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("size"));

        TableColumn articleTypeColumn = new TableColumn("Article Type") ;
        articleTypeColumn.setMinWidth(50);
        articleTypeColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("articleType"));

        TableColumn color1Column = new TableColumn("Color1") ;
        color1Column.setMinWidth(50);
        color1Column.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("color1"));

        TableColumn color2Column = new TableColumn("Color2") ;
        color2Column.setMinWidth(50);
        color2Column.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("color2"));

        TableColumn brandColumn = new TableColumn("Brand") ;
        brandColumn.setMinWidth(50);
        brandColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("brand"));

        TableColumn notesColumn = new TableColumn("Notes") ;
        notesColumn.setMinWidth(50);
        notesColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("notes"));

        TableColumn statusColumn = new TableColumn("Status") ;
        statusColumn.setMinWidth(50);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("status"));

        TableColumn donorFirstNameColumn = new TableColumn("Donor First Name") ;
        donorFirstNameColumn.setMinWidth(50);
        donorFirstNameColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("donorFirstName"));

        TableColumn donorLastNameColumn = new TableColumn("Donor Last Name") ;
        donorLastNameColumn.setMinWidth(50);
        donorLastNameColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("donorLastName"));

        TableColumn donorPhoneColumn = new TableColumn("Donor Phone") ;
        donorPhoneColumn.setMinWidth(50);
        donorPhoneColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("donorPhone"));

        TableColumn donorEmailColumn = new TableColumn("Donor Email") ;
        donorEmailColumn.setMinWidth(50);
        donorEmailColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("donorEmail"));

        TableColumn receiverNetIdColumn = new TableColumn("Receiver Net ID") ;
        receiverNetIdColumn.setMinWidth(50);
        receiverNetIdColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("receiverNetId"));

        TableColumn receiverFirstNameColumn = new TableColumn("Receiver First Name") ;
        receiverFirstNameColumn.setMinWidth(50);
        receiverFirstNameColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("receiverFirstName"));

        TableColumn receiverLastNameColumn = new TableColumn("Receiver Last Name") ;
        receiverLastNameColumn.setMinWidth(50);
        receiverLastNameColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("receiverLastName"));

        TableColumn dateDonatedColumn = new TableColumn("Date Donated") ;
        dateDonatedColumn.setMinWidth(50);
        dateDonatedColumn.setCellValueFactory(
                new PropertyValueFactory<InventoryTableModel, String>("dateDonated"));

        TableColumn dateTakenColumn = new TableColumn("Date Taken") ;
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

        VBox doneCont = new VBox(10);
        doneCont.setAlignment(Pos.CENTER);
        checkoutButton = new Button("Checkout Items Anyways");
        checkoutButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        checkoutButton.setPrefSize(250, 20);
        checkoutButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e)
            {
                clearErrorMessage();
                myModel.stateChangeRequest("ReceiverData", null);
            }

        });
        doneCont.getChildren().add(checkoutButton);

        doneCont.setAlignment(Pos.CENTER);
        cancelButton = new Button("Cancel");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setPrefSize(250, 20);
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

    //--------------------------------------------------------------------------
    public void updateState(String key, Object value)
    {
        if(key.equals("DisplayUpdateMessage2"))
        {
            String updateMessage = (String)myModel.getState("UpdateMessage");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, updateMessage);
            alert.setTitle("Checkout");
            alert.setHeaderText("Clothing Items have been checked out.");
            alert.show();
        }

    }
    //--------------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);
        return statusLog;
    }

    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }
}
