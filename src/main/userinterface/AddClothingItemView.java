// specify the package
package userinterface;

// system imports

import Utilities.UiConstants;
import Utilities.Utilities;
import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.StringConverter;
import model.ArticleType;
import model.Color;

import java.util.*;
import java.util.regex.Pattern;

//import javafx.scene.paint.Color;

// project imports

/**
 * The class containing the Add Clothing Item View  for the Professional Clothes
 * Closet application
 */
//==============================================================
public class AddClothingItemView extends View {

    // For showing error message
    protected MessageView statusLog;
    // GUI components
    protected ComboBox<String> genderCombo;
    protected TextField barcode;

    protected TextField sizeText;
    protected ComboBox<ArticleType> articleTypeCombo;
    protected ComboBox<Color> primaryColorCombo;
    protected ComboBox<Color> secondaryColorCombo;
    protected TextField brandText;
    protected TextField notesText;
    protected TextField donorLastNameText;
    protected TextField donorFirstNameText;
    protected TextField donorPhoneText;
    protected TextField donorEmailText;
    private PccButton submitButton;
    private PccButton cancelButton;

    // constructor for this class -- takes a model object
    public AddClothingItemView(IModel clothingItem) {
        super(clothingItem, "AddClothingItemView");

        // create a container for showing the contents
        VBox container = getParentContainer();
        container.setAlignment(Pos.CENTER);
        // Add a title for this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createPrompt());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);
    }

    @Override
    protected String getActionText() {
        return "Clothing Item Information";
    }
    private VBox createPrompt() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        PccText prompt = new PccText("Please Input Clothing Item Information:");
        prompt.setWrappingWidth(WRAPPING_WIDTH);
        prompt.setTextAlignment(TextAlignment.CENTER);
//        prompt.setFill(javafx.scene.paint.Color.BLUE);
        prompt.setFont(Font.font(APP_FONT, 20));
        vbox.getChildren().add(prompt);

