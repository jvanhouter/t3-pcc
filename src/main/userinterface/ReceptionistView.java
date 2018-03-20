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

// project imports
import impresario.IModel;

/** The class containing the Transaction Choice View  for the ATM application */
//==============================================================
public class ReceptionistView extends View
{

	// other private data
	private final int labelWidth = 120;
	private final int labelHeight = 25;

	// GUI components

	private Button addArticleTypeButton;
	private Button updateArticleTypeButton;
	private Button removeArticleTypeButton;
	private Button addColorButton;
	private Button updateColorButton;
	private Button removeColorButton;
	private Button addClothingItemButton;
	private Button updateClothingItemButton;
	private Button removeClothingItemButton;
	private Button logRequestButton;
	private Button fulfillRequestButton;
	private Button removeRequestButton;
	
	private Button checkoutClothingItemButton;
	private Button listAvailableInventoryButton;

	private Button cancelButton;

	private MessageView statusLog;

	/* Remove later --> Testing */

	private Button testing;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public ReceptionistView(IModel teller)
	{
		super(teller, "ReceptionistView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());
		
		// how do you add white space?
		container.getChildren().add(new Label(" "));

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContents());

		container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

		populateFields();

		myModel.subscribe("TransactionError", this);
	}

	// Create the labels and fields
	//-------------------------------------------------------------
	private VBox createTitle()
	{
		VBox container = new VBox(10);
		
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

		Text inquiryText = new Text("       What do you wish to do today?       ");
		inquiryText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		inquiryText.setWrappingWidth(350);
		inquiryText.setTextAlignment(TextAlignment.CENTER);
		inquiryText.setFill(Color.BLACK);
		container.getChildren().add(inquiryText);
	
		return container;
	}


	// Create the navigation buttons
	//-------------------------------------------------------------
	private VBox createFormContents()
	{

		VBox container = new VBox(15);

		// create the buttons, listen for events, add them to the container
		HBox checkoutCont = new HBox(10);
		checkoutCont.setAlignment(Pos.CENTER);
		checkoutClothingItemButton = new Button("Checkout Clothing Item");
		checkoutClothingItemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		checkoutClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("CheckoutClothingItem", null));
		checkoutCont.getChildren().add(checkoutClothingItemButton);
		
		container.getChildren().add(checkoutCont);

		// Article type choices
		HBox articleTypeCont = new HBox(10);
		articleTypeCont.setAlignment(Pos.CENTER_LEFT);
		Label atLabel = new Label("  Article Types: ");
		atLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		articleTypeCont.getChildren().add(atLabel);
		addArticleTypeButton = new Button("Add");
		addArticleTypeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		addArticleTypeButton.setOnAction(e -> myModel.stateChangeRequest("AddArticleType", null));
		articleTypeCont.getChildren().add(addArticleTypeButton);
		
		updateArticleTypeButton = new Button("Update");
		updateArticleTypeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateArticleTypeButton.setOnAction(e -> myModel.stateChangeRequest("UpdateArticleType", null));
		articleTypeCont.getChildren().add(updateArticleTypeButton);

		removeArticleTypeButton = new Button("Remove");
		removeArticleTypeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeArticleTypeButton.setOnAction(e -> myModel.stateChangeRequest("RemoveArticleType", null));
		articleTypeCont.getChildren().add(removeArticleTypeButton);

		container.getChildren().add(articleTypeCont);

		// Color choices
		HBox colorCont = new HBox(10);
		colorCont.setAlignment(Pos.CENTER_LEFT);
		Label colorLabel = new Label("             Colors: ");
		colorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		colorCont.getChildren().add(colorLabel);
		addColorButton = new Button("Add");
		addColorButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		addColorButton.setOnAction(e -> myModel.stateChangeRequest("AddColor", null));
		colorCont.getChildren().add(addColorButton);
		
		updateColorButton = new Button("Update");
		updateColorButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateColorButton.setOnAction(e -> myModel.stateChangeRequest("UpdateColor", null));
		colorCont.getChildren().add(updateColorButton);

		removeColorButton = new Button("Remove");
		removeColorButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeColorButton.setOnAction(e -> myModel.stateChangeRequest("RemoveColor", null));
		colorCont.getChildren().add(removeColorButton);

		// Clothing item choices
		container.getChildren().add(colorCont);
		
		HBox clothingItemCont = new HBox(10);
		clothingItemCont.setAlignment(Pos.CENTER_LEFT);
		Label ciLabel = new Label("Clothing Items: ");
		ciLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		clothingItemCont.getChildren().add(ciLabel);
		addClothingItemButton = new Button("Add");
		addClothingItemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		addClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("AddClothingItem", null));
		clothingItemCont.getChildren().add(addClothingItemButton);
		
		updateClothingItemButton = new Button("Update");
		updateClothingItemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("UpdateClothingItem", null));
		clothingItemCont.getChildren().add(updateClothingItemButton);

		removeClothingItemButton = new Button("Remove");
		removeClothingItemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("RemoveClothingItem", null));
		clothingItemCont.getChildren().add(removeClothingItemButton);
		
		container.getChildren().add(clothingItemCont);

		// Clothing item request
		HBox requestCont = new HBox(10);
		requestCont.setAlignment(Pos.CENTER_LEFT);
		Label reqLabel = new Label("         Requests: ");
		reqLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		requestCont.getChildren().add(reqLabel);
		logRequestButton = new Button("Log");
		logRequestButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		logRequestButton.setOnAction(e -> myModel.stateChangeRequest("LogRequest", null));
		requestCont.getChildren().add(logRequestButton);
		
		fulfillRequestButton = new Button(" Fulfill ");
		fulfillRequestButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		fulfillRequestButton.setOnAction(e -> myModel.stateChangeRequest("FulfillRequest", null));
		requestCont.getChildren().add(fulfillRequestButton);

		removeRequestButton = new Button("Remove");
		removeRequestButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeRequestButton.setOnAction(e -> myModel.stateChangeRequest("RemoveRequest", null));
		requestCont.getChildren().add(removeRequestButton);
		
		container.getChildren().add(requestCont);
		
		HBox listAvailCont = new HBox(10);
		listAvailCont.setAlignment(Pos.CENTER);
		listAvailableInventoryButton = new Button("List Available Inventory");
		listAvailableInventoryButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		listAvailableInventoryButton.setOnAction(e -> myModel.stateChangeRequest("ListAvailableInventory", null));
		listAvailCont.getChildren().add(listAvailableInventoryButton);
		
		container.getChildren().add(listAvailCont);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		cancelButton = new Button("Exit System");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(e -> myModel.stateChangeRequest("ExitSystem", null));
		doneCont.getChildren().add(cancelButton);

		container.getChildren().add(doneCont);

		return container;
	}

	// Create the status log field
	//-------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage)
	{

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{

	}
	

	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		if (key.equals("TransactionError"))
		{
			// display the passed text
			displayErrorMessage((String)value);
		}
	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
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


