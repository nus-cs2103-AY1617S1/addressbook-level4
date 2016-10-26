package seedu.todo.ui.components;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.todo.commons.EphemeralDB;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.models.Task;
import seedu.todo.models.Event;

public class TaskListDateItem extends MultiComponent {

    private static final String FXML_PATH = "components/TaskListDateItem.fxml";
    private static EphemeralDB ephemeralDb = EphemeralDB.getInstance();
    private static final String NO_DATE_STRING = "No Deadline";

    // Props
    public LocalDateTime dateTime;
    public List<Task> tasks = new ArrayList<>();
    public List<Event> events = new ArrayList<>();

    // FXML
    @FXML
    private Text dateHeader;
    @FXML
    private Text dateLabel;
    @FXML
    private VBox dateCalendarItemsPlaceholder;

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        
        // Set header for DateItem using the "x days ago" format
        String dateHeaderString;
        if (dateTime == TaskList.NO_DATE_VALUE) {
            dateHeaderString = NO_DATE_STRING;
        } else {
            dateHeaderString = DateUtil.formatDay(dateTime);
        }

        dateHeader.setText(dateHeaderString);
        
        // Set date label using the short date format (e.g. Fri 14 Oct)
        if (dateTime != TaskList.NO_DATE_VALUE) {
            String dateLabelString = DateUtil.formatShortDate(dateTime);
            dateLabel.setText(dateLabelString);
        }

        // Clear the TaskList of its items
        TaskListTaskItem.reset(dateCalendarItemsPlaceholder);

        // Load task and event items
        loadEventItems();
        loadTaskItems();
    }

    private void loadTaskItems() {
        for (Task task : tasks) {
            TaskListTaskItem item = load(primaryStage, dateCalendarItemsPlaceholder, TaskListTaskItem.class);

            // Add to EphemeralDB and get the index.
            int displayIndex = ephemeralDb.addToDisplayedCalendarItems(task);

            // Set the props and render the TaskListTaskItem.
            item.task = task;
            item.displayIndex = displayIndex;
            item.render();
        }
    }
    
    private void loadEventItems() {
        for (Event event : events) {
            TaskListEventItem item = load(primaryStage, dateCalendarItemsPlaceholder, TaskListEventItem.class);

            // Add to EphemeralDB and get the index.
            int displayIndex = ephemeralDb.addToDisplayedCalendarItems(event);

            // Set the props and render the TaskListTaskItem.
            item.event = event;
            item.displayIndex = displayIndex;
            item.render();
        }
    }

}
