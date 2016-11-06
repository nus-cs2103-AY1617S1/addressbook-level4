package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label done;
    @FXML
    private Label recurring;
    @FXML
    private Label frequency;
    @FXML
    private Label tags;
    @FXML
    private ImageView priority;

    private ReadOnlyTask task;
    private int displayedIndex;
    private Image priorityImage;
    private int priorityLevel;

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
        name.setText(task.getName().taskName);
        id.setText(displayedIndex + ". ");
        date.setText(task.getDate().getValue());
        done.setText(task.isDone() ? "DONE" : "");
        if(!task.tagsString().equals(""))
        	tags.setText("Tags: " + task.tagsString());
        recurring.setText(task.isRecurring()? "recurring":"");
       frequency.setText(task.isRecurring()?task.getRecurring().recurringFrequency:"");
//       priority.setText(task.getPriorityLevel().toString());
       priorityLevel = task.getPriorityLevel().priorityLevel;
       if(priorityLevel == 1)
       {
       		priorityImage = new Image("/images/thunderbolt.png");
       		priority.setImage(priorityImage);
       		priority.setFitWidth(21.0);
       		priority.setFitHeight(40.0);
       }
       else if(priorityLevel == 2)
       {
    	   priorityImage = new Image("/images/thunderbolt2.png");
    	   priority.setImage(priorityImage);
    	   priority.setFitWidth(36.0);
    	   priority.setFitHeight(40.0);
       }
       else if(priorityLevel == 3)
       {
    	   priorityImage = new Image("/images/thunderbolt3.png");
    	   priority.setImage(priorityImage);
    	   priority.setFitWidth(48.0);
    	   priority.setFitHeight(40.0);
       }
       // frequency.setText("not yet");

    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
