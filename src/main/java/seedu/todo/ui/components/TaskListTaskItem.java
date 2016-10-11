package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import seedu.todo.models.Task;

public class TaskListTaskItem extends MultiComponent {
	
	private static final String FXML_PATH = "components/TaskListTaskItem.fxml";

	// Props
	public Task task;
	public int displayIndex;
	
	// FXML
	@FXML
	private Text taskText;
	
	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
	@Override
	public void componentDidMount() {
		taskText.setText(displayIndex + ". " + task.getName());
	}

}
