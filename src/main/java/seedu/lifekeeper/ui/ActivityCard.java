package seedu.lifekeeper.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.model.activity.event.Event;
import seedu.lifekeeper.model.activity.event.ReadOnlyEvent;
import seedu.lifekeeper.model.activity.task.ReadOnlyTask;
import seedu.lifekeeper.model.activity.task.Task;

// @@author A0125097A
public class ActivityCard extends UiPart {
    
    private static final String COMPLETION_STYLE_RED = "-fx-text-fill: #500000;";
    private static final String COMPLETION_STYLE_YELLOW = "-fx-text-fill: #505000;";
    private static final String COMPLETION_STYLE_GREEN = "-fx-text-fill: #005000;";
    private static final String COMPLETION_STYLE_BLUE = "-fx-text-fill: #000080;";
    
    private static final String DEFAULT_COMPLETION_FONT = " -fx-font-size: 13;" + " -fx-font-family: Georgia;";
    
    private static final String CARDPANE_STYLE_RED = "-fx-background-color: salmon;";
    private static final String CARDPANE_STYLE_YELLOW = "-fx-background-color: yellow;";
    private static final String CARDPANE_STYLE_GREEN = "-fx-background-color: springgreen;";
    private static final String CARDPANE_STYLE_BLUE = "-fx-background-color: lightskyblue;";

    private static final String FXML = "ActivityListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label dateTime;
    @FXML
    private Label reminder;
    @FXML
    private Label tags;
    @FXML
    private Label completion;
    @FXML
    private ImageView priorityIcon;

    private ReadOnlyActivity activity;
    private int displayedIndex;

    public ActivityCard() {

    }

    public static ActivityCard load(ReadOnlyActivity activity, int displayedIndex) {
        ActivityCard card = new ActivityCard();
        card.activity = activity;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {

        name.setText(activity.getName().fullName);
        id.setText(displayedIndex + ". ");
        reminder.setText(activity.getReminder().forDisplay());
        tags.setText(activity.tagsString());
        completion.setText(activity.toStringCompletionStatus());

        String type = activity.getClass().getSimpleName().toLowerCase();

        switch (type) {

        case "activity":
            dateTime.setText("");

            break;

        case "task":
            if (((ReadOnlyTask) activity).getDueDate() != null) {
                dateTime.setText(((ReadOnlyTask) activity).getDueDate().forDisplay());
            }
            
            priorityIcon.setImage(((ReadOnlyTask) activity).getPriority().getPriorityIcon());

            if (((Task) activity).isDueDateApproaching()) {
                setCssStyle("yellow");
            } else if (((Task) activity).hasPassedDueDate()) {
                setCssStyle("red");
            }
            break;

        case "event":
            dateTime.setText(((ReadOnlyEvent) activity).displayTiming());
            if (((Event) activity).isOngoing()) {
                setCssStyle("blue");
            } else if (((Event) activity).isOver()) {
                setCssStyle("green");
            }
            break;
        }

        if (activity.getCompletionStatus()) {
            setCssStyle("green");
        }
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

    //@@author A0125680H
    /**
     * Styles the status text and the card background to the color specified.
     * 
     * @param color
     *            the color to be chosen as the style of the card.
     */
    private void setCssStyle(String color) {
        switch (color.toLowerCase()) {
        case "red":
            completion.setStyle(COMPLETION_STYLE_RED + DEFAULT_COMPLETION_FONT);
            cardPane.setStyle(CARDPANE_STYLE_RED);
            break;
        case "yellow":
            completion.setStyle(COMPLETION_STYLE_YELLOW + DEFAULT_COMPLETION_FONT);
            cardPane.setStyle(CARDPANE_STYLE_YELLOW);
            break;
        case "green":
            completion.setStyle(COMPLETION_STYLE_GREEN + DEFAULT_COMPLETION_FONT);
            cardPane.setStyle(CARDPANE_STYLE_GREEN);
            break;
        case "blue":
            completion.setStyle(COMPLETION_STYLE_BLUE + DEFAULT_COMPLETION_FONT);
            cardPane.setStyle(CARDPANE_STYLE_BLUE);
            break;
        }
    }
}
