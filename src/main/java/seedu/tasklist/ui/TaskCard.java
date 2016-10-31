package seedu.tasklist.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.tasklist.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

	private static final String FXML = "TaskListCard.fxml";
	private static final String IMAGE_DIR = "/images/";

	private static final String COMPLETED_ICON_FILE = "icon_checkmark.png";
	private static final String OVERDUE_ICON_FILE = "icon_exclamation.png";
	private static final String INCOMPLETE_ICON_FILE = "icon_incomplete.png";

	private static final String DAILY_RECUR_FILE = "daily_rec.png";
	private static final String WEEKLY_RECUR_FILE = "weekly_rec.png";
	private static final String MONTHLY_RECUR_FILE = "monthly_rec.png";
	private static final String YEARLY_RECUR_FILE = "yearly_rec.png";

	private static final String PRIORITY_HIGH_FILE = "three_stars.png";
	private static final String PRIORITY_MED_FILE = "two_stars.png";
	private static final String PRIORITY_LOW_FILE = "one_star.png";
	
	@FXML
	private AnchorPane cardPane;
	@FXML
	private Label name;
	@FXML
	private Label id;
	@FXML
	private Label startTime;
	@FXML
	private Label endTime;
	@FXML
	private Label priority;
	@FXML
	private Label recurring;
	@FXML
	private ImageView statusButton;

	private ReadOnlyTask task;
	private int displayedIndex;

	public TaskCard(){

	}

	public static TaskCard load(ReadOnlyTask task, int displayedIndex){
		TaskCard card = new TaskCard();
		card.task = task;
		card.displayedIndex = displayedIndex;
		return UiPartLoader.loadUiPart(card);
	}

	@FXML
	public void initialize() {
		name.setText(task.getTaskDetails().taskDetails);
		id.setText(displayedIndex + ". ");
		startTime.setText(task.getStartTime().toCardString());
		endTime.setText(task.getEndTime().toCardString());

		priority.setText("");
		recurring.setText("");
		
		setColour();
		setPriorityGraphic();
		setRecurringGraphic();
		statusButton.setVisible(true);
		setStatusButtonColour();
	}

	public AnchorPane getLayout() {
		return cardPane;
	}

	//@@author A0146107M
	private void setPriorityGraphic(){
		switch(task.getPriority().priorityLevel){
		case "high":
			priority.setGraphic(new ImageView(getImage(PRIORITY_HIGH_FILE)));
			break;
		case "med":
			priority.setGraphic(new ImageView(getImage(PRIORITY_MED_FILE)));
			break;
		case "low":	default:
			priority.setGraphic(new ImageView(getImage(PRIORITY_LOW_FILE)));
			break;
		}
	}
	
	private void setRecurringGraphic(){
		switch(task.getRecurringFrequency()){
		case "daily":
			recurring.setGraphic(new ImageView(getImage(DAILY_RECUR_FILE)));
			break;
		case "weekly":
			recurring.setGraphic(new ImageView(getImage(WEEKLY_RECUR_FILE)));
			break;
		case "monthly":
			recurring.setGraphic(new ImageView(getImage(MONTHLY_RECUR_FILE)));
			break;
		case "yearly":
			recurring.setGraphic(new ImageView(getImage(YEARLY_RECUR_FILE)));
		default:
			break;
		}					
	}

	private void setColour(){
		if(task.isComplete()){
			cardPane.setStyle("-fx-background-color: #C0FFC0;");
		}
		else if(task.isOverDue()){
			cardPane.setStyle("-fx-background-color: #FFC0C0;");
		}
		else {
			cardPane.setStyle("-fx-background-color: #FFFFFF;");
		}
	}

	public void setStatusButtonColour() {
		if(task.isComplete()){
			statusButton.setImage(getImage(COMPLETED_ICON_FILE));
		}
		else if(task.isOverDue()){
			statusButton.setImage(getImage(OVERDUE_ICON_FILE));
		}
		else{
			statusButton.setImage(getImage(INCOMPLETE_ICON_FILE));
		}
	}

	private Image getImage(String resource){
		String url = TaskCard.class.getResource(IMAGE_DIR + resource).toExternalForm();
		return new Image(url);
	}

	@Override
	public void setNode(Node node) {
		cardPane = (AnchorPane)node;
	}

	@Override
	public String getFxmlPath() {
		return FXML;
	}
}
