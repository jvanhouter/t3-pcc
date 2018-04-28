// specify the package
package model;

// system imports
import Utilities.Utilities;
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
        dependencies.setProperty("RemoveRequest", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    //----------------------------------------------------------
    public void processRequestRemoval()
    {
        if(myClothingRequest != null) {
            myClothingRequest.stateChangeRequest("Status", "Removed");
            myClothingRequest.update();
            Utilities.removeClothingRequestHash((String) myClothingRequest.getState("ID"));
            transactionErrorMessage = (String)myClothingRequest.getState("UpdateStatusMessage");
        }
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("RequestList") )
        {
            return myRequestCollection;
        }
        if (key.equals("TransactionError") )
        {
            return transactionErrorMessage;
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        if (key.equals("DoYourJob") )
        {
            doYourJob();
        }
        else
        if(key.equals("RequestSelected") == true) {
            // process removal
            myClothingRequest = myRequestCollection.retrieve((String) value);
            try
            {
                Scene newScene = createRemoveRequestView();
                swapToView(newScene);
            }
            catch (Exception ex)
            {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating RemoveRequestView", Event.ERROR);
            }
        } else
        if (key.equals("RemoveRequest") == true)
        {
            processRequestRemoval();
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

    //------------------------------------------------------
    protected Scene createRemoveRequestView() {
        View newView = ViewFactory.createView("RemoveRequestView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

}

