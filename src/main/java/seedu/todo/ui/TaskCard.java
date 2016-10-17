package seedu.todo.ui;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.commons.util.TimeUtil;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.ImmutableTask;

import java.time.LocalDateTime;
import java.util.*;

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

    /*Static Field*/
    //Provides a global reference between an ImmutableTask to the wrapper TaskCard class,
    //since we have no direct access of TaskCard from the ListView object.
    private static final Map<ImmutableTask, TaskCard> taskCardMap = new HashMap<>();
    
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

    /**
     * Loads and initialise one cell of the task in the to-do list ListView.
     * @param task to be displayed on the cell
     * @param displayedIndex index to be displayed on the card itself to the user
     * @return an instance of this class
     */
    public static TaskCard load(ImmutableTask task, int displayedIndex){
        TaskCard taskListCard = new TaskCard();
        taskListCard.task = task;
        taskListCard.displayedIndex = displayedIndex;
        taskCardMap.put(task, taskListCard);
        return UiPartLoader.loadUiPart(taskListCard);
    }

    /**
     * Initialise all the view elements in a task card.
     */
    @FXML
    public void initialize() {
        displayEverythingElse();
        displayTags();
        displayTimings();
        setStyle();
        setTimingAutoUpdate();
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
        List<Tag> tagList = new ArrayList<>(task.getTags());
        
        if (!tagList.isEmpty()) {
            FxViewUtil.setCollapsed(tagsBox, true);
        } else {
            tagList.sort((o1, o2) -> o1.toString().compareToIgnoreCase(o2.toString()));
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
        boolean isCompleted = task.isCompleted();
        boolean isOverdue = task.getEndTime().isPresent() && timeUtil.isOverdue(task.getEndTime().get());
        
        if (isCompleted) {
            FxViewUtil.addClassStyle(taskCard, STYLE_COMPLETED);
        } else if (isOverdue) {
            FxViewUtil.addClassStyle(taskCard, STYLE_OVERDUE);
        }
        FxViewUtil.addClassStyle(taskCard, STYLE_COLLAPSED);
    }
    
    /**
     * Displays formatted task or event timings in the time field.
     */
    private void displayTimings() {
        String displayTimingOutput;
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

    /**
     * Toggles the task card's collapsed or expanded state.
     */
    public void toggleCardCollapsing() {
        FxViewUtil.toggleClassStyle(taskCard, STYLE_COLLAPSED);
    }

    /**
     * Allows timing, and deadline highlight style to be updated automatically.
     */
    private void setTimingAutoUpdate() {
        Timeline timeline = FxViewUtil.setRecurringUiTask(30, event -> {
            displayTimings();
            setStyle();
        });
        timeline.play();
    }

    /**
     * Displays in the Ui whether this card is selected
     * @param isSelected true when the card is selected
     */
    public void markAsSelected(boolean isSelected) {
        if (isSelected) {
            FxViewUtil.addClassStyle(taskCard, STYLE_SELECTED);
        } else {
            FxViewUtil.removeClassStyle(taskCard, STYLE_SELECTED);
        }
    }

    /**
     * Gets the mapped {@link TaskCard} object from an {@link ImmutableTask} object
     * @param task that is being wrapped by the {@link TaskCard} object
     * @return a {@link TaskCard} object that contains this task (can be null if not available)
     */
    public static TaskCard getTaskCard(ImmutableTask task) {
        return taskCardMap.get(task);
    }
}
