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

    // @@author A0093960X
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

    //@@author
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

    //@@author A0093960X
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

        Paint taskPriorityColour = getPaintForPriority(taskPriority);

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

        String startDateText = getStartDateTextIfPresent();

        startDate.setText(startDateText);
    }

    /**
     * Get the appropriate start date text if the start date of the task is
     * present.
     * 
     * @return The appropriate start date text
     */
    private String getStartDateTextIfPresent() {
        boolean hasStartDate = task.getStartDate().isPresent();
        if (hasStartDate) {
            return prepareStartDateToDisplay();
        }
        return STRING_EMPTY;
    }

    /**
     * Sets the task card end date to the end date of the task it is displaying.
     */
    private void setTaskCardEndDate() {
        assert task != null;

        String endDateText = getEndDateTextIfPresent();
        endDate.setText(endDateText);
    }

    /**
     * Get the appropriate end date text if the end date of the task is present.
     * 
     * @return The appropriate end date text
     */
    private String getEndDateTextIfPresent() {
        boolean hasEndDate = task.getEndDate().isPresent();

        if (hasEndDate) {
            return prepareEndDateToDisplay();
        }
        return STRING_EMPTY;
    }

    /**
     * Sets the task card recurrence rate to the recurrence rate of the task it
     * is displaying.
     */
    private void setTaskCardRecurrence() {
        assert task != null;

        String recurrenceRateText = getRecurrenceTextIfPresent();

        recurrenceRate.setText(recurrenceRateText);
    }

    /**
     * Get the appropriate recurrence rate text if the recurrence rate of the
     * task is present.
     * 
     * @return The appropriate recurrence rate text
     */
    private String getRecurrenceTextIfPresent() {
        String recurrenceRateText = STRING_EMPTY;
        boolean taskIsRecurring = task.getRecurrenceRate().isPresent();

        if (taskIsRecurring) {
            recurrenceRateText = prepareRecurrenceRateToDisplay();
        }
        return recurrenceRateText;
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
