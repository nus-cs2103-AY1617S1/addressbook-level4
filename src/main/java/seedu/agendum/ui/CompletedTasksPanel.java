package seedu.agendum.ui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import seedu.agendum.model.task.ReadOnlyTask;
import seedu.agendum.model.task.Task;

//@@author A0148031R
/**
 * Panel contains the list of completed tasks
 */
public class CompletedTasksPanel extends TasksPanel {
    private static final String FXML = "CompletedTasksPanel.fxml";
    private static ObservableList<ReadOnlyTask> mainTaskList;
    private MultipleSelectionModel<ReadOnlyTask> selectionModel;

    @FXML
    private ListView<ReadOnlyTask> completedTasksListView;

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    protected void setConnections(ObservableList<ReadOnlyTask> taskList) {
        mainTaskList = taskList;
        completedTasksListView.setItems(taskList.filtered(task -> task.isCompleted()));
        completedTasksListView.setCellFactory(listView -> new CompletedTasksListViewCell());
        configure();
    }
    
    private void configure() {
        selectionModel = completedTasksListView.getSelectionModel();
        completedTasksListView.setSelectionModel(null);
        completedTasksListView.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                event.consume();
            }
        });
    }

    public void scrollTo(Task task, boolean hasMultipleTasks) {
        Platform.runLater(() -> {
            
            int index = mainTaskList.indexOf(task) - mainTaskList.filtered(t -> !t.isCompleted()).size();
            completedTasksListView.scrollTo(index);
            completedTasksListView.setSelectionModel(selectionModel);
            
            if(hasMultipleTasks) {
                completedTasksListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                completedTasksListView.getSelectionModel().select(index);
            } else {
                completedTasksListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                completedTasksListView.getSelectionModel().clearAndSelect(index);
            }
            
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(event -> completedTasksListView.getSelectionModel().clearSelection(index));
            delay.play();
        });
    }

    class CompletedTasksListViewCell extends ListCell<ReadOnlyTask> {
        public CompletedTasksListViewCell() {
            prefWidthProperty().bind(completedTasksListView.widthProperty());
            setMaxWidth(Control.USE_PREF_SIZE);
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TaskCard.load(task, mainTaskList.indexOf(task) + 1).getLayout());
            }
        }
    }
}
