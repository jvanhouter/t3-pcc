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

    /**
     * This method encapsulates all the logic of creating the article type,
     * verifying its uniqueness, etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props) {

        String desc = props.getProperty("Description");
        String alfaC = props.getProperty("AlphaCode");

        try {
            Scene newScene = createColorCollectionView();
            swapToView(newScene);
        } catch (Exception ex) {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating ColorCollectionView", Event.ERROR);
        }

    }

    private void processColorRemoval() {
        if (mySelectedItem != null) {
            mySelectedItem.stateChangeRequest("Status", "Removed");
            mySelectedItem.update();
            Utilities.removeClothingHash((String) mySelectedItem.getState("ID"));
            transactionErrorMessage = (String) mySelectedItem.getState("UpdateStatusMessage");
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
        } else if (key.equals("SearchClothingList") == true) {
            processTransaction((Properties) value);
        } else if (key.equals("ProcessBarcode") == true) {
            try {
                Properties p = (Properties) value;
                mySelectedItem = new ClothingItem((String) p.getProperty("Barcode")); //passes entire barcode
            } catch (InvalidPrimaryKeyException e) {
                e.printStackTrace();
            } catch (MultiplePrimaryKeysException e) {
                e.printStackTrace();
            }
            try {

                Scene newScene = createRemoveClothingItemView();

                swapToView(newScene);

            } catch (Exception ex) {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating RemoveClothingItemView", Event.ERROR);
            }
        } else if (key.equals("RemoveClothingItem") == true) {
            processColorRemoval();
        }

        myRegistry.updateSubscribers(key, this);
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

