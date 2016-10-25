package seedu.taskitty.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import seedu.taskitty.model.task.ReadOnlyTask;

//@@author A0130853L

/**
 * Panel containing the list of todo tasks.
 */
public class TodoListPanel extends TaskListPanel {
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

    public void configure(ObservableList<ReadOnlyTask> taskList) {
    	header.setText("TODOS [t]");
    	header.setStyle("-fx-text-fill: white");
        setConnections(todoListView, taskList);
        addToPlaceholder();
    }

    // since we have no more select command this becomes useless... right?
//    public void scrollTo(int index) {
//        Platform.runLater(() -> {
//            todoListView.scrollTo(index);
//            todoListView.getSelectionModel().clearAndSelect(index);
//        });
//    }

}
