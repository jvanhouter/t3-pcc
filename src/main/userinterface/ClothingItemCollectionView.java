package userinterface;

// system imports
import Utilities.UiConstants;
import Utilities.Utilities;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.*;

// project imports
import impresario.IModel;
import model.*;

import javax.rmi.CORBA.Util;

//==============================================================================
public class ClothingItemCollectionView extends View
{
	protected TableView<ClothingItemTableModel> tableOfClothingItems;
	protected Button cancelButton;
	protected Button submitButton;

	protected MessageView statusLog;


	//--------------------------------------------------------------------------
	public ClothingItemCollectionView(IModel matt)
	{
		super(matt, "ClothingItemCollectionView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                                            "));

		getChildren().add(container);

		populateFields();
	}

	//--------------------------------------------------------------------------
	protected void populateFields() { getEntryTableModelValues(); }

	//--------------------------------------------------------------------------
	private void refactor(ClothingItemTableModel ctm)
	{
		ctm.setArticleType((String) Utilities.collectArticleTypeHash().get(ctm.getArticleType()).getState("Description"));
		ctm.setColor1((String) Utilities.collectColorHash().get(ctm.getColor1()).getState("Description"));
		if(Utilities.collectColorHash().get(ctm.getColor2()) != null)
			ctm.setColor2((String) Utilities.collectColorHash().get(ctm.getColor2()).getState("Description"));
		else
			ctm.setColor2("");
	}

	//--------------------------------------------------------------------------
	protected void getEntryTableModelValues()
	{

		ObservableList<ClothingItemTableModel> tableData = FXCollections.observableArrayList();
		try
		{
			ClothingItemCollection clothingItemCollection =
				(ClothingItemCollection)myModel.getState("ClothingItemList");

	 		Vector entryList = (Vector)clothingItemCollection.getState("ClothingItems");
			if (entryList.size() > 0)
			{
				Enumeration entries = entryList.elements();

				while (entries.hasMoreElements() == true)
				{
					ClothingItem nextAT = (ClothingItem)entries.nextElement();
					Vector<String> view = nextAT.getEntryListView();

					// add this list entry to the list
					ClothingItemTableModel nextTableRowData = new ClothingItemTableModel(view);
					refactor(nextTableRowData);
					if(nextTableRowData.getSize().equals("" + UiConstants.GENERIC_SIZE))
						nextTableRowData.setSize("");
					tableData.add(nextTableRowData);

				}
			}
			else
			{
				displayMessage("No matching entries found!");
			}

			tableOfClothingItems.setItems(tableData);
		}
		catch (Exception e) {//SQLException e) {
			// Need to handle this exception
			e.printStackTrace();
		}
	}

	// Create the title container
	//-------------------------------------------------------------
	private Node createTitle()
	{
		VBox container = new VBox(10);
		container.setPadding(new Insets(1, 1, 1, 30));

		Text clientText = new Text(" Office of Career Services ");
		clientText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		clientText.setWrappingWidth(350);
		clientText.setTextAlignment(TextAlignment.CENTER);
		clientText.setFill(Color.DARKGREEN);
		container.getChildren().add(clientText);

		Text collegeText = new Text(" THE COLLEGE AT BROCKPORT ");
		collegeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		collegeText.setWrappingWidth(350);
		collegeText.setTextAlignment(TextAlignment.CENTER);
		collegeText.setFill(Color.DARKGREEN);
		container.getChildren().add(collegeText);

		Text titleText = new Text(" Professional Clothes Closet Management System ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(350);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);

		Text blankText = new Text("  ");
		blankText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		blankText.setWrappingWidth(350);
		blankText.setTextAlignment(TextAlignment.CENTER);
		blankText.setFill(Color.WHITE);
		container.getChildren().add(blankText);

		Text actionText = new Text("      ** Matching clothing items **       ");
		actionText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		actionText.setWrappingWidth(350);
		actionText.setTextAlignment(TextAlignment.CENTER);
		actionText.setFill(Color.BLACK);
		container.getChildren().add(actionText);

		return container;
	}

	// Create the main form content
	//-------------------------------------------------------------
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);

		Text prompt = new Text("");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
		prompt.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		vbox.getChildren().add(prompt);

		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
       	grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 25, 10, 0));

		tableOfClothingItems = new TableView<ClothingItemTableModel>();
		tableOfClothingItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		TableColumn barcodeColumn = new TableColumn("Barcode") ;
		barcodeColumn.setMinWidth(50);
		barcodeColumn.setCellValueFactory(
	                new PropertyValueFactory<ClothingItemTableModel, String>("barcode"));

		TableColumn genderColumn = new TableColumn("Gender") ;
		genderColumn.setMinWidth(150);
		genderColumn.setCellValueFactory(
	                new PropertyValueFactory<ClothingItemTableModel, String>("gender"));

		TableColumn sizeColumn = new TableColumn("Size") ;
		sizeColumn.setMinWidth(50);
		sizeColumn.setCellValueFactory(
				new PropertyValueFactory<ClothingItemTableModel, String>("size"));


		TableColumn colorOneColumn = new TableColumn("Color1") ;
		colorOneColumn.setMinWidth(50);
		colorOneColumn.setCellValueFactory(
	                new PropertyValueFactory<ClothingItemTableModel, String>("color1"));

		TableColumn colorTwoColumn = new TableColumn("Color2") ;
		colorTwoColumn.setMinWidth(50);
		colorTwoColumn.setCellValueFactory(
				new PropertyValueFactory<ClothingItemTableModel, String>("color2"));

		TableColumn articleTypeColumn = new TableColumn("Article Type") ;
		articleTypeColumn.setMinWidth(50);
		articleTypeColumn.setCellValueFactory(
				new PropertyValueFactory<ClothingItemTableModel, String>("articleType"));

		TableColumn brandColumn = new TableColumn("Brand") ;
		brandColumn.setMinWidth(50);
		brandColumn.setCellValueFactory(
				new PropertyValueFactory<ClothingItemTableModel, String>("brand"));

		TableColumn donorFirstNameColumn = new TableColumn("Donor First Name") ;
		donorFirstNameColumn.setMinWidth(50);
		donorFirstNameColumn.setCellValueFactory(
				new PropertyValueFactory<ClothingItemTableModel, String>("donorFirstName"));

		TableColumn donorLastNameColumn = new TableColumn("Donor Last Name") ;
		donorLastNameColumn.setMinWidth(50);
		donorLastNameColumn.setCellValueFactory(
				new PropertyValueFactory<ClothingItemTableModel, String>("donorLastName"));

		TableColumn notesColumn = new TableColumn("Notes") ;
		notesColumn.setMinWidth(50);
		notesColumn.setCellValueFactory(
				new PropertyValueFactory<ClothingItemTableModel, String>("notes"));

		tableOfClothingItems.getColumns().addAll(barcodeColumn,
				genderColumn, sizeColumn, colorOneColumn, colorTwoColumn, articleTypeColumn, brandColumn, donorFirstNameColumn, donorLastNameColumn, notesColumn);

		tableOfClothingItems.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
					clearErrorMessage();
					displayMessage("Loading...");
					processClothingItemSelected();
				}
			}
		});
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(150, 150);
		scrollPane.setContent(tableOfClothingItems);

		submitButton = new PccButton("Submit");
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		submitButton.setOnMouseEntered(me ->
        {
        	submitButton.setScaleX(1.1);
        	submitButton.setScaleY(1.1);
        });

        submitButton.setOnMouseExited(me ->
        {
        	submitButton.setScaleX(1);
        	submitButton.setScaleY(1);
        });

        submitButton.setOnMousePressed(me ->
    {
    	submitButton.setScaleX(0.9);
    	submitButton.setScaleY(0.9);
    });
        submitButton.setOnMouseReleased(me ->
    {
    	submitButton.setScaleX(1.1);
    	submitButton.setScaleY(1.1);
    });
 		submitButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	clearErrorMessage();
       		     	displayMessage("Loading...");
					// do the inquiry
					processClothingItemSelected();

            	 }
        	});

		cancelButton = new PccButton("Return");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnMouseEntered(me ->
				 {
					 cancelButton.setScaleX(1.1);
					 cancelButton.setScaleY(1.1);
				 });

				 cancelButton.setOnMouseExited(me ->
				 {
					 cancelButton.setScaleX(1);
					 cancelButton.setScaleY(1);
				 });

				 cancelButton.setOnMousePressed(me ->
		 {
			 cancelButton.setScaleX(0.9);
			 cancelButton.setScaleY(0.9);
		 });
				 cancelButton.setOnMouseReleased(me ->
		 {
			 cancelButton.setScaleX(1.1);
			 cancelButton.setScaleY(1.1);
		 });
 		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
					/**
					 * Process the Cancel button.
					 * The ultimate result of this action is that the transaction will tell the Receptionist to
					 * to switch to the Receptionist view. BUT THAT IS NOT THIS VIEW'S CONCERN.
					 * It simply tells its model (controller) that the transaction was canceled, and leaves it
					 * to the model to decide to tell the Receptionist to do the switch back.
			 		*/
					//----------------------------------------------------------
       		     	clearErrorMessage();
       		     	myModel.stateChangeRequest("CancelClothingItemList", null);
            	  }
        	});

		HBox btnContainer = new HBox(100);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(submitButton);
		btnContainer.getChildren().add(cancelButton);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(scrollPane);
		vbox.getChildren().add(btnContainer);

		return vbox;
	}

	//--------------------------------------------------------------------------
	public void updateState(String key, Object value)
	{
	}

	//--------------------------------------------------------------------------
	protected void processClothingItemSelected()
	{

		ClothingItemTableModel selectedItem = tableOfClothingItems.getSelectionModel().getSelectedItem();

		if(selectedItem != null)
		{
			String selectedBarcode = selectedItem.getBarcode();

			myModel.stateChangeRequest("ClothingItemSelected", selectedBarcode);
		}
	}

	//--------------------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}


	/**
	 * Display info message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}


}
