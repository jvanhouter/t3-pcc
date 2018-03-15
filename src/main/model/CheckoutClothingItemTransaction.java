// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.text.SimpleDateFormat;
import java.util.*;

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
    private ClothingItem myClothingItem;
    private LinkedList<ClothingItem> clothingItems = new LinkedList<ClothingItem>();

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
        dependencies.setProperty("ReceiverData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the ClothingItem,
     * verifying its uniqueness, etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        String barcode = props.getProperty("Barcode");
        try
        {
            // Try to create a new ClothingItem
            myClothingItem = new ClothingItem(barcode);
//          DEBUG System.out.println(myClothingItem.getEntryListView());
            // If the clothing item exists and is not "Received"
            if(myClothingItem != null && myClothingItem.getState("Status").equals("Donated"))
            {
                // Add the ClothingItem to a list
                clothingItems.add(myClothingItem);
                // Swap to a scene that asks if the user wishes to enter another barcode or continue onwards.
                try
                {
                    Scene newScene = createBarcodeHelperView();
                    swapToView(newScene);
                }
                catch (Exception ex)
                {
                    new Event(Event.getLeafLevelClassName(this), "processTransaction",
                            "Error in creating BarcodeHelperView", Event.ERROR);
                }
            }
            // Otherwise let the user know the barcode was not added to the list
            else
            {
                handleBarcodeProblems();
            }
//            DEBUG
//            Iterator<ClothingItem> i = clothingItems.iterator();
//            while(i.hasNext()){
//                System.out.println(i.next().getEntryListView());
//            }
        }
        catch (InvalidPrimaryKeyException e)
        {
//          DEBUG e.printStackTrace();
            // Let the user know the barcode was not added to the list
            handleBarcodeProblems();
        }
        catch (MultiplePrimaryKeysException e)
        {
            e.printStackTrace();
        }
    }

    private void processReceiver(Properties props) {

        String receiverNetid = props.getProperty("ReceiverNetid");
        String receiverFirstName = props.getProperty("ReceiverFirstName");
        String receiverLastName = props.getProperty("ReceiverLastName");

        // Set receiver properties change status to received and update
        Iterator<ClothingItem> i = clothingItems.iterator();
        while(i.hasNext()) {

            ClothingItem currItem = i.next();
            currItem.stateChangeRequest("ReceiverNetid", receiverNetid);
            currItem.stateChangeRequest("ReceiverFirstName", receiverFirstName);
            currItem.stateChangeRequest("ReceiverLastName", receiverLastName);
            currItem.stateChangeRequest("Status", "Received");

            //Compose current date
            Calendar currDate = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            String date = dateFormat.format(currDate.getTime());
            currItem.stateChangeRequest("DateTaken", date);

//          System.out.println(myClothingItem.getEntryListView());
            currItem.update();
            transactionErrorMessage = (String) currItem.getState("UpdateStatusMessage");
        }
    }

    private void switchToEnterReceiverInformationView()
    {

        Scene newScene = createEnterReceiverInformationView();
        swapToView(newScene);
    }

    public void stateChangeRequest(String key, Object value)
    {
        //DEBUG System.out.println("CheckoutClothingItemTransaction.sCR: key: " + key);
        //This should bring up the EnterClothingItemBarcodeView
        if (key.equals("DoYourJob") == true)
        {
            doYourJob();
        }
        //The EnterClothingItemBarcodeView should call here
        else if (key.equals("ProcessBarcode") == true)
        {
//            System.out.println("BarcodeView returned: " + value);
            processTransaction((Properties)value);
        }
        //The EnterReceiverInformationView should call here
        else if (key.equals("ReceiverData") == true)
        {
//            System.out.println("ReceiverView returned: " + value);
            processReceiver((Properties)value);
        }
        else if (key.equals("MoreData") == true)
        {
           doYourJob();
        }
        else if (key.equals("NoMoreData") == true)
        {
           switchToEnterReceiverInformationView();
        }
//      System.out.println(key);
        myRegistry.updateSubscribers(key, this);
    }

    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }

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

    protected Scene createBarcodeHelperView()
    {
        View newView = ViewFactory.createView("BarcodeHelperView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }
    protected Scene createCheckoutInvalidItemView()
    {
        View newView = ViewFactory.createView("CheckoutInvalidItemView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;
    }

    private void handleBarcodeProblems()
    {
        try
        {
            Scene newScene = createCheckoutInvalidItemView();
            swapToView(newScene);
        }
        catch (Exception ex)
        {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating CheckoutInvalidItemView", Event.ERROR);
        }
    }
}
