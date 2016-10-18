package seedu.address.ui;

import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.TimePeriod;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Rectangle priority;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
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
        switch(task.getPriorityValue()){
            case LOW:
                priority.setFill(Paint.valueOf("green"));
                break;
            case MEDIUM:
                priority.setFill(Paint.valueOf("yellow"));
                break;
            case HIGH:
                priority.setFill(Paint.valueOf("red"));
                break;
            default:
                assert false: "priority should only be LOW, MEDIUM, or HIGH";
        }       

        
        id.setText(displayedIndex + ".");
        
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
        String startDateText, endDateText, recurrenceRateText;
        
        startDateText = "";
        endDateText = "";
        recurrenceRateText = "";
        
        if (task.getStartDate().isPresent()){
            startDateText = "Start: " + dateFormatter.format(task.getStartDate().get());
        }
        
        if (task.getEndDate().isPresent()){
            endDateText = "End: " + dateFormatter.format(task.getEndDate().get());
        }
        
        startDate.setText(startDateText);
        endDate.setText(endDateText);
        
        if (task.getRecurrenceRate().isPresent()){
            Integer recurrenceRateInteger = task.getRecurrenceRate().get().rate;
            TimePeriod timePeriod = task.getRecurrenceRate().get().timePeriod;
            if (recurrenceRateInteger != null && timePeriod != null) {
                recurrenceRateText = "every " 
                        + (recurrenceRateInteger == 1 ? "" : recurrenceRateInteger.toString() + " ")
                        + timePeriod.toString().toLowerCase() 
                        + (recurrenceRateInteger.intValue() > 1 ? "s" : "");
            } else if (recurrenceRateInteger == null && timePeriod != null) {
                recurrenceRateText = "every " + timePeriod.toString().toLowerCase();
            }
        }
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
