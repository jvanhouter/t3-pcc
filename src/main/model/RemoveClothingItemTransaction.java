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

    private ClothingItemCollection myClothingItemList;

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

        myRegistry.setDependencies(dependencies);
    }

    private void processClothingRemoval() {
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
        if (key.equals("ClothingItemList")) {
            return myClothingItemList;
        }  else if (key.equals("Barcode")) {
            if (mySelectedItem != null)
                return mySelectedItem.getState("Barcode");
            else
                return "";
        } else if (key.equals("Gender")) {
            if (mySelectedItem != null)
                return mySelectedItem.getState("Gender");
            else
                return "";
        } else if (key.equals("Color1")) {
            if (mySelectedItem != null)
                return mySelectedItem.getState("Color1");
            else
                return "";
        } else if (key.equals("Color2")) {
            if (mySelectedItem != null)
                return mySelectedItem.getState("Color2");
            else
                return "";
        } else if (key.equals("Brand")) {
            if (mySelectedItem != null)
                return mySelectedItem.getState("Brand");
            else
                return "";
        } else if (key.equals("ArticleType")) {
            if (mySelectedItem != null)
                return mySelectedItem.getState("ArticleType");
            else
                return "";
        } else if (key.equals("DonorFirstName")) {
            if (mySelectedItem != null)
                return mySelectedItem.getState("DonorFirstName");
            else
                return "";
        } else if (key.equals("DonorLastName")) {
            if (mySelectedItem != null)
                return mySelectedItem.getState("DonorLastName");
            else
                return "";
        } else if (key.equals("DonorEmail")) {
            if (mySelectedItem != null)
                return mySelectedItem.getState("DonorEmail");
            else
                return "";
        } else if (key.equals("DonorPhone")) {
            if (mySelectedItem != null)
                return mySelectedItem.getState("DonorPhone");
            else
                return "";
        } else if (key.equals("Notes")) {
            if (mySelectedItem != null)
                return mySelectedItem.getState("Notes");
            else
                return "";
        } else if (key.equals("Size")) {
            if (mySelectedItem != null)
                return mySelectedItem.getState("Size");
            else
                return "";
        } else if (key.equals("TransactionError")) {
            return transactionErrorMessage;
//        } else if (key.equals("Gender")) {
//            return gender;
//        } else if (key.equals("Articles")) {
//            return myArticleTypeList.retrieveAll();
//        } else if (key.equals("Colors")) {
//            return myColorList.retrieveAll();
        } else if (key.equals("ListAll")) {
            return false;
        } else
            return null;
    }

    public void processTransaction(Properties props) {
        myClothingItemList = new ClothingItemCollection();

        if (props.getProperty("Barcode") != null) {
            String barcode = props.getProperty("Barcode");
            myClothingItemList.findByBarcode(barcode);
        } /*else { Properties doesn't contain gender or article type
            String genderString = props.getProperty("Gender");
            String articleTypeString = props.getProperty("ArticleType");
            myClothingItemList.findByCriteria(articleTypeString, genderString);
        }*/ else {
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

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        if ((key.equals("DoYourJob")) || (key.equals("CancelClothingItemList"))) {
            doYourJob();
        } else if (key.equals("ProcessBarcode")) {
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
            View newView = ViewFactory.createView("BarcodeScannerView", this);
            currentScene = new Scene(newView);
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

