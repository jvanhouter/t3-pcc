// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the RemoveColorTransaction for the Professional Clothes Closet application */
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
        //myColorList = new ColorCollection();
        String desc = props.getProperty("Description");
        String alfaC = props.getProperty("AlphaCode");
        //myColorList.findByCriteria(desc, alfaC);

        try
        {
            Scene newScene = createColorCollectionView();
            swapToView(newScene);
        } catch (Exception ex) {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating ColorCollectionView", Event.ERROR);
        }

    }

    private void processColorRemoval()
    {
        if(mySelectedItem != null) {
            mySelectedItem.stateChangeRequest("Status", "Removed");
            mySelectedItem.update();
            transactionErrorMessage = (String)mySelectedItem.getState("UpdateStatusMessage");
        }
    }


    //-----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("TransactionError") == true) {
            return transactionErrorMessage;
        }
        else
            /*
        if (key.equals("ClothingList") == true) {
            return myColorList;
        }
        else
        if (key.equals("BarcodePrefix") == true)
        {
            if (mySelectedColor != null)
                return mySelectedColor.getState("BarcodePrefix");
            else
                return "";
        }
        else
        if (key.equals("Description") == true)
        {
            if (mySelectedColor != null)
                return mySelectedColor.getState("Description");
            else
                return "";
        }
        else
        if (key.equals("AlphaCode") == true)
        {
            if (mySelectedColor != null)
                return mySelectedColor.getState("AlphaCode");
            else
                return "";
        }*/
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        if ((key.equals("DoYourJob") == true) || (key.equals("CancelClothingItemList") == true))
        {
            doYourJob();
        }
        else
        if (key.equals("SearchClothingList") == true)
        {
            processTransaction((Properties)value);
        }
        else
        if (key.equals("ProcessBarcode") == true)
        {
            try {
                Properties p = (Properties) value;
                mySelectedItem = new ClothingItem((String) p.get("Barcode")); //passes entire barcode
            } catch (InvalidPrimaryKeyException e) {
                e.printStackTrace();
            } catch (MultiplePrimaryKeysException e) {
                e.printStackTrace();
            }
            try
            {

                Scene newScene = createRemoveClothingItemView();

                swapToView(newScene);

            }
            catch (Exception ex)
            {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating RemoveClothingItemView", Event.ERROR);
            }
        }
        else
        if (key.equals("RemoveClothingItem") == true)
        {
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

