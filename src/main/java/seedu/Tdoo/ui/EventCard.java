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
//@@author A0132157M
import org.ocpsoft.prettytime.PrettyTime;

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
	private Label date;
	@FXML
	private Label endDate;
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
	
	PrettyTime p = new PrettyTime();
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
		date.setText("Start Date: " + task.getStartDate().date);
		if (task.checkEndDateTime() && this.task.getDone().equals("true")) {
			endDate.setText("End Date: " + task.getEndDate().endDate);
			startTime.setText("Start Time: " + task.getStartTime().startTime);
			endTime.setText("End Time: " + task.getEndTime().endTime);
	        countdown.setText(convertDateToMilli(task.getEndDate().endDate, task.getEndTime().endTime));
			done.setText("Completed");
			cardPane.setStyle("-fx-background-color: #01DF01");
		} else if (!task.checkEndDateTime() && this.task.getDone().equals("false")) {
			endDate.setText("End Date: " + task.getEndDate().endDate);
			startTime.setText("Start Time: " + task.getStartTime().startTime);
			endTime.setText("End Time: " + task.getEndTime().endTime);
			done.setText("Overdue");
	        countdown.setText(convertDateToMilli(task.getEndDate().endDate, task.getEndTime().endTime));
			cardPane.setStyle("-fx-background-color: #ff2002");
		} else {
			endDate.setText("End Date: " + task.getEndDate().endDate);
			startTime.setText("Start Time: " + task.getStartTime().startTime);
			endTime.setText("End Time: " + task.getEndTime().endTime);
			done.setText("Not Completed");
			countdown.setText(convertDateToMilli(task.getEndDate().endDate, task.getEndTime().endTime));
			cardPane.setStyle("-fx-background-color: #FFFFFF");
		}
	}

	//@@author A0132157M
    public String convertDateToMilli(String s, String q) throws ParseException {
        String string = s;
        String[] parts = string.split(" ");
        String part1 = parts[0]; 
        String part2 = parts[1]; 
        String part3 = parts[2];
        String part4 = part1.substring(0, 2);
        String bstring = q;
        String input = part3 + " " + part2 + " " + part4 + " " + bstring;
        String result = convertToSDF(input);
        return result;
    }
    
    //@@author A0132157M
    public String convertToSDF(String input) throws ParseException {
        Date date = new SimpleDateFormat("yyyy MMMM dd HH:mmaaa", Locale.ENGLISH).parse(input);
        long milliseconds = date.getTime();
        long dateToMilli = milliseconds - (new Date()).getTime();
        String result = setPrettyTime(dateToMilli);
        return result;
    }
    
    //@@author A0132157M
    public String setPrettyTime(long input) {
        String result = p.format(new Date(System.currentTimeMillis() + input));
        return result; 
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