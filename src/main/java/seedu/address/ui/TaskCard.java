package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskDateComponent;
import seedu.address.model.task.TaskType;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label tags;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label recurringType;

    private ReadOnlyTask task;
    private int displayedIndex;
    private TaskDateComponent dateComponent;
    
    public TaskCard() {}

    public static TaskCard load(TaskDateComponent taskComponent, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = taskComponent.getTaskReference();
        card.displayedIndex = displayedIndex;
        card.dateComponent = taskComponent;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        tags.setText(task.tagsString());
        initializeDate();
        initializeRecurringType();
    }

    private void initializeRecurringType() {
        String recurringTypeToShow = "";
        if (!task.getRecurringType().equals(RecurringType.NONE)) {
            recurringTypeToShow = task.getRecurringType().name();
        }
        recurringType.setText(recurringTypeToShow);
    }

    
    private void initializeDate() {
    	if (dateComponent.getStartDate().getDateInLong() == TaskDate.DATE_NOT_PRESENT) {
            startDate.setText("");
        } else {
            startDate.setText(dateComponent.getStartDate().getFormattedDate());
        }
    	
    	if (dateComponent.getEndDate().getDateInLong() == TaskDate.DATE_NOT_PRESENT) {
            endDate.setText("");
        } else {
        	endDate.setText(dateComponent.getEndDate().getFormattedDate());
        }
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
