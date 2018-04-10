package model;

// system imports

import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

// project imports

/**
 * The class containing the ModifyClothingItemTransaction for the Professional Clothes Closet application
 */
//==============================================================
public class ModifyClothingItemTransaction extends Transaction {

    private ClothingItemCollection myClothingItemList;
    private ClothingItem mySelectedClothingItem;

    private ArticleTypeCollection myArticleTypeList;
    private ColorCollection myColorList;
    private String gender;

    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     */
    //----------------------------------------------------------
    public ModifyClothingItemTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelSearchClothingItem", "CancelTransaction");
        dependencies.setProperty("CancelBarcodeSearch", "CancelTransaction");
        dependencies.setProperty("CancelAddCI", "CancelTransaction");
        dependencies.setProperty("ClothingItemData", "TransactionError");
        dependencies.setProperty("ProcessBarcode", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the clothing item collection and showing the view
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        myClothingItemList = new ClothingItemCollection();

        if (props.getProperty("Barcode") != null)
        {
            String barcode = props.getProperty("Barcode");
            myClothingItemList.findByBarcode(barcode);
        }

        try
        {
            Scene newScene = createClothingItemCollectionView();
            swapToView(newScene);
        } catch (Exception ex)
        {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating ClothingItemCollectionView", Event.ERROR);
        }
    }

