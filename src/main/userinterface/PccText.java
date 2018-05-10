package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class PccText extends Text {
    int fontSize = 16;

    PccText(String text) {
        super(text);
        initialize();
    }

    PccText(String text, int fontSize) {
        super(text);
        this.fontSize = fontSize;

        initialize();
    }

    private void initialize() {
        this.setFont(Font.font(View.APP_FONT, fontSize));
        this.setFill(Color.web(View.APP_TEXT_COLOR));
    }
}
