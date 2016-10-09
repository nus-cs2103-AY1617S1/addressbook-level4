package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import seedu.todo.commons.util.FxViewUtil;

public class Header extends Component {
	
	private static final String FXML_PATH = "components/Header.fxml";
	
	// Props
	public String versionString;
	
	// FXML
	@FXML
	private Text headerVersionText;

	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
	@Override
	public void componentDidMount() {
		// Makes the Component full width wrt parent container.
		FxViewUtil.makeFullWidth(this.mainNode);
		
		headerVersionText.setText("version " + versionString);
	}

}
