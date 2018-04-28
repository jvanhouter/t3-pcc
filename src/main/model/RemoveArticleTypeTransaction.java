package model;

import Utilities.Utilities;
import event.Event;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;


/**
 * The class containing the RemoveArticleTypeTransaction for the Professional Clothes Closet application
 */

//==============================================================
public class RemoveArticleTypeTransaction extends Transaction {

    private ArticleTypeCollection myArticleTypeList;
    private ArticleType mySelectedArticleType;


    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     */

    public RemoveArticleTypeTransaction() throws Exception {
        super();
    }


    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("CancelSearchArticleType", "CancelTransaction");
        dependencies.setProperty("RemoveArticleType", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the article type collection and showing the view
     */

    public void processTransaction(Properties props) {
        myArticleTypeList = new ArticleTypeCollection();
        if (props.getProperty("BarcodePrefix") != null) {
            String barcodePrefix = props.getProperty("BarcodePrefix");
            myArticleTypeList.findByBarcodePrefix(barcodePrefix);
        } else {
            String desc = props.getProperty("Description");
            String alfaC = props.getProperty("AlphaCode");
            myArticleTypeList.findByCriteria(desc, alfaC);
        }
        try {
            Scene newScene = createArticleTypeCollectionView();
            swapToView(newScene);
        } catch (Exception ex) {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating ArticleTypeCollectionView", Event.ERROR);
        }
    }

    /**
     * This method encapsulates all the logic of removing the article type,
     * verifying the new barcode, etc.
     */

    private void processArticleTypeRemoval() {
        if (mySelectedArticleType != null) {
            mySelectedArticleType.stateChangeRequest("Status", "Inactive");
            mySelectedArticleType.update();
            Utilities.removeArticleHashData((String) mySelectedArticleType.getState("ID"));
            transactionErrorMessage = (String) mySelectedArticleType.getState("UpdateStatusMessage");
        }
    }


    public Object getState(String key) {
        if (key.equals("ArticleTypeList")) {
            return myArticleTypeList;
        } else if (key.equals("BarcodePrefix")) {
            if (mySelectedArticleType != null)
                return mySelectedArticleType.getState("BarcodePrefix");
            else
                return "";
        } else if (key.equals("Description")) {
            if (mySelectedArticleType != null)
                return mySelectedArticleType.getState("Description");
            else
                return "";
        } else if (key.equals("AlphaCode")) {
            if (mySelectedArticleType != null)
                return mySelectedArticleType.getState("AlphaCode");
            else
                return "";
        } else if (key.equals("TransactionError")) {
            return transactionErrorMessage;
        }

        return null;
    }


    public void stateChangeRequest(String key, Object value) {
        // DEBUG System.out.println("RemoveArticleTypeTransaction.sCR: key: " + key);

        if ((key.equals("DoYourJob")) || (key.equals("CancelArticleTypeList") || (key.equals("CancelRemoveAT")))) {
            doYourJob();
        } else if (key.equals("SearchArticleType")) {
            processTransaction((Properties) value);
        } else if (key.equals("ArticleTypeSelected")) {
            mySelectedArticleType = myArticleTypeList.retrieve((String) value);
            try {

                Scene newScene = createRemoveArticleTypeView();

                swapToView(newScene);

            } catch (Exception ex) {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating RemoveArticleTypeView", Event.ERROR);
            }
        } else if (key.equals("RemoveArticleType")) {
            processArticleTypeRemoval();
        }
        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */

    protected Scene createView() {
        Scene currentScene = myViews.get("SearchArticleTypeView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("SearchArticleTypeView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchArticleTypeView", currentScene);

            return currentScene;
        } else {
            return currentScene;
        }
    }

    /**
     * Create the view containing the table of all matching article types on the search criteria sents
     */

    protected Scene createArticleTypeCollectionView() {
        View newView = ViewFactory.createView("ArticleTypeCollectionView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

    /**
     * Create the view using which data about selected article type can be modified
     */

    protected Scene createRemoveArticleTypeView() {
        View newView = ViewFactory.createView("RemoveArticleTypeView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

}

