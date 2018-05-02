// specify the package

// system imports

import Utilities.Utilities;
import event.Event;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Receptionist;
import userinterface.MainStageContainer;
import userinterface.WindowPosition;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// project imports


/**
 * The class containing the main program  for the Professional Clothes Closet application
 */
//==============================================================
public class Closet extends Application {

    private Receptionist myReceptionist;        // the main behavior for the application

    /**
     * Main stage of the application
     */
    private Stage mainStage;

    private Image image = new Image("splash.jpg");
    private ImageView SPLASH_IMAGE = new ImageView(image);

    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    // change for later
    private int SPLASH_WIDTH = (int) image.getWidth();
    private int SPLASH_HEIGHT = (int) image.getHeight();

    // start method for this class, the main application object

    /**
     * The "main" entry point for the application. Carries out actions to
     * set up the application
     */

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void init() {
        ImageView splash = SPLASH_IMAGE;
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
        progressText = new Label("Loading Hashmaps . . .");
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle(
                "-fx-padding: 5; " +
                        "-fx-background-color: cornsilk; " +
                        "-fx-border-width:5; " +
                        "-fx-border-color: " +
                        "linear-gradient(" +
                        "to bottom, " +
                        "chocolate, " +
                        "derive(chocolate, 50%)" +
                        ");"
        );
        splashLayout.setEffect(new DropShadow());
    }

    public void start(Stage initStage) {
        System.out.println("Brockport Professional Clothes Closet Version 1.00");
        System.out.println("Copyright 2018 Sandeep Mitra and Students");

        final Task<String> myTask = new Task<String>() {
            @Override
            protected String call() throws InterruptedException {
                updateMessage("Loading Article Type HashMap . . .");
                updateProgress(1,4);
                Utilities.collectArticleTypeHash();
                Thread.sleep(400);
                updateMessage("Loading Color HashMap . . .");
                updateProgress(2, 4);
                Utilities.collectColorHash();
                Thread.sleep(400);
                updateMessage("Loading Clothing Item HashMap . . .");
                updateProgress(3, 4);
                Utilities.collectClothingHash();
                Thread.sleep(400);
                updateMessage("Loading Clothing Request HashMap . . .");
                updateProgress(4, 4);
                Utilities.collectClothingRequestHash();
                Thread.sleep(400);
                updateMessage("All HashMaps have been loaded.");
                return "SUCCESS!";
            }
        };

        showSplash(
                initStage,
                myTask,
                () -> showMainStage()
        );
        new Thread(myTask).start();
    }

    private void showMainStage() {
        // Create the top-level container (main frame) and add contents to it.
        Stage primaryStage = new Stage();
        MainStageContainer.setStage(primaryStage, "Brockport Professional Clothes Closet Version 1.00");
        mainStage = MainStageContainer.getInstance();
        mainStage.getIcons().add(new Image("grandleader.png"));
        // Finish setting up the stage (ENABLE THE GUI TO BE CLOSED USING THE TOP RIGHT
        // 'X' IN THE WINDOW), and show it.
        mainStage.setOnCloseRequest(event -> System.exit(0));

        try {
            myReceptionist = new Receptionist();
        } catch (Exception exc) {
            System.err.println("Closet.Closet - could not create Receptionist!");
            new Event(Event.getLeafLevelClassName(this), "Closet.<init>", "Unable to create Receptionist object", Event.ERROR);
            exc.printStackTrace();
        }


        WindowPosition.placeCenter(mainStage);

        mainStage.show();
    }

    private void showSplash(
            final Stage initStage,
            Task<?> task,
            InitCompletionHandler initCompletionHandler
    ) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                initCompletionHandler.complete();
            } // todo add code to gracefully handle other task states.
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
    }

    public interface InitCompletionHandler {
        void complete();
    }

}
