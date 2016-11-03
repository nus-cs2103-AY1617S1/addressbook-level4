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
	@FXML
    private Label countdown;

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
	public void initialize() throws ParseException {
		name.setText(task.getName().name);
		id.setText(displayedIndex + ". ");
		if (task.checkEndDateTime() && this.task.getDone().equals("true")) {
			date.setText("Date: " + task.getStartDate().date);
			endTime.setText("End Time: " + task.getEndTime().endTime);
			done.setText("Completed");
	        countdown.setText(convertDateToMilli(task.getStartDate().date, task.getEndTime().endTime));
			cardPane.setStyle("-fx-background-color: #01DF01");
		} else if (!task.checkEndDateTime() && this.task.getDone().equals("false")) {
			date.setText("Date: " + task.getStartDate().date);
			endTime.setText("End Time: " + task.getEndTime().endTime);
			done.setText("Overdue");
	        countdown.setText(convertDateToMilli(task.getStartDate().date, task.getEndTime().endTime));
			cardPane.setStyle("-fx-background-color: #ff2002");
		} else {
			date.setText("Date: " + task.getStartDate().date);
			endTime.setText("End Time: " + task.getEndTime().endTime);
			done.setText("Not Completed");
	        countdown.setText(convertDateToMilli(task.getStartDate().date, task.getEndTime().endTime));
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