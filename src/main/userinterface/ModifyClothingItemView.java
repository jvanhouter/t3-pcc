package userinterface;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

// project imports
import impresario.IModel;
import model.ArticleType;

/** The class containing the Modify Article Type View  for the Professional Clothes
	 *  Closet application 
	 */
	//==============================================================
	public class ModifyClothingItemView extends AddClothingItemView
	{

		//

		// constructor for this class -- takes a model object
		//----------------------------------------------------------
		public ModifyClothingItemView(IModel at)
		{
			super(at);
		}

		//-------------------------------------------------------------
		protected String getActionText()
		{
			return "** Modify Clothing Item Data **";
		}

		//-------------------------------------------------------------
		public void populateFields()
		{
			super.populateFields();
			String barcodeString = (String)myModel.getState("Barcode");
			if (barcodeString != null)
			{
				//barcode.setText(barcodeString);
			}
			String genderString = (String)myModel.getState("Gender");
			if (genderString != null)
			{
				genderCombo.setValue(genderString);
			}
			String atString = (String)myModel.getState("ArticleType");
			if (atString != null)
			{
				Vector ArticleList = (Vector) myModel.getState("Articles");
				Iterator articles = ArticleList.iterator();
				ObservableList<ArticleType> articleTypes = FXCollections.observableArrayList();
				while (articles.hasNext()) {
					ArticleType at = (ArticleType) articles.next();
					System.out.println("ModifyClothingItemView: " + atString);
					if(at.getState("ID") == atString) {
						articleTypeCombo.setValue(at);
					}
				}
			}
			String color1String= (String)myModel.getState("Color1");
			if (color1String != null)
			{
			//	color1.setPromptText(color1String);
			}
			String color2String = (String)myModel.getState("Color2");
			if (color2String != null)
			{
			//	color2.setPromptText(color2String);
			}
			String brandString = (String)myModel.getState("Brand");
			if (brandString != null)
			{
			//	brand.setText(brandString);
			}
			String donorInfoString = (String)myModel.getState("DonorInformation");
			if (donorInfoString != null)
			{
			//	donorInformation.setText(donorInfoString);
			}
			String noteString = (String)myModel.getState("Notes");
			if (noteString != null)
			{
			//	notes.setText(noteString);
			}
			
		}

	}

	//---------------------------------------------------------------
//		Revision History:
	//


