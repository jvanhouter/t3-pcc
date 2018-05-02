// specify the package
package model;

// system imports

import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

// project imports

/**
 * The class containing the List Inventory for the Professional Clothes Closet application
 */
//==============================================================
public class ListInventoryTransaction extends Transaction {

    private InventoryItemCollection myInvList;

    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     */
    //----------------------------------------------------------
    public ListInventoryTransaction() throws Exception {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelInventory", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    //-----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("TransactionError")) {
            return transactionErrorMessage;
        } else if (key.equals("InventoryList")) {
            return myInvList;
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        if ((key.equals("DoYourJob"))) {
            doYourJob();
        }else if (key.equals("Filter"))
        {
            processFilter((String) value);
        }
        myRegistry.updateSubscribers(key, this);
    }

    private void processFilter(String value)
    {
        myInvList = new InventoryItemCollection();
        myInvList.findCustom(value);

        switchToInventoryItemCollectionView();
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView() {

        Scene currentScene = null;

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("ListInventoryFilterView", this);
            currentScene = new Scene(newView);
            myViews.put("ListInventoryFilterView", currentScene);

            return currentScene;
        } else {
            return currentScene;
        }
    }
    protected Scene createInventoryItemCollectionView() {
        View newView = ViewFactory.createView("InventoryItemCollectionView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;
    }

    private void switchToInventoryItemCollectionView() {
        Scene newScene = createInventoryItemCollectionView();
        swapToView(newScene);
    }
}

