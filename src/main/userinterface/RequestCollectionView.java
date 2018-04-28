package userinterface;

// system imports

import Utilities.UiConstants;
import Utilities.Utilities;
import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import model.ClothingRequest;
import model.RequestCollection;

import java.util.Enumeration;
import java.util.Vector;

// project imports
//We need to look at these - can we avoid the outside library, and
// slim down the model.* to just the classes we need? - JVH


//==============================================================================
public class RequestCollectionView extends View {
    protected TableView<RequestTableModel> tableOfRequests;
    protected PccButton cancelButton;
    protected PccButton submitButton;

    protected MessageView statusLog;


    //--------------------------------------------------------------------------
    public RequestCollectionView(IModel matt) {
        super(matt, "RequestCollectionView");

        // create a container for showing the contents
        VBox container = getParentContainer();

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));

        getChildren().add(container);

        populateFields();
    }

    //--------------------------------------------------------------------------
    protected void populateFields() {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    private void refactor(RequestTableModel ctm) {

        try {
            ctm.setArticleType((String) Utilities.collectArticleTypeHash().get(ctm.getArticleType()).getState("Description"));
        } catch (Exception e) {
            ctm.setArticleType("Clothing Item Removed");
        }
        try {
            if (Utilities.collectColorHash().get(ctm.getColor1()) != null)
                ctm.setColor1((String) Utilities.collectColorHash().get(ctm.getColor1()).getState("Description"));
            else
                ctm.setColor1("");
        } catch (Exception e) {
            ctm.setColor1("Color Removed");
        }
        try {
            if (Utilities.collectColorHash().get(ctm.getColor2()) != null)
                ctm.setColor2((String) Utilities.collectColorHash().get(ctm.getColor2()).getState("Description"));
            else
                ctm.setColor2("");
        } catch (Exception e) {
            ctm.setColor2("Color Removed");
        }
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues() {
        ObservableList<RequestTableModel> tableData = FXCollections.observableArrayList();
        try {
            RequestCollection requestCollection =
                    (RequestCollection) myModel.getState("RequestList");

            Vector entryList = (Vector) requestCollection.getState("Requests");

            if (entryList.size() > 0) {
                Enumeration entries = entryList.elements();

                while (entries.hasMoreElements()) {
                    ClothingRequest nextRQ = (ClothingRequest) entries.nextElement();
                    Vector<String> view = nextRQ.getEntryListView();

                    // add this list entry to the list
                    RequestTableModel nextTableRowData = new RequestTableModel(view);
                    if (nextTableRowData.getSize().equals("" + UiConstants.GENERIC_SIZE))
                        nextTableRowData.setSize("");
                    refactor(nextTableRowData);
                    tableData.add(nextTableRowData);

                }
            } else {
                displayMessage("No matching entries found!");
            }

            tableOfRequests.setItems(tableData);
        } catch (Exception e) {//SQLException e) {
            // Need to handle this exception
            e.printStackTrace();
        }
    }

    @Override
    protected String getActionText() {
        return "** Matching Requests **";
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent() {
        VBox vbox = new VBox(10);

        PccText prompt = new PccText("");
        prompt.setWrappingWidth(WRAPPING_WIDTH);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.web(APP_TEXT_COLOR));
        prompt.setFont(Font.font(APP_FONT, FontWeight.BOLD, 18));
        vbox.getChildren().add(prompt);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 25, 10, 0));

        tableOfRequests = new TableView<>();
        tableOfRequests.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn id = new TableColumn("ID");
        id.setMaxWidth(50);
        id.setCellValueFactory(
                new PropertyValueFactory<RequestTableModel, String>("id"));

        TableColumn requesterNetid = new TableColumn("Net ID");
        requesterNetid.setMaxWidth(100);
        requesterNetid.setCellValueFactory(
                new PropertyValueFactory<RequestTableModel, String>("requesterNetid"));

        TableColumn phone = new TableColumn("Phone");
        phone.setMaxWidth(100);
        phone.setCellValueFactory(
                new PropertyValueFactory<RequestTableModel, String>("phone"));

        TableColumn email = new TableColumn("Email Address");
        email.setMaxWidth(100);
        email.setCellValueFactory(
                new PropertyValueFactory<RequestTableModel, String>("email"));

        TableColumn lastName = new TableColumn("Last Name");
        lastName.setMaxWidth(100);
        lastName.setCellValueFactory(
                new PropertyValueFactory<RequestTableModel, String>("lastName"));

        TableColumn firstName = new TableColumn("First Name");
        firstName.setMaxWidth(100);
        firstName.setCellValueFactory(
                new PropertyValueFactory<RequestTableModel, String>("firstName"));

        TableColumn gender = new TableColumn("Gender");
        gender.setMaxWidth(100);
        gender.setCellValueFactory(
                new PropertyValueFactory<RequestTableModel, String>("gender"));

        TableColumn articleType = new TableColumn("Article Type");
        articleType.setMaxWidth(100);
        articleType.setCellValueFactory(
                new PropertyValueFactory<RequestTableModel, String>("articleType"));

        TableColumn color1 = new TableColumn("Color 1");
        color1.setMaxWidth(100);
        color1.setCellValueFactory(
                new PropertyValueFactory<RequestTableModel, String>("color1"));

        TableColumn color2 = new TableColumn("Color 2");
        color2.setMaxWidth(100);
        color2.setCellValueFactory(
                new PropertyValueFactory<RequestTableModel, String>("color2"));

        TableColumn size = new TableColumn("Size");
        size.setMaxWidth(100);
        size.setCellValueFactory(
                new PropertyValueFactory<RequestTableModel, String>("size"));

        TableColumn brand = new TableColumn("Brand");
        brand.setMaxWidth(100);
        brand.setCellValueFactory(
                new PropertyValueFactory<RequestTableModel, String>("brand"));

        TableColumn requestMadeDate = new TableColumn("Made Date");
        requestMadeDate.setMaxWidth(100);
        requestMadeDate.setCellValueFactory(
                new PropertyValueFactory<RequestTableModel, String>("requestMadeDate"));

        TableColumn statusColumn = new TableColumn("Status");
        statusColumn.setMinWidth(50);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<ArticleTypeTableModel, String>("status"));

        tableOfRequests.getColumns().addAll(id,
                requesterNetid, phone, email, lastName, firstName,
                gender, articleType, color1, color2, size, brand,
                requestMadeDate, statusColumn);

        tableOfRequests.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() >= 2) {
                processRequestSelected();
            }
        });
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(150, 150);
        scrollPane.setContent(tableOfRequests);

        submitButton = new PccButton("Submit");

        submitButton.setOnAction(e -> {
            clearErrorMessage();
            // do the inquiry
            processRequestSelected();

        });

        cancelButton = new PccButton("Return");

        cancelButton.setOnAction(e -> {
            //----------------------------------------------------------
            clearErrorMessage();
            myModel.stateChangeRequest("CancelRequest", null);
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(submitButton);
        btnContainer.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(scrollPane);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }

    //--------------------------------------------------------------------------
    public void updateState(String key, Object value) {
    }

    //--------------------------------------------------------------------------
    protected void processRequestSelected() {
        RequestTableModel selectedItem = tableOfRequests.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            String id = selectedItem.getId();
            myModel.stateChangeRequest("RequestSelected", id);
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
