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
        } else if (transType.equals("AddClothingItem")) {
            return new AddClothingItemTransaction();
        } else if (transType.equals("UpdateClothingItem")) {
            return null; //new UpdateClothingItemTransaction();
        } else if (transType.equals("RemoveClothingItem")) {
            return null; //new RemoveClothingItemTransaction();
//        } else if (transType.equals("BarcodeSearch")) {
//            return new BarcodeSearchTransaction();
        } else if (transType.equals("RemoveArticleType")) {
            return new RemoveArticleTypeTransaction();
        } else if (transType.equals("AddColor")) {
            return new AddColorTransaction();
        } else if (transType.equals("UpdateColor")) {
            return new UpdateColorTransaction();
        } else if (transType.equals("RemoveColor")) {
            return new RemoveColorTransaction();
        } else {
            return null;
        }
    }
}
