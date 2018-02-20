// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Properties;

// project imports
import impresario.IModel;

/** The class containing the Modify Article Type View  for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class ModifyArticleTypeView extends AddArticleTypeView
{

	//

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public ModifyArticleTypeView(IModel at)
	{
		super(at);
	}

	//-------------------------------------------------------------
	protected String getActionText()
	{
		return "** Modify Article Type Data **";
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		String bcPrefix = (String)myModel.getState("BarcodePrefix");
		if (bcPrefix != null)
		{
			barcodePrefix.setText(bcPrefix);
		}
		String desc = (String)myModel.getState("Description");
		if (desc != null)
		{
			description.setText(desc);
		}
		String alfaC = (String)myModel.getState("AlphaCode");
		if (alfaC != null)
		{
			alphaCode.setText(alfaC);
		}
	}

}

//---------------------------------------------------------------
//	Revision History:
//


