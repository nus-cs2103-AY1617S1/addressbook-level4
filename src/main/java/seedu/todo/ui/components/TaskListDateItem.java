package seedu.todo.ui.components;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.todo.commons.util.DateUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.ui.views.IndexView.TaskStub;

public class TaskListDateItem extends MultiComponent {

	private static final String FXML_PATH = "components/TaskListDateItem.fxml";
	
	// Props
	public LocalDateTime dateTime;
	public ArrayList<TaskStub> tasks;
	
	// FXML
	@FXML
	private Text dateText;
	@FXML
	private VBox dateTaskItemsPlaceholder;
	
	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
	@Override
	public void componentDidMount() {
		// Set header for DateItem
		String dateHeader = String.format("%s (%d %s)", DateUtil.formatDay(dateTime), tasks.size(), 
										  StringUtil.pluralizer(tasks.size(), "task", "tasks"));
		dateText.setText(dateHeader);
		
		// Load task items
		loadTaskItems();
	}
	
	private void loadTaskItems() {
		TaskListTaskItem.reset(dateTaskItemsPlaceholder);
		
		for (TaskStub task : tasks) {
			TaskListTaskItem item = new TaskListTaskItem();
			
			item.passInProps(c -> {
				TaskListTaskItem view = (TaskListTaskItem) c;
				view.task = task;
				return view;
			});
			
			item.render(primaryStage, dateTaskItemsPlaceholder);
		}
	}

}
