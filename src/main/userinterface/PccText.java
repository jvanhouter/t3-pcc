package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class PccText extends Text {
    PccText(String text) {
        super(text);
        initialize();
    }

    private void initialize() {
        this.setFont(Font.font(View.APP_FONT, 16));
        this.setFill(Color.web(View.APP_TEXT_COLOR));
    }
}
