
package model;

//system imports
import java.util.Properties;
import java.util.Vector;

import Utilities.UiConstants;
import Utilities.Utilities;
import javafx.scene.Scene;

//project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;


/** The class containing the ClothingItemCollection for the Professional Clothes
*  Closet application 
*/
//==============================================================
public class ClothingItemCollection  extends EntityBase implements IView
{
	// ClothingItem is not a table inside the database
	private static final String myTableName = "Inventory";

	private Vector<ClothingItem> clothingItems;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public ClothingItemCollection( ) 
	{
		super(myTableName);
	}
	
	//-----------------------------------------------------------
	private void populateCollectionHelper(String query)
	{
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null)
		{
			clothingItems = new Vector<ClothingItem>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextClothingData = allDataRetrieved.elementAt(cnt);

				ClothingItem ci = new ClothingItem(nextClothingData);

				if (ci != null)
				{
					ci.updateExistsToTrue();
					addClothingItem(ci);
				}
			}

		}
	}
	
	//-----------------------------------------------------------
	public void findByBarcode(String barcode)
	{
		String query = "SELECT * FROM " + myTableName + " WHERE (Barcode = '" + barcode +
			"')";
		populateCollectionHelper(query);
	}
	
	//-----------------------------------------------------------
	public void findAll()
	{
		String query = "SELECT * FROM " + myTableName;
		populateCollectionHelper(query);
	}
	//-----------------------------------------------------------
	public void findDonatedCriteria(String articleType, String gender, String size)
	{
		if(size.equals("")) size = "" + UiConstants.GENERIC_SIZE;
		String query = "SELECT * FROM " + myTableName + " WHERE ";
		if (((gender != null) && (gender.length() > 0))&&
				((articleType != null) && (articleType.length() > 0)) && ((size != null) && (size.length() > 0) && !size.equals("" + UiConstants.GENERIC_SIZE)))
		{
			// both values get into criteria
			query += "(Gender LIKE '" + gender + "') AND (ArticleType LIKE '%" +
					articleType + "%') AND (Size LIKE '%" + size + "%') AND Status='Donated';";
		}
		else {
			query += "Status = 'Donated' AND ";
			if ((gender != null) && (gender.length() > 0)) {
				// only description gets into criteria
				query += "((Gender LIKE '" + gender + "') OR (Gender LIKE 'Unisex')) AND ";
			}
			if ((articleType != null) && (articleType.length() > 0)) {
				// only alphaCode gets into criteria
				query += "(ArticleType LIKE '%" + articleType + "%') AND ";
			}
			if ((size != null) && (size.length() > 0)) {
				size = "";
				query += "(Size LIKE '%" + size + "%') AND ";
			}
			query = Utilities.replaceLast("AND", " AND Status='Donated';", query);
		}
		populateCollectionHelper(query);
	}
	//-----------------------------------------------------------
	public void findByCriteria(String gender, String articleType)
	{
		String query = "SELECT * FROM " + myTableName;
		if (((gender != null) && (gender.length() > 0))&& 
			((articleType != null) && (articleType.length() > 0)))
		{
			// both values get into criteria
			query += " WHERE (Gender LIKE '%" + gender + "%') AND (ArticleType LIKE '%" +
					articleType + "%')";
		}
		else 
		if ((gender != null) && (gender.length() > 0))
		{
			// only description gets into criteria
			query += " WHERE (Gender LIKE '%" + gender + "%')";
		}
		else
		if ((articleType != null) && (articleType.length() > 0))
		{
			// only alphaCode gets into criteria
			query += " WHERE (ArticleType LIKE '%" + articleType + "%')";
		}
		else
		{
			query += "";
		}
		populateCollectionHelper(query);
	}
		
	
	//----------------------------------------------------------------------------------
	private void addClothingItem(ClothingItem a)
	{
		int index = findIndexToAdd(a);
		clothingItems.insertElementAt(a,index); // To build up a collection sorted on some key
	}

	//----------------------------------------------------------------------------------
	private int findIndexToAdd(ClothingItem a)
	{
		int low=0;
		int high = clothingItems.size()-1;
		int middle;

		while (low <=high)
		{
			middle = (low+high)/2;

			ClothingItem midSession = clothingItems.elementAt(middle);

			int result = ClothingItem.compare(a,midSession);

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
		if (key.equals("ClothingItems"))
			return clothingItems;
		else
		if (key.equals("ClothingItemList"))
			return this;
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		myRegistry.updateSubscribers(key, this);
	}

	//----------------------------------------------------------
	public ClothingItem retrieve(String barcode)
	{
		ClothingItem retValue = null;
		for (int cnt = 0; cnt < clothingItems.size(); cnt++)
		{
			ClothingItem nextCI = clothingItems.elementAt(cnt);
			String nextBarcode = (String)nextCI.getState("Barcode");
			if (nextBarcode.equals(barcode) == true)
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

		Scene localScene = myViews.get("ClothingItemCollectionView");

		if (localScene == null)
		{
				// create our new view
				View newView = ViewFactory.createView("ClothingItemCollectionView", this);
				localScene = new Scene(newView);
				myViews.put("ClothingItemCollectionView", localScene);
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
