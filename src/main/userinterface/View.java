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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public abstract class View extends Group
        implements IView, IControl {
    // private data
    protected IModel myModel;
    protected ControlRegistry myRegistry;
    protected VBox container;

    protected final Integer WRAPPING_WIDTH = 400;
    protected static final String APP_FONT = "Roboto";
    protected static final String APP_TEXT_COLOR="#ffc726";
    
    // GUI components


    // Class constructor

    public View(IModel model, String classname) {
        myModel = model;

        myRegistry = new ControlRegistry(classname);

        container = new VBox(10);
        container.setStyle("-fx-background-color: #647585");
        container.setPadding(new Insets(50, 50, 50, 50));
    }
    VBox getParentContainer() {
        return container;
    }

    Node createTitle() {
        VBox container = new VBox(10);

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

        Text titleText = new Text("Professional Clothes Closet Management System");
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

