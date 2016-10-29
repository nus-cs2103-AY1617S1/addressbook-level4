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
 * Panel contains the list of other tasks
 */
public class OtherTasksPanel extends UiPart {
    private static final String FXML = "OtherTasksPanel.fxml";
    private AnchorPane panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> otherTasksListView;

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

    public static OtherTasksPanel load(Stage primaryStage, AnchorPane OtherTasksPlaceholder,
            ObservableList<ReadOnlyTask> taskList) {
        OtherTasksPanel otherTasksPanel = UiPartLoader.loadUiPart(primaryStage, OtherTasksPlaceholder, new OtherTasksPanel());
        otherTasksPanel.configure(taskList);
        return otherTasksPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList.filtered(task -> !task.isCompleted()));
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> otherTasks) {
        otherTasksListView.setItems(otherTasks);
        otherTasksListView.setCellFactory(listView -> new otherTasksListViewCell());
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            otherTasksListView.scrollTo(index);
            otherTasksListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class otherTasksListViewCell extends ListCell<ReadOnlyTask> {

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
