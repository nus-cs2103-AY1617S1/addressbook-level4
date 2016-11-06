package tars.ui;

import javafx.scene.paint.Color;

/**
 * Manages color for UI parts
 * @@author A0121533W
 *
 */
public class UiColor {

    public static final String STATUS_UNDONE_TEXT_FILL =
            "-fx-text-fill: #212121";
    public static final String STATUS_DONE_TEXT_FILL = "-fx-text-fill: lightgrey";
    public static final String CIRCLE_LABEL_COLOR = "-fx-text-fill: white;";
    public static final String TASK_CARD_NEWLY_ADDED_BORDER = "-fx-border-color: lightblue";
    public static final String TASK_CARD_DEFAULT_BORDER = "-fx-border-color: #455A64";
    
    
    public enum CircleColor {
        HIGH(Color.RED), MEDIUM(Color.ORANGE), LOW(Color.GREEN),
        DONE(Color.LIGHTGREY), NONE(Color.TRANSPARENT);
        private Color circleColor;

        CircleColor(Color circleColor) {
            this.circleColor = circleColor;
        }

        Color getCircleColor() {
            return circleColor;
        }
    }
}
