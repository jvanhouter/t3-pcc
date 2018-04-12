package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;

import impresario.IView;

/**
 * Jan 30, 2018
 * @author Jackson Taber & Kyle Darling
 */

public class ColorType extends EntityBase{

    private static final String myTableName = "Color";

    protected Properties dependencies;


    private String updateStatusMessage = "";

    public ColorType(String barcodePrefix) throws InvalidPrimaryKeyException
    {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (BarcodePrefix = " + barcodePrefix + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple colors matching Barcode Prefix : "
                        + barcodePrefix + " found.");
            }
            else
            {

                Properties retrievedAccountData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedAccountData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedAccountData.getProperty(nextKey);


                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
            }
        }
        // If no account found for this user name, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No color matching Barcode Prefix : "
                    + barcodePrefix + " found.");
        }
    }

    public ColorType(Properties props)
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
    public void update()
    {
        updateStateInDatabase();
    }

    private void updateStateInDatabase()
    {
        try
        {
            if (persistentState.getProperty("ID") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("ID",
                        persistentState.getProperty("ID"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Updated successfully in database!";
            }
            else
            {
                Integer ID = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("ID", "" + ID.intValue());
                updateStatusMessage = "Color installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing color data in database!";
        }

    }
    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();
        v.addElement(persistentState.getProperty("ID"));
        v.addElement(persistentState.getProperty("Description"));
        v.addElement(persistentState.getProperty("BarcodePrefix"));
        v.addElement(persistentState.getProperty("AlphaCode"));
        v.addElement(persistentState.getProperty("Status"));

        return v;
    }
    public static int compare(ColorType a, ColorType b)
    {
        String aNum = (String)a.getState("ID");
        String bNum = (String)b.getState("ID");

        return aNum.compareTo(bNum);
    }
    private void setDependencies()
    {
        dependencies = new Properties();
        myRegistry.setDependencies(dependencies);
    }
    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }

    public void stateChangeRequest(String key, Object value)
    {
        if (persistentState.getProperty(key) != null)
        {
            persistentState.setProperty(key, (String)value);
        }
        myRegistry.updateSubscribers(key, this);
    }

    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public String status(){
        return updateStatusMessage;
    }
    public String toString(){
        String returnString = "";
        for(String key : persistentState.stringPropertyNames()) {
            String value = persistentState.getProperty(key);
            returnString += key + " = " + value + " , ";
        }
        return returnString.substring(0, returnString.length()-2)+"\n";
    }
}



