package userinterface;

// system imports
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.StringBuilder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Optional;

// project imports
import impresario.IModel;
import model.ArticleType;
import model.ArticleTypeCollection;
//import model.Item;
import model.ClothingItem;
import model.InventoryItemCollection;

//==============================================================================
public class InventoryItemCollectionView extends View
{
    protected TableView<InventoryTableModel> InventoryTable;
    protected Button cancelButton;
    protected Button outputButton;

    protected MessageView statusLog;
//    protected Stage stage; //Added as part of Excel work


    //--------------------------------------------------------------------------
    public InventoryItemCollectionView(IModel inv)
    {
        super(inv, "InventoryItemCollectionView");

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


    //--------------------------------------------------------------------------
    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues()
    {
        ObservableList<InventoryTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            InventoryItemCollection inventoryItemCollection =
                    (InventoryItemCollection)myModel.getState("InventoryList");

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

    @Override
    protected String getActionText() {
        return "** Available Inventory **";
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        Text prompt = new Text("");
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
        scrollPane.setPrefSize(1000, 300);
        scrollPane.setContent(InventoryTable);

        cancelButton = new PccButton("Return");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(e -> {
            /**
             * Process the Cancel button.
             * The ultimate result of this action is that the transaction will tell the Receptionist to
             * to switch to the Receptionist view. BUT THAT IS NOT THIS VIEW'S CONCERN.
             * It simply tells its model (controller) that the transaction was canceled, and leaves it
             * to the model to decide to tell the Receptionist to do the switch back.
             */
            //----------------------------------------------------------
            clearErrorMessage();
            myModel.stateChangeRequest("CancelInventory", null);
        });

        outputButton = new PccButton("Save to File");
        outputButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        outputButton.setOnAction(e -> {
            /**
             * This sends the current dataset out to a CSV file
             */
            clearErrorMessage();
            saveToExcelFile();
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);

        btnContainer.getChildren().add(outputButton);
        btnContainer.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(scrollPane);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }

    //--------------------------------------------------------------------------
    public void updateState(String key, Object value)
    {

    }
    //--------------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
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

    //Added stuff for Output to Excel
    //----------------------------------------------------------
    //--------------------------------------------------------------------------
    protected void saveToExcelFile() {
        // Put up JFileChooser
        // Retrieve full path name of file user selects
        // Create the file appropriately if it does not exist
        String reportsPath = System.getProperty("user.dir");// + "/reports";
        File reportsDir = new File(reportsPath);



        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(reportsDir);

        FileChooser.ExtensionFilter filter =
                new FileChooser.ExtensionFilter(
                        "CSV - (.cvs)", "*.cvs");

        chooser.setSelectedExtensionFilter(filter);

        File file = chooser.showSaveDialog(MainStageContainer.getInstance());

        try {
            String fileName = "";


            try {
                fileName = file.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String tempName = fileName.toLowerCase();

            if (tempName.endsWith(".csv")) {
                writeToFile(fileName);
            } else {
                fileName += ".csv";
                writeToFile(fileName);
            }
        } catch (Exception ex) {
            ButtonType bar = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Error in saving to file: " + ex.toString(),
                    bar);

            alert.setTitle("ERROR");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    //-------------------------------------------------------------
    protected void writeToFile(String fName) {

//        Vector allColumnNames = new Vector();
//        ArticleTypeCollection articleTypeCollection =
//                (ArticleTypeCollection) myModel.getState("ArticleTypeList");
//        Vector clothingVector = (Vector) articleTypeCollection.getState("ArticleTypes");

        try {
            FileWriter outFile = new FileWriter(fName);
            PrintWriter out = new PrintWriter(outFile);

//            if ((clothingVector == null) || (clothingVector.size() == 0))
//                return;

            //This is hardcoded to the Inventory Table model - we need to find a way to
            // dynamically change this string
            out.println("Barcode, Gender, Size, Article Type, Color 1, Color 2,"+
                    "Date Donated, Donor First Name, Donor Last Name, Donor Email," +
                    "Donor Phone,");

            //Added from above - gets the collection from InventoryItemCollection
            InventoryItemCollection inventoryItemCollection =
                    (InventoryItemCollection)myModel.getState("InventoryList");

            Vector entryList = (Vector)inventoryItemCollection.getState("InventoryItems");

            StringBuilder valuesLine = new StringBuilder();

            if (entryList.size() > 0)
            {
                Enumeration entries = entryList.elements();

                while (entries.hasMoreElements() == true)
                {
                    ClothingItem nextCI = (ClothingItem)entries.nextElement();
                    Vector<String> view = nextCI.getEntryListView();

                    // add this list entry to the list
                    InventoryTableModel nextTableRowData = new InventoryTableModel(view);
                    valuesLine.append(nextTableRowData.getBarcode() + ", " +
                            nextTableRowData.getGender() + ", " +
                            nextTableRowData.getSize() + ", " +
                            nextTableRowData.getArticleType() + ", " +
                            nextTableRowData.getColor1() + ", " +
                            nextTableRowData.getColor2() + ", " +
                            nextTableRowData.getDateDonated() + ", " +
                            nextTableRowData.getDonorFirstName() + ", " +
                            nextTableRowData.getDonorLastName() + ", " +
                            nextTableRowData.getDonorEmail() + ", " +
                            nextTableRowData.getDonorPhone() + "\n");
                }
            }
            else
            {
                displayMessage("No matching entries found!");
            }

            //Output the string
            out.println(valuesLine.toString());

            // Also print the item count and filter type (eventually)
            out.println("\nTotal number of : " + entryList.size());

            // Finally, print the time-stamp
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            DateFormat timeFormat = new SimpleDateFormat("hh:mm aaa");
            Date date = new Date();
            String timeStamp = dateFormat.format(date) + " " +
                    timeFormat.format(date);

            out.println("Report created on " + timeStamp);
            out.close();


            // Acknowledge successful completion to user with Alert

            ButtonType bar = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Report data saved successfully to selected file",
                    bar);

            alert.setTitle("Save Successful!");
            Optional<ButtonType> result = alert.showAndWait();

        } catch (FileNotFoundException e) {
            ButtonType bar = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Could not access file to save: " + fName,
                    bar);

            alert.setTitle("Save Error");
            Optional<ButtonType> result = alert.showAndWait();

        } catch (IOException e) {
            ButtonType bar = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Error in saving to file: " + e.toString(),
                    bar);

            alert.setTitle("Save Error");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }
}
