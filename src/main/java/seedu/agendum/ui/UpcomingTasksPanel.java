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
 * Panel contains the list of all uncompleted tasks with time
 */
public class UpcomingTasksPanel extends TasksPanel {
    private static final String FXML = "UpcomingTasksPanel.fxml";
    private static ObservableList<ReadOnlyTask> mainTaskList;
    private MultipleSelectionModel<ReadOnlyTask> selectionModel;

    @FXML
    private ListView<ReadOnlyTask> upcomingTasksListView;

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    protected void setConnections(ObservableList<ReadOnlyTask> taskList) {
        mainTaskList = taskList;
        upcomingTasksListView.setItems(taskList.filtered(task -> task.hasTime() && !task.isCompleted()));
        upcomingTasksListView.setCellFactory(listView -> new upcomingTasksListViewCell());
        configure();
    }
    
    private void configure() {
        selectionModel = upcomingTasksListView.getSelectionModel();
        upcomingTasksListView.setSelectionModel(null);
        upcomingTasksListView.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                event.consume();
            }
        });
    }

    public void scrollTo(Task task, boolean hasMultipleTasks) {
        Platform.runLater(() -> {
            
            int index = mainTaskList.indexOf(task);
            upcomingTasksListView.scrollTo(index);
            upcomingTasksListView.setSelectionModel(selectionModel);
            
            if(hasMultipleTasks) {
                upcomingTasksListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                upcomingTasksListView.getSelectionModel().select(index);
            } else {
                upcomingTasksListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                upcomingTasksListView.getSelectionModel().clearAndSelect(index);
            }
            
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(event -> upcomingTasksListView.getSelectionModel().clearSelection(index));
            delay.play();
        });
    }

    class upcomingTasksListViewCell extends ListCell<ReadOnlyTask> {

        public upcomingTasksListViewCell() {
            prefWidthProperty().bind(upcomingTasksListView.widthProperty());
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
