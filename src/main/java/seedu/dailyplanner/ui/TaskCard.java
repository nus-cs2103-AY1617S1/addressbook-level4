package seedu.dailyplanner.ui;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.dailyplanner.model.task.ReadOnlyTask;

public class TaskCard extends UiPart {

	private static final String FXML = "TaskListCard.fxml";
	private static final String DUE_SOON_LABEL_STYLE = "-fx-background-color: rgba(247, 170, 69, 1);";
    private static final String OVERDUE_LABEL_STYLE = "-fx-background-color: rgba(183, 48, 36, 1);";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;
    @FXML
    private Label tags;
    @FXML
    private Label isComplete;
    @FXML
    private Label startAtLabel;
    @FXML
    private Label endAtLabel;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard() {
    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex) {
	TaskCard card = new TaskCard();
	card.task = task;
	card.displayedIndex = displayedIndex;
	return UiPartLoader.loadUiPart(card);
    }
  //@@author A0140124B
    @FXML
    public void initialize() {
	name.setText(task.getName());
	name.setWrapText(true);
	id.setText(displayedIndex + ". ");
	startDate.setText(task.getStart().getDate().toString());
	startTime.setText(task.getStart().getTime().toString());
	endDate.setText(task.getEnd().getDate().toString());
	endTime.setText(task.getEnd().getTime().toString());
	tags.setText(task.tagsString());
	if (task.isComplete()) {
	    isComplete.setText(task.getCompletion());
	    isComplete.setVisible(true);
	} else {
	    String dueStatus = task.getDueStatus();
	    if(dueStatus.equals("")) {
	        isComplete.setVisible(false);
	    } else {
	        isComplete.setText(dueStatus);
	        if (dueStatus.equals("DUE SOON")) {
	            isComplete.setStyle(DUE_SOON_LABEL_STYLE);
	        } else if (dueStatus.equals("OVERDUE")) {
	            isComplete.setStyle(OVERDUE_LABEL_STYLE);
	        }
	        
	    }
	}

	if (task.getStart().getDate().toString().equals("")) {
	    startAtLabel.setVisible(false);
	} else {
	    startAtLabel.setText("Starts at: ");
	}

	if (task.getEnd().getDate().toString().equals("")) {
	    endAtLabel.setVisible(false);
	} else {
	    endAtLabel.setText("Ends at: ");
	}

    }
  //@@author
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
