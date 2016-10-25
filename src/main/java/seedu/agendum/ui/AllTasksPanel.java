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
 * Panel contains the list of all tasks
 */
public class AllTasksPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(AllTasksPanel.class);
    private static final String FXML = "AllTasksPanel.fxml";
    private AnchorPane panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> allTasksListView;

    public AllTasksPanel() {
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

    public static AllTasksPanel load(Stage primaryStage, AnchorPane AllTasksPlaceholder,
            ObservableList<ReadOnlyTask> taskList) {
        AllTasksPanel allTasksPanel = UiPartLoader.loadUiPart(primaryStage, AllTasksPlaceholder, new AllTasksPanel());
        allTasksPanel.configure(taskList);
        return allTasksPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> allTasks) {
        setConnections(allTasks);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> allTasks) {
        allTasksListView.setItems(allTasks);
        allTasksListView.setCellFactory(listView -> new allTasksListViewCell());
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            allTasksListView.scrollTo(index);
            allTasksListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class allTasksListViewCell extends ListCell<ReadOnlyTask> {

        public allTasksListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TaskCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }

}
