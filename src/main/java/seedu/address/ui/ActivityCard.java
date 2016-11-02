package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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
		String type = activity.getClass().getSimpleName().toLowerCase();

		switch (type) {

		case "activity":
			line1.setText("");

			break;

		case "task":
			line1.setText(((ReadOnlyTask) activity).getDueDate().forDisplay());
			priorityIcon.setImage(((ReadOnlyTask) activity).getPriority().getPriorityIcon());

			if (((Task) activity).isDueDateApproaching()) {
			    completion.setStyle("-fx-text-fill: #505000;"
                        + " -fx-font-size: 13;"
                        + " -fx-font-family: Georgia;");
				cardPane.setStyle("-fx-background-color: yellow;");
			} else if (((Task) activity).hasPassedDueDate()) {
			    completion.setStyle("-fx-text-fill: #500000;"
                + " -fx-font-size: 13;"
                + " -fx-font-family: Georgia;");
				cardPane.setStyle("-fx-background-color: salmon;");
			}
			break;

		case "event":
			line1.setText(((ReadOnlyEvent) activity).displayTiming());
			if (((Event) activity).isOngoing()) {
			    completion.setStyle("-fx-text-fill: #000080;"
	                    + " -fx-font-size: 13;"
	                    + " -fx-font-family: Georgia;");
				cardPane.setStyle("-fx-background-color: lightskyblue;");
			} else if (((Event) activity).isOver()) {
			    completion.setStyle("-fx-text-fill: #005000;"
	                    + " -fx-font-size: 13;"
	                    + " -fx-font-family: Georgia;");
	            cardPane.setStyle("-fx-background-color: springgreen;");
			}
			break;
		}

		reminder.setText(activity.getReminder().forDisplay());
		tags.setText(activity.tagsString());
		completion.setText(activity.toStringCompletionStatus());
		
		if (activity.getCompletionStatus()) {
		    completion.setStyle("-fx-text-fill: #005000;"
	                + " -fx-font-size: 13;"
	                + " -fx-font-family: Georgia;");
			cardPane.setStyle("-fx-background-color: springgreen;");
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
