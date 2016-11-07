package tars.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tars.commons.core.LogsCenter;
import tars.commons.events.ui.RsvTaskAddedEvent;
import tars.model.task.rsv.RsvTask;

// @@author A0121533W
/**
 * UI Controller for panel containing the list of reserved tasks.
 */
public class RsvTaskListPanel extends UiPart {
    private static String LOG_MESSAGE_LAYOUT_UPDATING =
            "Updating layout for %s";
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String FXML = "RsvTaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    private static final int START_INDEX = 1;

    @FXML
    private ListView<RsvTask> rsvTaskListView;

    public RsvTaskListPanel() {
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

    public static RsvTaskListPanel load(Stage primaryStage,
            AnchorPane rsvTaskListPlaceholder,
            ObservableList<RsvTask> rsvTaskList) {
        RsvTaskListPanel rsvTaskListPanel = UiPartLoader.loadUiPart(
                primaryStage, rsvTaskListPlaceholder, new RsvTaskListPanel());
        rsvTaskListPanel.configure(rsvTaskList);
        return rsvTaskListPanel;
    }

    private void configure(ObservableList<RsvTask> rsvTaskList) {
        setConnections(rsvTaskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<RsvTask> rsvTaskList) {
        rsvTaskListView.setItems(rsvTaskList);
        rsvTaskListView.setCellFactory(listView -> new RsvTaskListViewCell());
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            rsvTaskListView.scrollTo(index);
        });
    }

    class RsvTaskListViewCell extends ListCell<RsvTask> {
        private RsvTask newlyAddedRsvTask;

        public RsvTaskListViewCell() {
            registerAsAnEventHandler(this);
        }

        @Override
        protected void updateItem(RsvTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                RsvTaskCard card =
                        RsvTaskCard.load(task, getIndex() + START_INDEX);
                HBox layout = card.getLayout();
                if (this.newlyAddedRsvTask != null
                        && this.newlyAddedRsvTask.isSameStateAs(task)) {
                    layout.setStyle(UiColor.TASK_CARD_NEWLY_ADDED_BORDER);
                } else {
                    layout.setStyle(UiColor.TASK_CARD_DEFAULT_BORDER);
                }
                setGraphic(layout);
            }
        }

        @Subscribe
        private void handleRsvTaskAddedEvent(RsvTaskAddedEvent event) {
            logger.info(LogsCenter.getEventHandlingLogMessage(event,
                    String.format(LOG_MESSAGE_LAYOUT_UPDATING,
                            event.task.toString())));
            this.newlyAddedRsvTask = event.task;
        }
    }

}
