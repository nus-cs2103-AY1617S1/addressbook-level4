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
 * Panel contains the list of uncompleted floating tasks
 */
public class FloatingTasksPanel extends TasksPanel {
    private static final String FXML = "FloatingTasksPanel.fxml";
    private static ObservableList<ReadOnlyTask> mainTaskList;
    private MultipleSelectionModel<ReadOnlyTask> selectionModel;

    @FXML
    private ListView<ReadOnlyTask> floatingTasksListView;

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    protected void setConnections(ObservableList<ReadOnlyTask> taskList) {
        mainTaskList = taskList;
        floatingTasksListView.setItems(taskList.filtered(task -> !task.isCompleted() && !task.hasTime()));
        floatingTasksListView.setCellFactory(listView -> new FloatingTasksListViewCell());
        configure();
    }
    
    private void configure() {
        selectionModel = floatingTasksListView.getSelectionModel();
        floatingTasksListView.setSelectionModel(null);
        floatingTasksListView.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                event.consume();
            }
        });
    }

    public void scrollTo(Task task, boolean hasMultipleTasks) {
        Platform.runLater(() -> {
            
            int index = mainTaskList.indexOf(task) - 
                    mainTaskList.filtered(t -> (t.hasTime() && !t.isCompleted())).size();
            floatingTasksListView.scrollTo(index);
            floatingTasksListView.setSelectionModel(selectionModel);
            
            if(hasMultipleTasks) {
                floatingTasksListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                floatingTasksListView.getSelectionModel().select(index);
            } else {
                floatingTasksListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                floatingTasksListView.getSelectionModel().clearAndSelect(index);
            }
            
            PauseTransition delay = new PauseTransition(Duration.seconds(4));
            delay.setOnFinished(event -> floatingTasksListView.getSelectionModel().clearSelection(index));
            delay.play();
        });
    }

    class FloatingTasksListViewCell extends ListCell<ReadOnlyTask> {
        public FloatingTasksListViewCell() {
            prefWidthProperty().bind(floatingTasksListView.widthProperty());
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
//                scrollTo();
            }
        }
    }
}
