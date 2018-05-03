// specify the package
package model;

// system imports

import Utilities.Utilities;
import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

// project imports

/**
 * The class containing the RemoveClothingItemTransaction for the Professional Clothes Closet application
 */
//==============================================================
public class RemoveClothingItemTransaction extends Transaction {

    //private ColorCollection myColorList;
    private ClothingItem mySelectedItem;

    // GUI Components

    private String transactionErrorMessage = "";
    private String clothingRemoveStatusMessage = "";

    /**
     * Constructor for this class.
     */
    //----------------------------------------------------------
    public RemoveClothingItemTransaction() throws Exception {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelSearchClothingItem", "CancelTransaction");
        dependencies.setProperty("CancelBarcodeSearch", "CancelTransaction");
        dependencies.setProperty("CancelRemoveCI", "CancelTransaction");
        dependencies.setProperty("RemoveClothingItem", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    private void processColorRemoval() {
        if (mySelectedItem != null) {
            mySelectedItem.stateChangeRequest("Status", "Removed");
            mySelectedItem.update();
            transactionErrorMessage = (String) mySelectedItem.getState("UpdateStatusMessage");
            if(!transactionErrorMessage.toLowerCase().contains("error"))
                Utilities.removeClothingHash((String) mySelectedItem.getState("ID"));
        }
    }


    //-----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("TransactionError")) {
            return transactionErrorMessage;
        } else
            return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        if ((key.equals("DoYourJob") == true) || (key.equals("CancelClothingItemList") == true)) {
            doYourJob();
        } else if (key.equals("ProcessBarcode") == true) {
            Properties props = (Properties) value;
            String barcode = props.getProperty("Barcode");
            barcode = barcode.toUpperCase();
            try {
                mySelectedItem = new ClothingItem(barcode);
                if (mySelectedItem != null) {
                    if (mySelectedItem.getState("Status").equals("Donated")) {
                        try {

                            Scene newScene = createRemoveClothingItemView();

                            swapToView(newScene);

                        } catch (Exception ex) {
                            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                                    "Error in creating RemoveClothingItemView", Event.ERROR);
                        }
                    } else {
                        transactionErrorMessage = barcode + " is not available for removal.";
                        handleBarcodeProblems(transactionErrorMessage);
                    }
                } else {
                    transactionErrorMessage = barcode + " does not exist in the database.";
                    handleBarcodeProblems(transactionErrorMessage);
                }
            } catch (InvalidPrimaryKeyException e) {
                transactionErrorMessage = barcode + " does not exist in the database.";
                handleBarcodeProblems(transactionErrorMessage);
            } catch (MultiplePrimaryKeysException e) {
                e.printStackTrace();
            }
        } else if (key.equals("RemoveClothingItem") == true) {
            processColorRemoval();
        }

        myRegistry.updateSubscribers(key, this);
    }

    private void handleBarcodeProblems(String msg) {
        PccAlert myAlert = PccAlert.getInstance();
        myAlert.displayErrorMessage(msg);
        // needs more clarification stateChangeRequest("HandleBarcodeProblems", "");
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView() {
        Scene currentScene = myViews.get("BarcodeScannerView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("BarcodeScannerView", this);
            currentScene = new Scene(newView);
            myViews.put("BarcodeScannerView", currentScene);

            return currentScene;
        } else {
            return currentScene;
        }
    }

    protected Scene createColorCollectionView() {
        View newView = ViewFactory.createView("ColorCollectionView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }


    //------------------------------------------------------
    protected Scene createRemoveClothingItemView() {
        View newView = ViewFactory.createView("RemoveClothingItemView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

}

