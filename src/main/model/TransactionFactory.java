// specify the package
package model;

// system imports
import java.util.Vector;


// project imports

/** The class containing the TransactionFactory for the Professional Clothes 
 *  Closet application 
 */
//==============================================================
public class TransactionFactory
{

	/**
	 *
	 */
	//----------------------------------------------------------
	public static Transaction createTransaction(String transType)
		throws Exception
	{
		Transaction retValue = null;

		if (transType.equals("AddArticleType") == true)
		{
			retValue = new AddArticleTypeTransaction();
		} 
		else
		if (transType.equals("UpdateArticleType") == true)
		{
			retValue = new UpdateArticleTypeTransaction();
		}
		else
		if (transType.equals("CheckoutClothingItem") == true)
		{
			retValue = new CheckoutClothingItemTransaction();
		}/*
		else
		if (transType.equals("BalanceInquiry") == true)
		{
			retValue = new BalanceInquiryTransaction(cust);
		}
		else
		if (transType.equals("ImposeServiceCharge") == true)
		{
			retValue = new ImposeServiceChargeTransaction(cust);
		} */

		return retValue;
	}
}
