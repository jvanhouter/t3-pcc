// specify the package
package model;

// system imports
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


/** The class containing the ArticleTypeCollection for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class ArticleTypeCollection  extends EntityBase implements IView
{
	private static final String myTableName = "ArticleType";

	private Vector<ArticleType> articleTypes;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public ArticleTypeCollection( ) 
	{
		super(myTableName);

	}
	
	//-----------------------------------------------------------
	private void populateCollectionHelper(String query)
	{
		
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null)
		{
			articleTypes = new Vector<ArticleType>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextATData = allDataRetrieved.elementAt(cnt);

				ArticleType at = new ArticleType(nextATData);

				if (at != null)
				{
					addArticleType(at);
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
	private void addArticleType(ArticleType a)
	{
		int index = findIndexToAdd(a);
		articleTypes.insertElementAt(a,index); // To build up a collection sorted on some key
	}

	//----------------------------------------------------------------------------------
	private int findIndexToAdd(ArticleType a)
	{
		int low=0;
		int high = articleTypes.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			ArticleType midSession = articleTypes.elementAt(middle);

			int result = ArticleType.compare(a,midSession);

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
		if (key.equals("ArticleTypes"))
			return articleTypes;
		else
		if (key.equals("ArticleTypeList"))
			return this;
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		myRegistry.updateSubscribers(key, this);
	}

	//----------------------------------------------------------
	public ArticleType retrieve(String barcodePrefix)
	{
		ArticleType retValue = null;
		for (int cnt = 0; cnt < articleTypes.size(); cnt++)
		{
			ArticleType nextAT = articleTypes.elementAt(cnt);
			String nextBarcodePrefix = (String)nextAT.getState("BarcodePrefix");
			if (nextBarcodePrefix.equals(barcodePrefix) == true)
			{
				retValue = nextAT;
				return retValue; // we should say 'break;' here
			}
		}

		return retValue;
	}
	public Vector retrieveAll() {
		return articleTypes;
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

		Scene localScene = myViews.get("ArticleTypeCollectionView");

		if (localScene == null)
		{
				// create our new view
				View newView = ViewFactory.createView("ArticleTypeCollectionView", this);
				localScene = new Scene(newView);
				myViews.put("ArticleTypeCollectionView", localScene);
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
