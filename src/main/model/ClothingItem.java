package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import impresario.IView;

	public class ClothingItem extends EntityBase implements IView
	{
		private static final String myTableName = "Inventory";

		protected Properties dependencies;

		// GUI Components
		private String updateStatusMessage = "";

		// constructor for this class
		//----------------------------------------------------------
		public ClothingItem(String barcode) throws InvalidPrimaryKeyException, MultiplePrimaryKeysException
		{
			super(myTableName);

			setDependencies();
			
			barcode = barcode.trim();
			String query = "SELECT * FROM " + myTableName + " WHERE (Barcode = '" + barcode + "')";

			Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

			// You must get one at least
			if (allDataRetrieved != null)
			{
				int size = allDataRetrieved.size();
				// if size = 0 throw the Invalid Primary Key Exception
				if (size == 0)
				{
					throw new InvalidPrimaryKeyException("No clothing item matching barcode prefix : "
					+ barcode + " found.");
				}
				else
				// There should be EXACTLY one. More than that is an error
				if (size != 1)
				{
					
					throw new MultiplePrimaryKeysException("Multiple Clothing items matching barcode prefix : "
						+ barcode + " found.");
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
			// If not found for this barcode, throw an Invalid Primary key exception
			else
			{
				throw new InvalidPrimaryKeyException("No clothing item matching barcode prefix : "
					+ barcode + " found.");
			}
		}

		/**
		 * Alternate constructor. Can be used to create a NEW Article Type 
		 */
		//----------------------------------------------------------
		public ClothingItem(Properties props)
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

			return persistentState.getProperty(key);
		}

		//----------------------------------------------------------------
		public void stateChangeRequest(String key, Object value)
		{
			if (persistentState.getProperty(key) != null)
			{
//				System.out.println(value);
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
		public static int compare(ClothingItem a, ClothingItem b)
		{
			String aVal = (String)a.getState("Description");
			String bVal = (String)b.getState("Description");

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
				if (persistentState.getProperty("Barcode") != null)
				{
					Properties whereClause = new Properties();
					whereClause.setProperty("Barcode", persistentState.getProperty("Barcode"));
					updatePersistentState(mySchema, persistentState, whereClause);
					updateStatusMessage = "ClothingItem with prefix : " + persistentState.getProperty("Barcode") + " updated successfully!";
				}
				//TODO this should not use ID, I have no idea what it should be though.
				else
				{
					Integer ciID =
						insertAutoIncrementalPersistentState(mySchema, persistentState);
					persistentState.setProperty("ID", "" + ciID.intValue());
					updateStatusMessage = "clothing item with barcode : " +  persistentState.getProperty("Barcode")
						+ " installed successfully!";
				}
			}
			catch (SQLException ex)
			{
				updateStatusMessage = "Error sending clothing item data to database!";
			}
			//DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
		}


		/**
		 * This method is needed solely to enable the Clothing item information to be displayable in a table
		 *s
		 */
		//--------------------------------------------------------------------------
		public Vector<String> getEntryListView()
		{
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

		//-----------------------------------------------------------------------------------
		protected void initializeSchema(String tableName)
		{
			if (mySchema == null)
			{
				mySchema = getSchemaInfo(tableName);
			}
		}
	}