    /**
     * Helper method for clothing item update
     */
    //--------------------------------------------------------------------------
    private void clothingItemModificationHelper(Properties props)
    {
        String barcode = props.getProperty("Barcode");
        if (barcode.length() > 12)    //needs to be changed to proper length.
        {
            transactionErrorMessage = "ERROR: barcode too long! ";
        } else if (barcode.length() <= 0)    //needs to be changed to proper length.
        {
            transactionErrorMessage = "ERROR: please enter barcode";
        } else {
            String gender = props.getProperty("Gender");
            if (gender.length() > 5)
            {
                transactionErrorMessage = "ERROR: Gender too long (max length = 5)! ";
            } else if (gender.length() <= 0)
            {
                transactionErrorMessage = "Please Enter Gender ";
            } else
                {
                String articleType = props.getProperty("ArticleType");
                if (barcode.length() > 30)    //needs to be changed to proper length.
                {
                    transactionErrorMessage = "ERROR: ArticleType too long! ";
                }
                if (barcode.length() <= 0)    //needs to be changed to proper length.
                {
                    transactionErrorMessage = "Please enter Article Type ";
                }
                else
                    {
                    String color1 = props.getProperty("Color1");
                    if (color1.length() > 30)    //needs to be changed to proper length.
                    {
                        transactionErrorMessage = "ERROR: Color1 too long! ";
                    }
                    else if (color1.length() <= 0)    //needs to be changed to proper length.
                    {
                        transactionErrorMessage = "please enter a a color ";
                    }
                    else {
                        String color2 = props.getProperty("Color2");
                        if (color2.length() > 30)    //needs to be changed to proper length.
                        {
                            transactionErrorMessage = "ERROR: Color2 too long! ";
                        }
                        else
                            {
                            String brand = props.getProperty("Brand");
                            if (brand.length() > 30)    //needs to be changed to proper length.
                            {
                                transactionErrorMessage = "ERROR: Brand too long! ";
                            }
                            else if (brand.length() <= 0)    //needs to be changed to proper length.
                            {
                                transactionErrorMessage = "please enter a brand ";
                            }
                            else
                                {
                                String donorInfo = props.getProperty("DonorInformation");
                                if (donorInfo.length() > 100)    //needs to be changed to proper length.
                                {
                                    transactionErrorMessage = "ERROR: DonorInformation too long! ";
                                }
                                else if (donorInfo.length() <= 0)    //needs to be changed to proper length.
                                {
                                    transactionErrorMessage = "please enter donor info ";
                                }
                                else
                                    {
                                    String notes = props.getProperty("Notes");
                                    if (notes.length() > 100)    //needs to be changed to proper length.
                                    {
                                        transactionErrorMessage = "ERROR: notes too long! ";
                                    }
                                    else
                                        {

                                        mySelectedClothingItem.stateChangeRequest("Barcode", barcode);
                                        mySelectedClothingItem.stateChangeRequest("Gender", gender);
                                        mySelectedClothingItem.stateChangeRequest("ArticleType", articleType);
                                        mySelectedClothingItem.stateChangeRequest("Color1", color1);
                                        mySelectedClothingItem.stateChangeRequest("Color2", color2);
                                        mySelectedClothingItem.stateChangeRequest("Brand", brand);
                                        mySelectedClothingItem.stateChangeRequest("DonorInformation", donorInfo);
                                        mySelectedClothingItem.stateChangeRequest("Notes", notes);
                                        mySelectedClothingItem.update();
                                        transactionErrorMessage = (String) mySelectedClothingItem.getState("UpdateStatusMessage");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method encapsulates all the logic of modifiying the clothing item,
     * verifying the new barcode, etc.
     */
    //----------------------------------------------------------
    private void processClothingItemModification(Properties props)
    {
        if (props.getProperty("Barcode") != null)
        {
            String barcode = props.getProperty("Barcode");
            String originalBarcode = (String) mySelectedClothingItem.getState("Barcode");
            if (barcode.equals(originalBarcode) == false)
            {
                try {
                    ClothingItem oldClothingItem = new ClothingItem(barcode);
                    transactionErrorMessage = "ERROR: Barcode  " + barcode
                            + " already exists!";
                    new Event(Event.getLeafLevelClassName(this), "processTransaction",
                            "clothing item with barcode : " + barcode + " already exists!",
                            Event.ERROR);
                } catch (InvalidPrimaryKeyException ex)
                {
                    // Barcode prefix does not exist, validate data
                    try {
                        int barcodeVal = Integer.parseInt(barcode);
                        // Barcode prefix ok, so set it
                        mySelectedClothingItem.stateChangeRequest("Barcode", barcode);
                        // Process the rest (description, alpha code). Helper does all that
                        clothingItemModificationHelper(props);
                    } catch (Exception excep)
                    {
                        transactionErrorMessage = "ERROR: Invalid barcode: " + barcode
                                + "! Must be numerical.";
                        new Event(Event.getLeafLevelClassName(this), "processTransaction",
                                "Invalid barcode : " + barcode + "! Must be numerical.",
                                Event.ERROR);
                    }

                }
                catch (MultiplePrimaryKeysException ex2)
                {
                    transactionErrorMessage = "ERROR: Multiple clothing items with barcode prefix!";
                    new Event(Event.getLeafLevelClassName(this), "processTransaction",
                            "Found multiple clothing items with barcode prefix : " + barcode + ". Reason: " + ex2.toString(),
                            Event.ERROR);

                }
            } else
                {
                // No change in barcode, so just process the rest (description, alpha code). Helper does all that
                clothingItemModificationHelper(props);
            }

        }

    }

    //-----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("ClothingItemList") == true) {
            return myClothingItemList;
        } else if (key.equals("Barcode") == true) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("Barcode");
            else
                return "";
        } else if (key.equals("Gender") == true) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("Gender");
            else
                return "";
        } else if (key.equals("Color1") == true) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("Color1");
            else
                return "";
        } else if (key.equals("Color2") == true) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("Color2");
            else
                return "";
        } else if (key.equals("Brand") == true) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("Brand");
            else
                return "";
        } else if (key.equals("ArticleType") == true) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("ArticleType");
            else
                return "";
        } else if (key.equals("DonorInformation") == true) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("DonorInformation");
            else
                return "";
        } else if (key.equals("Notes") == true) {
            if (mySelectedClothingItem != null)
                return mySelectedClothingItem.getState("Notes");
            else
                return "";
        } else if (key.equals("TransactionError") == true) {
            return transactionErrorMessage;
        } else if (key.equals("Gender")) {
            return gender;
        } else if (key.equals("Articles")) {
            return myArticleTypeList.retrieveAll();
        } else if (key.equals("Colors")) {
            return myColorList.retrieveAll();
        }
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        // DEBUG System.out.println("ModifyClothingItemTransaction.sCR: key: " + key);

        if ((key.equals("DoYourJob") == true) || (key.equals("CancelClothingItemList") == true)) {
            doYourJob();
        } else if (key.equals("ProcessBarcode")) {
            processTransaction((Properties) value);
        } else if (key.equals("ClothingItemSelected") == true) {
            mySelectedClothingItem = myClothingItemList.retrieve((String) value);
            myArticleTypeList = new ArticleTypeCollection();
            myArticleTypeList.findAll();
            myColorList = new ColorCollection();
            myColorList.findAll();
            String barcode = (String) mySelectedClothingItem.getState("Barcode");
            if(barcode.substring(0, 1).equals("1"))
                gender = "Mens";
            else
                gender = "Womens";
            try {

                Scene newScene = createModifyClothingItemView();

                swapToView(newScene);

            } catch (Exception ex) {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating ModifyClothingItemView", Event.ERROR);
                ex.printStackTrace();
            }
        } else if (key.equals("ClothingItemData") == true) {
            processClothingItemModification((Properties) value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView() {
        Scene currentScene = myViews.get("BarcodeScannerView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("BarcodeScannerView", this);
            currentScene = new Scene(newView);
            myViews.put("BarcodeScannerView", currentScene);

            return currentScene;
        } else {
            return currentScene;
        }
    }

    /**
     * Create the view containing the table of all matching clothing items on the search criteria sents
     */
    //------------------------------------------------------
    protected Scene createClothingItemCollectionView() {
        View newView = ViewFactory.createView("ClothingItemCollectionView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

    /**
     * Create the view using which data about selected clothing item can be modified
     */
    //------------------------------------------------------
    protected Scene createModifyClothingItemView() {
        View newView = ViewFactory.createView("ModifyClothingItemView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

}

