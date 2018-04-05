// specify the package
package model;

// system imports

import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

// project imports

/** The class containing the BarcodeSearchTransaction for the Professional Clothes Closet application */
//==============================================================
public class BarcodeSearchTransaction extends Transaction
{

	private ArticleType myArticleType;
//	private Color myColor;


	// GUI Components

	private String transactionErrorMessage = "";

	/**
	 * Constructor for this class.
	 *
	 */
	//----------------------------------------------------------
	public BarcodeSearchTransaction() throws Exception
	{
	    super();

	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("CancelBarcodeSearch", "CancelTransaction");
		dependencies.setProperty("OK", "CancelTransaction");
		dependencies.setProperty("ProcessBarcode", "TransactionError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of creating the article type,
	 * verifying its uniqueness, etc.
	 */
	//----------------------------------------------------------
	public void processTransaction(Properties props)
	{
		if (props.getProperty("Barcode") != null)
		{
			String barcode = props.getProperty("Barcode");
		}
	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("TransactionError"))
		{
			return transactionErrorMessage;
		}
		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// DEBUG System.out.println("AddArticleTypeTransaction.sCR: key: " + key);

		if (key.equals("DoYourJob"))
		{
			doYourJob();
		}
		else
		if (key.equals("ProcessBarcode"))
		{
			processTransaction((Properties)value);
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
		Scene currentScene = myViews.get("BarcodeScannerView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("BarcodeScannerView", this);
			currentScene = new Scene(newView);
			myViews.put("BarcodeScannerView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
}

