package seedu.Tdoo.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.Tdoo.model.task.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
//author A0132157M
import org.ocpsoft.prettytime.PrettyTime;

//@@author A0144061U-reused
public class DeadlineCard extends UiPart {

	private static final String FXML = "DeadlineCard.fxml";

	@FXML
	private HBox cardPane;
	@FXML
	private Label name;
	@FXML
	private Label id;
	@FXML
	private Label date;
	@FXML
	private Label endTime;
	@FXML
	private Label done;

	private Deadline task;
	private int displayedIndex;

	public DeadlineCard() {

	}
	
	PrettyTime p = new PrettyTime();
    public static DeadlineCard load(ReadOnlyTask task, int displayedIndex){
        DeadlineCard card = new DeadlineCard();
        card.task = (Deadline) task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }
    
	// @@author A0144061U-reused
	@FXML
	public void initialize() {
		name.setText(task.getName().name);
		id.setText(displayedIndex + ". ");
		if (task.checkEndDateTime() && this.task.getDone().equals("true")) {
			date.setText("Date: " + task.getStartDate().date);
			endTime.setText("End Time: " + task.getEndTime().endTime);
			done.setText("Completed");
			cardPane.setStyle("-fx-background-color: #01DF01");
		} else if (!task.checkEndDateTime() && this.task.getDone().equals("false")) {
			date.setText("Date: " + task.getStartDate().date);
			endTime.setText("End Time: " + task.getEndTime().endTime);
			done.setText("Overdue");
			cardPane.setStyle("-fx-background-color: #ff2002");
		} else {
			date.setText("Date: " + task.getStartDate().date);
			endTime.setText("End Time: " + task.getEndTime().endTime);
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