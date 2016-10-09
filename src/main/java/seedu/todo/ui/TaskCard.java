package seedu.todo.ui;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.commons.util.TimeUtil;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.ImmutableTask;

/**
 * This class links up with TaskCard.fxml layout to display details of a given ReadOnlyTask to users via the TaskListPanel.fxml.  
 */
public class TaskCard extends UiPart{
    /*Constants*/
    private static final String FXML = "TaskCard.fxml";
    
    private static final String STYLE_BASE = "/style/taskcardstyles/TaskCardBaseStyle.css";
    private static final String STYLE_COLLAPSED = "/style/taskcardstyles/TaskCardCollapsedStyle.css";
    private static final String STYLE_COMPLETED = "/style/taskcardstyles/TaskCardCompletedStyle.css";
    private static final String STYLE_OVERDUE = "/style/taskcardstyles/TaskCardOverdueStyle.css";
    private static final String STYLE_SELECTED = "/style/taskcardstyles/TaskCardSelectedStyle.css";
    
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
    private Label typeLabel0, typeLabel1;
    @FXML
    private Label tagLabel0, tagLabel1, tagLabel2, tagLabel3, tagLabel4;
    @FXML
    private Label descriptionLabel, dateLabel, locationLabel;
    @FXML
    private HBox descriptionBox, dateBox, locationBox;
    
    /*Variables*/
    private ImmutableTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ImmutableTask task, int displayedIndex){
        TaskCard taskListCard = new TaskCard();
        taskListCard.task = task;
        taskListCard.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(taskListCard);
    }

    @FXML
    public void initialize() {
        setStyle();
                
        titleLabel.setText(String.valueOf(displayedIndex) + ". " + task.getTitle());
        pinImage.setVisible(task.isPinned());
        
        if (task.isEvent()) {
            typeLabel0.setText(EVENT_TYPE);
            typeLabel1.setText(EVENT_TYPE);
        } else {
            typeLabel0.setText(TASK_TYPE);
            typeLabel1.setText(TASK_TYPE);
        }
        
        displayTags();
        
        //Display task description when available
        if (task.getDescription().isPresent()) {
            descriptionLabel.setText(task.getDescription().get());
        } else {
            FxViewUtil.setCollapsed(descriptionBox, true);
        }
        
        //Display time when available
        TimeUtil timeUtil = new TimeUtil();
        if (task.isEvent() && task.getStartTime().isPresent() && task.getEndTime().isPresent()) {
            dateLabel.setText(timeUtil.getEventTimeText(task.getStartTime().get(), task.getEndTime().get()));
        } else if (!task.isEvent() && task.getEndTime().isPresent()) {
            dateLabel.setText(timeUtil.getTaskDeadlineText(task.getEndTime().get()));
        } else {
            FxViewUtil.setCollapsed(dateBox, true);
        }
        
        //Display location when available
        if (task.getLocation().isPresent()) {
            locationLabel.setText(task.getLocation().get());
        } else {
            FxViewUtil.setCollapsed(locationBox, true);
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
     * Displays the tags with tag labels sorted lexicographically, ignoring case.
     */
    private void displayTags(){
        Label[] tagLabels = {tagLabel0, tagLabel1, tagLabel2, tagLabel3, tagLabel4};
        
        int numberOfTags = task.getTags().size();
        assert (numberOfTags <= 5);
        
        LinkedList<Tag> tagList = new LinkedList<>(task.getTags());
        tagList.sort(new Comparator<Tag>(){
            @Override
            public int compare(Tag o1, Tag o2) {
                return o1.toString().compareToIgnoreCase(o2.toString());
            }
        });
        
        for (Label label : tagLabels) {
            if (tagList.isEmpty()) {
                FxViewUtil.setCollapsed(label, true);
            } else {
                label.setText(tagList.poll().tagName);
            }
        }
    }
    
    private void setStyle() {
        ObservableList<String> stylesheets = taskCard.getStylesheets();
        stylesheets.add(STYLE_BASE);
        stylesheets.add(STYLE_COLLAPSED);
        
        if (task.isCompleted()) {
            stylesheets.add(STYLE_COMPLETED);
        } else if (task.getEndTime().isPresent() && task.getEndTime().get().isBefore(LocalDateTime.now())) {
            stylesheets.add(STYLE_OVERDUE);
        }
    }
}
