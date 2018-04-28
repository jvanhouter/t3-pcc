// specify the package

// system imports

import Utilities.Utilities;
import event.Event;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Receptionist;
import userinterface.MainStageContainer;
import userinterface.WindowPosition;

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


    // start method for this class, the main application object

    /**
     * The "main" entry point for the application. Carries out actions to
     * set up the application
     */

    public static void main(String[] args) {

        launch(args);
    }

    public void start(Stage primaryStage) {
        System.out.println("Brockport Professional Clothes Closet Version 1.00");
        System.out.println("Copyright 2018 Sandeep Mitra and Students");

        /*
         * create HashMap creation
         */
        Utilities.collectArticleTypeHash();
        Utilities.collectColorHash();
        Utilities.collectClothingHash();
        Utilities.collectClothingRequestHash();

        // Create the top-level container (main frame) and add contents to it.
        MainStageContainer.setStage(primaryStage, "Brockport Professional Clothes Closet Version 1.00");
        mainStage = MainStageContainer.getInstance();

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

}
