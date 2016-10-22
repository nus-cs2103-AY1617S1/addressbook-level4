package seedu.taskitty.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.taskitty.commons.core.LogsCenter;
import seedu.taskitty.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.taskitty.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */
public class TodoListPanel extends TaskListPanel {
    private final Logger logger = LogsCenter.getLogger(TodoListPanel.class);
    private static final String FXML = "TodoListPanel.fxml";

    @FXML
    private Label header;
    @FXML
    private ListView<ReadOnlyTask> todoListView;

    public TodoListPanel() {
        super();
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    protected void configure(ObservableList<ReadOnlyTask> taskList) {
    	header.setText("TODOS [t]");
    	header.setStyle("-fx-text-fill: white");
        setConnections(todoListView, taskList);
        addToPlaceholder();
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            todoListView.scrollTo(index);
            todoListView.getSelectionModel().clearAndSelect(index);
        });
    }

}
