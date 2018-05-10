// specify the package
package model;

// system imports

import Utilities.UiConstants;
import Utilities.Utilities;
import event.Event;
import exception.InvalidPrimaryKeyException;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

// project imports

/**
 * The class containing the AddArticleTypeTransaction for the Professional Clothes Closet application
 */
//==============================================================
public class AddColorTransaction extends Transaction {


    private Color myColor;


    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     */

    public AddColorTransaction() throws Exception {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelAddCT", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("ColorData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the article type,
     * verifying its uniqueness, etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props) {
        if (props.getProperty("BarcodePrefix") != null) {
            String barcodePrefix = props.getProperty("BarcodePrefix");
            try {

                Color oldColor = new Color(barcodePrefix);
                /*System.out.println((String) oldColor.getState("Status"));
                if(((String) oldColor.getState("Status")).equals("Inactive")) {
                    oldColor.stateChangeRequest("Status", "Active");
                    oldColor.update();
                    transactionErrorMessage = "Color type with barcode prefix : " + barcodePrefix + " reinserted successfully";
                    Utilities.putColorHash((String) myColor.getState("ID"), myColor);
                    return;
                }*/
                oldColor.update();

                transactionErrorMessage = "ERROR: Barcode Prefix " + barcodePrefix
                        + " already exists!";
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Color type with barcode prefix : " + barcodePrefix + " already exists!",
                        Event.ERROR);
            } catch (InvalidPrimaryKeyException ex) {
                // Barcode prefix does not exist, validate data
                try {
                    int barcodePrefixVal = Integer.parseInt(barcodePrefix);
                    String descriptionOfCL = props.getProperty("Description");
                    if (descriptionOfCL.length() > UiConstants.COLOR_DESCRIPTION_MAX_LENGTH) {
                        transactionErrorMessage = "ERROR: Color Type Description too long! ";
                    } else {
                        String alphaCode = props.getProperty("AlphaCode");
                        if (alphaCode.length() > UiConstants.ALPHACODE_MAX_LENGTH) {
                            transactionErrorMessage = "ERROR: Alpha code too long (max length = 5)! ";
                        } else {
                            props.setProperty("Status", "Active");

                            myColor = new Color(props);
                            myColor.update();
                            // set after update on error.
                            transactionErrorMessage = (String) myColor.getState("UpdateStatusMessage");
                            if(!transactionErrorMessage.toLowerCase().contains("error"))
                                Utilities.putColorHash((String) myColor.getState("ID"), myColor);
                        }
                    }
                } catch (Exception excep) {
                    excep.printStackTrace();
                    transactionErrorMessage = "ERROR: Invalid barcode prefix: " + barcodePrefix
                            + "! Must be numerical.";
                    new Event(Event.getLeafLevelClassName(this), "processTransaction",
                            "Invalid barcode prefix : " + barcodePrefix + "! Must be numerical.",
                            Event.ERROR);
                }

            } catch (Exception ex2) {
                transactionErrorMessage = "ERROR: Multiple color types with barcode prefix!";
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Found multiple color types with barcode prefix : " + barcodePrefix + ". Reason: " + ex2.toString(),
                        Event.ERROR);

            }
        }

    }

    //-----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("TransactionError")) {
            return transactionErrorMessage;
        }


        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        // DEBUG System.out.println("AddArticleTypeTransaction.sCR: key: " + key);

        if (key.equals("DoYourJob")) {
            doYourJob();
        } else if (key.equals("ColorData")) {
            processTransaction((Properties) value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView() {
        Scene currentScene = myViews.get("AddColorView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("AddColorView", this);
            currentScene = new Scene(newView);
            myViews.put("AddColorView", currentScene);

            return currentScene;
        } else {
            return currentScene;
        }
    }

}

