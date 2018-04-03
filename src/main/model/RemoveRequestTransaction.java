// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.text.SimpleDateFormat;
import java.util.Date;
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
public class RemoveRequestTransaction extends Transaction
{

    private RequestCollection myRequestCollection;
    private ClothingRequest myClothingRequest;


    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     */
    //----------------------------------------------------------
    public RemoveRequestTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelRequest", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("RemoveData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the article type,
     * verifying its uniqueness, etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {

    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("RequestList") == true)
        {
            return myRequestCollection;
        }
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        if (key.equals("DoYourJob") == true)
        {
            doYourJob();
        }
        else
        if(key.equals("RequestSelected") == true) {
            // process removal
        } else
        if (key.equals("ClothingRequestData") == true)
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

        myRequestCollection = new RequestCollection();
        myRequestCollection.findAll();

        Scene currentScene = myViews.get("RequestCollectionView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("RequestCollectionView", this);
            currentScene = new Scene(newView);
            myViews.put("RequestCollectionView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

}

