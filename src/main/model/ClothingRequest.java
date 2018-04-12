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

/** The class containing the request for the Professional Clothes Closet
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
    public ClothingRequest(String netId, String gender, String type, String reqSize, String color1, String color2, String brand) throws InvalidPrimaryKeyException, MultiplePrimaryKeysException
    {
        super(myTableName);

        setDependencies();

        netId = netId.trim();
        gender = gender.trim();
        type = type.trim();
        reqSize = reqSize.trim();
        color1 = color1.trim();
        color2 = color2.trim();
        brand = brand.trim();
        String query = "SELECT * FROM " + myTableName + " WHERE (RequesterNetid = '" + netId + "' AND RequestedGender = '" + gender + "' AND RequestedArticleType = '" + type + "'" +
                " AND RequestedColor1 = '" + color1 + "' AND RequestedColor2 = '" + color2 + "' AND RequestedSize = '" + reqSize + "' AND RequestedBrand = '" + brand + "')";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one request at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();
            // if size = 0 throw the Invalid Primary Key Exception
            if (size == 0)
            {
                throw new InvalidPrimaryKeyException("No clothing request match for requester id : "
                        + netId + " found.");
            }
            else
                // There should be EXACTLY one request. More than that is an error
                if (size != 1)
                {

                    throw new MultiplePrimaryKeysException("Multiple clothing requests matching requests for requester id : "
                            + netId + " found.");
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
        // If no request found for this barcode prefix, throw an Invalid Primary key exception
        else
        {
            throw new InvalidPrimaryKeyException("No clothing request match for requester id : "
                    + netId + " found.");
        }

    }

    /**
     * Alternate constructor. Can be used to create a NEW request
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

        if(key.equals("ID"))
            return persistentState.getProperty("ID");

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
            ex.printStackTrace();
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

        v.add((persistentState.getProperty("ID")));
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

