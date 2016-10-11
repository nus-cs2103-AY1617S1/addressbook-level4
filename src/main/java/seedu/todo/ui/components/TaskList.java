package seedu.todo.ui.components;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import seedu.todo.commons.EphemeralDB;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.models.Task;

public class TaskList extends Component {

	private static final String FXML_PATH = "components/TaskList.fxml";
	private static EphemeralDB ephemeralDb = EphemeralDB.getInstance();
	
	// Props
	public ArrayList<Task> tasks; // stub
	
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
	
	private void loadTasks() {
		TaskListDateItem.reset(taskListDateItemsPlaceholder);

		// Clears displayedTasks in EphemeralDB.
		ephemeralDb.displayedTasks = new ArrayList<Task>();
		
		// Get a list of tasks mapped to each date
		HashMap<LocalDateTime, ArrayList<Task>> tasksByDate = getTasksByDate(tasks);
		
		// Get unique task dates and sort them
		ArrayList<LocalDateTime> taskDates = new ArrayList<LocalDateTime>();
		taskDates.addAll(tasksByDate.keySet());
		java.util.Collections.sort(taskDates);
		
		// For each dateTime, individually the ArrayList of Tasks.
		for (LocalDateTime dateTime : taskDates) {
			TaskListDateItem item = new TaskListDateItem();
			ArrayList<Task> tasksForDate = tasksByDate.get(dateTime);
			
			// Pass in as prop to view.
			item.passInProps(c -> {
				TaskListDateItem view = (TaskListDateItem) c;
				view.dateTime = dateTime;
				view.tasks = tasksForDate;
				return view;
			});
			
			// Finally, can render into the placeholder.
			item.render(primaryStage, taskListDateItemsPlaceholder);
		}
	}
	
	private HashMap<LocalDateTime, ArrayList<Task>> getTasksByDate(ArrayList<Task> tasks) {
		HashMap<LocalDateTime, ArrayList<Task>> tasksByDate = new HashMap<LocalDateTime, ArrayList<Task>>();
		
		for (Task task : tasks) {
			LocalDateTime taskDate = DateUtil.floorDate(task.getCalendarDT());
			
			// Creates ArrayList if not already exists.
			if (!tasksByDate.containsKey(taskDate)) 
				tasksByDate.put(taskDate, new ArrayList<Task>());
			
			// Adds to the ArrayList.
			tasksByDate.get(taskDate).add(task);
		}
		
		return tasksByDate;
	}
	
}
