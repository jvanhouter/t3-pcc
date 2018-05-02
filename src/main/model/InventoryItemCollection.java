// specify the package
package model;

// system imports

import impresario.IView;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

// project imports


//==============================================================
public class InventoryItemCollection extends EntityBase implements IView {
    private static final String myTableName = "Inventory";
    Vector<Properties> allColorItemsRetrieved = getColors();
    ;
    Vector<Properties> allArticleTypesRetrieved = getArticleTypes();
    private Vector<ClothingItem> inventoryItems = new Vector<ClothingItem>();
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public InventoryItemCollection() {
        super(myTableName);
    }

    //-----------------------------------------------------------
    private void populateCollectionHelper(String query) {
        Vector<Properties> allClothingItemsRetrieved = getSelectQueryResult(query);
//        System.out.println(allClothingItemsRetrieved);
        Iterator colorIterator = null;
        Iterator articleTypeIterator = null;
        if (allClothingItemsRetrieved != null) {
            for (int cnt = 0; cnt < allClothingItemsRetrieved.size(); cnt++) {
                Properties nextInventoryItemData = allClothingItemsRetrieved.elementAt(cnt);
                ClothingItem ci = new ClothingItem(nextInventoryItemData);

                if (ci != null) {

                    String color1 = "";
                    String color2 = "";
                    String articleType = "";
                    Properties temp = null;
                    colorIterator = allColorItemsRetrieved.iterator();
                    articleTypeIterator = allArticleTypesRetrieved.iterator();

                    while (colorIterator.hasNext()) {
                        temp = (Properties) colorIterator.next();

                        if (((String) temp.getProperty("ID")).equals(ci.getState("Color1"))) {
                            color1 = temp.getProperty("Description");
                        }
                        if (((String) temp.getProperty("ID")).equals(ci.getState("Color2"))) {
                            color2 = temp.getProperty("Description");
                        }
                    }
                    while (articleTypeIterator.hasNext()) {
                        temp = (Properties) articleTypeIterator.next();
                        if (((String) temp.getProperty("ID")).equals(ci.getState("ArticleType"))) {
                            articleType = temp.getProperty("Description");
                        }

                    }
                    formatClothingItem(ci, color1, color2, articleType);
                    inventoryItems.add(ci);
                }
            }
        }
    }

    //-----------------------------------------------------------
    public void formatClothingItem(ClothingItem ci, String color1, String color2, String articleType) {
        if (ci.getState("ArticleType") != null) {
            ci.stateChangeRequest("ArticleType", articleType);
        }
        if (ci.getState("Color1") != null) {
            ci.stateChangeRequest("Color1", color1);
        }
        if (ci.getState("Color2") != null) {
            ci.stateChangeRequest("Color2", color2);
        }
    }

    //-----------------------------------------------------------
    public Vector<Properties> getColors() {
        String query = "Select Id, Description FROM color;";
        return getSelectQueryResult(query);
    }

    //-----------------------------------------------------------
    public Vector<Properties> getArticleTypes() {
        String query = "SELECT Id, Description FROM articletype;";
        return getSelectQueryResult(query);
    }

    //-----------------------------------------------------------
    public void findAll() {
        String query = "SELECT * FROM " + myTableName + " WHERE (Status = 'Donated')";
        populateCollectionHelper(query);
    }

    public void findByBarCode(String barcode) {
        String query = "SELECT * FROM " + myTableName + " WHERE (Barcode = '" + barcode + "')";
        populateCollectionHelper(query);
    }

    public void findCustom(String query) {
        populateCollectionHelper(query);
    }

    public void findRecent(String netId) {
        Calendar currDate = Calendar.getInstance();
        currDate.add(Calendar.MONTH, -6);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(currDate.getTime());
//        System.out.println(date);
        String query = "SELECT * FROM inventory WHERE ReceiverNetid = '" + netId + "' AND DateTaken >= '" + date + "';";
//        System.out.println(query);
        populateCollectionHelper(query);
    }

    //----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("InventoryItems")) {
            return inventoryItems;
        } else if (key.equals("InventoryList")) {
            return this;
        }
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    //------------------------------------------------------
    protected void createAndShowView() {

        Scene localScene = myViews.get("InventoryItemCollectionView");

        if (localScene == null) {
            // create our new view
            View newView = ViewFactory.createView("InventoryItemCollectionView", this);
            localScene = new Scene(newView);
            myViews.put("InventoryItemCollectionView", localScene);
        }
        // make the view visible by installing it into the frame
        swapToView(localScene);

    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
