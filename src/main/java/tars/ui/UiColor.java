package tars.ui;

import javafx.scene.paint.Color;

public class UiColor {

    public static final String STATUS_UNDONE_TEXT_FILL =
            "-fx-text-fill: #212121";
    public static final String STATUS_DONE_TEXT_FILL = "-fx-text-fill: #BDBDBD";
    public static final String STATUS_DONE_TICK_COLOR = "-fx-text-fill: white";;

    public enum Priority {
        HIGH(Color.RED), MEDIUM(Color.ORANGE), LOW(Color.GREEN), DEFAULT(
                Color.DARKGREY);
        private Color circleColor;

        Priority(Color circleColor) {
            this.circleColor = circleColor;
        }

        Color getCircleColor() {
            return circleColor;
        }
    }
}
