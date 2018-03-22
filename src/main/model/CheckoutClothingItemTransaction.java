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
    private String cart = "";
    private String barcodeError = "";

    /**
     * Constructor for this class.
     *
     */
    //----------------------------------------------------------
    public CheckoutClothingItemTransaction() throws Exception
    {
        super();
    }
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
        barcode = barcode.toUpperCase();
        try
        {
            // Try to create a new ClothingItem
            myClothingItem = new ClothingItem(barcode);
//          DEBUG System.out.println(myClothingItem.getEntryListView());
            // If the clothing item exists and is not "Received"
            if(myClothingItem != null)
            {
                if( myClothingItem.getState("Status").equals("Donated"))
                {
                   if(!cart.contains(barcode))
                   {
                       // Add the ClothingItem to a list
                       clothingItems.add(myClothingItem);
                       cart = cart + barcode.toUpperCase() + " ";
                       // Swap to a scene that asks if the user wishes to enter another barcode or continue onwards.
                       try
                       {
                           Scene newScene = createCheckoutHelperView();
                           System.out.println("Made it here");
                           swapToView(newScene);
                       }
                       catch (Exception ex)
                       {
                           new Event(Event.getLeafLevelClassName(this), "processTransaction",
                                   "Error in creating BarcodeHelperView", Event.ERROR);
                       }
                   }
                   else
                   {
                       barcodeError = barcode + " is already in the cart!";
                       handleBarcodeProblems();
                   }
                }
                else
                {
                    barcodeError = barcode + "'s status is not donated!";
                    handleBarcodeProblems();
                }

            }
            // Otherwise let the user know the barcode was not added to the list
            else
            {
                barcodeError = barcode + " does not exist!";
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
            barcodeError = barcode + " does not exist!";
          e.printStackTrace();
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

        if(clothingItems.size() > 0) {
            // Set receiver properties change status to received and update
            Iterator<ClothingItem> i = clothingItems.iterator();
            while (i.hasNext()) {

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
                if(((String) currItem.getState("UpdateStatusMessage")).contains("updated successfully"))
                {
                    transactionErrorMessage = transactionErrorMessage + currItem.getState("Barcode") + " ";
                }
            }
            transactionErrorMessage = transactionErrorMessage + " Updated!";
        }
        else
        {
            transactionErrorMessage = "There are no Clothing Items to update.";
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
        else if (key.equals("Cart") == true)
        {
            return cart;
        }
        else if (key.equals("BarcodeError") == true)
        {
            return barcodeError;
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

    protected Scene createCheckoutHelperView()
    {
        View newView = ViewFactory.createView("CheckoutHelperView", this);
        Scene currentScene = new Scene(newView);
        return currentScene;

    }
    protected Scene createCheckoutInvalidItemView()
    {
        View newView = ViewFactory.createView("CheckoutInvalidItemView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;
    }

    //handle barcode problems will display a screen to inform the user the barcode will not be added
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
