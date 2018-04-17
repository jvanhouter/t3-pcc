package userinterface;

// system imports
import impresario.ControlRegistry;
import impresario.IControl;
import impresario.IModel;
import impresario.IView;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class PccView extends View {
    VBox container;

    public PccView(IModel model, String classname) {
        super(model, classname);        container = new VBox(10);

        /* container.setResizable(true); Swing call */
        container.setStyle("-fx-background-color: #708090;");
        container.setPadding(new Insets(50, 50, 50, 50));
        container.getChildren().add(createTitle());
    }

    @Override
    public void updateState(String key, Object value) {

    }

    Node createTitle() {
        VBox container = new VBox(10);
        Text clientText = new Text(" Office of Career Services ");
        clientText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        clientText.setWrappingWidth(350);
        clientText.setTextAlignment(TextAlignment.CENTER);
        clientText.setEffect(createDropShadow());
        clientText.setFill(Color.web("#ffc726"));
        container.getChildren().add(clientText);

        Text collegeText = new Text(" THE COLLEGE AT BROCKPORT ");
        collegeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        collegeText.setWrappingWidth(350);
        collegeText.setTextAlignment(TextAlignment.CENTER);
        collegeText.setEffect(createDropShadow());
        collegeText.setFill(Color.web("#ffc726"));
        container.getChildren().add(collegeText);

        Text titleText = new Text(" Professional Clothes Closet Management System ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(350);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setEffect(createDropShadow());
        titleText.setFill(Color.web("#ffc726"));
        container.getChildren().add(titleText);

        return container;
    }
    DropShadow createDropShadow() {
        DropShadow ds = new DropShadow();
        ds.setRadius(5.0);
        ds.setOffsetX(3.0);
        ds.setOffsetY(3.0);
        return ds;
    }
}
