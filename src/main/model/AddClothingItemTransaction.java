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

/** The class containing the AddArticleTypeTransaction for the Professional Clothes Closet application */
//==============================================================
public class AddClothingItemTransaction extends Transaction
{

	private ArticleType myArticleType;
	private ArticleTypeCollection myArticleTypeList;



	// GUI Components

	private String transactionErrorMessage = "";

	/**
	 * Constructor for this class.
	 *
	 */
	//----------------------------------------------------------
	public AddClothingItemTransaction() throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("CancelAddClothingItem", "CancelTransaction");
		dependencies.setProperty("CancelBarcodeSearch", "CancelTransaction");
		dependencies.setProperty("OK", "CancelTransaction");
		dependencies.setProperty("ClothingItemData", "TransactionError");
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
		if (props.getProperty("BarcodePrefix") != null)
		{
			String barcodePrefix = props.getProperty("BarcodePrefix");
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
					String descriptionOfAT = props.getProperty("Description");
					if (descriptionOfAT.length() > 30)
					{
						transactionErrorMessage = "ERROR: Article Type Description too long! ";
					}
					else
					{
						String alphaCode = props.getProperty("AlphaCode");
						if (alphaCode.length() > 5)
						{
							transactionErrorMessage = "ERROR: Alpha code too long (max length = 5)! ";
						}
						else
						{
								props.setProperty("Status", "Active");
								myArticleType = new ArticleType(props);
								myArticleType.update();
								transactionErrorMessage = (String)myArticleType.getState("UpdateStatusMessage");
						}
					}
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
		
	}

	private void processBarcode(Properties props) {
		if (props.getProperty("Barcode") != null) {
			String barcode = props.getProperty("Barcode");
//			try {
//				ClothingItem oldClothingItem = new ClothingItem(barcode);
//				transactionErrorMessage = "ERROR: Barcode Prefix " + barcode
//						+ " already exists!";
//				new Event(Event.getLeafLevelClassName(this), "processTransaction",
//						"Clothing item with barcode : " + barcode + " already exists!",
//						Event.ERROR);
//			} catch (InvalidPrimaryKeyException ex){
//				// Barcode does not exist, parse barcode and populate view
				String gender = barcode.substring(0, 1);
				String articleTypePrefix = barcode.substring(1, 3);
				String colorP1Prefix = barcode.substring(3, 5);
				String sequence = barcode.substring(5, 8);
//			}
            ArticleType test = null;
//            try {
                myArticleTypeList = new ArticleTypeCollection();
                myArticleTypeList.findAll();
//            } catch (InvalidPrimaryKeyException ex) {
//                System.out.println("DEBUG");
//
//            } catch (MultiplePrimaryKeysException ex2) {
//                transactionErrorMessage = "ERROR: Multiple article types with barcode prefix!";
//                new Event(Event.getLeafLevelClassName(this), "processTransaction",
//                        "Found multiple article types with barcode prefix : " + articleTypePrefix + ". Reason: " + ex2.toString(),
//                        Event.ERROR);
//
//            }
            System.out.println(myArticleTypeList);
            createAndShowAddClothingItemView();
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

		if (key.equals("DoYourJob") || key.equals("CancelAddClothingItem")) {
//		    createAndShowBarcodeScannerView();
			doYourJob();
		} else if (key.equals("ProcessBarcode")) {
//			doYourJob();
			processBarcode((Properties) value);
		} else if (key.equals("ClothingItemData")) {
			processTransaction((Properties)value);
		}

		myRegistry.updateSubscribers(key, this);
	}

    //------------------------------------------------------------
    private void createAndShowAddClothingItemView() {
        Scene currentScene = (Scene) myViews.get("AddClothingItemView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("AddClothingItemView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("AddClothingItemView", currentScene);
        }

        swapToView(currentScene);

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
			currentScene = new Scene(ViewFactory.createView("BarcodeScannerView", this));
			myViews.put("BarcodeScannerView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}

}

