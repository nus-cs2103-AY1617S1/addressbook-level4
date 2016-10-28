package seedu.address.ui;

import java.util.logging.Logger;

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
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyTask;

public class DeadlineListPanel extends UiPart {

    private final Logger logger = LogsCenter.getLogger(DeadlineListPanel.class);
    private static final String FXML = "DeadlineListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> deadlineListView;

    public DeadlineListPanel() {
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

    public static DeadlineListPanel load(Stage primaryStage, AnchorPane deadlineListPlaceholder,
                                       ObservableList<ReadOnlyTask> deadlineList) {
        DeadlineListPanel deadlineListPanel =
                UiPartLoader.loadUiPart(primaryStage, deadlineListPlaceholder, new DeadlineListPanel());
        deadlineListPanel.configure(deadlineList);
        return deadlineListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> deadlineList) {
        setConnections(deadlineList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> deadlineList) {
        deadlineListView.setItems(deadlineList);
        deadlineListView.setCellFactory(listView -> new DeadlineListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        deadlineListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in deadline list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            deadlineListView.scrollTo(index);
            deadlineListView.getSelectionModel().clearAndSelect(index);
        });
    }
    
    public void clear() {
        Platform.runLater(() -> {
            deadlineListView.getSelectionModel().clearSelection();
        });
    }


    class DeadlineListViewCell extends ListCell<ReadOnlyTask> {

        public DeadlineListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask deadline, boolean empty) {
            super.updateItem(deadline, empty);

            if (empty || deadline == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(DeadlineCard.load(deadline, getIndex() + 1).getLayout());
            }
        }
    }

}
