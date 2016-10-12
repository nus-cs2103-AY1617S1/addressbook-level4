package seedu.address.ui;

import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.item.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;
    @FXML
    private Label recurrenceRate;
    @FXML
    private Label tags;
    
    // TODO: add in recurrence rate later

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
        name.setText(task.getName().name);
        priority.setText(task.getPriorityValue().toString());
        id.setText(displayedIndex + "");
        
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, d MMM yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        String startDateText, endDateText, startTimeText, endTimeText, recurrenceRateText;
        
        startDateText = "";
        startTimeText = "";
        endDateText = "";
        endTimeText = "";
        recurrenceRateText = "";
        
        if (task.getStartDate().isPresent()){
            startDateText = dateFormatter.format(task.getStartDate().get());
            startTimeText = timeFormatter.format(task.getStartDate().get());
        }
        
        if (task.getEndDate().isPresent()){
            endDateText = dateFormatter.format(task.getEndDate().get());
            endTimeText = timeFormatter.format(task.getEndDate().get());
        }
        
        
        startDate.setText(startDateText);
        startTime.setText(startTimeText);
        endDate.setText(endDateText);
        endTime.setText(endTimeText);
        
        Integer recurrenceRateInteger = task.getRecurrenceRate().recurrenceRate;
        if (recurrenceRateInteger.intValue() != 0)
            recurrenceRateText = "every " + recurrenceRateInteger.toString() + " day" + (recurrenceRateInteger.intValue() > 1 ? "s" : "");
            recurrenceRate.setText(recurrenceRateText);
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
