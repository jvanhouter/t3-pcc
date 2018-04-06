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
				//genderCombo.setValue(genderString);
			}
			String atString = (String)myModel.getState("ArticleType");
			if (atString != null)
			{
			//articleType.setPromptText(atString);
			}
			String color1String= (String)myModel.getState("Color1");
			if (color1String != null)
			{
				//color1.setPromptText(color1String);
			}
			String color2String = (String)myModel.getState("Color2");
			if (color2String != null)
			{
				//color2.setPromptText(color2String);
			}
			String brandString = (String)myModel.getState("Brand");
			if (brandString != null)
			{
				//brand.setText(brandString);
			}
			String sizeString = (String)myModel.getState("Size");
			if (sizeString != null)
			{
			//size.setText(sizeString);
			}
			String donorLast = (String)myModel.getState("DonorLastName");
			if (donorLast != null)
			{
				//donorLastName.setText(donorLast);
			}
			String donorFirst = (String)myModel.getState("DonorFirstName");
			if (donorFirst != null)
			{
				//donorFirstName.setText(donorFirst);
			}
			String donorPhoneString = (String)myModel.getState("DonorPhone");
			if (donorPhoneString != null)
			{
				//donorphone.setText(donorPhoneString);
			}
			String donorEmailString = (String)myModel.getState("DonorEmail");
			if (donorEmailString != null)
			{
				//donorEmail.setText(donorEmailString);
			}
			String noteString = (String)myModel.getState("Notes");
			if (noteString != null)
			{
				//notes.setText(noteString);
			}

		}

	}

	//---------------------------------------------------------------
//		Revision History:
	//
