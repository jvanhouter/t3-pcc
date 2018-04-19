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
        this.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        this.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
        this.setOnMouseEntered(e -> {
//            grow();
            hover();
        });
        this.setOnMouseExited(e -> {
            reset();
//            normal();
        });
        this.setOnMouseReleased(e -> {
//            grow();
            reset();
        });
        this.setOnMousePressed(e -> {
//            shrink();
            clicked();
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
    private void clicked(){
        this.setStyle("-fx-border-color: #cc9402; -fx-border-width: 1px; -fx-background-color: #00200b; -fx-text-fill: #eeb615");
    }
    private void hover() {
        this.setStyle("-fx-border-color: #eeb615; -fx-border-width: 1px; -fx-background-color: #00422d; -fx-text-fill: #eeb615");
    }
    private void reset() {
        this.setStyle("-fx-border-color: #ffc726; -fx-border-width: 1px; -fx-background-color: #00533e; -fx-text-fill: #ffc726");
    }
}
