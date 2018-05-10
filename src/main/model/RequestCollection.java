// specify the package
package model;

// system imports
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;


/** The class containing the ClothingRequestCollection for the Professional Clothes
 *  Closet application
 */
//==============================================================
public class RequestCollection  extends EntityBase implements IView
{
    private static final String myTableName = "ClothingRequest";

    private Vector<ClothingRequest> requests;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public RequestCollection( )
    {
        super(myTableName);

    }

    //-----------------------------------------------------------
    private void populateCollectionHelper(String query)
    {

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {
            requests = new Vector<ClothingRequest>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextRQData = allDataRetrieved.elementAt(cnt);
                ClothingRequest rq = new ClothingRequest(nextRQData);

                if (rq != null)
                {
                    addClothingRequest(rq);
                }
            }

        }
    }

    //-----------------------------------------------------------
    public void findBySet(String netId, String gender, String type, String reqSize, String color1, String color2, String brand)
    {
        String query = "SELECT * FROM " + myTableName + " WHERE ";
        if(netId != null) {
            query += "(RequesterNetid = '" + netId + "') AND ";
        }
        if(gender != null) {
            query += "(RequestedGender ='" + gender + "') AND ";
        }
        if(type != null) {
            query += "(RequestedArticleType = '" + type + "') AND ";
        }
        if(reqSize != null) {
            query += "(RequestedSize = '" + reqSize + "') AND ";
        }
        if(color1 != null) {
            query += "(RequestedColor1 = '" + color1 + "') AND ";
        }
        if(color2 != null) {
            query += "(RequestedColor2 = '" + color2 + "') AND ";
        }
        if(brand != null) {
            query += "(RequestedBrand = '" + brand + "') AND";
        }
        query = replaceLast(query, "AND", "");
        populateCollectionHelper(query);
    }

    //-----------------------------------------------------------
    // used to poll only requests with matching items
    public void findByItem(ClothingItemCollection cic)
    {
        String query = "SELECT * FROM " + myTableName + " WHERE (Status = 'Pending')";
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {
            requests = new Vector<ClothingRequest>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextRQData = allDataRetrieved.elementAt(cnt);
                ClothingRequest rq = new ClothingRequest(nextRQData);
                cic.findDonatedCriteria((String) rq.getState("RequestedArticleType"),
                        (String) rq.getState("RequestedGender"),
                        (String) rq.getState("RequestedSize"));
                if (rq != null) {
                    if (((Vector) cic.getState("ClothingItems")).size() > 0) {
                        addClothingRequest(rq);
                    }
                }
                if (rq != null)
                {
                    addClothingRequest(rq);
                }
            }

        }
    }

    //-----------------------------------------------------------
    public void findAll()
    {
        String query = "SELECT * FROM " + myTableName + " WHERE (Status = 'Pending')";
        populateCollectionHelper(query);
    }

    //----------------------------------------------------------------------------------
    private void addClothingRequest(ClothingRequest a)
    {
        int index = findIndexToAdd(a);
        requests.insertElementAt(a,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    private String replaceLast(String string, String substring, String replacement)
    {
        int index = string.lastIndexOf(substring);
        if (index == -1)
            return string;
        return string.substring(0, index) + replacement
                + string.substring(index+substring.length());
    }

    //----------------------------------------------------------------------------------
    private int findIndexToAdd(ClothingRequest a)
    {
        int low=0;
        int high = requests.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            ClothingRequest midSession = requests.elementAt(middle);

            int result = ClothingRequest.compare(a,midSession);

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
        if (key.equals("Requests"))
            return requests;
        else
        if (key.equals("RequestList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        if (key.equals("SetRequests") == true)
        {
            requests = (Vector<ClothingRequest>) value;
        }
        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public ClothingRequest retrieve(String id)
    {
        ClothingRequest retValue = null;
        for (int cnt = 0; cnt < requests.size(); cnt++)
        {
            ClothingRequest nextRQ = requests.elementAt(cnt);
            String nextId = (String)nextRQ.getState("ID");
            if (nextId.equals(id) )
            {
                retValue = nextRQ;
                return retValue; // we should say 'break;' here
            }
        }

        return retValue;
    }

    //----------------------------------------------------------
    public void setRequests(Vector<ClothingRequest> cr) {
        Iterator iter = cr.iterator();
        if(requests == null) requests = new Vector<>();
        requests.clear();
        while(iter.hasNext()) {
            requests.add((ClothingRequest) iter.next());
        }
    }

    /** Called via the IView relationship *
     *
     */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //------------------------------------------------------
    protected void createAndShowView()
    {

        Scene localScene = myViews.get("RequestCollectionView");

        if (localScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("RequestCollectionView", this);
            localScene = new Scene(newView);
            myViews.put("RequestCollectionView", localScene);
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
