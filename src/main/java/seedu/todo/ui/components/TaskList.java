package seedu.todo.ui.components;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todo.commons.EphemeralDB;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.models.Task;
import seedu.todo.ui.UiPartLoader;

public class TaskList extends Component {

    private static final String FXML_PATH = "components/TaskList.fxml";
    private static EphemeralDB ephemeralDb = EphemeralDB.getInstance();

    // Props
    public List<Task> tasks; // stub

    // FXML
    @FXML
    private VBox taskListDateItemsPlaceholder;

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        loadTasks();
    }

    public static TaskList load(Stage primaryStage, Pane placeholderPane) {
        return UiPartLoader.loadUiPart(primaryStage, placeholderPane, new TaskList());
    }

    private void loadTasks() {
        TaskListDateItem.reset(taskListDateItemsPlaceholder);

        // Clears displayedTasks in EphemeralDB.
        ephemeralDb.displayedTasks = new ArrayList<Task>();

        // Get a list of tasks mapped to each date
        HashMap<LocalDateTime, ArrayList<Task>> tasksByDate = getTasksByDate(tasks);

        // Get unique task dates and sort them
        List<LocalDateTime> taskDates = new ArrayList<LocalDateTime>();
        taskDates.addAll(tasksByDate.keySet());
        java.util.Collections.sort(taskDates);

        // For each dateTime, individually render a single TaskListDateItem.
        for (LocalDateTime dateTime : taskDates) {
            TaskListDateItem item = TaskListDateItem.load(primaryStage, taskListDateItemsPlaceholder);
            List<Task> tasksForDate = tasksByDate.get(dateTime);

            item.dateTime = dateTime;
            item.tasks = tasksForDate;            

            // Finally, can render into the placeholder.
            item.render();
        }
    }

    private HashMap<LocalDateTime, ArrayList<Task>> getTasksByDate(List<Task> tasks) {
        HashMap<LocalDateTime, ArrayList<Task>> tasksByDate = new HashMap<LocalDateTime, ArrayList<Task>>();

        for (Task task : tasks) {
            LocalDateTime taskDate = DateUtil.floorDate(task.getCalendarDT());

            // Creates ArrayList if not already exists.
            if (!tasksByDate.containsKey(taskDate)) {
                tasksByDate.put(taskDate, new ArrayList<Task>());
            }
            // Adds to the ArrayList.
            tasksByDate.get(taskDate).add(task);
        }

        return tasksByDate;
    }

}
