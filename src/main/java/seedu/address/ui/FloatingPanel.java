package seedu.address.ui;

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
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */
public class FloatingPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(FloatingPanel.class);
    private static final String FXML = "FloatingPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> floatingListView;

    public FloatingPanel() {
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

    public static FloatingPanel load(Stage primaryStage, AnchorPane floatingPanelPlaceholder,
                                       ObservableList<ReadOnlyTask> floatingList) {
        FloatingPanel floatingPanel =
                UiPartLoader.loadUiPart(primaryStage, floatingPanelPlaceholder, new FloatingPanel());
        floatingPanel.configure(floatingList);
        return floatingPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> floatingList) {
        setConnections(floatingList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> floatingList) {
        floatingListView.setItems(floatingList);
        floatingListView.setCellFactory(listView -> new FloatingListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        floatingListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            floatingListView.scrollTo(index);
            floatingListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class FloatingListViewCell extends ListCell<ReadOnlyTask> {

        public FloatingListViewCell() {
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

