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
        } else if (viewName.equals("EnterReceiverInformationView")) {
            return new EnterReceiverInformationView(model);
        } else if (viewName.equals("AddClothingItemView")) {
            return new AddClothingItemView(model);
        } else if (viewName.equals("BarcodeSearchView")) {
            return new BarcodeSearchView(model);
        } else
            return null;
    }


}