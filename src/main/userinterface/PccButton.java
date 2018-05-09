package userinterface;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

class PccButton extends Button {

    PccButton(String text) {
        super(text);
        initialize();
    }

    private void initialize() {
        this.setFont(Font.font(View.APP_FONT, FontWeight.NORMAL, 16));
        this.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
        this.setMinWidth(80);
        this.setMaxWidth(Double.MAX_VALUE);
        this.setOnMouseEntered(e -> {
            colorHover();
        });
        this.setOnMouseExited(e -> {
            normalColor();
        });
        this.setOnMouseReleased(e -> {
            normalColor();
        });
        this.setOnMousePressed(e -> {
            colorClicked();

        });

    }

    private void normalColor() {
        this.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
    }

    private void colorHover() {
        // Original 007a58
        this.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #6c8072; -fx-text-fill: #ffc726");
    }

    private void colorClicked() {
        this.setStyle("-fx-border-color: #8d8d8d; -fx-border-width: 1px; -fx-background-color: #8d8d8d; -fx-text-fill: #ffc726");
    }
}
