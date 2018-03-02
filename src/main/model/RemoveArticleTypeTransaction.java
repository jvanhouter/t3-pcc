package model;

import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import userinterface.View;
import userinterface.ViewFactory;

import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;


/** The class containing the UpdateArticleTypeTransaction for the Professional Clothes Closet application */
//==============================================================
public class RemoveArticleTypeTransaction extends Transaction
{

    private ArticleTypeCollection myArticleTypeList;
    private ArticleType mySelectedArticleType;


    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     */
    //----------------------------------------------------------
    public RemoveArticleTypeTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelSearchArticleType", "CancelTransaction");
        dependencies.setProperty("CancelAddAT", "CancelTransaction");
        dependencies.setProperty("ArticleTypeData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the article type collection and showing the view
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        myArticleTypeList = new ArticleTypeCollection();
        if (props.getProperty("BarcodePrefix") != null)
        {
            String barcodePrefix = props.getProperty("BarcodePrefix");
            myArticleTypeList.findByBarcodePrefix(barcodePrefix);
        }
        else
        {
            String desc = props.getProperty("Description");
            String alfaC = props.getProperty("AlphaCode");
            myArticleTypeList.findByCriteria(desc, alfaC); // SHOW FIND PARTIAL DESCRIPTION OR PERFECT MATCH:
                                                           // :ALSO SHOULD DO "OR" TO FIND ONE OR ANOTHER SINCE
                                                           // SINCE BOTH NOT NEEDED?
        }
        try
        {
            Scene newScene = createArticleTypeCollectionView();
            swapToView(newScene);
        }
        catch (Exception ex)
        {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating ArticleTypeCollectionView", Event.ERROR);
        }
    }

    /**
     * This method encapsulates all the logic of removing the article type,
     * verifying the new barcode, etc.
     */
    //----------------------------------------------------------
    private void processArticleTypeRemoval()
    {
        if(mySelectedArticleType != null) {
            mySelectedArticleType.stateChangeRequest("Status", "Inactive");

        }
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("ArticleTypeList") == true)
        {
            return myArticleTypeList;
        }
        else
        if (key.equals("BarcodePrefix") == true)
        {
            if (mySelectedArticleType != null)
                return mySelectedArticleType.getState("BarcodePrefix");
            else
                return "";
        }
        else
        if (key.equals("Description") == true)
        {
            if (mySelectedArticleType != null)
                return mySelectedArticleType.getState("Description");
            else
                return "";
        }
        else
        if (key.equals("AlphaCode") == true)
        {
            if (mySelectedArticleType != null)
                return mySelectedArticleType.getState("AlphaCode");
            else
                return "";
        }
        else
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }

        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // DEBUG System.out.println("RemoveArticleTypeTransaction.sCR: key: " + key);

        if ((key.equals("DoYourJob") == true) || (key.equals("CancelArticleTypeList") == true))
        {
            doYourJob();
        }
        else
        if (key.equals("SearchArticleType") == true)
        {
            processTransaction((Properties)value);
        }
        else
        if (key.equals("ArticleTypeSelected") == true)
        {
            mySelectedArticleType = myArticleTypeList.retrieve((String)value);
            try
            {

                Scene newScene = createRemoveArticleTypeView();

                swapToView(newScene);

            }
            catch (Exception ex)
            {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating RemoveArticleTypeView", Event.ERROR);
            }
        }
        else
        if (key.equals("RemoveArticleType")) {
            processArticleTypeRemoval();
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
        Scene currentScene = myViews.get("SearchArticleTypeView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("SearchArticleTypeView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchArticleTypeView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

    /**
     * Create the view containing the table of all matching article types on the search criteria sents
     */
    //------------------------------------------------------
    protected Scene createArticleTypeCollectionView()
    {
        View newView = ViewFactory.createView("ArticleTypeCollectionView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

    /**
     * Create the view using which data about selected article type can be modified
     */
    //------------------------------------------------------
    protected Scene createRemoveArticleTypeView()
    {
        View newView = ViewFactory.createView("RemoveArticleTypeView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

}

