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
import model.Color;

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
			if(atString != null) {
				Vector ArticleList = (Vector) myModel.getState("Articles");
				Iterator articles = ArticleList.iterator();
				ObservableList<ArticleType> articleTypes = FXCollections.observableArrayList();
				while (articles.hasNext()) {
					//articleTypes.add((ArticleType) articles.next());
					ArticleType at = (ArticleType) articles.next();
					if (at.getState("ID").equals(atString)) {
						articleTypeCombo.setValue(at);
					}
				}
			}
			String color1String= (String)myModel.getState("Color1");
			if (color1String != null)
			{
				Vector ColorList = (Vector) myModel.getState("Colors");
				Iterator colors = ColorList.iterator();
				ObservableList<model.Color> colorItems = FXCollections.observableArrayList();
				while (colors.hasNext()) {
					Color ct = (Color) colors.next();
					if(ct.getState("ID").equals(color1String)) {
						primaryColorCombo.setValue(ct);
					}
				}
			}
			String color2String = (String)myModel.getState("Color2");
			if (color2String != null)
			{
				Vector ColorList = (Vector) myModel.getState("Colors");
				Iterator colors = ColorList.iterator();
				ObservableList<model.Color> colorItems = FXCollections.observableArrayList();
				while (colors.hasNext()) {
					Color ct = (Color) colors.next();
					if(ct.getState("ID").equals(color2String)) {
						secondaryColorCombo.setValue(ct);
					}
				}
			}
			String brandString = (String)myModel.getState("Brand");
			if (brandString != null)
			{
				brandText.setText(brandString);
			}
			String sizeString = (String)myModel.getState("Size");
			if (sizeString != null)
			{
				sizeText.setText(sizeString);
			}
			String donorLast = (String)myModel.getState("DonorLastName");
			if (donorLast != null)
			{
				donorLastNameText.setText(donorLast);
			}
			String donorFirst = (String)myModel.getState("DonorFirstName");
			if (donorFirst != null)
			{
				donorFirstNameText.setText(donorFirst);
			}
			String donorPhoneString = (String)myModel.getState("DonorPhone");
			if (donorPhoneString != null)
			{
				donorPhoneText.setText(donorPhoneString);
			}
			String donorEmailString = (String)myModel.getState("DonorEmail");
			if (donorEmailString != null)
			{
				donorEmailText.setText(donorEmailString);
			}
			String noteString = (String)myModel.getState("Notes");
			if (noteString != null)
			{
				notesText.setText(noteString);
			}

		}

	}

	//---------------------------------------------------------------
//		Revision History:
	//
