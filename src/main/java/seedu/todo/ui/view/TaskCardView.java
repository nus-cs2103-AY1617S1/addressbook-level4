package seedu.todo.ui.view;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.todo.commons.util.TimeUtil;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.util.UiPartLoaderUtil;
import seedu.todo.ui.util.FxViewUtil;
import seedu.todo.ui.util.ViewGeneratorUtil;
import seedu.todo.ui.util.ViewStyleUtil;

import java.time.LocalDateTime;
import java.util.*;

/**
 * This class links up with TaskCardView.fxml layout to display details of a given ReadOnlyTask to users via the TaskListPanel.fxml.
 */
public class TaskCardView extends UiPart {
    /*Constants*/
    private static final String FXML = "TaskCardView.fxml";

    private static final String TASK_TYPE = "Task";
    private static final String EVENT_TYPE = "Event";

    /*Static Field*/
    //Provides a global reference between an ImmutableTask to the wrapper TaskCardView class,
    //since we have no direct access of TaskCardView from the ListView object.
    private static final Map<ImmutableTask, TaskCardView> taskCardMap = new HashMap<>();
    
    /*Layout Declarations*/
    @FXML
    private VBox taskCard;
    @FXML
    private ImageView pinImage;
    @FXML
    private Label titleLabel;
    @FXML
    private Label typeLabel, moreInfoLabel;
    @FXML
    private Label descriptionLabel, dateLabel, locationLabel;
    @FXML
    private HBox descriptionBox, dateBox, locationBox;
    @FXML
    private FlowPane tagsBox;
    
    /* Variables */
    private ImmutableTask task;
    private int displayedIndex;
    private TimeUtil timeUtil = new TimeUtil();

    /* Default Constructor */
    private TaskCardView(){
    }

    /* Initialisation Methods */
    /**
     * Loads and initialise one cell of the task in the to-do list ListView.
     * @param task to be displayed on the cell
     * @param displayedIndex index to be displayed on the card itself to the user
     * @return an instance of this class
     */
    public static TaskCardView load(ImmutableTask task, int displayedIndex){
        TaskCardView taskListCard = new TaskCardView();
        taskListCard.task = task;
        taskListCard.displayedIndex = displayedIndex;
        taskCardMap.put(task, taskListCard);
        return UiPartLoaderUtil.loadUiPart(taskListCard);
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
        initialiseCollapsibleView();
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
                Label tagLabel = ViewGeneratorUtil.constructRoundedText(tag.tagName);
                tagsBox.getChildren().add(tagLabel);
            }
        }
    }
    
    /**
     * Sets style according to the status (e.g. completed, overdue, etc) of the task.
     */
    private void setStyle() {
        boolean isCompleted = task.isCompleted();
        boolean isOverdue = task.getEndTime().isPresent() && timeUtil.isOverdue(task.getEndTime().get()) && !task.isEvent();
        boolean isOngoing = task.isEvent() && timeUtil.isOngoing(task.getStartTime().get(), task.getEndTime().get());
        
        if (isCompleted) {
            ViewStyleUtil.addClassStyles(taskCard, ViewStyleUtil.STYLE_COMPLETED);
        } else if (isOverdue) {
            ViewStyleUtil.addClassStyles(taskCard, ViewStyleUtil.STYLE_OVERDUE);
        } else if (isOngoing){
            ViewStyleUtil.addClassStyles(taskCard, ViewStyleUtil.STYLE_ONGOING);
        }
    }

    /**
     * Initialise the view to show collapsed state if it can be collapsed,
     * else hide the {@link #moreInfoLabel} otherwise.
     */
    private void initialiseCollapsibleView() {
        ViewStyleUtil.addRemoveClassStyles(true, taskCard, ViewStyleUtil.STYLE_COLLAPSED);
        FxViewUtil.setCollapsed(moreInfoLabel, !isTaskCollapsible());
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
     * Allows timing, and deadline highlight style to be updated automatically.
     */
    private void setTimingAutoUpdate() {
        Timeline timeline = FxViewUtil.setRecurringUiTask(30, event -> {
            displayTimings();
            setStyle();
        });
        timeline.play();
    }

    /* Methods interfacing with UiManager*/
    /**
     * Toggles the task card's collapsed or expanded state, only if this card is collapsible.
     */
    public void toggleCardCollapsing() {
        if (isTaskCollapsible()) {
            //Sets both the collapsed style of the card, and mark the visibility of the "more" label.
            boolean isCollapsing = ViewStyleUtil.toggleClassStyle(taskCard, ViewStyleUtil.STYLE_COLLAPSED);
            FxViewUtil.setCollapsed(moreInfoLabel, !isCollapsing);
        }
    }

    /**
     * Displays in the Ui whether this card is selected
     * @param isSelected true when the card is selected
     */
    public void markAsSelected(boolean isSelected) {
        ViewStyleUtil.addRemoveClassStyles(isSelected, taskCard, ViewStyleUtil.STYLE_SELECTED);
    }

    /* Helper Methods */
    /**
     * Returns true if this task card can be collapsed, based on the information given from the {@link ImmutableTask}
     */
    private boolean isTaskCollapsible() {
        boolean hasDescription = task.getDescription().isPresent();
        boolean hasTags = !task.getTags().isEmpty();
        return hasDescription || hasTags;
    }

    /* Getters */
    /**
     * Gets the mapped {@link TaskCardView} object from an {@link ImmutableTask} object
     * @param task that is being wrapped by the {@link TaskCardView} object
     * @return a {@link TaskCardView} object that contains this task (can be null if not available)
     */
    public static TaskCardView getTaskCard(ImmutableTask task) {
        return taskCardMap.get(task);
    }

    public int getDisplayedIndex() {
        return displayedIndex;
    }

    public VBox getLayout() {
        return taskCard;
    }

    /* Override Methods */
    @Override
    public void setNode(Node node) {
        taskCard = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
