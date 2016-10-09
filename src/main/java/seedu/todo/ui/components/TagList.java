package seedu.todo.ui.components;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class TagList extends Component {
	
	private static final String FXML_PATH = "components/TagList.fxml";
	
	// Props
	public ArrayList<Object> tags; // stub
	
	// FXML
	@FXML
	private Text titleText;

	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
	@Override
	public void componentDidMount() {
		titleText.setText("Tags (" + tags.size() + ")");
	}

}
