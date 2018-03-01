// specify the package
package model;

/**
 * The class containing the TransactionFactory for the Professional Clothes
 * Closet application
 */
//==============================================================
public class TransactionFactory {
    public static Transaction createTransaction(String transType)
            throws Exception {

        if (transType.equals("AddArticleType")) {
            return new AddArticleTypeTransaction();
        } else if (transType.equals("UpdateArticleType")) {
            return new UpdateArticleTypeTransaction();
        } else if (transType.equals("CheckoutClothingItem")) {
            return new CheckoutClothingItemTransaction();
        } else if (transType.equals("AddClothingItem") ||
                transType.equals("CheckoutClothingItem") ||
                transType.equals("UpdateClothingItem") ||
                transType.equals("RemoveClothingItem"))  {
            return new BarcodeSearchTransaction(transType);
        } else {
            return null;
        }
    }
}
