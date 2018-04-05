package model;

//System imports
import exception.InvalidPrimaryKeyException;
import impresario.IView;
import javafx.scene.Scene;
import model.ClothingItem;
import model.EntityBase;
import userinterface.View;
import userinterface.ViewFactory;

//Project imports
import java.util.Properties;
import java.util.Vector;

public class ListInventoryCollection  extends EntityBase implements IView
{
    private static final String myTableName = "Inventory";


    //Make sure that we account for an empty inventory - JVH
    private Vector<ClothingItem> invList;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    // Replaced words: AccountCollection = InventoryCollection, accounts = list, Account = inventory,
    //                 nextAcct = nextItem, ListInventoryView = InventoryCollectionView,
    //                 nextAccountData = ---------, nextAccNum = ---------,
    //----------------------------------------------------------
    public ListInventoryCollection( ) throws
            Exception
    {
        super(myTableName);

        String query = "SELECT inv.Barcode, inv.Gender, inv.Size, inv.Brand, c1.Description as color_1, c2.Description as color_2, atype.Description as article_type, inv.Notes, inv.Status\n" +
                "FROM inventory as inv \n" +
                "LEFT JOIN color as c1 on c1.ID=inv.Color1 \n" +
                "LEFT JOIN color as c2 on c2.ID=inv.Color2 \n" +
                "LEFT JOIN articletype as atype on atype.ID=inv.ArticleType";

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {
            invList = new Vector<ClothingItem>();


            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextInventoryItem = (Properties)allDataRetrieved.elementAt(cnt);

                ClothingItem item = new ClothingItem(nextInventoryItem);
            }

        }
        else
        {
            throw new InvalidPrimaryKeyException("No items in inventory");
        }

    }

    //----------------------------------------------------------------------------------


    // keys we are using? Doublecheck this - JVH


    public Object getState(String key)
    {
        if (key.equals("Inventory"))
        {
            return invList;  //change
            }
        else if (key.equals("InventoryList"))
        {
            return this;
            }
            else{

        return null;
        }
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
   /* public ClothingItem inventoryRetrieve(String barcode)
    {
        ClothingItem retValue = null;
        for (int cnt = 0; cnt < invList.size(); cnt++)
        {
            ClothingItem nextItem = invList.elementAt(cnt);

            String nextBarcode = (String)nextItem.getState("Barcode");
            if (nextBarcode.equals(barcode) == true)
            {
                retValue = nextItem;
                return retValue; // we should say 'break;' here
            }
        }

        return retValue;
    }
*/
    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //------------------------------------------------------
    protected void createAndShowView()
    {

        Scene localScene = myViews.get("ListInventoryView");

        if (localScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("ListInventoryView", this);
            localScene = new Scene(newView);
            myViews.put("ListInventoryView", localScene);
        }
        // make the view visible by installing it into the frame
        swapToView(localScene);

    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
