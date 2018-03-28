// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;

// project imports
import event.Event;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the RemoveColorTransaction for the Professional Clothes Closet application */
//==============================================================
public class RemoveColorTransaction extends Transaction {

    private ColorCollection myColorList;
    private Color mySelectedColor;


    // GUI Components

    private String transactionErrorMessage = "";
    private String colorRemoveStatusMessage = "";

    /**
     * Constructor for this class.
     */
    //----------------------------------------------------------
    public RemoveColorTransaction() throws Exception {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelSearchColor", "CancelTransaction");
        dependencies.setProperty("CancelRemoveCT", "CancelTransaction");
        dependencies.setProperty("RemoveColor", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the article type,
     * verifying its uniqueness, etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props) {
        myColorList = new ColorCollection();
		String desc = props.getProperty("Description");
		String alfaC = props.getProperty("AlphaCode");
		myColorList.findByCriteria(desc, alfaC);
        
        try
        {
            Scene newScene = createColorCollectionView();
            swapToView(newScene);
        } catch (Exception ex) {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating ColorCollectionView", Event.ERROR);
        }

    }

    private void processColorRemoval()
    {
        if(mySelectedColor != null) {
            mySelectedColor.stateChangeRequest("Status", "Inactive");
            mySelectedColor.update();
            transactionErrorMessage = (String)mySelectedColor.getState("UpdateStatusMessage");
        }
    }


    //-----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("TransactionError") == true) {
            return transactionErrorMessage;
        } 
		else
		if (key.equals("ColorList") == true) {
			return myColorList;
		}
		else
        if (key.equals("BarcodePrefix") == true)
        {
            if (mySelectedColor != null)
                return mySelectedColor.getState("BarcodePrefix");
            else
                return "";
        }
        else
        if (key.equals("Description") == true)
        {
            if (mySelectedColor != null)
                return mySelectedColor.getState("Description");
            else
                return "";
        }
        else
        if (key.equals("AlphaCode") == true)
        {
            if (mySelectedColor != null)
                return mySelectedColor.getState("AlphaCode");
            else
                return "";
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        if ((key.equals("DoYourJob") == true) || (key.equals("CancelColorList") == true))
        {
            doYourJob();
        }
        else
        if (key.equals("SearchColor") == true)
        {
            processTransaction((Properties)value);
        }
        else
        if (key.equals("ColorSelected") == true)
        {
            mySelectedColor = myColorList.retrieve((String)value);
            try
            {

                Scene newScene = createRemoveColorView();

                swapToView(newScene);

            }
            catch (Exception ex)
            {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating RemoveColorView", Event.ERROR);
            }
        }
        else
        if (key.equals("RemoveColor") == true)
        {
            processColorRemoval();
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView() {
        Scene currentScene = myViews.get("SearchColorView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("SearchColorView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchColorView", currentScene);

            return currentScene;
        } else {
            return currentScene;
        }
    }

    protected Scene createColorCollectionView() {
        View newView = ViewFactory.createView("ColorCollectionView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }


    //------------------------------------------------------
    protected Scene createRemoveColorView() {
        View newView = ViewFactory.createView("RemoveColorView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

}

