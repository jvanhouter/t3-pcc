// specify the package
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

// project imports
import impresario.IModel;
import javafx.util.StringConverter;
import model.ArticleType;
import model.Color;

/** The class containing the Add Article Type View  for the Professional Clothes
 *  Closet application
 */
//==============================================================
public class LogARequestView extends View
{

    // GUI components
    protected TextField netId;
    protected TextField phoneNumber;
    protected TextField lastName;
    protected TextField firstName;
    protected ComboBox<String> gender;
    protected ComboBox<ArticleType> articleType;
    protected ComboBox<Color> color1;
    protected ComboBox<Color> color2;
    protected TextField brand;
    protected TextField size;

    protected Button submitButton;
    protected Button cancelButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public LogARequestView(IModel lv)
    {
        super(lv, "LogARequestView");

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
        return "** Log a request view **";
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
        clientText.setFill(javafx.scene.paint.Color.DARKGREEN);
        container.getChildren().add(clientText);

        Text collegeText = new Text(" THE COLLEGE AT BROCKPORT ");
        collegeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        collegeText.setWrappingWidth(350);
        collegeText.setTextAlignment(TextAlignment.CENTER);
        collegeText.setFill(javafx.scene.paint.Color.DARKGREEN);
        container.getChildren().add(collegeText);

        Text titleText = new Text(" Professional Clothes Closet Management System ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(350);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(javafx.scene.paint.Color.DARKGREEN);
        container.getChildren().add(titleText);

        Text blankText = new Text("  ");
        blankText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        blankText.setWrappingWidth(350);
        blankText.setTextAlignment(TextAlignment.CENTER);
        blankText.setFill(javafx.scene.paint.Color.WHITE);
        container.getChildren().add(blankText);

        Text actionText = new Text("     " + getActionText() + "       ");
        actionText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        actionText.setWrappingWidth(350);
        actionText.setTextAlignment(TextAlignment.CENTER);
        actionText.setFill(javafx.scene.paint.Color.BLACK);
        container.getChildren().add(actionText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        Text prompt = new Text("LOG REQUEST INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(javafx.scene.paint.Color.BLACK);
        prompt.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        vbox.getChildren().add(prompt);


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 25, 10, 0));

        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);

        Text myNetId = new Text(" Net ID : ");
        myNetId.setFont(myFont);
        myNetId.setWrappingWidth(150);
        myNetId.setTextAlignment(TextAlignment.RIGHT);
        grid.add(myNetId, 0, 1);

        netId = new TextField();
        grid.add(netId, 1, 1);

        Text myFirstName = new Text(" First Name : ");
        myFirstName.setFont(myFont);
        myFirstName.setWrappingWidth(150);
        myFirstName.setTextAlignment(TextAlignment.RIGHT);
        grid.add(myFirstName, 0, 2);

        firstName = new TextField();
        grid.add(firstName, 1, 2);

        Text myLastName = new Text(" Last Name :");
        myLastName.setFont(myFont);
        myLastName.setWrappingWidth(150);
        myLastName.setTextAlignment(TextAlignment.RIGHT);
        grid.add(myLastName, 0, 3);

        lastName = new TextField();
        grid.add(lastName, 1, 3);

        Text myPhone = new Text(" Phone Number : ");
        myPhone.setFont(myFont);
        myPhone.setWrappingWidth(150);
        myPhone.setTextAlignment(TextAlignment.RIGHT);
        grid.add(myPhone, 0, 4);

        phoneNumber = new TextField();
        grid.add(phoneNumber, 1, 4);

        phoneNumber.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

            }
        });

        Text myGender = new Text(" Gender : ");
        myGender.setFont(myFont);
        myGender.setWrappingWidth(150);
        myGender.setTextAlignment(TextAlignment.RIGHT);
        grid.add(myGender, 0, 5);

        gender = new ComboBox<String>();
        grid.add(gender, 1, 5);
        gender.getItems().addAll("Mens", "Womens");
        gender.setValue("Mens");

        // Article Type UI Items ===========================================
        Text articleTypeLabel = new Text(" Article Type : ");
        articleTypeLabel.setFont(myFont);
        articleTypeLabel.setWrappingWidth(150);
        articleTypeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(articleTypeLabel, 0, 6);

        articleType = new ComboBox<>();
        articleType.setConverter(new StringConverter<ArticleType>() {
            @Override
            public String toString(ArticleType object) {
                return (String) object.getState("Description");
            }

            @Override
            public ArticleType fromString(String string) {
                return articleType.getItems().stream().filter(at ->
                        at.getState("Description").equals(string)).findFirst().orElse(null);
            }
        });
        articleType.valueProperty().addListener((obs, oldval, newval) -> {
            /*if (newval != null) {
                System.out.println("Selected article type: " + newval.getState("Description")
                        + " ID: " + newval.getState("ID"));
            }*/
        });

        grid.add(articleType, 1, 6);
        // =================================================================
        // Primary Color UI Items ==========================================
        Text primaryColorLabel = new Text(" Primary Color : ");
        primaryColorLabel.setFont(myFont);
        primaryColorLabel.setWrappingWidth(150);
        primaryColorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(primaryColorLabel, 0, 7);

        color1 = new ComboBox<>();
        color1.setConverter(new StringConverter<Color>() {
            @Override
            public String toString(Color object) {
                return (String) object.getState("Description");
            }

            @Override
            public Color fromString(String string) {
                return color1.getItems().stream().filter(ct ->
                        ct.getState("Description").equals(string)).findFirst().orElse(null);
            }
        });
        grid.add(color1, 1, 7);
        // =================================================================
        // Secondary Color UI Items ========================================
        Text secondaryColorLabel = new Text(" Secondary Color : ");
        secondaryColorLabel.setFont(myFont);
        secondaryColorLabel.setWrappingWidth(150);
        secondaryColorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(secondaryColorLabel, 0, 8);

        color2 = new ComboBox<>();
        color2.setConverter(new StringConverter<Color>() {
            @Override
            public String toString(Color object) {
                return (String) object.getState("Description");
            }

            @Override
            public Color fromString(String string) {
                return color2.getItems().stream().filter(ct ->
                        ct.getState("Description").equals(string)).findFirst().orElse(null);
            }
        });
        grid.add(color2, 1, 8);
        // =================================================================
        // Brand UI Items ==================================================
        Text brandLabel = new Text(" Brand : ");
        brandLabel.setFont(myFont);
        brandLabel.setWrappingWidth(150);
        brandLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(brandLabel, 0, 9);

        brand = new TextField();
        //brand.setOnAction(this::processAction);
        grid.add(brand, 1, 9);

        // =================================================================
        // Brand UI Items ==================================================
        Text mySize = new Text(" Size : ");
        mySize.setFont(myFont);
        mySize.setWrappingWidth(150);
        mySize.setTextAlignment(TextAlignment.RIGHT);
        grid.add(mySize, 0, 10);

        size = new TextField();
        //brand.setOnAction(this::processAction);
        grid.add(size, 1, 10);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                Properties props = new Properties();
                props.setProperty("RequesterNetid", netId.getText());
                props.setProperty("RequestedGender", gender.getValue());
                props.setProperty("RequestedArticleType", (String) articleType.getValue().getState("ID"));
                props.setProperty("RequestedColor1", (String) color1.getValue().getState("ID"));
                props.setProperty("RequestedColor2", (String) color2.getValue().getState("ID"));
                props.setProperty("RequestedBrand", brand.getText());
                props.setProperty("RequestedSize", size.getText());
                props.setProperty("RequesterPhone", phoneNumber.getText());
                props.setProperty("RequesterFirstName", firstName.getText());
                props.setProperty("RequesterLastName", lastName.getText());
                myModel.stateChangeRequest("ClothingRequestData", props);
            }
        });
        doneCont.getChildren().add(submitButton);

        cancelButton = new Button("Return");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelLogRequest", null);
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
        clearErrorMessage();
        gender.setValue((String) myModel.getState("Gender"));
        Vector ArticleList = (Vector) myModel.getState("Articles");
        Iterator articles = ArticleList.iterator();
        ObservableList<ArticleType> articleTypes = FXCollections.observableArrayList();
        while (articles.hasNext()) {
            articleTypes.add((ArticleType) articles.next());
        }
        articleType.setItems(articleTypes);
        articleType.getSelectionModel().select(0);

        Vector ColorList = (Vector) myModel.getState("Colors");
        Iterator colors = ColorList.iterator();
        ObservableList<Color> colorItems = FXCollections.observableArrayList();
        while (colors.hasNext()) {
            colorItems.add((Color) colors.next());
        }
        color1.setItems(colorItems);
        color1.getSelectionModel().select(0);
        color2.setItems(colorItems);
        gender.getSelectionModel().select(0);
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


