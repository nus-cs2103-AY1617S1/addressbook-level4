package seedu.agendum.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
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
 * Panel contains the list of completed tasks
 */
public class CompletedTasksPanel extends UiPart {
    private static final String FXML = "CompletedTasksPanel.fxml";
    private AnchorPane panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> completedTasksListView;

    public CompletedTasksPanel() {
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

    public static CompletedTasksPanel load(Stage primaryStage, AnchorPane CompletedTasksPlaceholder,
            ObservableList<ReadOnlyTask> taskList) {
        CompletedTasksPanel completedTasksPanel = UiPartLoader.loadUiPart(primaryStage, CompletedTasksPlaceholder, new CompletedTasksPanel());
        completedTasksPanel.configure(taskList);
        return completedTasksPanel;
    }
    
    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        completedTasksListView.setItems(taskList.filtered(task -> task.isCompleted()));
        completedTasksListView.setCellFactory(listView -> new completedTasksListViewCell());
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            completedTasksListView.scrollTo(index);
            completedTasksListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class completedTasksListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TaskCard.load(task).getLayout());
            }
        }
    }

}
