package seedu.dailyplanner.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.dailyplanner.model.task.ReadOnlyTask;

public class PersonCard extends UiPart {

    private static final String FXML = "PersonListCard.fxml";

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

    public PersonCard() {
    }

    public static PersonCard load(ReadOnlyTask task, int displayedIndex) {
	PersonCard card = new PersonCard();
	card.task = task;
	card.displayedIndex = displayedIndex;
	return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
	name.setText(task.getName());
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
	    isComplete.setText("");
	    isComplete.setVisible(false);
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
