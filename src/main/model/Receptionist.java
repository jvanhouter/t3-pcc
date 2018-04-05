// specify the package //This is a test change, in the notes - JVH
package model;

// system imports

import event.Event;
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

import java.util.Hashtable;
import java.util.Properties;

// project imports

/**
 * The class containing the Receptionist  for the ATM application
 */
//==============================================================
public class Receptionist implements IView, IModel
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
    // For Impresario
    private Properties dependencies;
    private ModelRegistry myRegistry;


    // GUI Components
    private Hashtable<String, Scene> myViews;
    private Stage myStage;

    private String transactionErrorMessage = "";
    private String historyEvent = "";

    // constructor for this class
    //----------------------------------------------------------
    public Receptionist()
    {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        // STEP 3.1: Create the Registry object - if you inherit from
        // EntityBase, this is done for you. Otherwise, you do it yourself
        myRegistry = new ModelRegistry("Receptionist");
        if (myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "Receptionist",
                    "Could not instantiate Registry", Event.ERROR);
        }

        // STEP 3.2: Be sure to set the dependencies correctly
        setDependencies();

        // Set up the initial view
        createAndShowReceptionistView();
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies()
    {
        dependencies = new Properties();
        myRegistry.setDependencies(dependencies);
    }

    /**
     * Method called from client to get the value of a particular field
     * held by the objects encapsulated by this object.
     *
     * @param    key    Name of database column (field) for which the client wants the value
     * @return Value associated with the field
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        return "";
    }
    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)

    {
        // STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Receptionist.sCR: key = " + key);

        if (key.equals("CancelTransaction"))
        {
            createAndShowReceptionistView();
        }
        else if (key.equals("ExitSystem"))
        {
            System.exit(0);
        }
        else if ((key.equals("AddArticleType")) || (key.equals("UpdateArticleType")) ||
                (key.equals("RemoveArticleType")) || (key.equals("AddColor")) ||
                (key.equals("UpdateColor")) || (key.equals("RemoveColor")) ||
                (key.equals("AddClothingItem")) || (key.equals("UpdateClothingItem")) ||
                (key.equals("RemoveClothingItem")) || (key.equals("CheckoutClothingItem")) ||
                (key.equals("LogRequest")) || (key.equals("FulfillRequest")) ||
                (key.equals("RemoveRequest")) || (key.equals("ListAvailableInventory")))
        {
            String transType = key;

            transType = transType.trim();

            doTransaction(transType);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Called via the IView relationship
     */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        // DEBUG System.out.println("Receptionist.updateState: key: " + key);

        stateChangeRequest(key, value);
    }

    /**
     * Create a Transaction depending on the Transaction type (deposit,
     * withdraw, transfer, etc.). Use the AccountHolder holder data to do the
     * create.
     */
    //----------------------------------------------------------
    public void doTransaction(String transactionType)
    {
        try {
            Transaction trans = TransactionFactory.createTransaction(transactionType);

            trans.subscribe("CancelTransaction", this);
            trans.stateChangeRequest("DoYourJob", "");
        } catch (Exception ex)
        {
            transactionErrorMessage = "FATAL ERROR: TRANSACTION FAILURE: Unrecognized transaction!!";
            new Event(Event.getLeafLevelClassName(this), "createTransaction",
                    "Transaction Creation Failure: Unrecognized transaction " + ex.toString(),
                    Event.ERROR);
        }
    }


    //------------------------------------------------------------
    private void createAndShowReceptionistView()
    {
        Scene currentScene = (Scene) myViews.get("ReceptionistView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("ReceptionistView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("ReceptionistView", currentScene);
        }
        swapToView(currentScene);
    }

    /**
     * Register objects to receive state updates.
     */
    //----------------------------------------------------------
    public void subscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
        // forward to our registry
        myRegistry.subscribe(key, subscriber);
    }

    /**
     * Unregister previously registered objects.
     */
    //----------------------------------------------------------
    public void unSubscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager.unSubscribe");
        // forward to our registry
        myRegistry.unSubscribe(key, subscriber);
    }
    //-----------------------------------------------------------------------------
    public void swapToView(Scene newScene)
    {
        if (newScene == null)
        {
            System.out.println("Receptionist.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        myStage.setScene(newScene);
        myStage.sizeToScene();

        //Place in center
        WindowPosition.placeCenter(myStage);
    }
}





