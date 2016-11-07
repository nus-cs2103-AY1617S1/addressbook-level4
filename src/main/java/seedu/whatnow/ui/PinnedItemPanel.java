package seedu.whatnow.ui;

//@@author A0139772U
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
public class PinnedItemPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "PinnedItemPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> pinnedListView;

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

    public static PinnedItemPanel load(Stage primaryStage, AnchorPane pinnedListPlaceholder,
            ObservableList<ReadOnlyTask> taskList) {
        PinnedItemPanel pinnedItemPanel = UiPartLoader.loadUiPart(primaryStage, pinnedListPlaceholder,
                new PinnedItemPanel());
        pinnedItemPanel.configure(taskList);
        return pinnedItemPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> pinnedList) {
        setConnections(pinnedList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> pinnedList) {
        pinnedListView.setItems(pinnedList);
        pinnedListView.setCellFactory(listView -> new PinnedItemListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        pinnedListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in pinned item list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    /** Clears the pinned item list view of any selection */
    public void clear() {
        pinnedListView.getSelectionModel().clearSelection();
    }

    class PinnedItemListViewCell extends ListCell<ReadOnlyTask> {

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