package seedu.todo.ui.components;

import java.util.ArrayList;

public class TaskList extends Component {

	private static final String FXML_PATH = "components/TaskList.fxml";
	
	// Props
	public ArrayList<Object> tasks; // stub

	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
}
