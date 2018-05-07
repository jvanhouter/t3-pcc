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

    private Image image = new Image("splash.png");
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
        progressText = new Label("Loading Assets . . .");
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        progressText.setStyle("-fx-text-fill: #fff; -fx-font-weight: bold;");
        splashLayout.setStyle(
                "-fx-padding: 5; " +
                        "-fx-background-color: #023627; " +
                        "-fx-border-width:0; " +
                        "-fx-border-color: " +
                        "linear-gradient(" +
                        "to bottom, " +
                        "#5a7f15, " +
                        "derive(#5a7f15, 50%)" +
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
                updateMessage("Loading Assets. . .");
                updateProgress(1,4);
                Utilities.collectArticleTypeHash();
                Thread.sleep(400);
                updateMessage("Loading Assets . . .");
                updateProgress(2, 4);
                Utilities.collectColorHash();
                Thread.sleep(400);
                updateMessage("Loading Assets. . .");
                updateProgress(3, 4);
                Utilities.collectClothingHash();
                Thread.sleep(400);
                updateMessage("Loading Assets . . .");
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
        mainStage.getIcons().add(new Image("icon.png"));
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
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(.2), splashLayout);
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
        initStage.getIcons().add(new Image("icon.png"));
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

/* Future Development Notes.
 * Functionality notes on splash screen by Kyle Darling (2018)
 *
 * Description: Functionality regarding the Splash Screen enacts the
 * screen on its own separate thread in order to work asynchronously
 * with the developing backend processes on going.
 * These processes are regarded to the hash maps to increase the
 * speed of the program immensely all the while running on a single host
 * machine. Later efforts may yield a thread that enacts a query
 * pool in order to make up for connection loss or enable synchronous
 * behaviour among many machines using the software.
 *
 * Splash Screen Development: The Splash Screen requires a task to be
 * created in order for the splash screen to track any progress being done.
 * The separate thread is also created for the asynchronous behaviour at hand.
 *
 * Any additional additions are advised to be placed within the Task rather than
 * anywhere else in the program. This allows for better management and the
 * updateProgress will keep track of itself if given the proper parameters if
 * developing a loop, else a static instance is needed for both parameters
 * throughout the loading process, like above.
 */
