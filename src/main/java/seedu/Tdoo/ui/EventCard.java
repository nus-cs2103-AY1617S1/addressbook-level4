package seedu.Tdoo.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.Tdoo.model.task.*;
import seedu.Tdoo.model.task.attributes.Countdown;
import java.text.ParseException;

//@@author A0144061U-reused
public class EventCard extends UiPart {

	private static final String FXML = "EventCard.fxml";

	@FXML
	private HBox cardPane;
	@FXML
	private Label name;
	@FXML
	private Label id;
	@FXML
	private Label startTime;
	@FXML
	private Label endTime;
	@FXML
	private Label done;
	@FXML
    private Label countdown;

	private Event task;
	private int displayedIndex;

	public EventCard() {

	}
	
    public static EventCard load(ReadOnlyTask task, int displayedIndex){
        EventCard card = new EventCard();
        card.task = (Event) task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

	@FXML
	//@@author A0139923X
	public void initialize() throws ParseException {
		name.setText(task.getName().name);
		id.setText(displayedIndex + ". ");
		startTime.setText("Start Time: " + task.getStartDate().date + " @" + task.getStartTime().startTime);
		endTime.setText("End Time: " + task.getEndDate().endDate + " @" + task.getEndTime().endTime);
		countdown.setText(task.getCountdown());
		if (task.checkEndDateTime() && this.task.getDone().equals("true")) {
			done.setText("Completed");
			cardPane.setStyle("-fx-background-color: #01DF01");
		} else if (!task.checkEndDateTime() && this.task.getDone().equals("false")) {
			done.setText("Overdue");
			cardPane.setStyle("-fx-background-color: #ff2002");
		} else {
			done.setText("Not Completed");
			cardPane.setStyle("-fx-background-color: #FFFFFF");
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