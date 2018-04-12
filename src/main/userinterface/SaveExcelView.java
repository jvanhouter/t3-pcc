package userinterface;

// system imports

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
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
import model.ArticleType;
import model.ArticleTypeCollection;
import model.SaveExcelTransaction;

import java.awt.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

// project imports

//==============================================================================
public class SaveExcelView extends View {
    protected Button cancelButton;
    protected Button submitButton;

    protected MessageView statusLog;
    protected Dialog dialog;

    protected Stage stage;


    //--------------------------------------------------------------------------
    public SaveExcelView(IModel matt) {
        super(matt, "SaveExcelView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);
        stage = (Stage) myModel.getState("TransactionSaveExcel");
        dialog = new Dialog();
    }

    //--------------------------------------------------------------------------
    protected void populateFields() {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues() {
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle() {
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

        Text actionText = new Text("      ** Are sure to save in CSV ? **       ");
        actionText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        actionText.setWrappingWidth(350);
        actionText.setTextAlignment(TextAlignment.CENTER);
        actionText.setFill(Color.BLACK);
        container.getChildren().add(actionText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent() {
        VBox vbox = new VBox(10);

        submitButton = new Button("Save");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                // do the inquiry
                saveToExcelFile();
            }
        });

        cancelButton = new Button("Return");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                /**
                 * Process the Cancel button.
                 * The ultimate result of this action is that the transaction will tell the Receptionist to
                 * to switch to the Receptionist view. BUT THAT IS NOT THIS VIEW'S CONCERN.
                 * It simply tells its model (controller) that the transaction was canceled, and leaves it
                 * to the model to decide to tell the Receptionist to do the switch back.
                 */
                //----------------------------------------------------------
                clearErrorMessage();
                myModel.stateChangeRequest("CancelArticleTypeList", null);
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(submitButton);
        btnContainer.getChildren().add(cancelButton);

        vbox.getChildren().add(btnContainer);

        return vbox;
    }

    //--------------------------------------------------------------------------
    public void updateState(String key, Object value) {
    }

    //--------------------------------------------------------------------------
    protected void processArticleTypeSelected() {

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

    //-------------------------------------------------------------
    protected void writeToFile(String fName) {

        Vector allColumnNames = new Vector();
        ArticleTypeCollection articleTypeCollection =
                (ArticleTypeCollection) myModel.getState("ArticleTypeList");
        Vector clothingVector = (Vector) articleTypeCollection.getState("ArticleTypes");


        try {
            FileWriter outFile = new FileWriter(fName);
            PrintWriter out = new PrintWriter(outFile);

            if ((clothingVector == null) || (clothingVector.size() == 0))
                return;

            String line = "Description, Barcode Prefix, Alpha Code, Status";

            out.println(line);

            String valuesLine = "";

            Enumeration entries = clothingVector.elements();

            while (entries.hasMoreElements() == true) {
                ArticleType nextAT = (ArticleType) entries.nextElement();
                Vector<String> view = nextAT.getEntryListView();

                // add this list entry to the list
                ArticleTypeTableModel nextTableRowData = new ArticleTypeTableModel(view);

                valuesLine += nextTableRowData.getDescription() + ", " + nextTableRowData.getBarcodePrefix() + ", " + nextTableRowData.getAlphaCode() + ", " + nextTableRowData.getStatus() + "\n";
            }
            out.println(valuesLine);

            out.println("");

            // Also print the shift count and filter type
            out.println("\nTotal number of : " + clothingVector.size());

            // Finally, print the time-stamp
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            DateFormat timeFormat = new SimpleDateFormat("hh:mm aaa");
            Date date = new Date();
            String timeStamp = dateFormat.format(date) + " " +
                    timeFormat.format(date);

            out.println("Report created on " + timeStamp);

            out.close();


            // Acknowledge successful completion to user with JOptionPane


            Dialog dialog = new Dialog();
            dialog.setContentText("Report data saved successfully to selected file");
            dialog.showAndWait();
        } catch (FileNotFoundException e) {
            dialog.setContentText("Could not access file to save: " + fName);
            dialog.showAndWait();
        } catch (IOException e) {
            dialog.setContentText("Error in saving to file: " + e.toString());
            dialog.showAndWait();
        }

    }

    //--------------------------------------------------------------------------
    protected void saveToExcelFile() {
        // Put up JFileChooser
        // Retrieve full path name of file user selects
        // Create the file appropriately if it does not exist
        String reportsPath = System.getProperty("user.dir") + "/reports";
        File reportsDir = new File(reportsPath);


        // if the directory does not exist, create it
        if (!reportsDir.exists()) {
            reportsDir.mkdir();
        }

        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(reportsDir);

        FileChooser.ExtensionFilter filter =
                new FileChooser.ExtensionFilter(
                        "CSV - (.cvs)", "*.cvs");

        chooser.setSelectedExtensionFilter(filter);

        try {
            String fileName = "";

            File selectedFile = chooser.showOpenDialog(stage);

            fileName = selectedFile.getCanonicalPath();

            String tempName = fileName.toLowerCase();

            if (tempName.endsWith(".csv")) {
                writeToFile(fileName);
            } else {
                fileName += ".csv";
                writeToFile(fileName);
            }

            Desktop desktop = null;

            // Before more Desktop API is used, first check
            // whether the API is supported by this particular
            // virtual machine (VM) on this particular host.
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();

                if (desktop.isSupported(Desktop.Action.OPEN)) {

                    // Custom button text
                    Object[] options = {"Open reports folder",
                            "Open this report",
                            "Do nothing, continue"};

                    // Ask user what (s)he wants to do
                    ButtonType foo = new ButtonType("Export to Excel", ButtonBar.ButtonData.OK_DONE);
                    ButtonType bar = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    Alert alert = new Alert(Alert.AlertType.WARNING,
                            "What do you want to do next?",
                            foo,
                            bar);

                    alert.setTitle("Date format warning");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == foo) {
                        desktop.open(reportsDir);
                    } else {
                        File reportPath = new File(fileName);
                        desktop.open(reportPath);
                    }
                }
            }
        } catch (Exception ex) {
            dialog.setContentText("Error in saving to file: " + ex.toString());
            dialog.showAndWait();
        }
    }


}
