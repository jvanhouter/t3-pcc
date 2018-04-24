// specify the package
package model;

// system imports
import Utilities.Utilities;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;

import userinterface.View;
import userinterface.ViewFactory;
import Utilities.UiConstants;

import javax.rmi.CORBA.Util;

/** The class containing the ModifyArticleTypeTransaction for the Professional Clothes Closet application */
//==============================================================
public class ModifyArticleTypeTransaction extends Transaction
{

	private ArticleTypeCollection myArticleTypeList;
	private ArticleType mySelectedArticleType;
	

	// GUI Components

	private String transactionErrorMessage = "";

	/**
	 * Constructor for this class.
	 *
	 */
	//----------------------------------------------------------
	public ModifyArticleTypeTransaction() throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("CancelSearchArticleType", "CancelTransaction");
		dependencies.setProperty("CancelAddAT", "CancelTransaction");
		dependencies.setProperty("OK", "CancelTransaction");
		dependencies.setProperty("ArticleTypeData", "TransactionError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of creating the article type collection and showing the view
	 */
	//----------------------------------------------------------
	public void processTransaction(Properties props)
	{
		myArticleTypeList = new ArticleTypeCollection();
		if (props.getProperty("BarcodePrefix") != null)
		{
			String barcodePrefix = props.getProperty("BarcodePrefix");
			myArticleTypeList.findByBarcodePrefix(barcodePrefix);
		}
		else
		{
			String desc = props.getProperty("Description");
			String alfaC = props.getProperty("AlphaCode");
			myArticleTypeList.findByCriteria(desc, alfaC);
		}
		try
		{	
			Scene newScene = createArticleTypeCollectionView();	
			swapToView(newScene);
		}
		catch (Exception ex)
		{
				new Event(Event.getLeafLevelClassName(this), "processTransaction",
					"Error in creating ArticleTypeCollectionView", Event.ERROR);
		}
	}

	/**
	 * Helper method for article type update
	 */
	//--------------------------------------------------------------------------
	private void articleTypeModificationHelper(Properties props)
	{
		String descriptionOfAT = props.getProperty("Description");
		if (descriptionOfAT.length() > UiConstants.AT_DESCRIPTION_MAX_LENGTH)
		{
			transactionErrorMessage = "ERROR: Article Type Description too long! ";
		}
		else
		{
			String alphaCode = props.getProperty("AlphaCode");
			if (alphaCode.length() > UiConstants.ALPHACODE_MAX_LENGTH)
			{
				transactionErrorMessage = "ERROR: Alpha code too long (max length = 5)! ";
			}
			else
			{
				// Everything OK
				
				mySelectedArticleType.stateChangeRequest("Description", descriptionOfAT);
				mySelectedArticleType.stateChangeRequest("AlphaCode", alphaCode);
				mySelectedArticleType.update();
				Utilities.putArticleTypeHash((String) mySelectedArticleType.getState("ID"), mySelectedArticleType);
				transactionErrorMessage = (String)mySelectedArticleType.getState("UpdateStatusMessage");
			}
		}
	}
	
	/**
	 * This method encapsulates all the logic of modifiying the article type,
	 * verifying the new barcode, etc.
	 */
	//----------------------------------------------------------
	private void processArticleTypeModification(Properties props)
	{
		if (props.getProperty("BarcodePrefix") != null)
		{
			String barcodePrefix = props.getProperty("BarcodePrefix");
			String originalBarcodePrefix = (String)mySelectedArticleType.getState("BarcodePrefix");
			if (barcodePrefix.equals(originalBarcodePrefix) == false)
			{
				try
				{
					ArticleType oldArticleType = new ArticleType(barcodePrefix);
					transactionErrorMessage = "ERROR: Barcode Prefix " + barcodePrefix 
						+ " already exists!";
					new Event(Event.getLeafLevelClassName(this), "processTransaction",
						"Article type with barcode prefix : " + barcodePrefix + " already exists!",
						Event.ERROR);
				}
				catch (InvalidPrimaryKeyException ex)
				{
					// Barcode prefix does not exist, validate data
					try
					{
						int barcodePrefixVal = Integer.parseInt(barcodePrefix);
						// Barcode prefix ok, so set it
						mySelectedArticleType.stateChangeRequest("BarcodePrefix", barcodePrefix);
						// Process the rest (description, alpha code). Helper does all that
						articleTypeModificationHelper(props);
					}
					catch (Exception excep)
					{
						transactionErrorMessage = "ERROR: Invalid barcode prefix: " + barcodePrefix 
							+ "! Must be numerical.";
						new Event(Event.getLeafLevelClassName(this), "processTransaction",
							"Invalid barcode prefix : " + barcodePrefix + "! Must be numerical.",
							Event.ERROR);
					}

				}
				catch (MultiplePrimaryKeysException ex2)
				{
					transactionErrorMessage = "ERROR: Multiple article types with barcode prefix!";
					new Event(Event.getLeafLevelClassName(this), "processTransaction",
						"Found multiple article types with barcode prefix : " + barcodePrefix + ". Reason: " + ex2.toString(),
						Event.ERROR);

				}
			}
			else
			{
				// No change in barcode prefix, so just process the rest (description, alpha code). Helper does all that
				articleTypeModificationHelper(props);
			}
			
		}
		
	}
	
	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ArticleTypeList") == true)
		{
			return myArticleTypeList;
		}
		else
		if (key.equals("BarcodePrefix") == true)
		{
			if (mySelectedArticleType != null)
				return mySelectedArticleType.getState("BarcodePrefix");
			else
				return "";
		}
		else
		if (key.equals("Description") == true)
		{
			if (mySelectedArticleType != null)
				return mySelectedArticleType.getState("Description");
			else
				return "";
		}
		else
		if (key.equals("AlphaCode") == true)
		{
			if (mySelectedArticleType != null)
				return mySelectedArticleType.getState("AlphaCode");
			else
				return "";
		}
		else
		if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
		
		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// DEBUG System.out.println("ModifyArticleTypeTransaction.sCR: key: " + key);

		if ((key.equals("DoYourJob") == true) || (key.equals("CancelArticleTypeList") == true))
		{
			doYourJob();
		}
		else
		if (key.equals("SearchArticleType") == true)
		{
			processTransaction((Properties)value);
		}
		else
		if (key.equals("ArticleTypeSelected") == true)
		{
			mySelectedArticleType = myArticleTypeList.retrieve((String)value);
			try
			{
				
				Scene newScene = createModifyArticleTypeView();
			
				swapToView(newScene);

			}
			catch (Exception ex)
			{
				new Event(Event.getLeafLevelClassName(this), "processTransaction",
					"Error in creating ModifyArticleTypeView", Event.ERROR);
			}
		}
		else
		if (key.equals("ArticleTypeData") == true)
		{
			processArticleTypeModification((Properties)value);
		}

		myRegistry.updateSubscribers(key, this);
	}

	/**
	 * Create the view of this class. And then the super-class calls
	 * swapToView() to display the view in the frame
	 */
	//------------------------------------------------------
	protected Scene createView()
	{
		Scene currentScene = myViews.get("SearchArticleTypeView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchArticleTypeView", this);
			currentScene = new Scene(newView);
			myViews.put("SearchArticleTypeView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
	
	/**
	 * Create the view containing the table of all matching article types on the search criteria sents
	 */
	//------------------------------------------------------
	protected Scene createArticleTypeCollectionView()
	{
		View newView = ViewFactory.createView("ArticleTypeCollectionView", this);
		Scene currentScene = new Scene(newView);

		return currentScene;
		
	}
	
	/**
	 * Create the view using which data about selected article type can be modified
	 */
	//------------------------------------------------------
	protected Scene createModifyArticleTypeView()
	{
		View newView = ViewFactory.createView("ModifyArticleTypeView", this);
		Scene currentScene = new Scene(newView);

		return currentScene;
		
	}

}

