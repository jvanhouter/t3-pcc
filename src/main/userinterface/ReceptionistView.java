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
		checkoutCont.setStyle("-fx-background-color: #909090;");
		checkoutCont.setAlignment(Pos.CENTER);
		checkoutClothingItemButton = new Button("Checkout Clothing Item");
		checkoutClothingItemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		checkoutClothingItemButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
		checkoutClothingItemButton.setOnMouseEntered(me ->
				 {
					 checkoutClothingItemButton.setScaleX(1.1);
					 checkoutClothingItemButton.setScaleY(1.1);
				 });

				 checkoutClothingItemButton.setOnMouseExited(me ->
				 {
					 checkoutClothingItemButton.setScaleX(1);
					 checkoutClothingItemButton.setScaleY(1);
				 });

				 checkoutClothingItemButton.setOnMousePressed(me ->
		 {
			 checkoutClothingItemButton.setScaleX(0.9);
			 checkoutClothingItemButton.setScaleY(0.9);
		 });
				 checkoutClothingItemButton.setOnMouseReleased(me ->
		 {
			 checkoutClothingItemButton.setScaleX(1.1);
			 checkoutClothingItemButton.setScaleY(1.1);
		 });
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
		addArticleTypeButton = new Button(" Add ");
		addArticleTypeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		addArticleTypeButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
		addArticleTypeButton.setOnMouseEntered(me ->
				 {
					 addArticleTypeButton.setScaleX(1.1);
					 addArticleTypeButton.setScaleY(1.1);
				 });

				 addArticleTypeButton.setOnMouseExited(me ->
				 {
					 addArticleTypeButton.setScaleX(1);
					 addArticleTypeButton.setScaleY(1);
				 });

				 addArticleTypeButton.setOnMousePressed(me ->
		 {
			 addArticleTypeButton.setScaleX(0.9);
			 addArticleTypeButton.setScaleY(0.9);
		 });
				 addArticleTypeButton.setOnMouseReleased(me ->
		 {
			 addArticleTypeButton.setScaleX(1.1);
			 addArticleTypeButton.setScaleY(1.1);
		 });
		addArticleTypeButton.setOnAction(e -> myModel.stateChangeRequest("AddArticleType", null));
		articleTypeCont.getChildren().add(addArticleTypeButton);

		updateArticleTypeButton = new Button("Update");
		updateArticleTypeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateArticleTypeButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
		updateArticleTypeButton.setOnMouseEntered(me ->
				 {
					 updateArticleTypeButton.setScaleX(1.1);
					 updateArticleTypeButton.setScaleY(1.1);
				 });

				 updateArticleTypeButton.setOnMouseExited(me ->
				 {
					 updateArticleTypeButton.setScaleX(1);
					 updateArticleTypeButton.setScaleY(1);
				 });

				 updateArticleTypeButton.setOnMousePressed(me ->
		 {
			 updateArticleTypeButton.setScaleX(0.9);
			 updateArticleTypeButton.setScaleY(0.9);
		 });
				 updateArticleTypeButton.setOnMouseReleased(me ->
		 {
			 updateArticleTypeButton.setScaleX(1.1);
			 updateArticleTypeButton.setScaleY(1.1);
		 });
		updateArticleTypeButton.setOnAction(e -> myModel.stateChangeRequest("UpdateArticleType", null));
		articleTypeCont.getChildren().add(updateArticleTypeButton);

		removeArticleTypeButton = new Button("Remove");
		removeArticleTypeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeArticleTypeButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
		removeArticleTypeButton.setOnMouseEntered(me ->
				 {
					 removeArticleTypeButton.setScaleX(1.1);
					 removeArticleTypeButton.setScaleY(1.1);
				 });

				 removeArticleTypeButton.setOnMouseExited(me ->
				 {
					 removeArticleTypeButton.setScaleX(1);
					 removeArticleTypeButton.setScaleY(1);
				 });

				 removeArticleTypeButton.setOnMousePressed(me ->
		 {
			 removeArticleTypeButton.setScaleX(0.9);
			 removeArticleTypeButton.setScaleY(0.9);
		 });
				 removeArticleTypeButton.setOnMouseReleased(me ->
		 {
			 removeArticleTypeButton.setScaleX(1.1);
			 removeArticleTypeButton.setScaleY(1.1);
		 });
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
		addColorButton = new Button(" Add ");
		addColorButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		addColorButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
		addColorButton.setOnMouseEntered(me ->
				 {
					 addColorButton.setScaleX(1.1);
					 addColorButton.setScaleY(1.1);
				 });

				 addColorButton.setOnMouseExited(me ->
				 {
					 addColorButton.setScaleX(1);
					 addColorButton.setScaleY(1);
				 });

				 addColorButton.setOnMousePressed(me ->
		 {
			 addColorButton.setScaleX(0.9);
			 addColorButton.setScaleY(0.9);
		 });
				 addColorButton.setOnMouseReleased(me ->
		 {
			 addColorButton.setScaleX(1.1);
			 addColorButton.setScaleY(1.1);
		 });
		addColorButton.setOnAction(e -> myModel.stateChangeRequest("AddColor", null));
		colorCont.getChildren().add(addColorButton);

		updateColorButton = new Button("Update");
		updateColorButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateColorButton.setAlignment(Pos.CENTER);
		updateColorButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
		updateColorButton.setOnMouseEntered(me ->
				 {
					 updateColorButton.setScaleX(1.1);
					 updateColorButton.setScaleY(1.1);
				 });

				 updateColorButton.setOnMouseExited(me ->
				 {
					 updateColorButton.setScaleX(1);
					 updateColorButton.setScaleY(1);
				 });

				 updateColorButton.setOnMousePressed(me ->
		 {
			 updateColorButton.setScaleX(0.9);
			 updateColorButton.setScaleY(0.9);
		 });
				 updateColorButton.setOnMouseReleased(me ->
		 {
			 updateColorButton.setScaleX(1.1);
			 updateColorButton.setScaleY(1.1);
		 });
		updateColorButton.setOnAction(e -> myModel.stateChangeRequest("UpdateColor", null));
		colorCont.getChildren().add(updateColorButton);

		removeColorButton = new Button("Remove");
		removeColorButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeColorButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");

		removeColorButton.setOnMouseEntered(me ->
				 {
					 removeColorButton.setScaleX(1.1);
					 removeColorButton.setScaleY(1.1);
				 });

				 removeColorButton.setOnMouseExited(me ->
				 {
					 removeColorButton.setScaleX(1);
					 removeColorButton.setScaleY(1);
				 });

				 removeColorButton.setOnMousePressed(me ->
		 {
			 removeColorButton.setScaleX(0.9);
			 removeColorButton.setScaleY(0.9);
		 });
				 removeColorButton.setOnMouseReleased(me ->
		 {
			 removeColorButton.setScaleX(1.1);
			 removeColorButton.setScaleY(1.1);
		 });
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
		addClothingItemButton = new Button(" Add ");
		addClothingItemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		addClothingItemButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");

		addClothingItemButton.setOnMouseEntered(me ->
				 {
					 addClothingItemButton.setScaleX(1.1);
					 addClothingItemButton.setScaleY(1.1);
				 });

				 addClothingItemButton.setOnMouseExited(me ->
				 {
					 addClothingItemButton.setScaleX(1);
					 addClothingItemButton.setScaleY(1);
				 });

				 addClothingItemButton.setOnMousePressed(me ->
		 {
			 addClothingItemButton.setScaleX(0.9);
			 addClothingItemButton.setScaleY(0.9);
		 });
				 addClothingItemButton.setOnMouseReleased(me ->
		 {
			 addClothingItemButton.setScaleX(1.1);
			 addClothingItemButton.setScaleY(1.1);
		 });
		addClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("AddClothingItem", null));
		clothingItemCont.getChildren().add(addClothingItemButton);

		updateClothingItemButton = new Button("Update");
		updateClothingItemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateClothingItemButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
		updateClothingItemButton.setOnMouseEntered(me ->
				 {
					 updateClothingItemButton.setScaleX(1.1);
					 updateClothingItemButton.setScaleY(1.1);
				 });

				 updateClothingItemButton.setOnMouseExited(me ->
				 {
					 updateClothingItemButton.setScaleX(1);
					 updateClothingItemButton.setScaleY(1);
				 });

				 updateClothingItemButton.setOnMousePressed(me ->
		 {
			 updateClothingItemButton.setScaleX(0.9);
			 updateClothingItemButton.setScaleY(0.9);
		 });
				 updateClothingItemButton.setOnMouseReleased(me ->
		 {
			 updateClothingItemButton.setScaleX(1.1);
			 updateClothingItemButton.setScaleY(1.1);
		 });
		updateClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("ModifyClothingItem", null));
		clothingItemCont.getChildren().add(updateClothingItemButton);

		removeClothingItemButton = new Button("Remove");
		removeClothingItemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeClothingItemButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
		removeClothingItemButton.setOnMouseEntered(me ->
				 {
					 removeClothingItemButton.setScaleX(1.1);
					 removeClothingItemButton.setScaleY(1.1);
				 });

				 removeClothingItemButton.setOnMouseExited(me ->
				 {
					 removeClothingItemButton.setScaleX(1);
					 removeClothingItemButton.setScaleY(1);
				 });

				 removeClothingItemButton.setOnMousePressed(me ->
		 {
			 removeClothingItemButton.setScaleX(0.9);
			 removeClothingItemButton.setScaleY(0.9);
		 });
				 removeClothingItemButton.setOnMouseReleased(me ->
		 {
			 removeClothingItemButton.setScaleX(1.1);
			 removeClothingItemButton.setScaleY(1.1);
		 });
		removeClothingItemButton.setOnAction(e -> myModel.stateChangeRequest("RemoveClothingItem", null));
		clothingItemCont.getChildren().add(removeClothingItemButton);

		container.getChildren().add(clothingItemCont);

		// Clothing item request
		HBox requestCont = new HBox(10);
		requestCont.setAlignment(Pos.CENTER);
		Label reqLabel = new Label("         Requests: ");
		reqLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		requestCont.getChildren().add(reqLabel);
		logRequestButton = new Button(" Log ");
		logRequestButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		logRequestButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
		logRequestButton.setOnMouseEntered(me ->
				 {
					 logRequestButton.setScaleX(1.1);
					 logRequestButton.setScaleY(1.1);
				 });

				 logRequestButton.setOnMouseExited(me ->
				 {
					 logRequestButton.setScaleX(1);
					 logRequestButton.setScaleY(1);
				 });

				 logRequestButton.setOnMousePressed(me ->
		 {
			 logRequestButton.setScaleX(0.9);
			 logRequestButton.setScaleY(0.9);
		 });
				 logRequestButton.setOnMouseReleased(me ->
		 {
			 logRequestButton.setScaleX(1.1);
			 logRequestButton.setScaleY(1.1);
		 });
		logRequestButton.setOnAction(e -> myModel.stateChangeRequest("LogRequest", null));
		requestCont.getChildren().add(logRequestButton);

		fulfillRequestButton = new Button(" Fulfill ");
		fulfillRequestButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		fulfillRequestButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
		fulfillRequestButton.setOnMouseEntered(me ->
				 {
					 fulfillRequestButton.setScaleX(1.1);
					 fulfillRequestButton.setScaleY(1.1);
				 });

				 fulfillRequestButton.setOnMouseExited(me ->
				 {
					 fulfillRequestButton.setScaleX(1);
					 fulfillRequestButton.setScaleY(1);
				 });

				 fulfillRequestButton.setOnMousePressed(me ->
		 {
			 fulfillRequestButton.setScaleX(0.9);
			 fulfillRequestButton.setScaleY(0.9);
		 });
				 fulfillRequestButton.setOnMouseReleased(me ->
		 {
			 fulfillRequestButton.setScaleX(1.1);
			 fulfillRequestButton.setScaleY(1.1);
		 });
		fulfillRequestButton.setOnAction(e -> myModel.stateChangeRequest("FulfillRequest", null));
		requestCont.getChildren().add(fulfillRequestButton);

		removeRequestButton = new Button("Remove");
		removeRequestButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeRequestButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
		removeRequestButton.setOnMouseEntered(me ->
				 {
					 removeRequestButton.setScaleX(1.1);
					 removeRequestButton.setScaleY(1.1);
				 });

				 removeRequestButton.setOnMouseExited(me ->
				 {
					 removeRequestButton.setScaleX(1);
					 removeRequestButton.setScaleY(1);
				 });

				 removeRequestButton.setOnMousePressed(me ->
		 {
			 removeRequestButton.setScaleX(0.9);
			 removeRequestButton.setScaleY(0.9);
		 });
				 removeRequestButton.setOnMouseReleased(me ->
		 {
			 removeRequestButton.setScaleX(1.1);
			 removeRequestButton.setScaleY(1.1);
		 });
		removeRequestButton.setOnAction(e -> myModel.stateChangeRequest("RemoveRequest", null));
		requestCont.getChildren().add(removeRequestButton);

		container.getChildren().add(requestCont);

		HBox listAvailCont = new HBox(10);
		listAvailCont.setAlignment(Pos.CENTER);
		listAvailableInventoryButton = new Button("List Available Inventory");
		listAvailableInventoryButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		listAvailableInventoryButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
		listAvailableInventoryButton.setOnMouseEntered(me ->
				 {
					 listAvailableInventoryButton.setScaleX(1.1);
					 listAvailableInventoryButton.setScaleY(1.1);
				 });

				 listAvailableInventoryButton.setOnMouseExited(me ->
				 {
					 listAvailableInventoryButton.setScaleX(1);
					 listAvailableInventoryButton.setScaleY(1);
				 });

				 listAvailableInventoryButton.setOnMousePressed(me ->
		 {
			 listAvailableInventoryButton.setScaleX(0.9);
			 listAvailableInventoryButton.setScaleY(0.9);
		 });
				 listAvailableInventoryButton.setOnMouseReleased(me ->
		 {
			 listAvailableInventoryButton.setScaleX(1.1);
			 listAvailableInventoryButton.setScaleY(1.1);
		 });
		listAvailableInventoryButton.setOnAction(e -> myModel.stateChangeRequest("ListAvailableInventory", null));
		listAvailCont.getChildren().add(listAvailableInventoryButton);

		container.getChildren().add(listAvailCont);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		cancelButton = new Button("Exit System");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
		cancelButton.setOnAction(e -> myModel.stateChangeRequest("ExitSystem", null));
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