        return vbox;
      }

    // Create the main form content
    private VBox createFormContent() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);


        Font myFont = Font.font(APP_FONT, 16);


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 25, 20, 0));

        // Barcode
        PccText barcodeLabel = new PccText(" Barcode : ");
        barcodeLabel.setFont(myFont);
        barcodeLabel.setWrappingWidth(150);
        barcodeLabel.setTextAlignment(TextAlignment.RIGHT);

        barcode = new TextField();

        grid.add(barcodeLabel, 0, 0);
        grid.add(barcode, 1, 0);

        // Gender UI items ==================================================
        PccText genderLabel = new PccText(" Gender : ");
        genderLabel.setFont(myFont);
        genderLabel.setWrappingWidth(150);
        genderLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(genderLabel, 0, 1);

        genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Mens", "Womens", "Unisex");


        grid.add(genderCombo, 1, 1);

        // Size UI Items ===================================================
        PccText sizeLabel = new PccText(" Size : ");
        sizeLabel.setFont(myFont);
        sizeLabel.setWrappingWidth(150);
        sizeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(sizeLabel, 0, 2);

        sizeText = new TextField();
        sizeText.setOnAction(this::processAction);
        grid.add(sizeText, 1, 2);

        // =================================================================
        // Article Type UI Items ===========================================
        PccText articleTypeLabel = new PccText(" Article Type : ");
        articleTypeLabel.setFont(myFont);
        articleTypeLabel.setWrappingWidth(150);
        articleTypeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(articleTypeLabel, 0, 3);

        articleTypeCombo = new ComboBox<>();
        articleTypeCombo.setConverter(new StringConverter<ArticleType>() {
            @Override
            public String toString(ArticleType object) {
                return (String) object.getState("Description");
            }

            @Override
            public ArticleType fromString(String string) {
                return articleTypeCombo.getItems().stream().filter(at ->
                        at.getState("Description").equals(string)).findFirst().orElse(null);
            }
        });
        articleTypeCombo.valueProperty().addListener((obs, oldval, newval) -> {
            /*if (newval != null) {
                System.out.println("Selected article type: " + newval.getState("Description")
                        + " ID: " + newval.getState("ID"));
            }*/
        });

        grid.add(articleTypeCombo, 1, 3);
        // =================================================================
        // Primary Color UI Items ==========================================
        PccText primaryColorLabel = new PccText(" Primary Color : ");
        primaryColorLabel.setFont(myFont);
        primaryColorLabel.setWrappingWidth(150);
        primaryColorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(primaryColorLabel, 0, 4);

        primaryColorCombo = new ComboBox<>();
        primaryColorCombo.setConverter(new StringConverter<Color>() {
            @Override
            public String toString(Color object) {
                return (String) object.getState("Description");
            }

            @Override
            public Color fromString(String string) {
                return primaryColorCombo.getItems().stream().filter(ct ->
                        ct.getState("Description").equals(string)).findFirst().orElse(null);
            }
        });
        grid.add(primaryColorCombo, 1, 4);
        // =================================================================
        // Secondary Color UI Items ========================================
        PccText secondaryColorLabel = new PccText(" Secondary Color : ");
        secondaryColorLabel.setFont(myFont);
        secondaryColorLabel.setWrappingWidth(150);
        secondaryColorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(secondaryColorLabel, 0, 5);

        secondaryColorCombo = new ComboBox<>();
        secondaryColorCombo.setConverter(new StringConverter<Color>() {
            @Override
            public String toString(Color object) {
                return (String) object.getState("Description");
            }

            @Override
            public Color fromString(String string) {
                return secondaryColorCombo.getItems().stream().filter(ct ->
                        ct.getState("Description").equals(string)).findFirst().orElse(null);
            }
        });
        grid.add(secondaryColorCombo, 1, 5);
        // =================================================================
        // Brand UI Items ==================================================
        PccText brandLabel = new PccText(" Brand : ");
        brandLabel.setFont(myFont);
        brandLabel.setWrappingWidth(150);
        brandLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(brandLabel, 2, 0);

        brandText = new TextField();
        brandText.setOnAction(this::processAction);
        grid.add(brandText, 3, 0);

        // =================================================================
        // Notes UI Items ==================================================
        PccText notesLabel = new PccText(" Notes : ");
        notesLabel.setFont(myFont);
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 2, 1);

        notesText = new TextField();
        notesText.setOnAction(this::processAction);
        grid.add(notesText, 3, 1);

        // =================================================================
        // Donor UI Items ==================================================
        PccText donorFirstNameLabel = new PccText(" Donor First Name : ");
        PccText donorLastNameLabel = new PccText(" Donor Last Name : ");
        PccText donorPhoneLabel = new PccText(" Donor Phone Number : ");
        PccText donorEmailLabel = new PccText(" Donor E-Mail : ");

        donorFirstNameLabel.setFont(myFont);
        donorLastNameLabel.setFont(myFont);
        donorPhoneLabel.setFont(myFont);
        donorEmailLabel.setFont(myFont);

        donorFirstNameLabel.setWrappingWidth(150);
        donorLastNameLabel.setWrappingWidth(150);
        donorPhoneLabel.setWrappingWidth(150);
        donorEmailLabel.setWrappingWidth(150);

        donorFirstNameLabel.setTextAlignment(TextAlignment.RIGHT);
        donorLastNameLabel.setTextAlignment(TextAlignment.RIGHT);
        donorPhoneLabel.setTextAlignment(TextAlignment.RIGHT);
        donorEmailLabel.setTextAlignment(TextAlignment.RIGHT);

        grid.add(donorFirstNameLabel, 2, 2);
        grid.add(donorLastNameLabel, 2, 3);
        grid.add(donorPhoneLabel, 2, 4);
        grid.add(donorEmailLabel, 2, 5);

        donorFirstNameText = new TextField();
        donorFirstNameText.setOnAction(this::processAction);
        donorLastNameText = new TextField();
        donorLastNameText.setOnAction(this::processAction);
        donorPhoneText = new TextField();
        donorPhoneText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9-]{0,12}")) {
                donorPhoneText.setText(oldValue);
            }
            donorPhoneText.setText(Utilities.autoFillDashes(donorPhoneText.getText()));
        });
        donorPhoneText.setOnAction(this::processAction);
        donorEmailText = new TextField();
        donorEmailText.setOnAction(this::processAction);

        grid.add(donorFirstNameText, 3, 2);
        grid.add(donorLastNameText, 3, 3);
        grid.add(donorPhoneText, 3, 4);
        grid.add(donorEmailText, 3, 5);
        // =================================================================
        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        submitButton = new PccButton("Submit");
        submitButton.setOnAction(this::processAction);
        doneCont.getChildren().add(submitButton);

        cancelButton = new PccButton("Return");
        cancelButton.setOnAction(e -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelAddClothingItem", null);
        });
        doneCont.getChildren().add(cancelButton);

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
        clearErrorMessage();
        barcode.setText((String) myModel.getState("Barcode"));
        barcode.setDisable(true);

        genderCombo.setValue((String) myModel.getState("Gender"));
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
        articleTypeCombo.setItems(articleTypes);

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
        primaryColorCombo.setItems(colorItems);
        secondaryColorCombo.setItems(colorItems);
    }

    private void processAction(ActionEvent e) {
        clearErrorMessage();
        Properties props = new Properties();
        String gender = genderCombo.getValue();
        String size = sizeText.getText();
        ArticleType articleType = articleTypeCombo.getValue(); //.getState("ID");
        Color color1 = primaryColorCombo.getValue(); //.getState("ID");
        Color color2 = secondaryColorCombo.getValue();
        String brand = brandText.getText();
        String notes = notesText.getText();
        String donorFirstName = donorFirstNameText.getText();
        String donorLastName = donorLastNameText.getText();
        String donorPhone = donorPhoneText.getText();
        String donorEmail = donorEmailText.getText();

        Pattern emailValidation = Pattern.compile("^([\\w \\._]+\\<[a-z0-9!#$%&'*+/=?^_`{|}~-]+" +
                "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a" +
                "-z0-9](?:[a-z0-9-]*[a-z0-9])?\\>|[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%" +
                "&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-" +
                "]*[a-z0-9])?)$");
// TODO: Improve UI validation
        if (gender.length() == 0) {
            displayErrorMessage("ERROR: Please select gender!");
            genderCombo.requestFocus();
        }/* else if (size.length() == 0) {
            displayErrorMessage("ERROR: Please enter a size!");
            sizeText.requestFocus();
        } */ else if (articleType == null) {
            displayErrorMessage("ERROR: Please select an article type!");
            articleTypeCombo.requestFocus();
        } else if (color1 == null) {
            displayErrorMessage("ERROR: Please select a primary color!");
            primaryColorCombo.requestFocus();
        } else if ((donorEmail.length() > 0) &&
                (!emailValidation.matcher(donorEmail.toLowerCase()).matches())) {
            displayErrorMessage("Email not in name@address.domain format!");
            donorEmailText.requestFocus();
        } else {
            props.setProperty("Gender", gender);
            if (size.length() == 0)
                props.setProperty("Size", "" + UiConstants.GENERIC_SIZE);
            else
                props.setProperty("Size", size);
            props.setProperty("ArticleType", (String) articleType.getState("ID"));
            props.setProperty("Color1", (String) color1.getState("ID"));
            if (color2 != null) {
                props.setProperty("Color2", (String) color2.getState("ID"));
            } else {
                props.setProperty("Color2", "0");
            }
            props.setProperty("Brand", brand);
            props.setProperty("Notes", notes);
            props.setProperty("DonorFirstName", donorFirstName);
            props.setProperty("DonorLastName", donorLastName);
            props.setProperty("DonorPhone", donorPhone);
            props.setProperty("DonorEmail", donorEmail);
            myModel.stateChangeRequest("ClothingItemData", props);
            myModel.stateChangeRequest("OK", null);
        }
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
            } else if (val.length() > 0) {
                displayMessage(val);
                myModel.stateChangeRequest("CancelAddClothingItem", null);
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
