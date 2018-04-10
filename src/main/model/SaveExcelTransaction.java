// specify the package
package model;

// system imports

import event.Event;
import exception.InvalidPrimaryKeyException;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import userinterface.View;
import userinterface.ViewFactory;

import java.io.File;
import java.util.Properties;

// project imports

/** The class containing the AddArticleTypeTransaction for the Professional Clothes Closet application */
//==============================================================
public class SaveExcelTransaction extends Transaction
{
    // GUI Components

    private ArticleTypeCollection myArticleTypeList;
    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     */
    //----------------------------------------------------------
    public SaveExcelTransaction() throws Exception
    {
        super();

        myArticleTypeList = new ArticleTypeCollection();
        myArticleTypeList.findAll();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelAddCT", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("ColorData", "TransactionError");

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
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        if (key.equals("TransactionSaveExcel")) {
            return myStage;
        }
        if (key.equals("ArticleTypeList")) {
            return myArticleTypeList;
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
        if (key.equals("ColorData") == true)
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
        Scene currentScene = myViews.get("SaveExcelView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("SaveExcelView", this);
            currentScene = new Scene(newView);
            myViews.put("SaveExcelView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

}

