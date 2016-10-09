package seedu.todo.ui.components;

import java.util.ArrayList;

public class TagList extends Component {
	
	private static final String FXML_PATH = "components/TagList.fxml";
	
	// Props
	public ArrayList<Object> tags; // stub

	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}

}
