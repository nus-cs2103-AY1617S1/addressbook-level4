package seedu.malitio.ui;

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
import seedu.malitio.commons.core.LogsCenter;
import seedu.malitio.commons.events.ui.DeadlinePanelSelectionChangedEvent;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;

import java.util.logging.Logger;

/**
 * Panel containing the list of deadlines.
 */

public class DeadlineListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(DeadlineListPanel.class);
    private static final String FXML = "DeadlineListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyDeadline> deadlineListView;

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

    public static DeadlineListPanel load(Stage primaryStage, AnchorPane deadlineListPanelPlaceholder,
                                       ObservableList<ReadOnlyDeadline> deadlineList) {
        DeadlineListPanel deadlineListPanel =
                UiPartLoader.loadUiPart(primaryStage, deadlineListPanelPlaceholder, new DeadlineListPanel());
        deadlineListPanel.configure(deadlineList);
        return deadlineListPanel;
    }

    private void configure(ObservableList<ReadOnlyDeadline> deadlineList) {
        setConnections(deadlineList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyDeadline> deadlineList) {
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
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new DeadlinePanelSelectionChangedEvent(newValue));
            }
        });
    }
        
    public void scrollTo(int index) {
        Platform.runLater(() -> {
            deadlineListView.scrollTo(index);
            deadlineListView.getSelectionModel().clearAndSelect(index);
        });
    }
    
    public ListView<ReadOnlyDeadline> getDeadlineListView() {
        return deadlineListView;
    }

    class DeadlineListViewCell extends ListCell<ReadOnlyDeadline> {

        public DeadlineListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyDeadline deadline, boolean empty) {
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
