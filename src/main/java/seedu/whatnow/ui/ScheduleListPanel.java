package seedu.whatnow.ui;

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
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.whatnow.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */
public class ScheduleListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "ScheduleListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> scheduleListView;

    public ScheduleListPanel() {
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

    public static ScheduleListPanel load(Stage primaryStage, AnchorPane scheduleListPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
        ScheduleListPanel scheduleListPanel =
                UiPartLoader.loadUiPart(primaryStage, scheduleListPlaceholder, new ScheduleListPanel());
        scheduleListPanel.configure(taskList);
        return scheduleListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> scheduleList) {
        setConnections(scheduleList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> scheduleList) {
        scheduleListView.setItems(scheduleList);
        scheduleListView.setCellFactory(listView -> new ScheduleListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        scheduleListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void clear() {
        scheduleListView.getSelectionModel().clearSelection();
    }
    
    public void scrollTo(int index) {
        Platform.runLater(() -> {
            scheduleListView.scrollTo(index);
            scheduleListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class ScheduleListViewCell extends ListCell<ReadOnlyTask> {

        public ScheduleListViewCell() {
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