// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;

import exception.MultiplePrimaryKeysException;
import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;


//==============================================================
public class InventoryItemCollection  extends EntityBase implements IView
{
    private static final String myTableName = "Inventory";

    private Vector<ClothingItem> inventoryItems;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public InventoryItemCollection( )
    {
        super(myTableName);

    }

    //-----------------------------------------------------------
    private void populateCollectionHelper(String query)
    {

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
//        System.out.println(allDataRetrieved.toString().replace("}", "\n"));
        if (allDataRetrieved != null)
        {
            inventoryItems = new Vector<ClothingItem>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextInventoryItemData = allDataRetrieved.elementAt(cnt);
                ClothingItem ci = new ClothingItem(nextInventoryItemData);
                try
                {
                    ArticleType tempAT = null;
                    if(ci.getState("ArticleType") != null)
                    {
                        //TODO asj jason if there should be single digit integers with leading zeros in the article type table for the prefix.
                        //TODO also ask jason if we should be instantiating entre objects or making an sql statement to retrieve just the description we need.
                        String key = "0" + (String) ci.getState("ArticleType");
                        tempAT = new ArticleType(key);
                        if(tempAT != null)
                        {
                            ci.stateChangeRequest("ArticleType", tempAT.getState("Description"));
                        }
                    }

                    ColorType tempColor = null;
                    if(ci.getState("Color1") != null)
                    {
                        tempColor = new ColorType((String) ci.getState("Color1"));
                        if(tempColor != null)
                        {
                            ci.stateChangeRequest("Color1", tempColor.getState("Description"));
                        }
                    }

                    if(ci.getState("Color2") != null)
                    {
                        tempColor = new ColorType((String) ci.getState("Color2"));
                        if(tempColor != null)
                        {
                            ci.stateChangeRequest("Color2", tempColor.getState("Description"));
                        }
                    }

//                    xstateChangeRequest("Color2", tempColor.getState("Description"));
                } catch (InvalidPrimaryKeyException | MultiplePrimaryKeysException e)
                {
                    e.printStackTrace();
                }

                if (ci != null)
                {
                    inventoryItems.add(ci);
//                    System.out.println(ci.getEntryListView());

                }
            }
        }
    }

    //-----------------------------------------------------------
    public void findByBarcodePrefix(String barcodePrefix)
    {
        String query = "SELECT * FROM " + myTableName + " WHERE ((BarcodePrefix = '" + barcodePrefix +
                "') AND (Status = 'Active'))";
        populateCollectionHelper(query);
    }

    //-----------------------------------------------------------
    public void findAll()
    {
        String query = "SELECT * FROM " + myTableName + " WHERE (Status = 'Donated')";
        populateCollectionHelper(query);
    }

    //-----------------------------------------------------------
    public void findByCriteria(String description, String alphaCode)
    {
        String query = "SELECT * FROM " + myTableName;
        if (((description != null) && (description.length() > 0))&&
                ((alphaCode != null) && (alphaCode.length() > 0)))
        {
            // both values get into criteria
            query += " WHERE ((Status = 'Active') AND (Description LIKE '%" + description + "%') AND (AlphaCode LIKE '%" +
                    alphaCode + "%'))";
        }
        else
        if ((description != null) && (description.length() > 0))
        {
            // only description gets into criteria
            query += " WHERE ((Status = 'Active') AND (Description LIKE '%" + description + "%'))";
        }
        else
        if ((alphaCode != null) && (alphaCode.length() > 0))
        {
            // only alphaCode gets into criteria
            query += " WHERE ((Status = 'Active') AND (AlphaCode LIKE '%" + alphaCode + "%'))";
        }
        else
        {
            query += " WHERE (Status = 'Active')";
        }

        populateCollectionHelper(query);
    }

    public void findUsingMonsterQuery(){
        String query = "SELECT inv.Barcode, inv.Gender, inv.Size, inv.Brand, c1.Description as color_1, c2.Description as color_2, atype.Description as article_type, inv.Notes, inv.Status\n" +
                "FROM inventory as inv \n" +
                "LEFT JOIN color as c1 on c1.ID=inv.Color1 \n" +
                "LEFT JOIN color as c2 on c2.ID=inv.Color2 \n" +
                "LEFT JOIN articletype as atype on atype.ID=inv.ArticleType";

        populateCollectionHelper(query);
    }


    //----------------------------------------------------------------------------------
//    private void addInventoryItem(ClothingItem ci)
//    {
//        int index = findIndexToAdd(ci);
//       inventoryItems.insertElementAt(ci,index); // To build up a collection sorted on some key
//    }

    //----------------------------------------------------------------------------------
    private int findIndexToAdd(ClothingItem ci)
    {
        int low=0;
        int high = inventoryItems.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            ClothingItem midSession = inventoryItems.elementAt(middle);

            int result = ClothingItem.compare(ci,midSession);

            if (result ==0)
            {
                return middle;
            }
            else if (result<0)
            {
                high=middle-1;
            }
            else
            {
                low=middle+1;
            }
        }
        return low;
    }


    /**
     *
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("InventoryItems"))
            return inventoryItems;
        else
        if (key.equals("InventoryList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public ClothingItem retrieve(String barcodePrefix)
    {
        ClothingItem retValue = null;
        for (int cnt = 0; cnt < inventoryItems.size(); cnt++)
        {
            ClothingItem nextCI = inventoryItems.elementAt(cnt);
            String nextBarcodePrefix = (String)nextCI.getState("BarcodePrefix");
            if (nextBarcodePrefix.equals(barcodePrefix) == true)
            {
                retValue = nextCI;
                return retValue; // we should say 'break;' here
            }
        }

        return retValue;
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //------------------------------------------------------
    protected void createAndShowView()
    {

        Scene localScene = myViews.get("InventoryItemCollectionView");

        if (localScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("InventoryItemCollectionView", this);
            localScene = new Scene(newView);
            myViews.put("InventoryItemCollectionView", localScene);
        }
        // make the view visible by installing it into the frame
        swapToView(localScene);

    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}