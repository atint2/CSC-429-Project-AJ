package userinterface;

import impresario.IModel;
import model.PatronCollection;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if (viewName.equals("LibrarianView") == true) {
			return new LibrarianView(model);
		}
		else if(viewName.equals("TransactionChoiceView") == true)
		{
			return new TransactionChoiceView(model);
		}
		else if(viewName.equals("AccountCollectionView") == true)
		{
			return new AccountCollectionView(model);
		}
		else if(viewName.equals("AccountView") == true)
		{
			return new AccountView(model);
		}
		else if(viewName.equals("AccountHolderIDEntryView") == true)
		{
			return new AccountHolderIDEntryView(model);
		}
		else if(viewName.equals("DepositTransactionView") == true)
		{
			return new DepositTransactionView(model);
		}
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
		}
		else if (viewName.equals("BookView") == true)
		{
			return new BookView(model);
		}
		else if (viewName.equals("PatronView") == true) {
			return new PatronView(model);
		}
		else if (viewName.equals("SearchBookView") == true)
		{
			return new SearchBookView(model);
		}
		else if (viewName.equals("SearchPatronView") == true)
		{
			return new SearchPatronView(model);
		} else if (viewName.equals("BookCollectionView") == true)
		{
			return new BookCollectionView(model);
		} else if (viewName.equals("PatronCollectionView") == true)
		{
			return new PatronCollectionView(model);
		}
		else
			return null;
	}


	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else
			return null;
	}
	*/

}
