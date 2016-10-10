package seedu.todo.ui;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;
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
    
    private static final String STYLE_COLLAPSED = "collapsed";
    private static final String STYLE_COMPLETED = "completed";
    private static final String STYLE_OVERDUE = "overdue";
    private static final String STYLE_SELECTED = "selected";
    
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
    private Label descriptionLabel, dateLabel, locationLabel;
    @FXML
    private HBox descriptionBox, dateBox, locationBox;
    @FXML
    private FlowPane tagsBox;
    
    /*Variables*/
    private ImmutableTask task;
    private int displayedIndex;
    private TimeUtil timeUtil;

    public TaskCard(){
        this.timeUtil = new TimeUtil();
    }

    public static TaskCard load(ImmutableTask task, int displayedIndex){
        TaskCard taskListCard = new TaskCard();
        taskListCard.task = task;
        taskListCard.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(taskListCard);
    }

    @FXML
    public void initialize() {
        displayEverythingElse();
        displayTags();
        displayTimings();
        setStyle();
    }

    public VBox getLayout() {
        return taskCard;
    }

    @Override
    public void setNode(Node node) {
        taskCard = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    /**
     * Displays all other view elements, including title, type label, pin image, description and location texts.
     */
    private void displayEverythingElse() {
        titleLabel.setText(String.valueOf(displayedIndex) + ". " + task.getTitle());
        pinImage.setVisible(task.isPinned());
        typeLabel.setText(task.isEvent() ? EVENT_TYPE : TASK_TYPE);
        FxViewUtil.displayTextWhenAvailable(descriptionLabel, descriptionBox, task.getDescription());
        FxViewUtil.displayTextWhenAvailable(locationLabel, locationBox, task.getLocation());
    }
    
    /**
     * Displays the tags in lexicographical order, ignoring case.
     */
    private void displayTags(){
        LinkedList<Tag> tagList = new LinkedList<>(task.getTags());
        
        if (!tagList.isEmpty()) {
            FxViewUtil.setCollapsed(tagsBox, true);
        } else {
            tagList.sort(new Comparator<Tag>(){
                @Override
                public int compare(Tag o1, Tag o2) {
                    return o1.toString().compareToIgnoreCase(o2.toString());
                }
            });
            for (Tag tag : tagList) {
                Label tagLabel = FxViewUtil.constructRoundedText(tag.tagName);
                tagsBox.getChildren().add(tagLabel);
            }
        }
    }
    
    /**
     * Sets style according to the status (e.g. completed, overdue, etc) of the task.
     */
    private void setStyle() {
        ObservableList<String> styleClasses = taskCard.getStyleClass();
        boolean isCompleted = task.isCompleted();
        boolean isOverdue = task.getEndTime().isPresent() && timeUtil.isOverdue(task.getEndTime().get());
        
        if (isCompleted) {
            styleClasses.add(STYLE_COMPLETED);
        } else if (isOverdue) {
            styleClasses.add(STYLE_OVERDUE);
        }
        
        //styleClasses .add(STYLE_COLLAPSED); TODO: Disabled until implemented
    }
    
    /**
     * Displays formatted task or event timings in the time field.
     */
    private void displayTimings() {
        String displayTimingOutput = "";
        Optional<LocalDateTime> startTime = task.getStartTime();
        Optional<LocalDateTime> endTime = task.getEndTime();
        boolean isEventWithTime = task.isEvent() && startTime.isPresent() && endTime.isPresent();
        boolean isTaskWithTime = !task.isEvent() && endTime.isPresent();
        
        if (isEventWithTime) {
            displayTimingOutput = timeUtil.getEventTimeText(startTime.get(), endTime.get());
        } else if (isTaskWithTime) {
            displayTimingOutput = timeUtil.getTaskDeadlineText(endTime.get());
        } else {
            FxViewUtil.setCollapsed(dateBox, true);
            return;
        }
        
        dateLabel.setText(displayTimingOutput);
    }
}
