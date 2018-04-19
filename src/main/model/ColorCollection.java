// specify the package
package model;

// system imports
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

// project imports

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;

/**
 * Jan 30, 2018
 * @author Jackson Taber & Kyle Darling
 */
//==============================================================
public class ColorCollection  extends EntityBase implements IView
{
    private static final String myTableName = "Color";

    private Vector<Color> colors;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public ColorCollection( )
    {
        super(myTableName);

    }

    //-----------------------------------------------------------
    private void populateCollectionHelper(String query)
    {

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {
            colors = new Vector<Color>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextColorData = allDataRetrieved.elementAt(cnt);

                Color ct = new Color(nextColorData);

                if (ct != null)
                {
                    addColor(ct);
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
        String query = "SELECT * FROM " + myTableName + " WHERE (Status = 'Active')";
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


    //----------------------------------------------------------------------------------
    private void addColor(Color a)
    {
        int index = findIndexToAdd(a);
        colors.insertElementAt(a,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    private int findIndexToAdd(Color a)
    {
        int low=0;
        int high = colors.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Color midSession = colors.elementAt(middle);

            int result = Color.compare(a,midSession);

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
        if (key.equals("ColorTypes"))
            return colors;
        else
        if (key.equals("ColorList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public Color retrieve(String barcodePrefix)
    {
        Color retValue = null;
        for (int cnt = 0; cnt < colors.size(); cnt++)
        {
            Color nextAT = colors.elementAt(cnt);
            String nextBarcodePrefix = (String)nextAT.getState("BarcodePrefix");
            if (nextBarcodePrefix.equals(barcodePrefix) )
            {
                retValue = nextAT;
                return retValue; // we should say 'break;' here
            }
        }

        return retValue;
    }
    public Vector retrieveAll() {

        return colors;
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

        Scene localScene = myViews.get("ColorCollectionView");

        if (localScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("ColorCollectionView", this);
            localScene = new Scene(newView);
            myViews.put("ColorCollectionView", localScene);
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
