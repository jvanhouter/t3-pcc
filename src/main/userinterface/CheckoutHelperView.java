package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Properties;

// project imports
import impresario.IModel;

public class CheckoutHelperView extends View {

    // GUI components

    protected Button addAnotherBarcodeButton;
    protected Button enterReceiverInformationButton;
    protected Button cancelButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public CheckoutHelperView(IModel at)
    {
        super(at, "CheckoutHelperView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);
    }

    //-------------------------------------------------------------
    protected String getActionText()
    {
        return "** Success! **";
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
        actionText.setFill(Color.BLUE);
        container.getChildren().add(actionText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        Text prompt = new Text("Clothing Item added to checkout list!");
        prompt.setWrappingWidth(400);
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
        addAnotherBarcodeButton = new Button("Add Another Barcode");
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

        enterReceiverInformationButton = new Button("Checkout Items");
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
        cancelButton = new Button("Cancel");
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
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {

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
