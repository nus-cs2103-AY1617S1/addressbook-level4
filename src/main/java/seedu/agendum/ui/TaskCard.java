package seedu.agendum.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import seedu.agendum.model.task.ReadOnlyTask;

//@@author A0148031R
public class TaskCard extends UiPart {

    private static final String FXML = "TaskCard.fxml";
    private static final String OVERDUE_PREFIX = "Overdue\n";
    private static final String COMPLETED_PREFIX = "Completed on ";
    private static final String TASK_TIME_PATTERN = "HH:mm EEE, dd MMM";
    private static final String COMPLETED_TIME_PATTERN = "EEE, dd MMM";
    private static final String START_TIME_PREFIX = "from ";
    private static final String END_TIME_PREFIX = " to ";
    private static final String DEADLINE_PREFIX = "by ";
    private static final String EMPTY_PREFIX = "";
    private static final String OVERDUE_STYLE = "-fx-background-color: rgba(244, 67, 54, 0.8)";
    private static final String UPCOMING_STYLE = "-fx-background-color: rgba(255, 235, 59, 0.8)";
    private static final String OTHER_STYLE = "-fx-background-color: rgba(255,255,255,0.6)";
    private static final Color NAME_COLOR_DARK = Color.web("#3a3d42");
    private static final Color TIME_COLOR_DARK = Color.web("#4172c1");
    private static final Color NAME_COLOR_LIGHT = Color.web("#ffffff");
    private static final Color TIME_COLOR_LIGHT = Color.web("#fff59d");

    @FXML
    private HBox cardPane;
    @FXML
    private VBox taskVbox;
    @FXML
    private Label name;
    @FXML
    private Label id;

    private ReadOnlyTask task;
    private String displayedIndex;

    public TaskCard() {
    }

    public static TaskCard load(ReadOnlyTask task, int Index) {
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = String.valueOf(Index) + ".";
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {

        Label time = new Label();
        time.setId("time");

        if (task.isOverdue()) {
            cardPane.setStyle(OVERDUE_STYLE);
            name.setTextFill(NAME_COLOR_LIGHT);
            time.setTextFill(TIME_COLOR_LIGHT);
            id.setTextFill(NAME_COLOR_LIGHT);
        } else if (task.isUpcoming()) {
            cardPane.setStyle(UPCOMING_STYLE);
            name.setTextFill(NAME_COLOR_DARK);
            time.setTextFill(TIME_COLOR_DARK);
        } else {
            cardPane.setStyle(OTHER_STYLE);
            name.setTextFill(NAME_COLOR_DARK);
            time.setTextFill(TIME_COLOR_DARK);
        }

        StringBuilder timeDescription = new StringBuilder();
        timeDescription.append(formatTaskTime(task));

        if (task.isCompleted()) {
            timeDescription.append(formatUpdatedTime(task));
        }

        name.setText(task.getName().fullName);
        id.setText(displayedIndex);
        time.setText(timeDescription.toString());
        time.setMaxHeight(Control.USE_COMPUTED_SIZE);
        time.setWrapText(true);
        
        if (task.hasTime() || task.isCompleted()) {
            taskVbox.getChildren().add(time);
            taskVbox.setAlignment(Pos.CENTER_LEFT);
            time.setAlignment(Pos.CENTER_LEFT);
            time.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
        }
    }

    private String formatTime(String dateTimePattern, String prefix, Optional<LocalDateTime> dateTime) {

        StringBuilder sb = new StringBuilder();
        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateTimePattern);
        sb.append(prefix).append(dateTime.get().format(format));
        
        return sb.toString();
    }

    private String formatTaskTime(ReadOnlyTask task) {
        
        StringBuilder timeStringBuilder = new StringBuilder();

        if (task.isOverdue()) {
            timeStringBuilder.append(OVERDUE_PREFIX);
        }

        if (task.isEvent()) {
            String startTime = formatTime(TASK_TIME_PATTERN, START_TIME_PREFIX, task.getStartDateTime());
            String endTime = formatTime(TASK_TIME_PATTERN, END_TIME_PREFIX, task.getEndDateTime());
            timeStringBuilder.append(startTime);
            timeStringBuilder.append(endTime);
        } else if (task.hasDeadline()) {
            String deadline = formatTime(TASK_TIME_PATTERN, DEADLINE_PREFIX, task.getEndDateTime());
            timeStringBuilder.append(deadline);
        }

        return timeStringBuilder.toString();
    }

    private String formatUpdatedTime(ReadOnlyTask task) {
        StringBuilder timeStringBuilder = new StringBuilder();
        if (task.hasTime()) {
            timeStringBuilder.append("\n");
        }
        timeStringBuilder.append(COMPLETED_PREFIX);
        timeStringBuilder.append(formatTime(COMPLETED_TIME_PATTERN, EMPTY_PREFIX,
                Optional.ofNullable(task.getLastUpdatedTime())));
        return timeStringBuilder.toString();
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
