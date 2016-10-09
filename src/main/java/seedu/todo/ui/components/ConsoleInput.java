package seedu.todo.ui.components;

import javafx.fxml.FXML;

public class ConsoleInput extends Component {

	private static final String FXML_PATH = "components/ConsoleInput.fxml";
	
	// Props
	public String lastCommandEntered;

	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}

	/** ================ ACTION HANDLERS ================== **/
	@FXML
	public void handleConsoleInputChanged() {
		// TODO
	}
}
