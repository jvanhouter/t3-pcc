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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

// project imports

/**
 * The class containing the AddArticleTypeTransaction for the Professional Clothes Closet application
 */
//==============================================================
public class AddClothingItemTransaction extends Transaction {

    private ClothingItem myClothingItem;
    private ArticleTypeCollection myArticleTypeList;
    private ColorCollection myColorList;
    private String barcode;


    // GUI Components

    private String transactionErrorMessage = "";
    private String gender = "";

    /**
     * Constructor for this class.
     */

    public AddClothingItemTransaction() throws Exception {
        super();
    }


    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelAddClothingItem", "CancelTransaction");
        dependencies.setProperty("CancelBarcodeSearch", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("ClothingItemData", "TransactionError");
        dependencies.setProperty("ProcessBarcode", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the article type,
     * verifying its uniqueness, etc.
     */

    public void processTransaction(Properties props) {
        if (barcode != null) {
            props.setProperty("Barcode", barcode);

            try {

                props.setProperty("ReceiverNetid", "");
                props.setProperty("ReceiverFirstName", "");
                props.setProperty("ReceiverLastName", "");
                props.setProperty("DateTaken", "");

                props.setProperty("Status", "Donated");
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                props.setProperty("DateDonated", date);
                myClothingItem = new ClothingItem(props);
                myClothingItem.update();
                transactionErrorMessage = (String) myClothingItem.getState("UpdateStatusMessage");
                if(!transactionErrorMessage.toLowerCase().contains("error"))
                    Utilities.putClothingHash((String) myClothingItem.getState("Barcode"), myClothingItem);

            } catch (Exception excep) {
                transactionErrorMessage = "ERROR: Invalid barcode: " + barcode
                        + "!";
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Invalid barcode: " + barcode + "!",
                        Event.ERROR);
            }
        }
    }

    private void processBarcode(Properties props) {
        String barcode = props.getProperty("Barcode");
        barcode = barcode.toUpperCase();
        try {
            ClothingItem mySelectedItem = new ClothingItem(barcode);
            if (mySelectedItem != null) {
                if (mySelectedItem.getState("Status").equals("Donated")) {
                    if (barcode.substring(0, 1).equals("1"))
                        gender = "Mens";
                    else if (barcode.substring(0, 1).equals("0"))
                        gender = "Womens";
                    else if (barcode.substring(0, 1).equals("2"))
                        gender = "Unisex";
                    myArticleTypeList = new ArticleTypeCollection();
                    //myArticleTypeList.findAll();
                    myColorList = new ColorCollection();
                    //myColorList.findAll();
                    createAndShowAddClothingItemView();
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

    }

    public Object getState(String key) {
        if (key.equals("TransactionError")) {
            return transactionErrorMessage;
        } else if (key.equals("Gender")) {
            return gender;
        } else if (key.equals("Articles")) {
            return myArticleTypeList.retrieveAll();
        } else if (key.equals("Colors")) {
            return myColorList.retrieveAll();
        } else if (key.equals("Barcode")) {
            return barcode;
        } else if (key.equals("ListAll")) {
            return false;
        }
        return null;
    }


    public void stateChangeRequest(String key, Object value) {
        // DEBUG System.out.println("AddArticleTypeTransaction.sCR: key: " + key);

        if (key.equals("DoYourJob") || key.equals("CancelAddClothingItem")) {
//		    createAndShowBarcodeScannerView();
            doYourJob();
        } else if (key.equals("ProcessBarcode")) {
//			doYourJob();
            processBarcode((Properties) value);
        } else if (key.equals("ClothingItemData")) {
            processTransaction((Properties) value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    private void handleBarcodeProblems(String msg) {
        PccAlert myAlert = PccAlert.getInstance();
        myAlert.displayErrorMessage(msg);
        // needs more clarification stateChangeRequest("HandleBarcodeProblems", "");
    }

    private void createAndShowAddClothingItemView() {
        Scene currentScene = (Scene) myViews.get("AddClothingItemView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("AddClothingItemView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("AddClothingItemView", currentScene);
        }

        swapToView(currentScene);

    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */

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

}
