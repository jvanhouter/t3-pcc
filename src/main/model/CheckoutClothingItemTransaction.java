// specify the package
package model;

// system imports
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import java.text.SimpleDateFormat;
import java.util.*;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;

// project imports
import userinterface.View;
import userinterface.ViewFactory;

public class CheckoutClothingItemTransaction extends Transaction
{
    private ClothingItem myClothingItem;
    private InventoryItemCollection inventoryItems = new InventoryItemCollection();
    private Vector<ClothingItem> clothingItems = new Vector<ClothingItem>();

    // GUI Components
    private String transactionErrorMessage = "";
    private String barcodeError = "";

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

    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        String barcode = props.getProperty("Barcode");
        barcode = barcode.toUpperCase();
        try
        {
            myClothingItem = new ClothingItem(barcode);
//          DEBUG System.out.println(myClothingItem.getEntryListView());
            if(myClothingItem != null)
            {
                if( myClothingItem.getState("Status").equals("Donated"))
                {
                    if(!barcodeAlreadyAdded(barcode))
                    {
                        clothingItems.add(myClothingItem);
                        inventoryItems.findByBarCode(barcode);
                        switchToCheckoutHelperView();
                    }
                    else
                    {
                        barcodeError = barcode + " is already in the cart.";
                        handleBarcodeProblems();
                    }
                }
                else
                {
                    barcodeError = barcode + " is not avalible for checkout. The clothing item associated with this barcode has the incorrect status.";
                    handleBarcodeProblems();
                }

            }
            else
            {
                barcodeError = barcode + " does not exist in the database.";
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
            barcodeError = barcode + " does not exist in the database.";
            handleBarcodeProblems();
        }
        catch (MultiplePrimaryKeysException e)
        {
            e.printStackTrace();
        }
    }

    private boolean barcodeAlreadyAdded(String barcode)
    {
        for(ClothingItem ci : clothingItems)
        {
            if(ci.getState("Barcode").equals(barcode))
            {
                return true;
            }
        }
        return false;
    }

    private void processReceiver(Properties props)
    {
        String receiverNetid = props.getProperty("ReceiverNetid");
        String receiverFirstName = props.getProperty("ReceiverFirstName");
        String receiverLastName = props.getProperty("ReceiverLastName");

        if(clothingItems.size() > 0)
        {
            int numUpdated = 0;
            Iterator<ClothingItem> i = clothingItems.iterator();

            // Compose timestamp
            Calendar currDate = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            String date = dateFormat.format(currDate.getTime());

            while (i.hasNext())
            {
                ClothingItem currItem = i.next();
                currItem.stateChangeRequest("ReceiverNetid", receiverNetid);
                currItem.stateChangeRequest("ReceiverFirstName", receiverFirstName);
                currItem.stateChangeRequest("ReceiverLastName", receiverLastName);
                currItem.stateChangeRequest("Status", "Received");
                currItem.stateChangeRequest("DateTaken", date);

                currItem.update();

                if(((String) currItem.getState("UpdateStatusMessage")).contains("updated successfully"))
                {
                    numUpdated++;
                    transactionErrorMessage = transactionErrorMessage + currItem.getState("Barcode") + " ";
                }
            }

            transactionErrorMessage = numUpdated + " clothing items associated with the following barcodes:\n\n" + transactionErrorMessage + "\n\nHave been checked out!";

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, transactionErrorMessage);
            alert.setTitle("Checkout");
            alert.setHeaderText("Clothing Items have been checked out.");
            alert.show();

        }
        else
        {
            transactionErrorMessage = "There are no Clothing Items to update.";
        }
    }

    public void stateChangeRequest(String key, Object value)
    {
        //DEBUG System.out.println("CheckoutClothingItemTransaction.sCR: key: " + key);
        if (key.equals("DoYourJob") == true)
        {
            doYourJob();
        }
        else if (key.equals("ProcessBarcode") == true)
        {
            processTransaction((Properties)value);
        }
        else if (key.equals("ReceiverData") == true)
        {
            processReceiver((Properties)value);
            stateChangeRequest("CancelCheckoutCI", null);
        }
        else if (key.equals("MoreData") == true)
        {
            barcodeError = "";
            switchToBarcodeScannerView();
        }
        else if (key.equals("NoMoreData") == true)
        {
            switchToEnterReceiverInformationView();
        }
        else if(key.equals("CancelBarcodeSearch") && clothingItems.size() > 0)
        {
            switchToCheckoutHelperView();
        }
        else
        {
            myRegistry.updateSubscribers(key, this);
        }
    }

    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
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

    private void switchToCheckoutHelperView()
    {
        Scene newScene = createCheckoutHelperView();
        swapToView(newScene);
    }

    private void switchToEnterReceiverInformationView()
    {
        Scene newScene = createEnterReceiverInformationView();
        swapToView(newScene);
    }

    private void switchToBarcodeScannerView()
    {
        Scene newScene = createBarcodeScannerView();
        swapToView(newScene);
    }

    protected Scene createEnterReceiverInformationView()
    {
        View newView = ViewFactory.createView("EnterReceiverInformationView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;
    }

    protected Scene createBarcodeScannerView()
    {
        View newView = ViewFactory.createView("BarcodeScannerView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;
    }

    protected Scene createCheckoutHelperView()
    {
        View newView = ViewFactory.createView("CheckoutHelperView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;
    }

    private void handleBarcodeProblems()
    {
        barcodeError = "The clothing item associated with barcode "+ barcodeError + " This clothing item will not be added to the checkout cart.";
        Alert alert = new Alert(Alert.AlertType.ERROR, barcodeError);
        alert.setTitle("Barcode Error");
        alert.setHeaderText("There is a problem with the item you wish to checkout.");
        alert.show();
    }
}