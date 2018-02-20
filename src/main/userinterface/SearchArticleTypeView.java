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

import java.util.Properties;

// project imports
import impresario.IModel;

/** The class containing the Add Article Type View  for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class SearchArticleTypeView extends View
{

	// GUI components
	protected TextField barcodePrefix;
	protected TextField description;
	protected TextField alphaCode;

	protected Button submitButton;
	protected Button cancelButton;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public SearchArticleTypeView(IModel at)
	{
		super(at, "SearchArticleTypeView");

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
		return "** Search for Article Type **";
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
		
		Text prompt1 = new Text("Enter Article Type Barcode Prefix (if known)");
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
		
		Text barcodePrefixLabel = new Text(" Barcode Prefix : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		barcodePrefixLabel.setFont(myFont);
		barcodePrefixLabel.setWrappingWidth(150);
		barcodePrefixLabel.setTextAlignment(TextAlignment.RIGHT);
		grid0.add(barcodePrefixLabel, 0, 1);

		barcodePrefix = new TextField();
		barcodePrefix.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
					Properties props = new Properties();
					String bcPrfx = barcodePrefix.getText();
					if (bcPrfx.length() > 0)
					{
						props.setProperty("BarcodePrefix", bcPrfx);
						myModel.stateChangeRequest("SearchArticleType", props); 
					}
			}
		});
		grid0.add(barcodePrefix, 1, 1);
		
		vbox.getChildren().add(grid0);
		
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

		Text descripLabel = new Text(" Description : ");
		descripLabel.setFont(myFont);
		descripLabel.setWrappingWidth(150);
		descripLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(descripLabel, 0, 1);

		description = new TextField();
		description.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
					Properties props = new Properties();
					
					String descrip = description.getText();
					props.setProperty("Description", descrip);
					String alfaC = alphaCode.getText();
					props.setProperty("AlphaCode", alfaC);
					myModel.stateChangeRequest("SearchArticleType", props); 
					
			}
		});
		grid.add(description, 1, 1);

		Text alphaCodeLabel = new Text(" Alpha Code : ");
		alphaCodeLabel.setFont(myFont);
		alphaCodeLabel.setWrappingWidth(150);
		alphaCodeLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(alphaCodeLabel, 0, 2);

		alphaCode = new TextField();
		grid.add(alphaCode, 1, 2);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		submitButton = new Button("Submit");
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
					Properties props = new Properties();
					String bcPrfx = barcodePrefix.getText();
					if (bcPrfx.length() > 0)
					{
						props.setProperty("BarcodePrefix", bcPrfx);
						myModel.stateChangeRequest("SearchArticleType", props); 
					}
					else
					{
						String descrip = description.getText();
						props.setProperty("Description", descrip);
						String alfaC = alphaCode.getText();
						props.setProperty("AlphaCode", alfaC);
						myModel.stateChangeRequest("SearchArticleType", props); 
					}
			}
		});
		doneCont.getChildren().add(submitButton);
		
		cancelButton = new Button("Return");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	myModel.stateChangeRequest("CancelSearchArticleType", null);   
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


