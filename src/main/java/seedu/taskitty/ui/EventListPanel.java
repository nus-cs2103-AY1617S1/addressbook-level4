package seedu.taskitty.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.taskitty.commons.core.LogsCenter;
import seedu.taskitty.commons.events.ui.EventPanelSelectionChangedEvent;
import seedu.taskitty.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.taskitty.model.task.ReadOnlyTask;

// Dummy Placeholder for Event List Panel
// TO BE UPDATED

public class EventListPanel extends UiPart{
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);
    private static final String FXML = "EventListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    
    @FXML
    private Label date;
    
    @FXML
    private ListView<ReadOnlyTask> eventListView;
    
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
    
    public static EventListPanel load(Stage primaryStage, AnchorPane eventListPlaceholder,
            ObservableList<ReadOnlyTask> eventList) {
        EventListPanel eventListPanel =
                UiPartLoader.loadUiPart(primaryStage, eventListPlaceholder, new EventListPanel());
        eventListPanel.configure(eventList);
        return eventListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> eventList) {
    	setDate();
        setConnections(eventList);
        addToPlaceholder();
    }
    
    private void setDate() {
    	DateFormat df = new SimpleDateFormat("dd MMM yyyy");
    	Date dateobj = new Date();
    	date.setText(df.format(dateobj) + " (Today)");
    	date.setStyle("-fx-text-fill: white");
    }
    
    private void setConnections(ObservableList<ReadOnlyTask> eventList) {
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
                logger.fine("Selection in event list panel changed to : '" + newValue + "'");
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

    class EventListViewCell extends ListCell<ReadOnlyTask> {

        public EventListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(EventCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }
}
