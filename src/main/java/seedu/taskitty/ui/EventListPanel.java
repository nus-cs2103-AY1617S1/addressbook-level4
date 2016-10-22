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

public class EventListPanel extends TaskListPanel {
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);
    private static final String FXML = "EventListPanel.fxml";
    
    @FXML
    private Label date;
    
    @FXML
    private Label header;
    
    @FXML
    private ListView<ReadOnlyTask> eventListView;
    
    public EventListPanel() {
        super();
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }

    protected void configure(ObservableList<ReadOnlyTask> eventList) {
    	header.setText("EVENTS [e]");
    	header.setStyle("-fx-text-fill: white");
    	setDate();
        setConnections(eventListView, eventList);
        addToPlaceholder();
    }
    
    private void setDate() {
    	DateFormat df = new SimpleDateFormat("dd MMM yyyy");
    	Date dateobj = new Date();
    	date.setText(df.format(dateobj) + " (Today)");
    	date.setStyle("-fx-text-fill: black");
    	date.setStyle("-fx-background-color: white");
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
           eventListView.getSelectionModel().clearAndSelect(index);
        });
    }
}
