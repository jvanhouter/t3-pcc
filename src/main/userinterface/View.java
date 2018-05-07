//************************************************************
//	COPYRIGHT 2009/2015 Sandeep Mitra and Michael Steves, The
//    College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
package userinterface;

import impresario.ControlRegistry;
import impresario.IControl;
import impresario.IModel;
import impresario.IView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public abstract class View extends Group
        implements IView, IControl {
    protected static final String APP_FONT = "Roboto";
    protected static final String APP_TEXT_COLOR = "#ffc726";
    protected static final Color CAPP_TEXT_COLOR = Color.web("#FFC726");
    protected static final String APP_BACKGROUND_COLOR = "#647585";
    protected final Integer WRAPPING_WIDTH = 400;
    // private data
    protected IModel myModel;
    protected ControlRegistry myRegistry;
    protected VBox container;

    // GUI components


    // Class constructor

    public View(IModel model, String classname) {
        myModel = model;

        myRegistry = new ControlRegistry(classname);

        container = new VBox(10);
        //container.setStyle("-fx-background-color: #647585");
        container.setStyle(
                        "-fx-background-color: #023627; "
                      //  "-fx-border-width:3; " +
                      //  "-fx-border-color: #ffc726;"
                    //  "linear-gradient(" +
                      //  "to top, " +
                      //  "#ffc726, " +
                        //"#023627" +
                      //  ");"
        );
        container.setPadding(new Insets(30, 15, 15, 15));
    }

    VBox getParentContainer() {
        return container;
    }

    Node createTitle() {
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);
        Text clientText = new Text("Office of Career Services");
        clientText.setFont(Font.font(APP_FONT, FontWeight.BOLD, 24));
        clientText.setWrappingWidth(WRAPPING_WIDTH);
        clientText.setTextAlignment(TextAlignment.CENTER);
        clientText.setFill(Color.web(APP_TEXT_COLOR));
        container.getChildren().add(clientText);

        Text collegeText = new Text("THE COLLEGE AT BROCKPORT");
        collegeText.setFont(Font.font(APP_FONT, FontWeight.BOLD, 20));
        collegeText.setWrappingWidth(WRAPPING_WIDTH);
        collegeText.setTextAlignment(TextAlignment.CENTER);
        collegeText.setFill(Color.web(APP_TEXT_COLOR));
        container.getChildren().add(collegeText);

        Text titleText = new Text("Professional Clothes Closet \nManagement System");
        titleText.setFont(Font.font(APP_FONT, FontWeight.BOLD, 20));
        titleText.setWrappingWidth(WRAPPING_WIDTH);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.web(APP_TEXT_COLOR));
        container.getChildren().add(titleText);

        Text blankText = new Text(" ");
        blankText.setFont(Font.font(APP_FONT, FontWeight.BOLD, 24));
        blankText.setFill(Color.WHITE);
        container.getChildren().add(blankText);

        Text actionText = new Text(getActionText());
        actionText.setFont(Font.font(APP_FONT, FontWeight.BOLD, 18));
        actionText.setWrappingWidth(WRAPPING_WIDTH);
        actionText.setTextAlignment(TextAlignment.CENTER);
        actionText.setFill(Color.web(APP_TEXT_COLOR));
        container.getChildren().add(actionText);
        return container;
    }

    Node createBanner() {

    Image image = new Image("pccTitle.png");

    VBox pictureRegion = new VBox();
    pictureRegion.setAlignment(Pos.CENTER);
    final ImageView imv = new ImageView();
    imv.setImage(image);
    pictureRegion.getChildren().add(imv);

    Text blankText = new Text(" ");
    blankText.setFont(Font.font(APP_FONT, FontWeight.BOLD, 24));
    blankText.setFill(Color.WHITE);
    pictureRegion.getChildren().add(blankText);

    Text actionText = new Text(getActionText());
    actionText.setFont(Font.font(APP_FONT, FontWeight.BOLD, 18));
    actionText.setWrappingWidth(WRAPPING_WIDTH);
    actionText.setTextAlignment(TextAlignment.CENTER);
    actionText.setFill(Color.web(APP_TEXT_COLOR));
    pictureRegion.getChildren().add(actionText);

    return pictureRegion;
}

    protected String getActionText() {
        return " ";
    }

    public void setRegistry(ControlRegistry registry) {
        myRegistry = registry;
    }

    // Allow models to register for state updates
    public void subscribe(String key, IModel subscriber) {
        myRegistry.subscribe(key, subscriber);
    }

    // Allow models to unregister for state updates
    public void unSubscribe(String key, IModel subscriber) {
        myRegistry.unSubscribe(key, subscriber);
    }
}
