// specify the package
package userinterface;

// system imports
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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

	private PccButton addArticleTypeButton;
	private PccButton updateArticleTypeButton;
	private PccButton removeArticleTypeButton;
	private PccButton addColorButton;
	private PccButton updateColorButton;
	private PccButton removeColorButton;
	private PccButton addClothingItemButton;
	private PccButton updateClothingItemButton;
	private PccButton removeClothingItemButton;
	private PccButton logRequestButton;
	private PccButton fulfillRequestButton;
	private PccButton removeRequestButton;

	private PccButton checkoutClothingItemButton;
	private PccButton listAvailableInventoryButton;

	private PccButton cancelButton;

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
		/* container.setResizable(true); Swing call */
		container.setStyle("-fx-background-color: #707070;");


		container.setPadding(new Insets(50,	50, 50, 50));

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// how do you add white space? regex \\s for space
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
		container.setStyle("-fx-background-color: #808080;");

		Text clientText = new Text(" Office of Career Services ");
		clientText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
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
		inquiryText.setFill(Color.web("#ffc726"));
		inquiryText.setTextAlignment(TextAlignment.CENTER);
		container.getChildren().add(inquiryText);

		return container;
	}


	// Create the navigation buttons
	//-------------------------------------------------------------
	private VBox createFormContents()
	{

		VBox container = new VBox(15);
		container.setStyle("-fx-background-color: #808080;");

		// create the buttons, listen for events, add them to the container
		HBox checkoutCont = new HBox(10);
//		checkoutCont.setStyle("-fx-background-color: #909090;");
		checkoutCont.setAlignment(Pos.CENTER);
		checkoutClothingItemButton = new PccButton("Checkout Clothing Item");
		checkoutClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("CheckoutClothingItem", null));
		checkoutCont.getChildren().add(checkoutClothingItemButton);

		container.getChildren().add(checkoutCont);

		// Article type choices
		HBox articleTypeCont = new HBox(10);
		articleTypeCont.setAlignment(Pos.CENTER);
		Label atLabel = new Label("  Article Types:");
		atLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		atLabel.setStyle("-fx-text-fill: #ffc726");
		articleTypeCont.getChildren().add(atLabel);
		addArticleTypeButton = new PccButton(" Add ");
		addArticleTypeButton.setOnAction(e -> myModel.stateChangeRequest("AddArticleType", null));
		articleTypeCont.getChildren().add(addArticleTypeButton);

		updateArticleTypeButton = new PccButton("Update");
		updateArticleTypeButton.setOnAction(e -> myModel.stateChangeRequest("UpdateArticleType", null));
		articleTypeCont.getChildren().add(updateArticleTypeButton);

		removeArticleTypeButton = new PccButton("Remove");
		removeArticleTypeButton.setOnAction(e -> myModel.stateChangeRequest("RemoveArticleType", null));
		articleTypeCont.getChildren().add(removeArticleTypeButton);
		container.getChildren().add(articleTypeCont);

		// Color choices
		HBox colorCont = new HBox(10);
		colorCont.setAlignment(Pos.CENTER);
		Label colorLabel = new Label("             Colors: ");
		colorLabel.setStyle("-fx-text-fill: #ffc726");
		colorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		colorCont.getChildren().add(colorLabel);
		addColorButton = new PccButton(" Add ");
		addColorButton.setOnAction(e -> myModel.stateChangeRequest("AddColor", null));
		colorCont.getChildren().add(addColorButton);

		updateColorButton = new PccButton("Update");
		updateColorButton.setOnAction(e -> myModel.stateChangeRequest("UpdateColor", null));
		colorCont.getChildren().add(updateColorButton);

		removeColorButton = new PccButton("Remove");
		removeColorButton.setOnAction(e -> myModel.stateChangeRequest("RemoveColor", null));
		colorCont.getChildren().add(removeColorButton);

		// Clothing item choices
		container.getChildren().add(colorCont);

		HBox clothingItemCont = new HBox(10);
		clothingItemCont.setAlignment(Pos.CENTER);
		Label ciLabel = new Label("Clothing Items: ");
		ciLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		ciLabel.setStyle("-fx-text-fill: #ffc726");
		clothingItemCont.getChildren().add(ciLabel);
		addClothingItemButton = new PccButton(" Add ");
		addClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("AddClothingItem", null));
		clothingItemCont.getChildren().add(addClothingItemButton);

		updateClothingItemButton = new PccButton("Update");
		updateClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("ModifyClothingItem", null));
		clothingItemCont.getChildren().add(updateClothingItemButton);

		removeClothingItemButton = new PccButton("Remove");
		removeClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("RemoveClothingItem", null));
		clothingItemCont.getChildren().add(removeClothingItemButton);

		container.getChildren().add(clothingItemCont);

		// Clothing item request
		HBox requestCont = new HBox(10);
		requestCont.setAlignment(Pos.CENTER);
		Label reqLabel = new Label("         Requests: ");
		reqLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        reqLabel.setStyle("-fx-text-fill: #ffc726");
		requestCont.getChildren().add(reqLabel);
		logRequestButton = new PccButton(" Log ");
		logRequestButton.setOnAction(e -> myModel.stateChangeRequest("LogRequest", null));
		requestCont.getChildren().add(logRequestButton);

		fulfillRequestButton = new PccButton(" Fulfill ");
		fulfillRequestButton.setOnAction(e -> myModel.stateChangeRequest("FulfillRequest", null));
		requestCont.getChildren().add(fulfillRequestButton);

		removeRequestButton = new PccButton("Remove");
		removeRequestButton.setOnAction(e -> myModel.stateChangeRequest("RemoveRequest", null));
		requestCont.getChildren().add(removeRequestButton);

		container.getChildren().add(requestCont);

		HBox listAvailCont = new HBox(10);
		listAvailCont.setAlignment(Pos.CENTER);
		listAvailableInventoryButton = new PccButton("List Available Inventory");
		listAvailableInventoryButton.setOnAction(e -> myModel.stateChangeRequest("ListAvailableInventory", null));
		listAvailCont.getChildren().add(listAvailableInventoryButton);

		container.getChildren().add(listAvailCont);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		cancelButton = new PccButton("Exit System");
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
