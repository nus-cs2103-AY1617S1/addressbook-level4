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
public class TodayListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TodayListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> todayListView;

    public TodayListPanel() {
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

    public static TodayListPanel load(Stage primaryStage, AnchorPane todayListPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
        TodayListPanel todayListPanel =
                UiPartLoader.loadUiPart(primaryStage, todayListPlaceholder, new TodayListPanel());
        todayListPanel.configure(taskList);
        return todayListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> todayList) {
        setConnections(todayList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> todayList) {
        todayListView.setItems(todayList);
        todayListView.setCellFactory(listView -> new TodayListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        todayListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            todayListView.scrollTo(index);
            todayListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TodayListViewCell extends ListCell<ReadOnlyTask> {

        public TodayListViewCell() {
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