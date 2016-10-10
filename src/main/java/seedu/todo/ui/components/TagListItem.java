package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class TagListItem extends MultiComponent {

	private static final String FXML_PATH = "components/TagListItem.fxml";
	
	// Props
	public String tag;
	
	// FXML
	@FXML
	private Text labelText;
	
	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
	@Override
	public void componentDidMount() {
		labelText.setText(tag);
	}

}
