// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


// project imports
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the Article Type for the Professional Clothes Closet
 * application
 */
//==============================================================
public class ClothingRequest extends EntityBase implements IView
{
    private static final String myTableName = "ClothingRequest";

    protected Properties dependencies;

    // GUI Components
    private String updateStatusMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public ClothingRequest(String barcodePrefix) throws InvalidPrimaryKeyException, MultiplePrimaryKeysException
    {
        super(myTableName);
        /*
        setDependencies();

        barcodePrefix = barcodePrefix.trim();
        String query = "SELECT * FROM " + myTableName + " WHERE (BarcodePrefix = '" + barcodePrefix + "')";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one article type at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();
            // if size = 0 throw the Invalid Primary Key Exception
            if (size == 0)
            {
                throw new InvalidPrimaryKeyException("No article type matching barcode prefix : "
                        + barcodePrefix + " found.");
            }
            else
                // There should be EXACTLY one article type. More than that is an error
                if (size != 1)
                {

                    throw new MultiplePrimaryKeysException("Multiple article types matching barcode prefix : "
                            + barcodePrefix + " found.");
                }
                else
                {
                    // copy all the retrieved data into persistent state
                    Properties retrievedATData = allDataRetrieved.elementAt(0);
                    persistentState = new Properties();

                    Enumeration allKeys = retrievedATData.propertyNames();
                    while (allKeys.hasMoreElements() == true)
                    {
                        String nextKey = (String)allKeys.nextElement();
                        String nextValue = retrievedATData.getProperty(nextKey);
                        // accountNumber = Integer.parseInt(retrievedAccountData.getProperty("accountNumber"));

                        if (nextValue != null)
                        {
                            persistentState.setProperty(nextKey, nextValue);
                        }
                    }

                }
        }
        // If no article type found for this barcode prefix, throw an Invalid Primary key exception
        else
        {
            throw new InvalidPrimaryKeyException("No article type matching barcode prefix : "
                    + barcodePrefix + " found.");
        }
        This constructor needs to be reworked in figuring out what parameters are passed to search for such request*/
    }

    /**
     * Alternate constructor. Can be used to create a NEW Article Type
     */
    //----------------------------------------------------------
    public ClothingRequest(Properties props)
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies()
    {
        dependencies = new Properties();

        myRegistry.setDependencies(dependencies);
    }

    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        if (persistentState.getProperty(key) != null)
        {
            persistentState.setProperty(key, (String)value);
        }
        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }


    //-----------------------------------------------------------------------------------
    public static int compare(ClothingRequest a, ClothingRequest b)
    {
        String aVal = (String)a.getState("RequesterNetid"); // subject to change
        String bVal = (String)b.getState("RequesterNetid");

        return aVal.compareTo(bVal);
    }

    //-----------------------------------------------------------------------------------
    public void update()
    {
        updateStateInDatabase();
    }

    //-----------------------------------------------------------------------------------
    private void updateStateInDatabase()
    {
        try
        {
            if (persistentState.getProperty("ID") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("ID", persistentState.getProperty("ID"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Clothing request for requester id : " + persistentState.getProperty("RequesterNetid") + " updated successfully!";
            }
            else
            {
                Integer atID =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("ID", "" + atID.intValue());
                updateStatusMessage = "Clothing request for requester id : " +  persistentState.getProperty("RequesterNetid")
                        + " installed successfully!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing clothing request data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }


    /**
     * This method is needed solely to enable the Clothing Request information to be displayable in a table
     *s
     */
    //--------------------------------------------------------------------------
    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();

        v.addElement((persistentState.getProperty("RequesterNetid")));
        v.addElement((persistentState.getProperty("RequesterPhone")));
        v.addElement((persistentState.getProperty("RequesterLastName")));
        v.addElement((persistentState.getProperty("RequesterFirstName")));
        v.addElement((persistentState.getProperty("RequestedGender")));
        v.addElement((persistentState.getProperty("RequestedArticleType")));
        v.addElement((persistentState.getProperty("RequestedColor1")));
        v.addElement((persistentState.getProperty("RequestedColor2")));
        v.addElement((persistentState.getProperty("RequestedSize")));
        v.addElement((persistentState.getProperty("RequestedBrand")));
        v.addElement((persistentState.getProperty("Status")));
        v.addElement((persistentState.getProperty("FulfillItemBarcode")));
        v.addElement((persistentState.getProperty("RequestMadeDate")));
        v.addElement((persistentState.getProperty("RequestFulfilledDate")));

        return v;
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

