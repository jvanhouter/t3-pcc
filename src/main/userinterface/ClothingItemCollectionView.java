package userinterface;

// system imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import model.ClothingItem;
import model.ClothingItemCollection;

//==============================================================================
public class ClothingItemCollectionView extends View
{
	protected TableView<ClothingItemTableModel> tableOfClothingItems;
	protected PccButton cancelButton;
	protected PccButton submitButton;

	protected MessageView statusLog;



	public ClothingItemCollectionView(IModel clothingItem)
	{
		super(clothingItem, "ClothingItemCollectionView");

		// create a container for showing the contents
		VBox container = getParentContainer();

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

		container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

		populateFields();
	}


	protected void populateFields()
	{
		getEntryTableModelValues();
	}


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

				while (entries.hasMoreElements() )
				{
					ClothingItem nextAT = (ClothingItem)entries.nextElement();
					Vector<String> view = nextAT.getEntryListView();

					// add this list entry to the list
					ClothingItemTableModel nextTableRowData = new ClothingItemTableModel(view);
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

	@Override
	protected String getActionText() {
		return "** Matching clothing items **";
	}

	// Create the main form content

	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);

		Text prompt = new Text("");
        prompt.setWrappingWidth(WRAPPING_WIDTH);
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

		TableColumn donorInfoColumn = new TableColumn("Donor Info") ;
		donorInfoColumn.setMinWidth(50);
		donorInfoColumn.setCellValueFactory(
				new PropertyValueFactory<ClothingItemTableModel, String>("donorInformation"));

		TableColumn notesColumn = new TableColumn("Notes") ;
		notesColumn.setMinWidth(50);
		notesColumn.setCellValueFactory(
				new PropertyValueFactory<ClothingItemTableModel, String>("notes"));

		tableOfClothingItems.getColumns().addAll(barcodeColumn,
				genderColumn, sizeColumn, colorOneColumn, colorTwoColumn, articleTypeColumn, brandColumn, donorInfoColumn, notesColumn);

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


	public void updateState(String key, Object value)
	{
	}


	protected void processClothingItemSelected()
	{

		ClothingItemTableModel selectedItem = tableOfClothingItems.getSelectionModel().getSelectedItem();

		if(selectedItem != null)
		{
			String selectedBarcode = selectedItem.getBarcode();

			myModel.stateChangeRequest("ClothingItemSelected", selectedBarcode);
		}
	}


	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}


	/**
	 * Display info message
	 */

	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */

	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}


}
