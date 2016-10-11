package seedu.todo.ui.components;

import seedu.todo.commons.util.FxViewUtil;

public class Console extends Component {
	
	private static final String FXML_PATH = "components/Console.fxml";
	
	// Props
	public String consoleText = "";

	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
	@Override
	public void componentDidMount() {
		// Makes Console full width wrt parent container.
		FxViewUtil.makeFullWidth(this.mainNode);
	}

}
