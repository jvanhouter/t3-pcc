package userinterface;



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



public class DefaultButtonLayout {

  public void setDefaultButtonLayout(Button b)
  {
    b.setOnMouseEntered(me ->
				 {
					 b.setScaleX(1.1);
					 b.setScaleY(1.1);
				 });

				 b.setOnMouseExited(me ->
				 {
					 b.setScaleX(1);
					 b.setScaleY(1);
				 });

				 b.setOnMousePressed(me ->
		 {
			 b.setScaleX(0.9);
			 b.setScaleY(0.9);
		 });
				 b.setOnMouseReleased(me ->
		 {
			 b.setScaleX(1.1);
			 b.setScaleY(1.1);
		 });
  }

}
