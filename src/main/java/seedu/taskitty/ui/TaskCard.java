package seedu.taskitty.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import seedu.taskitty.commons.util.DateUtil;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.TaskDate;
import seedu.taskitty.model.task.TaskTime;

public class TaskCard extends UiPart {

    private static final String FXML = "TaskListCard.fxml";
    private static final String INDEX_PREFIX_TODO = "T";
    private static final String INDEX_PREFIX_DEADLINE = "D";
    private static final String INDEX_PREFIX_EVENT = "E";
    
    private static final int COLUMN_DATETIME = 2;
    private static final int COLUMN_DATETIME_SIZE_EVENT = 200;
    private static final int COLUMN_DATETIME_SIZE_DEADLINE = 120;
    private static final int COLUMN_DATETIME_SIZE_TODO = 0;

    @FXML
    private HBox cardPane;
    @FXML
    private GridPane cardGrid;
    @FXML
    private Label name;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label toLabel;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;
    @FXML
    private Label id;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private int displayedIndex;

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    //@@author A0139930B
    @FXML
    public void initialize() {
        
        if (task.isTodo()) {
            initializeTodo();
        } else if (task.isDeadline()) {
            initializeDeadline();
        } else if (task.isEvent()) {
            initializeEvent();
        } else {
            assert false : "Task has to be either todo, deadline or event";
        }
    }
    
    /**
     * Initializes the todo card to the correct state.
     * 
     * The resulting card will have id, name and tags.
     * If the task is done, panel will use .done css.
     */
    private void initializeTodo() {
        displayBasicTask(INDEX_PREFIX_TODO);
        hideStartDateTime();
        hideEndDateTime();
        setCardGridDateTimeColumnWidth(COLUMN_DATETIME_SIZE_TODO);
        
        //@@author A0130853L
        boolean isDone = task.getIsDone();
        if (isDone) {
            cardPane.setStyle("-fx-background-color: grey");
            name.setStyle("-fx-text-fill: white");
            id.setStyle("-fx-text-fill: white");
        }
    }
    
    //@@author A0139930B
    /**
     * Initializes the deadline card to the correct state.
     * 
     * The resulting card will have id, name, tags, end date-time.
     * If the task is done, panel will use .done css.
     * If the task is overdue, panel will use .overdue css.
     */
    private void initializeDeadline() {
        displayBasicTask(INDEX_PREFIX_DEADLINE);
        displayEndDateTime();
        hideStartDateTime();
        setCardGridDateTimeColumnWidth(COLUMN_DATETIME_SIZE_DEADLINE);
        
        //@@author A0130853L
        boolean isDone = task.getIsDone();
        if (isDone) {
            cardPane.setStyle("-fx-background-color: grey");
            name.setStyle("-fx-text-fill: white");
            id.setStyle("-fx-text-fill: white");
            endDate.setStyle("-fx-text-fill: white");
            
        } else {
            
            // only deadline tasks have isOverdue attribute
            boolean isOverdue = task.isOverdue();
            if (isOverdue) {
                cardPane.setStyle("-fx-background-color: #d9534f");
            }
        }
    }
    
    //@@author A0139930B
    /**
     * Initializes the event card to the correct state.
     * 
     * The resulting card will have id, name, tags, start date-time and end date-time .
     * If the task is done, panel will use .done css.
     */
    private void initializeEvent() {
        displayBasicTask(INDEX_PREFIX_EVENT);
        displayStartDateTime();
        displayEndDateTime();
        
        //@@author A0130853L
        boolean isDone = task.getIsDone();
        if (isDone) {
            cardPane.setStyle("-fx-background-color: grey");
            name.setStyle("-fx-text-fill: white");
            id.setStyle("-fx-text-fill: white");
            startDate.setStyle("-fx-text-fill: white");
            endDate.setStyle("-fx-text-fill: white");
        }
    }
    
    //@@author A0139930B
    /**
     * Shows the common elements of all tasks on the UI: id, name and tags.
     * 
     * @param indexPrefix to correctly display the index of the task
     */
    private void displayBasicTask(String indexPrefix) {  
        name.setText(task.getName().fullName);
        id.setText(indexPrefix + displayedIndex);
        tags.setText(task.tagsString());
    }
    
    /**
     * Shows the start date-time of the task on the UI
     */
    private void displayStartDateTime() {
        TaskDate startTaskDate = task.getPeriod().getStartDate();
        TaskTime startTaskTime = task.getPeriod().getStartTime();
        startTime.setText(DateUtil.formatTimeForUI(startTaskTime));
        startDate.setText(DateUtil.formatDateForUI(startTaskDate));
    }
    
    /**
     * Shows the end date-time of the task on the UI
     */
    private void displayEndDateTime() {
        TaskDate endTaskDate = task.getPeriod().getEndDate();  
        TaskTime endTaskTime = task.getPeriod().getEndTime();
        endTime.setText(DateUtil.formatTimeForUI(endTaskTime));
        endDate.setText(DateUtil.formatDateForUI(endTaskDate));
    }
    
    /**
     * Removes the start date-time from the display
     */
    private void hideStartDateTime() {
        startTime.setManaged(false);
        startDate.setManaged(false);
        toLabel.setManaged(false);
    }
    
    /**
     * Removes the start and end date-time from the display
     */
    private void hideEndDateTime() {
        endTime.setManaged(false);
        endDate.setManaged(false);
    }
    
    /**
     * Adjusts the width of the date-time column of the card to the specified width
     */
    private void setCardGridDateTimeColumnWidth(int width) {
        cardGrid.getColumnConstraints().get(COLUMN_DATETIME).setMinWidth(width);
        cardGrid.getColumnConstraints().get(COLUMN_DATETIME).setPrefWidth(width);
        cardGrid.getColumnConstraints().get(COLUMN_DATETIME).setMaxWidth(width);
    }

    //@@author
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
