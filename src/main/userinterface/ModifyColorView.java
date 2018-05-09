// specify the package
package userinterface;

// system imports

import impresario.IModel;

// project imports

/**
 * The class containing the Modify Article Type View  for the Professional Clothes
 * Closet application
 */
//==============================================================
public class ModifyColorView extends AddColorView {

    //

    // constructor for this class -- takes a model object

    public ModifyColorView(IModel color) {
        super(color);
    }


    protected String getActionText() {
        return "Modify a Color";
    }


    public void populateFields() {
        String bcPrefix = (String) myModel.getState("BarcodePrefix");
        if (bcPrefix != null) {
            barcodePrefix.setText(bcPrefix);
        }
        String desc = (String) myModel.getState("Description");
        if (desc != null) {
            description.setText(desc);
        }
        String alfaC = (String) myModel.getState("AlphaCode");
        if (alfaC != null) {
            alphaCode.setText(alfaC);
        }
    }

}


//	Revision History:
//
