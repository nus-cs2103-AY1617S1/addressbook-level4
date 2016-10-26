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
import seedu.malitio.commons.events.ui.EventPanelSelectionChangedEvent;
import seedu.malitio.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.malitio.model.task.ReadOnlyEvent;

import java.util.logging.Logger;

/**
 * Panel containing the list of deadlines.
 */


public class EventListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);
    private static final String FXML = "EventListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyEvent> eventListView;

    public EventListPanel() {
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

    public static EventListPanel load(Stage primaryStage, AnchorPane eventListPanelPlaceholder,
                                       ObservableList<ReadOnlyEvent> eventList) {
        EventListPanel eventListPanel =
                UiPartLoader.loadUiPart(primaryStage, eventListPanelPlaceholder, new EventListPanel());
        eventListPanel.configure(eventList);
        return eventListPanel;
    }

    private void configure(ObservableList<ReadOnlyEvent> eventList) {
        setConnections(eventList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyEvent> eventList) {
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new EventPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    public ListView<ReadOnlyEvent> getEventListView() {
        return eventListView;
    }
    
    class EventListViewCell extends ListCell<ReadOnlyEvent> {

        public EventListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyEvent event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(EventCard.load(event, getIndex() + 1).getLayout());
            }
        }
    }

}
