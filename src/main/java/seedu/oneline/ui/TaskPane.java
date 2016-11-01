package seedu.oneline.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.oneline.commons.core.LogsCenter;
import seedu.oneline.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.oneline.commons.util.FxViewUtil;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.ui.TagListPanel.TagListViewCell;
import seedu.oneline.model.tag.TagColorMap; 

import java.util.logging.Logger;

/**
 * The Task Pane of the App.
 */
public class TaskPane extends UiPart{

    private static Logger logger = LogsCenter.getLogger(TaskPane.class);
    private String FXML = "TaskPane.fxml";
    private TagColorMap colorMap; 
    
    @FXML
    private ListView<ReadOnlyTask> taskListView;

    private VBox panel;
    private AnchorPane placeHolderPane;

    public TaskPane() {
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

    public static TaskPane load(Stage primaryStage, AnchorPane taskPanePlaceholder,
                                       ObservableList<ReadOnlyTask> taskList, TagColorMap colorMap) {
        TaskPane taskPane =
                UiPartLoader.loadUiPart(primaryStage, taskPanePlaceholder, new TaskPane());
        taskPane.configure(taskList, colorMap);
        return taskPane;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList, TagColorMap colorMap) {
        setConnections(taskList);
        this.colorMap = colorMap; 
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
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
                setGraphic(TaskCard.load(task, colorMap.getTagColor(task.getTag()), getIndex() + 1).getLayout());
            }
        }
    }
}
