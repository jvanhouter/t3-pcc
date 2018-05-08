// specify the package
package userinterface;

// system imports
import Utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.*;
import java.util.regex.Pattern;

// project imports
import impresario.IModel;
import javafx.util.StringConverter;
import model.ArticleType;
import model.Color;
import Utilities.UiConstants;

// WHERE does this come from??? - JVH
//import javax.rmi.CORBA.Util;

/** The class containing the Add Article Type View  for the Professional Clothes
 *  Closet application
 */
//==============================================================
public class LogARequestView extends View
{

    // GUI components
    protected TextField netId;
    protected TextField phoneNumber;
    protected TextField email;
    protected TextField lastName;
    protected TextField firstName;
    protected ComboBox<String> gender;
    protected ComboBox<ArticleType> articleType;
    protected ComboBox<Color> color1;
    protected ComboBox<Color> color2;
    protected TextField brand;
    protected TextField size;

    protected PccButton submitButton;
    protected PccButton cancelButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object

    public LogARequestView(IModel lv)
    {
        super(lv, "LogARequestView");

        // create a container for showing the contents
        VBox container = getParentContainer();
        container.setAlignment(Pos.CENTER);

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createPrompt());
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);
    }


    @Override
    protected String getActionText()
    {
        return " \"Log a Request\" ";
    }

    // Create the main form content


    private VBox createPrompt()
    {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        PccText prompt = new PccText("LOG REQUEST INFORMATION");
        prompt.setWrappingWidth(WRAPPING_WIDTH);
        prompt.setTextAlignment(TextAlignment.CENTER);
  //        prompt.setFill(javafx.scene.paint.Color.BLACK);
        prompt.setFont(Font.font(APP_FONT, FontWeight.BOLD, 18));
        vbox.getChildren().add(prompt);
        return vbox;
      }

    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 25, 10, 0));

        Font myFont = Font.font(APP_FONT, FontWeight.BOLD, 12);

        PccText myNetId = new PccText(" Net ID : ");
        myNetId.setFont(myFont);
        myNetId.setWrappingWidth(150);
        myNetId.setTextAlignment(TextAlignment.RIGHT);
        grid.add(myNetId, 0, 1);

        netId = new TextField();
        grid.add(netId, 1, 1);

        PccText myFirstName = new PccText(" First Name : ");
        myFirstName.setFont(myFont);
        myFirstName.setWrappingWidth(150);
        myFirstName.setTextAlignment(TextAlignment.RIGHT);
        grid.add(myFirstName, 0, 2);

        firstName = new TextField();
        grid.add(firstName, 1, 2);

        PccText myLastName = new PccText(" Last Name :");
        myLastName.setFont(myFont);
        myLastName.setWrappingWidth(150);
        myLastName.setTextAlignment(TextAlignment.RIGHT);
        grid.add(myLastName, 0, 3);

        lastName = new TextField();
        grid.add(lastName, 1, 3);

        PccText myPhone = new PccText(" Phone Number : ");
        myPhone.setFont(myFont);
        myPhone.setWrappingWidth(150);
        myPhone.setTextAlignment(TextAlignment.RIGHT);
        grid.add(myPhone, 0, 4);

        phoneNumber = new TextField();
        phoneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9-]{0,12}")) {
                phoneNumber.setText(oldValue);
            }
            phoneNumber.setText(Utilities.autoFillDashes(phoneNumber.getText()));
        });
        grid.add(phoneNumber, 1, 4);

        PccText myEmail = new PccText(" Email Address : ");
        myEmail.setFont(myFont);
        myEmail.setWrappingWidth(150);
        myEmail.setTextAlignment(TextAlignment.RIGHT);
        grid.add(myEmail, 0, 5);

        email = new TextField();
        grid.add(email, 1, 5);

        email.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

            }
        });

        PccText myGender = new PccText(" Gender : ");
        myGender.setFont(myFont);
        myGender.setWrappingWidth(150);
        myGender.setTextAlignment(TextAlignment.RIGHT);
        grid.add(myGender, 0, 6);

        gender = new ComboBox<String>();
        grid.add(gender, 1, 6);
        gender.getItems().addAll("Mens", "Womens", "Unisex");
        gender.setValue("Mens");

        // Article Type UI Items ===========================================
        PccText articleTypeLabel = new PccText(" Article Type : ");
        articleTypeLabel.setFont(myFont);
        articleTypeLabel.setWrappingWidth(150);
        articleTypeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(articleTypeLabel, 2, 1);

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
        });

        grid.add(articleType, 3, 1);
        // =================================================================
        // Primary Color UI Items ==========================================
        PccText primaryColorLabel = new PccText(" Primary Color : ");
        primaryColorLabel.setFont(myFont);
        primaryColorLabel.setWrappingWidth(150);
        primaryColorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(primaryColorLabel, 2, 2);

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
        grid.add(color1, 3, 2);
        // =================================================================
        // Secondary Color UI Items ========================================
        PccText secondaryColorLabel = new PccText(" Secondary Color : ");
        secondaryColorLabel.setFont(myFont);
        secondaryColorLabel.setWrappingWidth(150);
        secondaryColorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(secondaryColorLabel, 2, 3);

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
        grid.add(color2, 3, 3);
        // =================================================================
        // Brand UI Items ==================================================
        PccText brandLabel = new PccText(" Brand : ");
        brandLabel.setFont(myFont);
        brandLabel.setWrappingWidth(150);
        brandLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(brandLabel, 2, 4);

        brand = new TextField();
        //brand.setOnAction(this::processAction);
        grid.add(brand, 3, 4);

        // =================================================================
        // Brand UI Items ==================================================
        PccText mySize = new PccText(" Size : ");
        mySize.setFont(myFont);
        mySize.setWrappingWidth(150);
        mySize.setTextAlignment(TextAlignment.RIGHT);
        grid.add(mySize, 2, 5);

        size = new TextField();
        //brand.setOnAction(this::processAction);
        grid.add(size, 3, 5);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        submitButton = new PccButton("Submit");

        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processTransaction();
            }
        });
        doneCont.getChildren().add(submitButton);

        cancelButton = new PccButton("Return");

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


    protected void processTransaction() {
        Pattern emailValidation = Pattern.compile("^([\\w \\._]+\\<[a-z0-9!#$%&'*+/=?^_`{|}~-]+" +
                "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a" +
                "-z0-9](?:[a-z0-9-]*[a-z0-9])?\\>|[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%" +
                "&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-" +
                "]*[a-z0-9])?)$");
        Properties props = new Properties();
        if(netId.getText().length() > 0 && netId.getText().length() <= UiConstants.RECEIVER_NETID_MAX_LENGTH && netId.getText().substring(0, 1).matches("[A-Za-z]")) {
            props.setProperty("RequesterNetid", netId.getText());
            props.setProperty("RequestedGender", gender.getValue());
            props.setProperty("RequestedArticleType", (String) articleType.getValue().getState("ID"));
            props.setProperty("RequestedColor1", (String) color1.getValue().getState("ID"));
            if(color2.getValue() != null && (String) color2.getValue().getState("ID") != null)
                props.setProperty("RequestedColor2", (String) color2.getValue().getState("ID"));
            props.setProperty("RequestedBrand", brand.getText());
            if(size.getText().equals("")) {
                props.setProperty("RequestedSize", "" + UiConstants.GENERIC_SIZE);
            } else
                props.setProperty("RequestedSize", size.getText());
            if(phoneNumber.getText().length() <= UiConstants.REQUESTED_PHONE_MAX_LENGTH) {
                props.setProperty("RequesterPhone", phoneNumber.getText());
                if(!firstName.getText().equals("") && firstName.getText().length() <= UiConstants.REQUESTED_FIRST_NAME_MAX_LENGTH) {
                    props.setProperty("RequesterFirstName", firstName.getText());
                    if(!lastName.getText().equals("") && lastName.getText().length() <= UiConstants.REQUESTED_LAST_NAME_MAX_LENGTH) {
                        props.setProperty("RequesterLastName", lastName.getText());
                        if(email.getText().length() <= UiConstants.REQUESTED_EMAIL_MAX_LENGTH) {
                                props.setProperty("RequesterEmail", email.getText());
                            if(!email.getText().equals("") || !phoneNumber.getText().equals("")) {
                                if(!email.getText().equals("") && !(emailValidation.matcher(email.getText().toLowerCase()).matches())) {
                                    displayErrorMessage("Invalid email entered.");
                                    return;
                                }
                                myModel.stateChangeRequest("ClothingRequestData", props);
                                myModel.stateChangeRequest("OK", "");
                            } else
                                displayErrorMessage("Phone number or email must be filled out.");
                        } else
                            displayErrorMessage("Email exceeds " + UiConstants.REQUESTED_EMAIL_MAX_LENGTH + " characters.");
                    } else
                        displayErrorMessage("Please fill out Last Name.");
                } else
                    displayErrorMessage("Please fill out First Name.");
            } else
                displayErrorMessage("Phone number exceeds 12 digits.");
        } else
            displayErrorMessage("Invalid net id.");
    }

    // Create the status log field

    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }


    public void populateFields()
    {
        clearErrorMessage();
        gender.setValue((String) myModel.getState("Gender"));
        Iterator articles = Utilities.collectArticleTypeHash().entrySet().iterator();
        ObservableList<ArticleType> articleTypes = FXCollections.observableArrayList();
        Comparator<ArticleType> compareAT = new Comparator<ArticleType>() {
            @Override
            public int compare(ArticleType o1, ArticleType o2) {
                return ((String) o1.getState("Description")).compareToIgnoreCase(((String) o2.getState("Description")));
            }
        };
        while (articles.hasNext()) {
            Map.Entry pair = (Map.Entry)articles.next();
            articleTypes.add((ArticleType) pair.getValue());
        }
        articleTypes.sort(compareAT);
        articleType.setItems(articleTypes);
        articleType.getSelectionModel().select(0);

        Iterator colors = Utilities.collectColorHash().entrySet().iterator();
        ObservableList<Color> colorItems = FXCollections.observableArrayList();
        Comparator<Color> compareCT = new Comparator<Color>() {
            @Override
            public int compare(Color o1, Color o2) {
                return ((String) o1.getState("Description")).compareToIgnoreCase(((String) o2.getState("Description")));
            }
        };
        while (colors.hasNext()) {
            Map.Entry pair = (Map.Entry)colors.next();
            colorItems.add((Color) pair.getValue());
        }
        colorItems.sort(compareCT);
        color1.setItems(colorItems);
        color1.getSelectionModel().select(0);
        color2.setItems(colorItems);
        gender.getSelectionModel().select(0);
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
        //model.Alert alert = new model.Alert(Alert.AlertType.INFORMATION);
        //alert.displayErrorMessage(message);
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */

    public void displayMessage(String message)
    {
        //Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.displayMessage(message);
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
