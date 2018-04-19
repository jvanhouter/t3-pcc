//************************************************************
//	COPYRIGHT 2010/2015 Sandeep Mitra and Students, The
//    College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MessageView extends Text {
    // Class constructor
    public MessageView(String initialMessage) {
        super(initialMessage);
        setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        setFill(Color.BLUE);
        setTextAlignment(TextAlignment.LEFT);
    }

    /**
     * Display ordinary message
     */
    public void displayMessage(String message) {
        // display the passed text in blue
        setFill(Color.BLUE);
        setText(message);
    }

    /**
     * Display error message
     */
    public void displayErrorMessage(String message) {
        // display the passed text in red
        setFill(Color.RED);
        setText(message);
    }

    /**
     * Clear error message
     */
    public void clearErrorMessage() {
        setText("                           ");
    }
}