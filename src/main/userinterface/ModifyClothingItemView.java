package userinterface;


import Utilities.UiConstants;
import Utilities.Utilities;
import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ArticleType;
import model.Color;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

// project imports

/**
 * The class containing the Modify Article Type View  for the Professional Clothes
 * Closet application
 */
//==============================================================
public class ModifyClothingItemView extends AddClothingItemView {

    //

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public ModifyClothingItemView(IModel clothingItem) {
        super(clothingItem);
    }

    //-------------------------------------------------------------
    protected String getActionText() {
        return "Modify a Clothing Item";
    }

    //-------------------------------------------------------------
    public void populateFields() {
        super.populateFields();
        String barcodeString = (String) myModel.getState("Barcode");
        if (barcodeString != null) {
            //barcode.setText(barcodeString);
        }
        String genderString = (String) myModel.getState("Gender");
        if (genderString != null) {
            genderCombo.setValue(genderString);
        }
        String atString = (String) myModel.getState("ArticleType");
        Iterator articles = Utilities.collectArticleTypeHash().entrySet().iterator();
        while (articles.hasNext()) {
            Map.Entry pair = (Map.Entry)articles.next();
            if(pair.getKey().equals(atString)) {
                articleTypeCombo.setValue((ArticleType) pair.getValue());
                break;
            }
        }
        String color1String = (String) myModel.getState("Color1");
        if (color1String != null) {
            Iterator colors = Utilities.collectColorHash().entrySet().iterator();
            while (colors.hasNext()) {
                Map.Entry pair = (Map.Entry)colors.next();
                if(pair.getKey().equals(color1String)) {
                    primaryColorCombo.setValue((Color) pair.getValue());
                    break;
                }
            }
        }
        String color2String = (String) myModel.getState("Color2");
        if (color2String != null) {
            Iterator colors = Utilities.collectColorHash().entrySet().iterator();
            while (colors.hasNext()) {
                Map.Entry pair = (Map.Entry)colors.next();
                if(pair.getKey().equals(color2String)) {
                    secondaryColorCombo.setValue((Color) pair.getValue());
                    break;
                }
            }
        }
        String brandString = (String) myModel.getState("Brand");
        if (brandString != null) {
            brandText.setText(brandString);
        }
        String sizeString = (String) myModel.getState("Size");
        if (sizeString != null) {
            if (sizeString.equals("" + UiConstants.GENERIC_SIZE))
                sizeText.setText("");
            else
                sizeText.setText(sizeString);
        }
        String donorLast = (String) myModel.getState("DonorLastName");
        if (donorLast != null) {
            donorLastNameText.setText(donorLast);
        }
        String donorFirst = (String) myModel.getState("DonorFirstName");
        if (donorFirst != null) {
            donorFirstNameText.setText(donorFirst);
        }
        String donorPhoneString = (String) myModel.getState("DonorPhone");
        if (donorPhoneString != null) {
            donorPhoneText.setText(donorPhoneString);
        }
        String donorEmailString = (String) myModel.getState("DonorEmail");
        if (donorEmailString != null) {
            donorEmailText.setText(donorEmailString);
        }
        String noteString = (String) myModel.getState("Notes");
        if (noteString != null) {
            notesText.setText(noteString);
        }

    }

}

//---------------------------------------------------------------
//		Revision History:
//
