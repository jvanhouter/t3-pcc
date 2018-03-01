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


//TODO This class is still a work in progress, presently all errors are due to the absence of a model class.
public class CheckoutClothingItemTransaction extends Transaction
{
    //TODO create ClothingItem model class
//    private ClothingItem myClothingItem;

    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     */
    //----------------------------------------------------------
    public CheckoutClothingItemTransaction() throws Exception
    {
        super();
    }
    // TODO dependencies may be subject to change
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelBarcodeSearch", "CancelTransaction");
        dependencies.setProperty("CancelCheckoutCI", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("InventoryData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the ClothingItem,
     * verifying its uniqueness, etc.
     */
    //----------------------------------------------------------
    //TODO parameters might need to be "String barcode"?
    public void processTransaction(Properties props)
    {
        //TODO a constructor needs to be created with functionality to retrieve by barcode
        //TODO should this be an InventoryItem? Not a ClothingItem?
//        myClothingItem = new ClothingItem(props);
        try
        {
            Scene newScene = createEnterReceiverInformationView();
            swapToView(newScene);
        }
        catch (Exception ex)
        {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating EnterReceiverInformationView", Event.ERROR);
        }
    }

    //TODO check this with team, namely the error checks.
    private void processReceiver(Properties props) {

        String receiverNetid = props.getProperty("ReceiverNetid");
        if (receiverNetid.length() != 9)
        {
            transactionErrorMessage = "ERROR: NetId not correct size! ";
        }
        else
        {
            String receiverFirstName = props.getProperty("ReceiverFirstName");
            if (receiverFirstName.length() > 30)
            {
                transactionErrorMessage = "ERROR: First name too long (max length = 30)! ";
            }
            else
            {
                String receiverLastName = props.getProperty("ReceiverLastName");
                if (receiverLastName.length() > 30)
                {
                    transactionErrorMessage = "ERROR: Last name too long (max length = 30)! ";
                }
                else
                {
                    // Everything OK
                    // Set receiver properties change status to received and update
//                    myClothingItem.stateChangeRequest("ReceiverNetid", receiverNetid);
//                    myClothingItem.stateChangeRequest("ReceiverFirstName", receiverFirstName);
//                    myClothingItem.stateChangeRequest("ReceiverLastName", receiverLastName);
//                    myClothingItem.stateChangeRequest("Status", "Received");
//                    myClothingItem.update();
//                    transactionErrorMessage = (String) myClothingItem.getState("UpdateStatusMessage");
                }
            }
        }
    }

    public void stateChangeRequest(String key, Object value)
    {
        // DEBUG System.out.println("UpdateArticleTypeTransaction.sCR: key: " + key);
        //This should bring up the EnterClothingItemBarcodeView
        if (key.equals("DoYourJob") == true)
        {
            doYourJob();
        }
        //The EnterClothingItemBarcodeView should call here
        else if (key.equals("InventoryData") == true)
        {
            processTransaction((Properties)value);
        }
        //The EnterReceiverInformationView should call here
        else if (key.equals("ReceiverData") == true)
        {
            processReceiver((Properties)value);
        }

        myRegistry.updateSubscribers(key, this);
    }


    public Object getState(String key)
    {
        //TODO
        return null;
    }

    protected Scene createView()
    {
        Scene currentScene = myViews.get("BarcodeScannerView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("BarcodeScannerView", this);
            currentScene = new Scene(newView);
            myViews.put("BarcodeScannerView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

    protected Scene createEnterReceiverInformationView()
    {
        View newView = ViewFactory.createView("EnterReceiverInformationView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }
}
