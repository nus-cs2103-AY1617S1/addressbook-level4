package seedu.todo.ui.views;

import javafx.stage.Stage;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.UiPartLoader;

public abstract class View extends UiPart {

    private String FXML_PATH;

	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}
	
	public void render(Stage primaryStage) {
		UiPartLoader.loadUiPart(primaryStage, this);
	}
	
}
