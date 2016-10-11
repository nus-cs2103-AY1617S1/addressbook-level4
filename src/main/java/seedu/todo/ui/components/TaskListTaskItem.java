package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.todo.models.Task;
import seedu.todo.ui.UiPartLoader;

public class TaskListTaskItem extends MultiComponent {

    private static final String FXML_PATH = "components/TaskListTaskItem.fxml";

    // Props
    public Task task;
    public int displayIndex;

    // FXML
    @FXML
    private Text taskText;

    public static TaskListTaskItem load(Stage primaryStage, Pane placeholderPane) {
        return UiPartLoader.loadUiPart(primaryStage, placeholderPane, new TaskListTaskItem());
    }

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        taskText.setText(displayIndex + ". " + task.getName());
    }

}
