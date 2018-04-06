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
public class LogRequestTransaction extends Transaction
{

    private ClothingRequest myClothingRequest;

    private ArticleTypeCollection myArticleTypeList;
    private ColorCollection myColorList;
    private String gender = "";

    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     */
    //----------------------------------------------------------
    public LogRequestTransaction() throws Exception
    {
        super();
        myArticleTypeList = new ArticleTypeCollection();
        myArticleTypeList.findAll();
        myColorList = new ColorCollection();
        myColorList.findAll();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelLogRequest", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");
        dependencies.setProperty("ClothingRequestData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the article type,
     * verifying its uniqueness, etc.
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        // in relation to step 3 requested by step 5 - Log A Request
        if(props.getProperty("RequesterNetid") != null &&
                props.getProperty("RequestedGender") != null &&
                props.getProperty("RequestedArticleType") != null &&
                props.getProperty("RequestedColor1") != null &&
                props.getProperty("RequestedSize") != null)
        {

            String netId = props.getProperty("RequesterNetid");
            String gender = props.getProperty("RequestedGender");
            String articleType = props.getProperty("RequestedArticleType");
            String color1 = props.getProperty("RequestedColor1");
            String size = props.getProperty("RequestedSize");

            String color2 = "";
            if(props.getProperty("RequestedColor2") != null)
                color2 = props.getProperty("RequestedColor2");
            String brand = "";
            if(props.getProperty("RequestedBrand") != null)
                brand = props.getProperty("RequestedBrand");

            try
            {
                ClothingRequest oldClothingRequest = new ClothingRequest(netId, gender, articleType, size, color1, color2, brand);
                transactionErrorMessage = "ERROR: Requester ID : " + netId + " of request for " + gender + ", " + articleType + ", " + size + ", " + size + ", " + color1 + ", " + color2 + ", " + brand + " already exists!";
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        transactionErrorMessage,
                        Event.ERROR);
            }
            catch (InvalidPrimaryKeyException ex)
            {
                // Barcode prefix does not exist, validate data
                try
                {
                    int netIdInt = Integer.parseInt(netId);
                    String phoneNumber = props.getProperty("RequesterPhone");
                    String firstName = props.getProperty("RequesterFirstName");
                    String lastName = props.getProperty("RequesterLastName");

                    /*
                    Perform specific checks on data before updating into clothing request
                     */

                    props.setProperty("Status", "Pending");

                    Date date = new Date();
                    String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date); // sql may only take yyyy-mm-dd
                    props.setProperty("RequestMadeDate", modifiedDate);

                    myClothingRequest = new ClothingRequest(props);
                    myClothingRequest.update();
                    transactionErrorMessage = (String) myClothingRequest.getState("UpdateStatusMessage");
                }
                catch (Exception excep)
                {
                    transactionErrorMessage = "ERROR: Invalid requester net id : " + netId
                            + "! Must be numerical.";
                    new Event(Event.getLeafLevelClassName(this), "processTransaction",
                            transactionErrorMessage,
                            Event.ERROR);
                }
            }
            catch (MultiplePrimaryKeysException ex2)
            {
                transactionErrorMessage = "ERROR: Mutliple clothing requests for specified information provided!";
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Found multiple clothing requests with requester id : " + netId + ". Reason: " + ex2.toString(),
                        Event.ERROR);

            }
        }
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }else if (key.equals("Gender")) {
            return gender;
        } else if (key.equals("Articles")) {
            return myArticleTypeList.retrieveAll();
        } else if (key.equals("Colors")) {
            return myColorList.retrieveAll();
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
        Scene currentScene = myViews.get("LogRequestView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("LogRequestView", this);
            currentScene = new Scene(newView);
            myViews.put("LogRequestView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

}

