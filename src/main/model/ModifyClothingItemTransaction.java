package model;

// system imports

import Utilities.UiConstants;
import Utilities.Utilities;
import event.Event;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

// project imports

/**
 * The class containing the ModifyClothingItemTransaction for the Professional Clothes Closet application
 */
//==============================================================
public class ModifyClothingItemTransaction extends Transaction {

    private ClothingItemCollection myClothingItemList;
    private ClothingItem mySelectedClothingItem;

    private HashMap myClothingItemHash;
    private HashMap myArticleTypeList;
    private HashMap myColorList;
    private String gender;

    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     */
    //----------------------------------------------------------
    public ModifyClothingItemTransaction() throws Exception {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelSearchClothingItem", "CancelTransaction");
        dependencies.setProperty("CancelBarcodeSearch", "CancelTransaction");
        dependencies.setProperty("CancelAddClothingItem", "CancelTransaction");
        dependencies.setProperty("ClothingItemData", "TransactionError");
        dependencies.setProperty("OK", "CancelTransaction");
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
            myClothingItemHash = Utilities.collectClothingHash();
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
     * Helper method for clothing item update
     */
    //--------------------------------------------------------------------------
    private void clothingItemModificationHelper(Properties props) {

        // Everything OK
        try {
            ClothingItem insertItem = new ClothingItem(props.getProperty("Barcode"));
            insertItem.stateChangeRequest("Barcode", props.getProperty("Barcode"));
            insertItem.stateChangeRequest("Gender", props.getProperty("Gender"));
            insertItem.stateChangeRequest("ArticleType", props.getProperty("ArticleType"));
            insertItem.stateChangeRequest("Color1", props.getProperty("Color1"));
            insertItem.stateChangeRequest("Color2", props.getProperty("Color2"));
            insertItem.stateChangeRequest("Brand", props.getProperty("Brand"));
            insertItem.stateChangeRequest("DonorFirstName", props.getProperty("DonorFirstName"));
            insertItem.stateChangeRequest("DonorLastName", props.getProperty("DonorLastName"));
            insertItem.stateChangeRequest("DonorEmail", props.getProperty("DonorEmail"));
            insertItem.stateChangeRequest("DonorPhone", props.getProperty("DonorPhone"));
            insertItem.stateChangeRequest("Notes", props.getProperty("Notes"));
            if (props.getProperty("Size").equals(""))
                insertItem.stateChangeRequest("Size", "" + UiConstants.GENERIC_SIZE);
            else
                insertItem.stateChangeRequest("Size", props.getProperty("Size"));
            insertItem.update();
            transactionErrorMessage = (String) insertItem.getState("UpdateStatusMessage");
            if(!transactionErrorMessage.toLowerCase().contains("error"))
                Utilities.putClothingHash((String) insertItem.getState("ID"), insertItem);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method encapsulates all the logic of modifiying the clothing item,
     * verifying the new barcode, etc.
     */
    private void processClothingItemModification(Properties props) {
        String originalBarcode = (String) mySelectedClothingItem.getState("Barcode");
        props.setProperty("Barcode", originalBarcode);
        clothingItemModificationHelper(props);
    }

    //-----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("ClothingItemList")) {
//            return myClothingItemList;
            return myClothingItemHash;
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
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        // DEBUG System.out.println("ModifyClothingItemTransaction.sCR: key: " + key);

        if ((key.equals("DoYourJob")) || (key.equals("CancelClothingItemList"))) {
            doYourJob();
        } else if (key.equals("ProcessBarcode")) {
            processTransaction((Properties) value);
        } else if (key.equals("ClothingItemSelected")) {
            mySelectedClothingItem = myClothingItemList.retrieve((String) value);
            String barcode = (String) mySelectedClothingItem.getState("Barcode");
            if (barcode.substring(0, 1).equals("1"))
                gender = "Mens";
            else if (barcode.substring(0, 1).equals("0"))
                gender = "Womens";
            else if (barcode.substring(0, 1).equals("2"))
                gender = "Unisex";
            try {

                Scene newScene = createModifyClothingItemView();

                swapToView(newScene);

            } catch (Exception ex) {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating ModifyClothingItemView", Event.ERROR);
                ex.printStackTrace();
            }
        } else if (key.equals("ClothingItemData")) {
            processClothingItemModification((Properties) value);
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

    /**
     * Create the view using which data about selected clothing item can be modified
     */
    //------------------------------------------------------
    protected Scene createModifyClothingItemView() {
        View newView = ViewFactory.createView("ModifyClothingItemView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

}
