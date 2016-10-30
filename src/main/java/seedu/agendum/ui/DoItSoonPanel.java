package seedu.agendum.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.agendum.model.task.ReadOnlyTask;
import seedu.agendum.commons.core.LogsCenter;

//@@author A0148031R
/**
 * Panel contains the list of all uncompleted tasks with time
 */
public class DoItSoonPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(DoItSoonPanel.class);
    private static final String FXML = "DoItSoonPanel.fxml";
    private AnchorPane panel;
    private AnchorPane placeHolderPane;
    private static ObservableList<ReadOnlyTask> mainTaskList;

    @FXML
    private ListView<ReadOnlyTask> timeTasksListView;

    public DoItSoonPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static DoItSoonPanel load(Stage primaryStage, AnchorPane AllTasksPlaceholder,
            ObservableList<ReadOnlyTask> taskList) {
        mainTaskList = taskList;
        DoItSoonPanel allTasksPanel = UiPartLoader.loadUiPart(primaryStage, AllTasksPlaceholder, new DoItSoonPanel());
        allTasksPanel.configure(taskList);
        return allTasksPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> allTasks) {
        setConnections(allTasks);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> allTasks) {
        timeTasksListView.setItems(allTasks.filtered(task -> task.hasTime() && !task.isCompleted()));
        timeTasksListView.setCellFactory(listView -> new allTasksListViewCell());
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            timeTasksListView.scrollTo(index);
            timeTasksListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class allTasksListViewCell extends ListCell<ReadOnlyTask> {

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
