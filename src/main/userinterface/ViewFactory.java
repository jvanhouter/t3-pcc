package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

    public static View createView(String viewName, IModel model) {
        if (viewName.equals("ReceptionistView")) {
            return new ReceptionistView(model);
        } else if (viewName.equals("AddArticleTypeView")) {
            return new AddArticleTypeView(model);
        } else if (viewName.equals("ModifyArticleTypeView")) {
            return new ModifyArticleTypeView(model);
        } else if (viewName.equals("ArticleTypeCollectionView")) {
            return new ArticleTypeCollectionView(model);
        } else if (viewName.equals("SearchArticleTypeView")) {
            return new SearchArticleTypeView(model);
        } else if(viewName.equals("RemoveArticleTypeView")) {
            return new RemoveArticleTypeView(model);
        } else if(viewName.equals("AddColorView")) {
            return new AddColorView(model);
        } else if(viewName.equals("ModifyColorView")) {
            return new ModifyColorView(model);
        } else if(viewName.equals("ColorCollectionView")) {
            return new ColorCollectionView(model);
        } else if(viewName.equals("SearchColorView")) {
            return new SearchColorView(model);
        } else if(viewName.equals("RemoveColorView")) {
            return new RemoveColorView(model);
        } else if (viewName.equals("AddClothingItemView")) {
            return new AddClothingItemView(model);
        } else if (viewName.equals("BarcodeScannerView")) {
            return new BarcodeScannerView(model);
        } else if (viewName.equals("CheckoutHelperView")) {
            return new CheckoutHelperView(model);
        } else if (viewName.equals("CheckoutInvalidItemView")) {
            return new CheckoutInvalidItemView(model);
        } else if (viewName.equals("RemoveClothingItemView")) {
            return new RemoveClothingItemView(model);
        } else if (viewName.equals("ModifyClothingItemView")) {
            return new ModifyClothingItemView(model);
        } else if (viewName.equals("ClothingItemCollectionView")) {
            return new ClothingItemCollectionView(model);
        } else if(viewName.equals("LogRequestView")) {
            return new LogARequestView(model);
        } else if(viewName.equals("RequestCollectionView")) {
            return new RequestCollectionView(model);
        } else if(viewName.equals("RemoveRequestView") ){
            return new RemoveRequestView(model);
        } else if (viewName.equals("InventoryItemCollectionView")) {
            return new InventoryItemCollectionView(model);
        } else if (viewName.equals("ReceiverRecentCheckoutView")) {
            return new ReceiverRecentCheckoutView(model);
        } else
            return null;
    }


}
