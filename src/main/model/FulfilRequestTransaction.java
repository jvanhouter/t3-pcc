// specify the package
package model;

// system imports

import Utilities.Utilities;
import event.Event;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.text.SimpleDateFormat;
import java.util.*;

// project imports


public class FulfilRequestTransaction extends Transaction {

    private RequestCollection myRequestCollection;
    private ClothingRequest myClothingRequest;
    private ClothingItem myClothingItem;
    private ClothingItemCollection myClothingCollection;

    private InventoryItemCollection receiverPastSixMonths = new InventoryItemCollection();

    private ArticleTypeCollection myArticleTypeList;
    private ColorCollection myColorList;
    private String gender;

    private String transactionErrorMessage = "";

    /* Patricks Components */
    private String updateMessage = "";
    private String receiverNetid = "";
    private String receiverFirstName = "";
    private String receiverLastName = "";
    private boolean verifiedHistory = false;

    //----------------------------------------------------------
    public FulfilRequestTransaction() throws Exception {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelRequest", "CancelTransaction");
        dependencies.setProperty("CancelClothingItemList", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("ProcessRequest", "TransactionError");
        dependencies.setProperty("ClothingRequestData", "TransactionError");
        // communicate with ReceiverRecentCheckoutView
        dependencies.setProperty("ReceiverData", "TransactionError");
        dependencies.setProperty("CancelCheckoutCI", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    //-----------------------------------------------------------
    public void processTransaction(Properties props) {
        myRequestCollection = new RequestCollection();
        myRequestCollection.findAll();

        try {
            Scene newScene = createRequestCollectionView();
            swapToView(newScene);
        } catch (Exception ex) {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating ColorCollectionView", Event.ERROR);
        }
    }

    private void processReceiver(Properties props) {
        receiverNetid = (String) myClothingRequest.getState("RequesterNetid");
        receiverPastSixMonths.findRecent(receiverNetid);
        if (!((Vector) receiverPastSixMonths.getState("InventoryItems")).isEmpty() && verifiedHistory == false) {
            switchToReceiverRecentCheckoutView();
            verifiedHistory = true;
        } else {
            myClothingItem.stateChangeRequest("Status", "Received");
            myClothingRequest.stateChangeRequest("Status", "Fulfilled");
            Date date = new Date();
            String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            myClothingItem.stateChangeRequest("DateTaken", modifiedDate);
            myClothingRequest.stateChangeRequest("RequestFulfilledDate", modifiedDate);
            myClothingItem.stateChangeRequest("ReceveiverNetid", (String) myClothingRequest.getState("RequesterNetid"));
            myClothingItem.stateChangeRequest("ReceveiverFirstName", (String) myClothingRequest.getState("RequesterFirstName"));
            myClothingItem.stateChangeRequest("ReceveiverLastName", (String) myClothingRequest.getState("RequesterLastName"));
            //myClothingItem.update();
            transactionErrorMessage = (String) myClothingItem.getState("UpdateStatusMessage");
            //myClothingRequest.update();
            updateMessage = (String) myClothingRequest.getState("UpdateStatusMessage");
            //if(!transactionErrorMessage.toLowerCase().contains("error") && !updateMessage.toLowerCase().contains("error")) {
            //    Utilities.removeClothingRequestHash((String) myClothingRequest.getState("ID"));
             //   Utilities.removeClothingHash((String) myClothingItem.getState("ID"));
           // }
            transactionErrorMessage = "Request has been fulfilled";
            stateChangeRequest("CancelCheckoutCI", null);
        }
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        if ((key.equals("DoYourJob") == true) || (key.equals("CancelTransaction") == true)) {
            myRequestCollection = new RequestCollection();
            if(value != null) {
                myRequestCollection.setRequests((Vector<ClothingRequest>) value);
            } else {
                myRequestCollection.findAll();
            }
            doYourJob();
        } else if (key.equals("SearchRequest") == true) {
            processTransaction((Properties) value);
        } else if (key.equals("ProcessRequest") == true || key.equals("ReceiverData") == true) {
            processReceiver((Properties) value);
        }
        if (key.equals("ClothingItemSelected") == true) {
            try {
                myClothingItem = myClothingCollection.retrieve((String) value);

                Scene newScene = createFulfilView();

                swapToView(newScene);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (key.equals("RequestSelected") == true) {
            myClothingRequest = myRequestCollection.retrieve((String) value);
            myClothingCollection = new ClothingItemCollection();
            myClothingCollection.findDonatedCriteria((String) myClothingRequest.getState("RequestedArticleType"),
                    (String) myClothingRequest.getState("RequestedGender"),
                    (String) myClothingRequest.getState("RequestedSize"));
            try {

                Scene newScene = createInventoryCollectionView();

                swapToView(newScene);

            } catch (Exception ex) {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating Inventory View", Event.ERROR);
            }
        } else if (key.equals("RequestData") == true) {
            myClothingRequest = myRequestCollection.retrieve((String) value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    //------------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("ReceiverRecentCheckouts") == true) {
            return receiverPastSixMonths;
        }
        if (key.equals("ClothingItemList") == true) {
            return myClothingCollection;
        }
        if (key.equals("RequestList") == true) {
            return myRequestCollection;
        }
        if (key.equals("ClothingItem") == true) {
            return myClothingItem;
        }
        if (key.equals("ClothingRequest") == true) {
            return myClothingRequest;
        }
        if (key.equals("TransactionError") == true) {
            return transactionErrorMessage;
        }
        return null;
    }

    //-------------------------------------------------------------
    protected Scene createView() {

        Scene currentScene = myViews.get("RequestCollectionView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("RequestCollectionView", this);
            currentScene = new Scene(newView);
            myViews.put("RequestCollectionView", currentScene);

            return currentScene;
        } else {
            return currentScene;
        }
    }

    //-------------------------------------------------------------
    protected Scene createFulfilView() {
        View newView = ViewFactory.createView("FulfillRequestView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;
    }

    //-------------------------------------------------------------
    protected Scene createRequestCollectionView() {
        View newView = ViewFactory.createView("RequestCollectionView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;
    }

    //-------------------------------------------------------------
    protected Scene createInventoryCollectionView() {
        View newView = ViewFactory.createView("ClothingItemCollectionView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;
    }

    //----------------------------------------------------------------
    private void switchToReceiverRecentCheckoutView() {
        Scene newScene = createReceiverRecentCheckoutView();
        swapToView(newScene);
    }

    //----------------------------------------------------------------
    protected Scene createReceiverRecentCheckoutView() {
        View newView = ViewFactory.createView("ReceiverRecentCheckoutView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;
    }

}
