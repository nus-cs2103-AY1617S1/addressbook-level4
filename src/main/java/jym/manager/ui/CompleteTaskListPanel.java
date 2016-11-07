package jym.manager.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jym.manager.commons.core.LogsCenter;
import jym.manager.commons.events.ui.TaskPanelSelectionChangedEvent;
import jym.manager.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * Panel containing the list of completed tasks.
 */
//@@author a0153617e
public class CompleteTaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CompleteTaskListPanel.class);
    private static final String FXML = "CompleteTaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> completeTaskListView;

    public CompleteTaskListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static CompleteTaskListPanel load(Stage primaryStage, AnchorPane completeTaskListPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {

        CompleteTaskListPanel completeTaskListPanel =
                UiPartLoader.loadUiPart(primaryStage, completeTaskListPlaceholder, new CompleteTaskListPanel());
        completeTaskListPanel.configure(taskList);
        return completeTaskListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        completeTaskListView.setItems(taskList);
        completeTaskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        completeTaskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            completeTaskListView.scrollTo(index);
            completeTaskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        public TaskListViewCell() {
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
