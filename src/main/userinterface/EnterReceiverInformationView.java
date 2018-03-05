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

public class EnterReceiverInformationView extends View {

    // GUI components
    protected TextField netId;
    protected TextField fName;
    protected TextField lName;

    protected Button submitButton;
    protected Button cancelButton;

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

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);
    }

    //-------------------------------------------------------------
    protected String getActionText()
    {
        return "** Checkout a Clothing Item **";
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

        Text lNameLabel = new Text(" Alpha Code : ");
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
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                Properties props = new Properties();
                String netIdReceiver = netId.getText();
                if (netIdReceiver.length() > 0)
                {
                    props.setProperty("ReceiverNetid", netIdReceiver);
                    String fNameReceiver = fName.getText();
                    if (fNameReceiver.length() > 0)
                    {
                        props.setProperty("ReceiverFirstName", fNameReceiver);
                        String lNameReceiver = lName.getText();
                        if (lNameReceiver.length() > 0)
                        {
                            props.setProperty("ReceiverLastName", lNameReceiver);
                            myModel.stateChangeRequest("ReceiverData", props);
                        }
                        else
                        {
                            displayErrorMessage("ERROR: Please enter a valid lastname!");
                        }
                    }
                    else
                    {
                        displayErrorMessage("ERROR: Please enter a firstname!");
                    }

                }
                else
                {
                    displayErrorMessage("ERROR: Please enter a valid netid!");

                }

            }
        });
        doneCont.getChildren().add(submitButton);

        cancelButton = new Button("Return");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                //TODO "CancelCheckoutItem" MAY BE DEPENDANT ON CONTROLLER AND THUS SUBJECT TO CHANGE OR FUTURE USE.
                myModel.stateChangeRequest("CancelCheckoutCI", null);
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
//