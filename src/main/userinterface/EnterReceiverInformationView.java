package userinterface;

// system imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import impresario.IModel;
import model.ClothingItem;
import model.InventoryItemCollection;

public class EnterReceiverInformationView extends View {

    // GUI components
    protected TextField netId;
    protected TextField fName;
    protected TextField lName;

    protected Button submitButton;
    protected Button cancelButton;

    protected TableView<InventoryTableModel> InventoryTable;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public EnterReceiverInformationView(IModel at)
    {
        super(at, "EnterReceiverInformationView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        //The initial status message neds to be the current barcodes.
        String initialMessage = "";
        String barcodeList = (String) myModel.getState("Cart");
        if(barcodeList == null || barcodeList.equals(""))
        {
            initialMessage = "Cart is empty";
        }
        else
        {
            initialMessage = "Cart: " + barcodeList;
        }
        container.getChildren().add(createStatusLog(initialMessage));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);

    }

    //-------------------------------------------------------------
    protected String getActionText()
    {
        return "** Checkout a Clothing Item **";
    }

    protected void getEntryTableModelValues()
    {
        ObservableList<InventoryTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            InventoryItemCollection inventoryItemCollection =
                    (InventoryItemCollection)myModel.getState("InventoryList");

            System.out.println("HEYOOO");

            System.out.println(inventoryItemCollection.toString());

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
        catch (Exception e) {//SQLException e) {
            // Need to handle this exception
        }
    }

    // Create the title container
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

        Text actionText = new Text("     " + getActionText() + "       ");
        actionText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        actionText.setWrappingWidth(350);
        actionText.setTextAlignment(TextAlignment.CENTER);
        actionText.setFill(Color.BLACK);
        container.getChildren().add(actionText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        Text prompt = new Text("ENTER RECIPIENT INFORMATION");
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

        Text netIdLabel = new Text(" Net ID : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        netIdLabel.setFont(myFont);
        netIdLabel.setWrappingWidth(150);
        netIdLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(netIdLabel, 0, 1);

        netId = new TextField();
        grid.add(netId, 1, 1);

        Text fNameLabel = new Text(" First Name : ");
        fNameLabel.setFont(myFont);
        fNameLabel.setWrappingWidth(150);
        fNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(fNameLabel, 0, 2);

        fName = new TextField();
        grid.add(fName, 1, 2);

        Text lNameLabel = new Text(" Last Name : ");
        lNameLabel.setFont(myFont);
        lNameLabel.setWrappingWidth(150);
        lNameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(lNameLabel, 0, 3);

        lName = new TextField();
        grid.add(lName, 1, 3);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnMouseEntered(me ->
        {
        	submitButton.setScaleX(1.1);
        	submitButton.setScaleY(1.1);
        });

        submitButton.setOnMouseExited(me ->
        {
        	submitButton.setScaleX(1);
        	submitButton.setScaleY(1);
        });

        submitButton.setOnMousePressed(me ->
    {
    	submitButton.setScaleX(0.9);
    	submitButton.setScaleY(0.9);
    });
        submitButton.setOnMouseReleased(me ->
    {
    	submitButton.setScaleX(1.1);
    	submitButton.setScaleY(1.1);
    });
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e)
            {
                clearErrorMessage();
                Properties props = new Properties();
                String netIdReceiver = netId.getText();
                if (netIdReceiver.length() == 9)
                {
                    props.setProperty("ReceiverNetid", netIdReceiver);
                    String fNameReceiver = fName.getText();
                    if (fNameReceiver.length() > 0 && fNameReceiver.length() < 36)
                    {
                        props.setProperty("ReceiverFirstName", fNameReceiver);
                        String lNameReceiver = lName.getText();
                        if (lNameReceiver.length() > 0 && lNameReceiver.length() < 36)
                        {
                            props.setProperty("ReceiverLastName", lNameReceiver);
                            myModel.stateChangeRequest("ReceiverData", props);
                        }
                        else
                        {
                            displayErrorMessage("ERROR: Last name incorrect size!");
                        }
                    }
                    else
                    {
                        displayErrorMessage("ERROR: First name incorrect size!");
                    }

                }
                else
                {
                    displayErrorMessage("ERROR: NetId Incorrect Size!");

                }

            }
        });
        doneCont.getChildren().add(submitButton);

        cancelButton = new Button("Return");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnMouseEntered(me ->
   	        {
   	        	cancelButton.setScaleX(1.1);
   	        	cancelButton.setScaleY(1.1);
   	        });

   	        cancelButton.setOnMouseExited(me ->
   	        {
   	        	cancelButton.setScaleX(1);
   	        	cancelButton.setScaleY(1);
   	        });

   	        cancelButton.setOnMousePressed(me ->
   	    {
   	    	cancelButton.setScaleX(0.9);
   	    	cancelButton.setScaleY(0.9);
   	    });
   	        cancelButton.setOnMouseReleased(me ->
   	    {
   	    	cancelButton.setScaleX(1.1);
   	    	cancelButton.setScaleY(1.1);
   	    });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelCheckoutCI", null);
            }
        });
        doneCont.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(scrollPane);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
            getEntryTableModelValues();
    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearErrorMessage();

        if (key.equals("TransactionError") == true)
        {
            String val = (String)value;
            if (val.startsWith("ERR") == true)
            {
                displayErrorMessage(val);
            }
            else
            {
                displayMessage(val);
            }

        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

}

//---------------------------------------------------------------
//	Revision History:
//
