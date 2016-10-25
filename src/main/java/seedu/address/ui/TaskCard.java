package seedu.address.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.TimePeriod;

//@@author A0093960X
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
    
    private ReadOnlyTask task;
    private int displayedIndex;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");

    public TaskCard() {
        
    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex) {
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        assert task != null && task.getName() != null && task.getPriorityValue() != null;
        
        setTaskCardId();
        setTaskCardName();
        setTaskCardPriority();                
        setTaskCardStartDate();
        setTaskCardEndDate();       
        setTaskCardRecurrence();       
    }
    
    private void setTaskCardId() {
        assert displayedIndex > 0;
        
        String taskCardId = displayedIndex + ".";
        id.setText(taskCardId);
    }
    
    private void setTaskCardName() {
        assert task != null && task.getName() != null && task.getName().name != null;
        
        String taskName = task.getName().name;
        name.setText(taskName);
    }
    
    private void setTaskCardPriority() {
        assert task != null && task.getPriorityValue() != null;
        
        Priority taskPriority = task.getPriorityValue();
        
        Paint taskPriorityColour = Paint.valueOf("yellow");
        
        switch (taskPriority) {
            case LOW:
                taskPriorityColour = Paint.valueOf("green");
                break;
            case MEDIUM:
                taskPriorityColour = Paint.valueOf("yellow");
                break;
            case HIGH:
                taskPriorityColour = Paint.valueOf("red");
                break;
            default:
                assert false: "priority should only be LOW, MEDIUM, or HIGH";
        }
        
        priority.setFill(taskPriorityColour);
    }
    
    private void setTaskCardStartDate() {
        String startDateText = "";
        boolean taskHasStartDate = checkIfStartDatePresent();
        
        if (taskHasStartDate) {
            startDateText = prepareStartDateToDisplay();
        }
        
        startDate.setText(startDateText);
    }
    
    private void setTaskCardEndDate() {
        String endDateText = "";
        boolean taskHasEndDate = checkIfEndDatePresent();
        if (taskHasEndDate) {
            endDateText = prepareEndDateToDisplay();
        }
        endDate.setText(endDateText);
    }
    
    private void setTaskCardRecurrence() {
        String recurrenceRateText = "";
        boolean taskIsRecurring = task.getRecurrenceRate().isPresent();

        if (taskIsRecurring) {
            recurrenceRateText = prepareRecurrenceRateToDisplay();
        }
        
        recurrenceRate.setText(recurrenceRateText);
    }

    private String prepareStartDateToDisplay() {
        assert task.getStartDate().isPresent();
        
        Date startDate = task.getStartDate().get();
        return "Start: " + formatDateForDisplay(startDate);
    }
    
    private String formatDateForDisplay(Date date) {
        assert date != null;
        
        return dateFormatter.format(date);
    }

    private boolean checkIfStartDatePresent() {
        assert task != null;

        return task.getStartDate().isPresent();
    }
    
    private String prepareEndDateToDisplay() {
        assert task.getEndDate().isPresent();

        Date endDate = task.getEndDate().get();
        return "End: " + formatDateForDisplay(endDate);
    }

    private boolean checkIfEndDatePresent() {
        assert task != null;
        
        return task.getEndDate().isPresent();
    }

    private String prepareRecurrenceRateToDisplay() {
        String recurrenceRateText = "";
        Integer recurrenceRateInteger = task.getRecurrenceRate().get().rate;
        TimePeriod timePeriod = task.getRecurrenceRate().get().timePeriod;
        boolean hasRecurrenceRateInt = checkIfHasRecurrenceRateInt(recurrenceRateInteger);
        
        if (hasRecurrenceRateInt) {
            recurrenceRateText = prepareRecurrenceRateWithInt(recurrenceRateInteger, timePeriod);
        } else {
            recurrenceRateText = prepareRecurrenceRateWithoutInt(timePeriod);
        }
        
        return recurrenceRateText;
    }

    private String prepareRecurrenceRateWithoutInt(TimePeriod timePeriod) {
        return "every " + timePeriod.toString().toLowerCase();
    }

    private String prepareRecurrenceRateWithInt(Integer recurrenceRateInteger, TimePeriod timePeriod) {
        return "every " 
                + (recurrenceRateInteger == 1 ? "" : recurrenceRateInteger.toString() + " ")
                + timePeriod.toString().toLowerCase() 
                + (recurrenceRateInteger.intValue() > 1 ? "s" : "");
    }

    private boolean checkIfHasRecurrenceRateInt(Integer recurrenceRateInteger) {
        return recurrenceRateInteger != null;
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
