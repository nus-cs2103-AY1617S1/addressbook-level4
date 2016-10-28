package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.task.ReadOnlyTask;
import seedu.address.model.activity.task.Task;
import seedu.address.model.activity.event.Event;
import seedu.address.model.activity.event.ReadOnlyEvent;

// @@author A0125097A
public class ActivityCard extends UiPart {

    private static final String FXML = "ActivityListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label line1;
    @FXML
    private Label line2;
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

    public static ActivityCard load(ReadOnlyActivity person2, int displayedIndex) {
        ActivityCard card = new ActivityCard();
        card.activity = person2;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {

        name.setText(activity.getName().fullName);
        id.setText(displayedIndex + ". ");

        if (activity.getClass().getSimpleName().equalsIgnoreCase("task")) {
            line1.setText(((ReadOnlyTask) activity).getDueDate().forDisplay());
            line2.setText(((ReadOnlyTask) activity).getPriority().forDisplay());
            priorityIcon.setImage(((ReadOnlyTask) activity).getPriority().getPriorityIcon());
        } else if (activity.getClass().getSimpleName().equalsIgnoreCase("event")) {
            line1.setText(((ReadOnlyEvent) activity).getStartTime().forDisplay());
            line2.setText(((ReadOnlyEvent) activity).getEndTime().forDisplay());
        } else {
            line1.setText("");
            line2.setText("");
        }

        reminder.setText(activity.getReminder().forDisplay());

        tags.setText(activity.tagsString());
        completion.setText(activity.toStringCompletionStatus());

        if (activity.getClass().getSimpleName().equalsIgnoreCase("event")) {
            if (((Event) activity).isOngoing()) {
                cardPane.setStyle("-fx-background-color: lightskyblue;");
            }
        } else {
            if (activity.getClass().getSimpleName().equalsIgnoreCase("task")) {
                if (((Task) activity).isDueDateApproaching()) {
                    cardPane.setStyle("-fx-background-color: yellow;");
                } else if (((Task) activity).hasPassedDueDate()) {
                    cardPane.setStyle("-fx-background-color: red;");
                }
            }
            
            if (activity.getCompletionStatus()) {
                cardPane.setStyle("-fx-background-color: springgreen;");
            }
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
}
