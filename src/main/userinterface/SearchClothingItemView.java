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

/** The class containing the Add Article Type View  for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class SearchClothingItemView extends View
{

	// GUI components
	protected TextField barcode;
	protected TextField articleType;
	protected TextField gender;

	protected Button submitButton;
	protected Button cancelButton;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public SearchClothingItemView(IModel at)
	{
		super(at, "SearchClothingView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());
		
		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

		container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

		populateFields();

		myModel.subscribe("TransactionError", this);
	}

	//-------------------------------------------------------------
	protected String getActionText()
	{
		return "** Search for Clothing **";
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

		Text actionText = new Text("     " + getActionText() + "       ");
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
		
		Text prompt1 = new Text("Enter Barcode (if known)");
        prompt1.setWrappingWidth(400);
        prompt1.setTextAlignment(TextAlignment.CENTER);
        prompt1.setFill(Color.BLACK);
		prompt1.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		vbox.getChildren().add(prompt1);
		
		GridPane grid0 = new GridPane();
		grid0.setAlignment(Pos.CENTER);
       	grid0.setHgap(10);
        grid0.setVgap(10);
        grid0.setPadding(new Insets(0, 25, 10, 0));
		
		Text barcodeLabel = new Text(" Barcode: ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		barcodeLabel.setFont(myFont);
		barcodeLabel.setWrappingWidth(150);
		barcodeLabel.setTextAlignment(TextAlignment.RIGHT);
		grid0.add(barcodeLabel, 0, 1);

		barcode = new TextField();
		barcode.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
					Properties props = new Properties();
					String barcodeString = barcode.getText();
					if (barcodeString.length() > 0)
					{
						props.setProperty("Barcode", barcodeString);
						myModel.stateChangeRequest("SearchClothingItem", props); 
					}
			}
		});
		grid0.add(barcode, 1, 1);
		
		vbox.getChildren().add(grid0);
		//--------------------------------------------------------------------------//
		Text prompt2 = new Text(" - Otherwise, enter other criteria below - ");
        prompt2.setWrappingWidth(400);
        prompt2.setTextAlignment(TextAlignment.CENTER);
        prompt2.setFill(Color.BLACK);
		prompt2.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		vbox.getChildren().add(prompt2);

		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
       	grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 25, 10, 0));

		Text atLabel = new Text("Article Type : ");
		atLabel.setFont(myFont);
		atLabel.setWrappingWidth(150);
		atLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(atLabel, 0, 1);

		articleType = new TextField();
		articleType.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
					Properties props = new Properties();
					
					String atString = articleType.getText();
					props.setProperty("ArticleType", atString);
					String genderString = gender.getText();
					props.setProperty("Gender", genderString);
					myModel.stateChangeRequest("SearchClothingItem", props); 
					
			}
		});
		grid.add(articleType, 1, 1);

		Text colorLabel = new Text("Gender  : ");
		colorLabel.setFont(myFont);
		colorLabel.setWrappingWidth(150);
		colorLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(colorLabel, 0, 2);

		gender = new TextField();
		grid.add(gender, 1, 2);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
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
					Properties props = new Properties();
					String bcString = barcode.getText();
					if (bcString.length() > 0)
					{
						props.setProperty("Barcode", bcString);
						myModel.stateChangeRequest("SearchClothingItem", props); 
					}
					else
					{
						String atString = articleType.getText();
						props.setProperty("ArticleType", atString);
						
						String genderString = gender.getText();
						props.setProperty("Gender", genderString);
						myModel.stateChangeRequest("SearchClothingItem", props); 
					}
			}
		});
		doneCont.getChildren().add(submitButton);
		
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
       		    	clearErrorMessage();
       		    	myModel.stateChangeRequest("CancelSearchClothingItem", null);   
            	  }
        	});
		doneCont.getChildren().add(cancelButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}


	// Create the status log field
	//-------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		
	}

	/**
	 * Update method
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		clearErrorMessage();

		if (key.equals("TransactionError") == true)
		{
			String val = (String)value;
			if (val.startsWith("ERR") == true)
			{
				displayErrorMessage(val);
			}
			else
			{
				displayMessage(val);
			}
			
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

//---------------------------------------------------------------
//	Revision History:
//



