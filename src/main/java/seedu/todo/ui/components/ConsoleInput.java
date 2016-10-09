package seedu.todo.ui.components;

import javafx.fxml.FXML;
import seedu.todo.commons.util.FxViewUtil;

public class ConsoleInput extends Component {

	private static final String FXML_PATH = "components/ConsoleInput.fxml";
	
	// Props
	public String lastCommandEntered;

	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
	@Override
	public void componentDidMount() {
		// Makes ConsoleInput full width wrt parent container.
		FxViewUtil.makeFullWidth(this.mainNode);
	}

	/** ================ ACTION HANDLERS ================== **/
	@FXML
	public void handleConsoleInputChanged() {
		// TODO
	}
}
