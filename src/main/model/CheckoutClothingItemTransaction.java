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

public class CheckoutClothingItemTransaction extends Transaction
{
    private ClothingItem myClothingItem;
    private InventoryItemCollection inventoryItems = new InventoryItemCollection();
    private Vector<ClothingItem> clothingItems = new Vector<ClothingItem>();

    // GUI Components
    private String transactionErrorMessage = "";
    //A string list of barcodes which will be updated upon entering user information
    private String cart = "";
    //A string to display to the user what error occurred in the gui
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
        //TODO ARE BARCODES ALWAYS UPPERCASE IN THE DATABASE?
        barcode = barcode.toUpperCase();
        try
        {
            myClothingItem = new ClothingItem(barcode);
//          DEBUG System.out.println(myClothingItem.getEntryListView());
            if(myClothingItem != null)
            {
                if( myClothingItem.getState("Status").equals("Donated"))
                {
                    //if the barcode has not already been added to the cart
                   if(!cart.contains(barcode))
                   {
                       // add the ClothingItem to a list
                       clothingItems.add(myClothingItem);
                       inventoryItems.findByBarCode(barcode);
                       // update the barcode list for the user
                       cart = generateCart();
                       // swap to a scene that asks if the user wishes to enter another barcode or continue onwards.
                       try
                       {
                           Scene newScene = createCheckoutHelperView();
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
//            while(i.hasNext())
//            {
//                System.out.println(i.next().getEntryListView());
//            }
        }
        catch (InvalidPrimaryKeyException e)
        {
            barcodeError = barcode + " does not exist!";
          //e.printStackTrace();
          handleBarcodeProblems();
        }
        catch (MultiplePrimaryKeysException e)
        {
            e.printStackTrace();
        }
    }

    private String generateCart()
    {
        String cart = "";
        ClothingItem temp = null;
        Iterator i = clothingItems.iterator();

        while(i.hasNext())
        {
            temp = (ClothingItem)i.next();
            cart = cart + temp.getState("Barcode") + " ";
        }

        return cart;
    }

    private void processReceiver(Properties props) {

        String receiverNetid = props.getProperty("ReceiverNetid");
        String receiverFirstName = props.getProperty("ReceiverFirstName");
        String receiverLastName = props.getProperty("ReceiverLastName");

        if(clothingItems.size() > 0)
        {
            // Set receiver properties change status to received and update
            Iterator<ClothingItem> i = clothingItems.iterator();

            while (i.hasNext())
            {
                ClothingItem currItem = i.next();
                currItem.stateChangeRequest("ReceiverNetid", receiverNetid);
                currItem.stateChangeRequest("ReceiverFirstName", receiverFirstName);
                currItem.stateChangeRequest("ReceiverLastName", receiverLastName);
                currItem.stateChangeRequest("Status", "Received");

                // Compose timestamp
                Calendar currDate = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                String date = dateFormat.format(currDate.getTime());
                currItem.stateChangeRequest("DateTaken", date);

//                System.out.println(myClothingItem.getEntryListView());
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
        if (key.equals("DoYourJob") == true)
        {
            doYourJob();
        }
        //The BarcodeScannerView should call here
        else if (key.equals("ProcessBarcode") == true)
        {
            processTransaction((Properties)value);
        }
        //The EnterReceiverInformationView should call here
        else if (key.equals("ReceiverData") == true)
        {
            processReceiver((Properties)value);
        }
        //The CheckoutHelperView and CheckoutInvalidItemView should call here if the user selects to
            // add another barcode
        else if (key.equals("MoreData") == true)
        {
            barcodeError = "";
           doYourJob();
        }
        //The CheckoutHelperView and CheckoutInvalidItemView should call here if the user selects checkout
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
        else if (key.equals("ClothingItems") == true)
        {
            return clothingItems;
        }
        else if(key.equals("InventoryList"))
        {
            return inventoryItems;
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
            Scene newScene = createCheckoutHelperView();
            swapToView(newScene);
        }
        catch (Exception ex)
        {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating CheckoutInvalidItemView", Event.ERROR);
        }
    }
}
