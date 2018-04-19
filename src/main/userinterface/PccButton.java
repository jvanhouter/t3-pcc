package userinterface;

import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

class PccButton extends Button {

    PccButton(String text) {
        super(text);
        initialize();
    }

    private void initialize() {
        this.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        this.setStyle("-fx-border-color: #ffc726; -fx-border-width: 2px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
        this.setOnMouseEntered(e -> {
//            grow();
            this.setEffect(colorHover());
        });
        this.setOnMouseExited(e -> {
            this.setEffect(normalColor());
//            normal();
        });
        this.setOnMouseReleased(e -> {
//            grow();
            this.setEffect(normalColor());
        });
        this.setOnMousePressed(e -> {
//            shrink();
           this.setEffect(colorClicked());

        });

    }

    private void grow() {
        this.setScaleX(1.03);
        this.setScaleY(1.03);
    }

    private void shrink() {
        this.setScaleX(0.97);
        this.setScaleY(0.97);
    }

    private void normal() {
        this.setScaleX(1.0);
        this.setScaleY(1.0);
    }
    private ColorAdjust normalColor() {
        ColorAdjust ca = new ColorAdjust();
        ca.setSaturation(0);
        ca.setBrightness(0);
        ca.setContrast(0);

        return ca;
    }
    private ColorAdjust colorHover() {
        ColorAdjust ca = new ColorAdjust();
        ca.setSaturation(-0.5);
        ca.setContrast(-0.1);
        ca.setBrightness(-0.2);

        return ca;
    }
    private ColorAdjust colorClicked() {
        ColorAdjust ca = new ColorAdjust();
        ca.setSaturation(-0.7);
        ca.setContrast(-0.2);
        ca.setBrightness(-0.3);

        return ca;
    }
}
