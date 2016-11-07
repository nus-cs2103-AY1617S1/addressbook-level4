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

//@@author A0093960X
public class TaskCard extends UiPart {

    private static final Paint PAINT_RED = Paint.valueOf("red");
    private static final Paint PAINT_GREEN = Paint.valueOf("green");
    private static final Paint PAINT_YELLOW = Paint.valueOf("yellow");

    private static final String END_DATE_DISPLAY_PREFIX = "End: ";
    private static final String START_DATE_DISPLAY_PREFIX = "Start: ";
    private static final String STRING_EMPTY = "";

    private static final String FXML = "TaskListCard.fxml";
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");

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

    private ReadOnlyTask task;
    private int displayedIndex;

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

        setTaskCardIndex();
        setTaskCardName();
        setTaskCardPriority();
        setTaskCardStartDate();
        setTaskCardEndDate();
        setTaskCardRecurrence();
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    // private helper methods below

    /**
     * Sets the task card displayed index to its position in the list.
     */
    private void setTaskCardIndex() {
        assert displayedIndex > 0;

        String taskCardId = displayedIndex + ".";
        id.setText(taskCardId);
    }

    /**
     * Sets the task card name to the name of the task of the task it is
     * displaying.
     */
    private void setTaskCardName() {
        assert task != null && task.getName() != null;

        String taskName = task.getName().toString();
        name.setText(taskName);
    }

    /**
     * Sets the task card priority rectangle to the appropriate color depending
     * on the task priority.
     */
    private void setTaskCardPriority() {
        assert task != null && task.getPriorityValue() != null;

        Priority taskPriority = task.getPriorityValue();

        Paint taskPriorityColour = PAINT_YELLOW;

        taskPriorityColour = getPaintForPriority(taskPriority);

        priority.setFill(taskPriorityColour);
    }

    /**
     * Gets the appropriate Paint given the Priority.
     * 
     * @param priority The priority to retrieve the Paint for
     * @return The Paint for the specified priority
     */
    private Paint getPaintForPriority(Priority priority) {
        switch (priority) {
        case LOW :
            return PAINT_GREEN;
        case MEDIUM :
            return PAINT_YELLOW;
        case HIGH :
            return PAINT_RED;
        default :
            assert false : "priority should only be LOW, MEDIUM, or HIGH";
            return PAINT_YELLOW;
        }
    }

    /**
     * Sets the task card start date to the start date of the task it is
     * displaying.
     */
    private void setTaskCardStartDate() {
        assert task != null;

        String startDateText = STRING_EMPTY;
        boolean hasStartDate = task.getStartDate().isPresent();

        if (hasStartDate) {
            startDateText = prepareStartDateToDisplay();
        }

        startDate.setText(startDateText);
    }

    /**
     * Sets the task card end date to the end date of the task it is displaying.
     */
    private void setTaskCardEndDate() {
        assert task != null;

        String endDateText = STRING_EMPTY;
        boolean hasEndDate = task.getEndDate().isPresent();

        if (hasEndDate) {
            endDateText = prepareEndDateToDisplay();
        }
        endDate.setText(endDateText);
    }

    /**
     * Sets the task card recurrence rate to the recurrence rate of the task it
     * is displaying.
     */
    private void setTaskCardRecurrence() {
        assert task != null;

        String recurrenceRateText = STRING_EMPTY;
        boolean taskIsRecurring = task.getRecurrenceRate().isPresent();

        if (taskIsRecurring) {
            recurrenceRateText = prepareRecurrenceRateToDisplay();
        }

        recurrenceRate.setText(recurrenceRateText);
    }

    /**
     * Prepares the start date for display by converting it into a pretty format
     * as a String.
     * 
     * @return The String representation of the start date for display on the
     *         task card
     */
    private String prepareStartDateToDisplay() {
        assert task != null && task.getStartDate().isPresent();

        Date startDate = task.getStartDate().get();
        return START_DATE_DISPLAY_PREFIX + dateFormatter.format(startDate);
    }

    /**
     * Prepares the end date for display by converting it into a pretty format
     * as a String.
     * 
     * @return The String representation of the end date for display on the task
     *         card
     */
    private String prepareEndDateToDisplay() {
        assert task != null && task.getEndDate().isPresent();

        Date endDate = task.getEndDate().get();
        return END_DATE_DISPLAY_PREFIX + dateFormatter.format(endDate);
    }

    /**
     * Prepares the recurrence rate for display by converting it into a pretty
     * format as a String.
     * 
     * @return The String representation of the recurrence rate for display on
     *         the task card
     */
    private String prepareRecurrenceRateToDisplay() {
        assert task != null && task.getRecurrenceRate().isPresent();

        String recurrenceRateText = task.getRecurrenceRate().get().toString();
        return recurrenceRateText;
    }

}
