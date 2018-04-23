package model;

import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

/**
 * Jan 30, 2018
 * @author Jackson Taber & Kyle Darling
 */

public class UpdateColorTransaction extends Transaction {

    private ColorCollection myColorList;
    private Color mySelectedColor;

    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     */

    public UpdateColorTransaction() throws Exception
    {
        super();
    }


    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelSearchColor", "CancelTransaction");
        dependencies.setProperty("CancelAddCT", "CancelTransaction");
        dependencies.setProperty("ColorData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the article type collection and showing the view
     */

    public void processTransaction(Properties props)
    {
        myColorList = new ColorCollection();
        
		String desc = props.getProperty("Description");
		String alfaC = props.getProperty("AlphaCode");
		myColorList.findByCriteria(desc, alfaC);
        
        try
        {
            Scene newScene = createColorCollectionView();
            swapToView(newScene);
        }
        catch (Exception ex)
        {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating ColorCollectionView", Event.ERROR);
        }
    }

    /**
     * Helper method for color type update
     */

    private void colorModificationHelper(Properties props)
    {
        String descriptionOfAT = props.getProperty("Description");
        if (descriptionOfAT.length() > 30)
        {
            transactionErrorMessage = "ERROR: Color Description too long! ";
        }
        else
        {
            String alphaCode = props.getProperty("AlphaCode");
            if (alphaCode.length() > 5)
            {
                transactionErrorMessage = "ERROR: Alpha code too long (max length = 5)! ";
            }
            else
            {
                // Everything OK

                mySelectedColor.stateChangeRequest("Description", descriptionOfAT);
                mySelectedColor.stateChangeRequest("AlphaCode", alphaCode);
                mySelectedColor.update();
                transactionErrorMessage = (String)mySelectedColor.getState("UpdateStatusMessage");
            }
        }
    }

    /**
     * This method encapsulates all the logic of modifiying the article type,
     * verifying the new barcode, etc.
     */

    private void processColorModification(Properties props)
    {
        if (props.getProperty("BarcodePrefix") != null)
        {
            String barcodePrefix = props.getProperty("BarcodePrefix");
            String originalBarcodePrefix = (String)mySelectedColor.getState("BarcodePrefix");
            if (barcodePrefix.equals(originalBarcodePrefix) == false)
            {
                try
                {
                    Color oldArticleType = new Color(barcodePrefix);
                    transactionErrorMessage = "ERROR: Barcode Prefix " + barcodePrefix
                            + " already exists!";
                    new Event(Event.getLeafLevelClassName(this), "processTransaction",
                            "Color with barcode prefix : " + barcodePrefix + " already exists!",
                            Event.ERROR);
                }
                catch (InvalidPrimaryKeyException ex)
                {
                    // Barcode prefix does not exist, validate data
                    try
                    {
                        int barcodePrefixVal = Integer.parseInt(barcodePrefix);
                        // Barcode prefix ok, so set it
                        mySelectedColor.stateChangeRequest("BarcodePrefix", barcodePrefix);
                        // Process the rest (description, alpha code). Helper does all that
                        colorModificationHelper(props);
                    }
                    catch (Exception excep)
                    {
                        transactionErrorMessage = "ERROR: Invalid barcode prefix: " + barcodePrefix
                                + "! Must be numerical.";
                        new Event(Event.getLeafLevelClassName(this), "processTransaction",
                                "Invalid barcode prefix : " + barcodePrefix + "! Must be numerical.",
                                Event.ERROR);
                    }

                }
                catch (Exception ex2)
                {
                    transactionErrorMessage = "ERROR: Multiple colors with barcode prefix!";
                    new Event(Event.getLeafLevelClassName(this), "processTransaction",
                            "Found multiple article types with barcode prefix : " + barcodePrefix + ". Reason: " + ex2.toString(),
                            Event.ERROR);

                }
            }
            else
            {
                // No change in barcode prefix, so just process the rest (description, alpha code). Helper does all that
                colorModificationHelper(props);
            }

        }

    }


    public Object getState(String key)
    {
        if (key.equals("ColorList") )
        {
            return myColorList;
        }
        else
        if (key.equals("BarcodePrefix") )
        {
            if (mySelectedColor != null)
                return mySelectedColor.getState("BarcodePrefix");
            else
                return "";
        }
        else
        if (key.equals("Description") )
        {
            if (mySelectedColor != null)
                return mySelectedColor.getState("Description");
            else
                return "";
        }
        else
        if (key.equals("AlphaCode") )
        {
            if (mySelectedColor != null)
                return mySelectedColor.getState("AlphaCode");
            else
                return "";
        }
        else
        if (key.equals("TransactionError") )
        {
            return transactionErrorMessage;
        }

        return null;
    }


    public void stateChangeRequest(String key, Object value)
    {

        if ((key.equals("DoYourJob") ) || (key.equals("CancelColorList") ))
        {
            doYourJob();
        }
        else
        if (key.equals("SearchColor") )
        {
            processTransaction((Properties)value);
        }
        else
        if (key.equals("ColorSelected") )
        {
            mySelectedColor = myColorList.retrieve((String)value);
            try
            {

                Scene newScene = createModifyColorView();

                swapToView(newScene);

            }
            catch (Exception ex)
            {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating ModifyColorView", Event.ERROR);
            }
        }
        else
        if (key.equals("ColorData") )
        {
            processColorModification((Properties)value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */

    protected Scene createView()
    {
        Scene currentScene = myViews.get("SearchColorView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("SearchColorView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchColorView", currentScene);

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

    protected Scene createColorCollectionView()
    {
        View newView = ViewFactory.createView("ColorCollectionView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

    /**
     * Create the view using which data about selected article type can be modified
     */

    protected Scene createModifyColorView()
    {
        View newView = ViewFactory.createView("ModifyColorView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

}

