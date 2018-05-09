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
import javafx.scene.layout.BorderPane;
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
    protected static final String APP_FONT = "Univers";
    protected static final String APP_TEXT_COLOR = "#ffc726";
    protected static final Color CAPP_TEXT_COLOR = Color.web("#FFC726");
//    protected static final String APP_BACKGROUND_COLOR = "#023627";
    protected static final String APP_BACKGROUND_COLOR = "#57527e";
    protected static final String APP_BACKGROUND_STYLE_COLOR = "-fx-background-color: #57527e";
    protected static final String APP_BACKGROUND2_STYLE_COLOR = "-fx-background-color: #00533e";
    protected final Integer WRAPPING_WIDTH = 400;
    // private data
    protected IModel myModel;
    protected ControlRegistry myRegistry;
    protected VBox container;
    protected BorderPane bp;

    // GUI components


    // Class constructor

    public View(IModel model, String classname) {
        myModel = model;

        myRegistry = new ControlRegistry(classname);

        bp = new BorderPane();


        container = new VBox(10);
        container.setStyle(APP_BACKGROUND_STYLE_COLOR);
        container.setAlignment(Pos.CENTER);


        container.setPadding(new Insets(30, 15, 15, 15));
    }

    VBox getParentContainer() {
        return container;
    }

    Node createTitle() {

        VBox container = new VBox(10);
        container.setStyle(APP_BACKGROUND2_STYLE_COLOR);
        container.setAlignment(Pos.CENTER);

        container.getChildren().add(new PccText(" "));

        PccText clientText = new PccText("Office of Career Services");
        clientText.setFont(Font.font(APP_FONT, FontWeight.BOLD, 24));
        clientText.setWrappingWidth(WRAPPING_WIDTH);
        clientText.setTextAlignment(TextAlignment.CENTER);
        container.getChildren().add(clientText);

        PccText collegeText = new PccText("THE COLLEGE AT BROCKPORT");
        collegeText.setFont(Font.font(APP_FONT, FontWeight.BOLD, 20));
        collegeText.setWrappingWidth(WRAPPING_WIDTH);
        collegeText.setTextAlignment(TextAlignment.CENTER);
        container.getChildren().add(collegeText);

        PccText titleText = new PccText("Professional Clothes Closet \nManagement System");
        titleText.setFont(Font.font(APP_FONT, FontWeight.BOLD, 20));
        titleText.setWrappingWidth(WRAPPING_WIDTH);
        titleText.setTextAlignment(TextAlignment.CENTER);
        container.getChildren().add(titleText);

        container.getChildren().add(new PccText(" "));

        return container;
    }

    Node createActionArea() {
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        PccText actionText = new PccText(getActionText());
        actionText.setFont(Font.font(APP_FONT, FontWeight.BOLD, 18));
        actionText.setWrappingWidth(WRAPPING_WIDTH);
        actionText.setTextAlignment(TextAlignment.CENTER);
        container.getChildren().add(actionText);

        container.getChildren().add(new PccText(" "));

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
    actionText.setFont(Font.font(APP_FONT, 18));
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
