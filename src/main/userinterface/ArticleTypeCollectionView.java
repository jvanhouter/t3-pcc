package userinterface;

// system imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
import model.ArticleType;
import model.ArticleTypeCollection;

//==============================================================================
public class ArticleTypeCollectionView extends View
{
	private TableView<ArticleTypeTableModel> tableOfArticleTypes;
	protected PccButton cancelButton;
	protected PccButton submitButton;

	protected MessageView statusLog;


	//--------------------------------------------------------------------------
    ArticleTypeCollectionView(IModel matt)
	{
		super(matt, "ArticleTypeCollectionView");

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
	protected void populateFields()
	{
		getEntryTableModelValues();
	}

	//--------------------------------------------------------------------------
    private void getEntryTableModelValues()
	{

		ObservableList<ArticleTypeTableModel> tableData = FXCollections.observableArrayList();
		try
		{
			ArticleTypeCollection articleTypeCollection =
				(ArticleTypeCollection)myModel.getState("ArticleTypeList");

	 		Vector entryList = (Vector)articleTypeCollection.getState("ArticleTypes");

			if (entryList.size() > 0)
			{
				Enumeration entries = entryList.elements();

				while (entries.hasMoreElements())
				{
					ArticleType nextAT = (ArticleType)entries.nextElement();
					Vector<String> view = nextAT.getEntryListView();

					// add this list entry to the list
					ArticleTypeTableModel nextTableRowData = new ArticleTypeTableModel(view);
					tableData.add(nextTableRowData);

				}
			}
			else
			{
				displayMessage("No matching entries found!");
			}

			tableOfArticleTypes.setItems(tableData);
		}
		catch (Exception e) {//SQLException e) {
			// Need to handle this exception
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

		Text actionText = new Text("      ** Matching Article Types **       ");
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

		tableOfArticleTypes = new TableView<ArticleTypeTableModel>();
		tableOfArticleTypes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		TableColumn barcodePrefixColumn = new TableColumn("Barcode Prefix") ;
		barcodePrefixColumn.setMinWidth(50);
		barcodePrefixColumn.setCellValueFactory(
	                new PropertyValueFactory<ArticleTypeTableModel, String>("barcodePrefix"));

		TableColumn descriptionColumn = new TableColumn("Description") ;
		descriptionColumn.setMinWidth(150);
		descriptionColumn.setCellValueFactory(
	                new PropertyValueFactory<ArticleTypeTableModel, String>("description"));

		TableColumn alphaCodeColumn = new TableColumn("Alpha Code") ;
		alphaCodeColumn.setMinWidth(50);
		alphaCodeColumn.setCellValueFactory(
	                new PropertyValueFactory<ArticleTypeTableModel, String>("alphaCode"));

		TableColumn statusColumn = new TableColumn("Status") ;
		statusColumn.setMinWidth(50);
		statusColumn.setCellValueFactory(
	                new PropertyValueFactory<ArticleTypeTableModel, String>("status"));

		tableOfArticleTypes.getColumns().addAll(descriptionColumn,
			barcodePrefixColumn, alphaCodeColumn, statusColumn);

		tableOfArticleTypes.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                processArticleTypeSelected();
            }
        });
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(150, 150);
		scrollPane.setContent(tableOfArticleTypes);

		submitButton = new PccButton("Submit");
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
 		submitButton.setOnAction(e -> {
 			clearErrorMessage();
            processArticleTypeSelected();

	  });

		cancelButton = new PccButton("Return");
 		cancelButton.setOnAction(e -> {
			 clearErrorMessage();
			 myModel.stateChangeRequest("CancelArticleTypeList", null);
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
    private void processArticleTypeSelected()
	{
		ArticleTypeTableModel selectedItem = tableOfArticleTypes.getSelectionModel().getSelectedItem();

		if(selectedItem != null)
		{
			String selectedBarcodePrefix = selectedItem.getBarcodePrefix();

			myModel.stateChangeRequest("ArticleTypeSelected", selectedBarcodePrefix);
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
