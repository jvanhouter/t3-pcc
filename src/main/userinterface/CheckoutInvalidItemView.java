package userinterface;

// system imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

// project imports
import impresario.IModel;

public class CheckoutInvalidItemView extends View {

    // GUI components

    protected PccButton addAnotherBarcodeButton;
    protected PccButton enterReceiverInformationButton;
    protected PccButton cancelButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object

    public CheckoutInvalidItemView(IModel at)
    {
        super(at, "CheckoutInvalidItemView");

        // create a container for showing the contents
        VBox container = getParentContainer();

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        //The initial status message needs to be the current barcodes.
        String initialMessage = (String) myModel.getState("BarcodeError");
        container.getChildren().add(createStatusLog(""));
        displayErrorMessage(initialMessage);

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);
    }


    @Override
    protected String getActionText()
    {
        return "** ERROR **";
    }

    // Create the main form content

    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        Text prompt = new Text("The Clothing Item associated with this barcode can not be checked out.");
        prompt.setWrappingWidth(WRAPPING_WIDTH);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        prompt.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        vbox.getChildren().add(prompt);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 25, 10, 0));

        VBox doneCont = new VBox(10);
        doneCont.setAlignment(Pos.CENTER);
        addAnotherBarcodeButton = new PccButton("Add Different Barcode");
        addAnotherBarcodeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        addAnotherBarcodeButton.setPrefSize(250, 20);
        addAnotherBarcodeButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();

                myModel.stateChangeRequest("MoreData", null);

            }
        });
        doneCont.getChildren().add(addAnotherBarcodeButton);

        enterReceiverInformationButton = new PccButton("Checkout Items");
        enterReceiverInformationButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        enterReceiverInformationButton.setPrefSize(250, 20);
        enterReceiverInformationButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("NoMoreData", null);
            }
        });
        doneCont.getChildren().add(enterReceiverInformationButton);


        doneCont.setAlignment(Pos.CENTER);
        cancelButton = new PccButton("Cancel");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setPrefSize(250, 20);
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelBarcodeSearch", null);
            }
        });
        doneCont.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    // Create the status log field

    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }


    public void populateFields()
    {

    }

    /**
     * Update method
     */

    public void updateState(String key, Object value)
    {
        clearErrorMessage();

        if (key.equals("TransactionError") )
        {
            String val = (String)value;
            if (val.startsWith("ERR") )
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

    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */

    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */

    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

}


//	Revision History:
