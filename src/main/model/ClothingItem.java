package model;

import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import impresario.IView;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class ClothingItem extends EntityBase implements IView {
	private static final String myTableName = "Inventory";

	protected Properties dependencies;
	private boolean exists;

	// GUI Components
	private String updateStatusMessage = "";

	// constructor for this class

	public ClothingItem(String barcode) throws InvalidPrimaryKeyException, MultiplePrimaryKeysException {
		super(myTableName);

		setDependencies();

		barcode = barcode.trim();
		String query = "SELECT * FROM " + myTableName + " WHERE (Barcode = '" + barcode + "')";

		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		// You must get one at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();
			// if size = 0 throw the Invalid Primary Key Exception
			if (size == 0) {
				exists = false;
				throw new InvalidPrimaryKeyException("No clothing item matching barcode prefix : "
						+ barcode + " found.");
			} else
				// There should be EXACTLY one. More than that is an error
				if (size != 1) {
					exists = true;
					throw new MultiplePrimaryKeysException("Multiple Clothing items matching barcode prefix : "
							+ barcode + " found.");
				} else {
					// copy all the retrieved data into persistent state
					exists = true;
					Properties retrievedATData = allDataRetrieved.elementAt(0);
					persistentState = new Properties();

					Enumeration allKeys = retrievedATData.propertyNames();
					while (allKeys.hasMoreElements() ) {
						String nextKey = (String) allKeys.nextElement();
						String nextValue = retrievedATData.getProperty(nextKey);
						// accountNumber = Integer.parseInt(retrievedAccountData.getProperty("accountNumber"));

						if (nextValue != null) {
							persistentState.setProperty(nextKey, nextValue);
						}
					}

				}
		}
		// If not found for this barcode, throw an Invalid Primary key exception
		else {
			throw new InvalidPrimaryKeyException("No clothing item matching barcode prefix : "
					+ barcode + " found.");
		}
	}

	/**
	 * Alternate constructor. Can be used to create a NEW Clothing Item
	 */

	public ClothingItem(Properties props) {
		super(myTableName);
		exists = false;

		setDependencies();
		persistentState = new Properties();
		Enumeration allKeys = props.propertyNames();
		while (allKeys.hasMoreElements() ) {
			String nextKey = (String) allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);

			if (nextValue != null) {
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}


	public static int compare(ClothingItem a, ClothingItem b) {
		String aVal = (String) a.getState("Description");
		String bVal = (String) b.getState("Description");
		if(aVal != null && bVal != null)
			return aVal.compareTo(bVal);
		return 0;
	}


	private void setDependencies() {
		dependencies = new Properties();

		myRegistry.setDependencies(dependencies);
	}


	public Object getState(String key) {
		if (key.equals("UpdateStatusMessage") )
			return updateStatusMessage;

		return persistentState.getProperty(key);
	}


	public void stateChangeRequest(String key, Object value) {
		if (persistentState.getProperty(key) != null) {
			persistentState.setProperty(key, (String) value);
		}
		myRegistry.updateSubscribers(key, this);
	}

	/**
	 * Called via the IView relationship
	 */

	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}


	public void update() {
		updateStateInDatabase();
	}


	private void updateStateInDatabase() {
		try {
			if ((persistentState.getProperty("Barcode") != null) && exists) {
				Properties whereClause = new Properties();
				whereClause.setProperty("Barcode", persistentState.getProperty("Barcode"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Clothing Item with barcode: "
						+ persistentState.getProperty("Barcode")
						+ " updated successfully!";
			} else if ((persistentState.getProperty("Barcode") != null) && !exists){
				Integer returnCode = insertPersistentState(mySchema, persistentState);
				updateStatusMessage = returnCode + " Clothing item with barcode : "
						+ persistentState.getProperty("Barcode")
						+ " installed successfully!";
			}
		} catch (SQLException ex) {
			updateStatusMessage = "Error sending clothing item data to database!";
		}
	}


	/**
	 * This method is needed solely to enable the Clothing item information to be displayable in a table
	 * s
	 */

	public Vector<String> getEntryListView() {
		Vector<String> v = new Vector<String>();

		v.addElement(persistentState.getProperty("Barcode"));
		v.addElement(persistentState.getProperty("Gender"));
		v.addElement(persistentState.getProperty("Size"));
		v.addElement(persistentState.getProperty("ArticleType"));
		v.addElement(persistentState.getProperty("Color1"));
		v.addElement(persistentState.getProperty("Color2"));
		v.addElement(persistentState.getProperty("Brand"));
		v.addElement(persistentState.getProperty("Notes"));
		v.addElement(persistentState.getProperty("Status"));
		v.addElement(persistentState.getProperty("DonorLastName"));
		v.addElement(persistentState.getProperty("DonorFirstName"));
		v.addElement(persistentState.getProperty("DonorPhone"));
		v.addElement(persistentState.getProperty("DonorEmail"));
		v.addElement(persistentState.getProperty("ReceiverNetid"));
		v.addElement(persistentState.getProperty("ReceiverLastName"));
		v.addElement(persistentState.getProperty("ReceiverFirstName"));
		v.addElement(persistentState.getProperty("DateDonated"));
		v.addElement(persistentState.getProperty("DateTaken"));


		return v;
	}


	protected void initializeSchema(String tableName) {
		if (mySchema == null) {
			mySchema = getSchemaInfo(tableName);
		}
	}
}
