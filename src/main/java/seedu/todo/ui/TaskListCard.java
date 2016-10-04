package seedu.todo.ui;

import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import seedu.todo.model.task.ReadOnlyTask;

/**
 * This class links up with TaskListCard.fxml layout to display details of a given ReadOnlyTask to users via the TaskListPanel.fxml.  
 */
public class TaskListCard extends UiPart{
    /*Constants*/
    private static final String FXML = "TaskListCard.fxml";
    private static final String TASK_TYPE = "Task";
    private static final String EVENT_TYPE = "Event";
    
    /*Layout Declarations*/
    @FXML
    private VBox taskCard;
    @FXML
    private ImageView pinImage;
    @FXML
    private Label titleLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label tagLabel0, tagLabel1, tagLabel2, tagLabel3, tagLabel4;
    @FXML
    private Label descriptionLabel, dateLabel, locationLabel;
    @FXML
    private HBox descriptionBox, dateBox, locationBox;
    
    private Label[] tagLabels = {
            tagLabel0, tagLabel1, tagLabel2, tagLabel3, tagLabel4
    };
    
    /*Variables*/
    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskListCard(){

    }

    public static TaskListCard load(ReadOnlyTask task, int displayedIndex){
        TaskListCard taskListCard = new TaskListCard();
        taskListCard.task = task;
        taskListCard.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(taskListCard);
    }

    @FXML
    public void initialize() {
        //Fill the details of the task into the views as required. 
        titleLabel.setText(String.valueOf(displayedIndex) + ". " + task.getTitle());
        pinImage.setVisible(task.isPinned());
        typeLabel.setText((task.isEvent()) ? EVENT_TYPE : TASK_TYPE);
        displayTags();
        
        //Display task description when available
        if (task.getDescription().isPresent()) {
            descriptionLabel.setText(task.getDescription().get());
        } else {
            descriptionBox.setVisible(false);
        }
        
        //Display time when available
        if (task.getStartTime().isPresent() || task.getEndTime().isPresent()) {
            //TODO: Either start or end time are present.
        } else {
            dateBox.setVisible(false);
        }
        
        //Display location when available
        if (task.getLocation().isPresent()) {
            locationLabel.setText(task.getLocation().get());
        } else {
            locationBox.setVisible(false);
        }
    }

    public VBox getLayout() {
        return taskCard;
    }

    @Override
    public void setNode(Node node) {
        taskCard = (VBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    /**
     * Displays the tags with tag labels sorted lexicographically.
     */
    private void displayTags(){
        //Obtain and verify that there are at most 5 tags.
        int numberOfTags = task.getTags().size();
        assert (numberOfTags <= 5);
        
        //Extract tag names, and sort the tags lexicographically.
        Stream<String> tagsStream = task.getTags().stream()
                .map(tag -> tag.tagName);
        tagsStream.sorted();
        String[] tags = (String[]) tagsStream.toArray();
        
        //Label or hide the tagLabels
        for (int i = 0; i < 5; i++) {
            if (i < numberOfTags) {
                tagLabels[i].setText(tags[i]);
            } else {
                tagLabels[i].setVisible(false);
            }
        }
    }
}
