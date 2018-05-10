// specify the package
package model;

// system imports

import Utilities.Utilities;
import event.Event;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.HashMap;
import java.util.Properties;

// project imports

/**
 * The class containing the RemoveClothingItemTransaction for the Professional Clothes Closet application
 */
//==============================================================
public class RemoveClothingItemTransaction extends Transaction {

    private ClothingItemCollection myClothingItemList;
    private ClothingItem mySelectedClothingItem;

    private HashMap myArticleTypeList;
    private HashMap myColorList;
    private String gender;

    private ClothingItem mySelectedItem;

    // GUI Components
    private String transactionErrorMessage = "";

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
        dependencies.setProperty("ProcessBarcode", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the clothing item collection and showing the view
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props) {
        myClothingItemList = new ClothingItemCollection();
        myArticleTypeList = Utilities.collectArticleTypeHash();
        myColorList = Utilities.collectColorHash();


        if (props.getProperty("Barcode") != null) {
            String barcode = props.getProperty("Barcode");
            myClothingItemList.findByBarcode(barcode);
        } else {
            myClothingItemList.findAll();
        }

        try {
            Scene newScene = createClothingItemCollectionView();
            swapToView(newScene);
        } catch (Exception ex) {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating ClothingItemCollectionView", Event.ERROR);
        }
    }
    /**
     * This method encapsulates all the logic of removing the clothing item,
     * verifying the new barcode, etc.
     */
    private void processClothingRemoval() {
        if (mySelectedClothingItem != null) {
            mySelectedClothingItem.stateChangeRequest("Status", "Removed");
            mySelectedClothingItem.update();
            transactionErrorMessage = (String) mySelectedClothingItem.getState("UpdateStatusMessage");
            if(!transactionErrorMessage.toLowerCase().contains("error"))
                Utilities.removeClothingHash((String) mySelectedClothingItem.getState("ID"));
        }
    }

    public Object getState(String key) {
        if (key.equals("ClothingItemList")) {
            return myClothingItemList;
        } else if (key.equals("Barcode")) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("Barcode");
            else
                return "";
        } else if (key.equals("Gender")) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("Gender");
            else
                return "";
        } else if (key.equals("Color1")) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("Color1");
            else
                return "";
        } else if (key.equals("Color2")) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("Color2");
            else
                return "";
        } else if (key.equals("Brand")) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("Brand");
            else
                return "";
        } else if (key.equals("ArticleType")) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("ArticleType");
            else
                return "";
        } else if (key.equals("DonorFirstName")) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("DonorFirstName");
            else
                return "";
        } else if (key.equals("DonorLastName")) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("DonorLastName");
            else
                return "";
        } else if (key.equals("DonorEmail")) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("DonorEmail");
            else
                return "";
        } else if (key.equals("DonorPhone")) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("DonorPhone");
            else
                return "";
        } else if (key.equals("Notes")) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("Notes");
            else
                return "";
        } else if (key.equals("Size")) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("Size");
            else
                return "";
        } else if (key.equals("TransactionError")) {
            return transactionErrorMessage;
        } else if (key.equals("Gender")) {
            return gender;
        } else if (key.equals("Articles")) {
            return myArticleTypeList;
        } else if (key.equals("Colors")) {
            return myColorList;
        } else if (key.equals("ListAll")) {
            return true;
        } else
            return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        if ((key.equals("DoYourJob")) || (key.equals("CancelClothingItemList"))) {
            doYourJob();
        } else if (key.equals("ProcessBarcode")) {
            processTransaction((Properties) value);
        } else if (key.equals("ClothingItemSelected")){
            mySelectedClothingItem = myClothingItemList.retrieve((String) value);
            String barcode = (String) mySelectedClothingItem.getState("Barcode");
            if (barcode.substring(0, 1).equals("1"))
                gender = "Mens";
            else if (barcode.substring(0, 1).equals("0"))
                gender = "Womens";
            else if (barcode.substring(0, 1).equals("2"))
                gender = "Unisex";
            try {

                Scene newScene = createRemoveClothingItemView();

                swapToView(newScene);

            } catch (Exception ex) {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating ModifyClothingItemView", Event.ERROR);
                ex.printStackTrace();
            }
        } else if (key.equals("RemoveClothingItem")) {
            processClothingRemoval();
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
            currentScene = new Scene(ViewFactory.createView("BarcodeScannerView", this));
            myViews.put("BarcodeScannerView", currentScene);

            return currentScene;
        } else {
            return currentScene;
        }
    }

    /**
     * Create the view containing the table of all matching clothing items on the search criteria sents
     */
    //------------------------------------------------------
    protected Scene createClothingItemCollectionView() {
        View newView = ViewFactory.createView("ClothingItemCollectionView", this);
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

