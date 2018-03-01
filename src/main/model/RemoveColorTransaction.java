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

/** The class containing the AddArticleTypeTransaction for the Professional Clothes Closet application */
//==============================================================
public class RemoveColorTransaction extends Transaction
{

    private ColorCollection myColorList;
    private ColorType mySelectedColor;


    // GUI Components

    private String transactionErrorMessage = "";
    private String colorRemoveStatusMessage = "";

    /**
     * Constructor for this class.
     *
     */
    //----------------------------------------------------------
    public RemoveColorTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelRemoveTransaction", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("ColorTypeData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the article type,
     * verifying its uniqueness, etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        if (props.getProperty("BarcodePrefix") != null)
        {
            String barcodePrefix = props.getProperty("BarcodePrefix");
            myColorList.findByBarcodePrefix(barcodePrefix);
        }
        else
        {
            String desc = props.getProperty("Description");
            String alfaC = props.getProperty("AlphaCode");
            myColorList.findByCriteria(desc, alfaC);
        }
        try
        {
            // fix for later
            //Scene newScene = createColorCollectionView();
            //swapToView(newScene);
        }
        catch (Exception ex)
        {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating ColorCollectionView", Event.ERROR);
        }

    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }


        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // DEBUG System.out.println("AddArticleTypeTransaction.sCR: key: " + key);

        if (key.equals("DoYourJob") == true)
        {
            doYourJob();
        }
        else
        if (key.equals("ArticleTypeData") == true)
        {
            processTransaction((Properties)value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView()
    {
        Scene currentScene = myViews.get("AddArticleTypeView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("AddArticleTypeView", this);
            currentScene = new Scene(newView);
            myViews.put("AddArticleTypeView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

}

