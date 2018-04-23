// specify the package
package Utilities;

// system imports
import javafx.scene.control.Alert;
import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// project imports

/** Useful Utilities */
//==============================================================
public class Utilities
{
	// Hash String, String maps to ID, Description
	private static HashMap<String, ArticleType> articleTypeHash;
	private static HashMap<String, Color> colorHash;
	private static HashMap<String, ClothingItem> clothingHash;

	//----------------------------------------------------------
	public static HashMap<String, ClothingItem> collectClothingHash()
	{
		if(clothingHash == null)
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Preparing Local HashMap...");
			alert.setHeaderText("Loading...");
			alert.setContentText("Please wait while ClothingItem hash is created.");
			alert.show();
			clothingHash = new HashMap<>();
			ClothingItemCollection clothing = new ClothingItemCollection();
			clothing.findAll();
			Vector clothingEntryList = (Vector)clothing.getState("ClothingItems");
			if (clothingEntryList.size() > 0)
			{
				Enumeration entries = clothingEntryList.elements();

				while (entries.hasMoreElements() == true)
				{
					ClothingItem nextCI = (ClothingItem) entries.nextElement();
					clothingHash.put((String) nextCI.getState("ID"), nextCI);
				}
			}
			alert.close();
		}
		return clothingHash;
	}

	//----------------------------------------------------------
	public static void putClothingHash(String id, ClothingItem item)
	{
		if(clothingHash == null)
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Preparing Local HashMap...");
			alert.setHeaderText("Loading...");
			alert.setContentText("Please wait while Clothing Item hash is created.");
			alert.show();
			clothingHash = new HashMap<>();
			ClothingItemCollection clothing = new ClothingItemCollection();
			clothing.findAll();
			Vector clothingEntryList = (Vector)clothing.getState("ClothingItems");
			if (clothingEntryList.size() > 0)
			{
				Enumeration entries = clothingEntryList.elements();

				while (entries.hasMoreElements() == true)
				{
					ClothingItem nextCI = (ClothingItem) entries.nextElement();
					clothingHash.put((String) nextCI.getState("ID"), nextCI);
				}
			}
			alert.close();
		}
		clothingHash.put(id, item);
	}

	//----------------------------------------------------------
	public static void putColorHash(String id, Color color)
	{
		if(colorHash == null)
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Preparing Local HashMap...");
			alert.setHeaderText("Loading...");
			alert.setContentText("Please wait while Color hash is created.");
			alert.show();
			colorHash = new HashMap<>();
			ColorCollection colors = new ColorCollection();
			colors.findAll();
			Vector colorEntryList = (Vector)colors.getState("ColorTypes");
			if (colorEntryList.size() > 0)
			{
				Enumeration entries = colorEntryList.elements();

				while (entries.hasMoreElements() == true)
				{
					model.Color nextCT = (model.Color) entries.nextElement();
					colorHash.put((String) nextCT.getState("ID"), nextCT);
				}
			}
			alert.close();
		}
		colorHash.put(id, color);
	}

	//----------------------------------------------------------
	public static void putArticleTypeHash(String id, ArticleType article)
	{
		if(articleTypeHash == null)
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Preparing Local HashMap...");
			alert.setHeaderText("Loading...");
			alert.setContentText("Please wait while Article Type hash is created.");
			alert.show();
			articleTypeHash = new HashMap<>();
			ArticleTypeCollection atc = new ArticleTypeCollection();
			atc.findAll();
			Vector entryList = (Vector)atc.getState("ArticleTypes");
			if (entryList.size() > 0)
			{
				Enumeration entries = entryList.elements();

				while (entries.hasMoreElements() == true)
				{
					ArticleType nextAT = (ArticleType) entries.nextElement();
					articleTypeHash.put((String) nextAT.getState("ID"), nextAT);
				}
			}
			alert.close();
		}
		articleTypeHash.put(id, article);
	}

	//----------------------------------------------------------
	public static HashMap<String, Color> collectColorHash()
	{
		if(colorHash == null)
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Preparing Local HashMap...");
			alert.setHeaderText("Loading...");
			alert.setContentText("Please wait while Color hash is created.");
			alert.show();
			colorHash = new HashMap<>();
			ColorCollection colors = new ColorCollection();
			colors.findAll();
			Vector colorEntryList = (Vector)colors.getState("ColorTypes");
			if (colorEntryList.size() > 0)
			{
				Enumeration entries = colorEntryList.elements();

				while (entries.hasMoreElements() == true)
				{
					model.Color nextCT = (model.Color) entries.nextElement();
					colorHash.put((String) nextCT.getState("ID"), nextCT);
				}
			}
			alert.close();
		}
		return colorHash;
	}

	//----------------------------------------------------------
	public static HashMap<String, ArticleType> collectArticleTypeHash()
	{
		if(articleTypeHash == null)
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Preparing Local HashMap...");
			alert.setHeaderText("Loading...");
			alert.setContentText("Please wait while Article Type hash is created.");
			alert.show();
			articleTypeHash = new HashMap<>();
			ArticleTypeCollection atc = new ArticleTypeCollection();
			atc.findAll();
			Vector entryList = (Vector)atc.getState("ArticleTypes");
			if (entryList.size() > 0)
			{
				Enumeration entries = entryList.elements();

				while (entries.hasMoreElements() == true)
				{
					ArticleType nextAT = (ArticleType) entries.nextElement();
					articleTypeHash.put((String) nextAT.getState("ID"), nextAT);
				}
			}
			alert.close();
		}
		return articleTypeHash;
	}

	//----------------------------------------------------------
	// developing method to reformat size for query optimization for approximating sizes based on input value
	public static String rebufferSize(String size) {
		if(size.matches("[0-9]+")) {

		}
		return size;
	}

	//----------------------------------------------------------
	// great for query manipulation i.e appending AND then stripping the last AND
	public static String replaceLast(String find, String replace, String string) {
		int lastIndex = string.lastIndexOf(find);

		if (lastIndex == -1) {
			return string;
		}

		String beginString = string.substring(0, lastIndex);
		String endString = string.substring(lastIndex + find.length());

		return beginString + replace + endString;
	}

	//----------------------------------------------------------
	public static String convertToDefaultDateFormat(Date theDate)
	{

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

		String valToReturn = formatter.format(theDate);

		return valToReturn;

	}

	//----------------------------------------------------------
	public static String convertDateStringToDefaultDateFormat(String dateStr)
	{

		Date theDate = validateDateString(dateStr);

		if (theDate == null)
		{
			return null;
		}
		else
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

			String valToReturn = formatter.format(theDate);

			return valToReturn;
		}
	}

	//----------------------------------------------------------
	protected static Date validateDateString(String str)
	{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

			Date theDate = null;

			try
			{
				theDate = formatter.parse(str);
				return theDate;
			}
			catch (ParseException ex)
			{
				SimpleDateFormat formatter2 =
					new SimpleDateFormat("yyyy-MM-dd");

				try
				{
					theDate = formatter2.parse(str);
					return theDate;
				}
				catch (ParseException ex2)
				{
					SimpleDateFormat formatter3 =
						new SimpleDateFormat("yyyy/MMdd");

					try
					{
						theDate = formatter3.parse(str);
						return theDate;
					}
					catch (ParseException ex3)
					{
						SimpleDateFormat formatter4 =
							new SimpleDateFormat("yyyyMM/dd");

						try
						{
							theDate = formatter4.parse(str);
							return theDate;
						}
						catch (ParseException ex4)
						{
							return null;
						}
					}
				}
			}
	}

	//----------------------------------------------------------
	protected String mapMonthToString(int month)
	{
		if (month == Calendar.JANUARY)
		{
			return "January";
		}
		else
		if (month == Calendar.FEBRUARY)
		{
			return "February";
		}
		else
		if (month == Calendar.MARCH)
		{
			return "March";
		}
		else
		if (month == Calendar.APRIL)
		{
			return "April";
		}
		else
		if (month == Calendar.MAY)
		{
			return "May";
		}
		else
		if (month == Calendar.JUNE)
		{
			return "June";
		}
		else
		if (month == Calendar.JULY)
		{
			return "July";
		}
		else
		if (month == Calendar.AUGUST)
		{
			return "August";
		}
		else
		if (month == Calendar.SEPTEMBER)
		{
			return "September";
		}
		else
		if (month == Calendar.OCTOBER)
		{
			return "October";
		}
		else
		if (month == Calendar.NOVEMBER)
		{
			return "November";
		}
		else
		if (month == Calendar.DECEMBER)
		{
			return "December";
		}
		
		return "";
	}

	//----------------------------------------------------------
	protected int mapMonthNameToIndex(String monthName)
	{
		if (monthName.equals("January") == true)
		{
			return Calendar.JANUARY;
		}
		else
		if (monthName.equals("February") == true)
		{
			return Calendar.FEBRUARY;
		}
		else
		if (monthName.equals("March") == true)
		{
			return Calendar.MARCH;
		}
		else
		if (monthName.equals("April") == true)
		{
			return Calendar.APRIL;
		}
		else
		if (monthName.equals("May") == true)
		{
			return Calendar.MAY;
		}
		else
		if (monthName.equals("June") == true)
		{
			return Calendar.JUNE;
		}
		else
		if (monthName.equals("July") == true)
		{
			return Calendar.JULY;
		}
		else
		if (monthName.equals("August") == true)
		{
			return Calendar.AUGUST;
		}
		else
		if (monthName.equals("September") == true)
		{
			return Calendar.SEPTEMBER;
		}
		else
		if (monthName.equals("October") == true)
		{
			return Calendar.OCTOBER;
		}
		else
		if (monthName.equals("November") == true)
		{
			return Calendar.NOVEMBER;
		}
		else
		if (monthName.equals("December") == true)
		{
			return Calendar.DECEMBER;
		}
		
		return -1;
	}
	
	
	//----------------------------------------------------
   	protected boolean checkProperLetters(String value)
   	{
   		for (int cnt = 0; cnt < value.length(); cnt++)
   		{
   			char ch = value.charAt(cnt);
   			
   			if ((ch >= 'A') && (ch <= 'Z') || (ch >= 'a') && (ch <= 'z'))
   			{
   			}
   			else
   			if ((ch == '-') || (ch == ',') || (ch == '.') || (ch == ' '))
   			{
   			}
   			else
   			{
   				return false;
   			}
   		}
   		
   		return true;
   	}
   	
   	//----------------------------------------------------
   	protected boolean checkProperPhoneNumber(String value)
   	{
   		if ((value == null) || (value.length() < 7))
   		{
   			return false;
   		}
   		
   		for (int cnt = 0; cnt < value.length(); cnt++)
   		{
   			char ch = value.charAt(cnt);
   			
   			if ((ch >= '0') && (ch <= '9'))
   			{
   			}
   			else
   			if ((ch == '-') || (ch == '(') || (ch == ')') || (ch == ' '))
   			{
   			}
   			else
   			{
   				return false;
   			}
   		}
   		
   		return true;
   	}

}

