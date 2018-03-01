package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("ReceptionistView") == true)
		{
			return new ReceptionistView(model);
		} 
		else if(viewName.equals("AddArticleTypeView") == true)
		{
			return new AddArticleTypeView(model);
		} 
		else if(viewName.equals("ModifyArticleTypeView") == true)
		{
			return new ModifyArticleTypeView(model);
		} 
		else if(viewName.equals("ArticleTypeCollectionView") == true)
		{
			return new ArticleTypeCollectionView(model);
		}
		else if(viewName.equals("SearchArticleTypeView") == true)
		{
			return new SearchArticleTypeView(model);
		}
		else if(viewName.equals("RemoveArticleTypeView") == true)
		{
			return new RemoveArticleTypeView(model);
		}
		/*
		else if(viewName.equals("DepositAmountView") == true)
		{
			return new DepositAmountView(model);
		}
		else if(viewName.equals("WithdrawTransactionView") == true)
		{
			return new WithdrawTransactionView(model);
		}
		else if(viewName.equals("TransferTransactionView") == true)
		{
			return new TransferTransactionView(model);
		}
		else if(viewName.equals("BalanceInquiryTransactionView") == true)
		{
			return new BalanceInquiryTransactionView(model);
		}
		else if(viewName.equals("BalanceInquiryReceipt") == true)
		{
			return new BalanceInquiryReceipt(model);
		}
		else if(viewName.equals("WithdrawReceipt") == true)
		{
			return new WithdrawReceipt(model);
		}
		else if(viewName.equals("DepositReceipt") == true)
		{
			return new DepositReceipt(model);
		}
		else if(viewName.equals("TransferReceipt") == true)
		{
			return new TransferReceipt(model);
		} */
		else
			return null;
	}


	

}
