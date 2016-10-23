package seedu.taskitty.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import seedu.taskitty.model.task.ReadOnlyTask;

/**
 * Panel containing the list of event tasks.
 */
public class EventListPanel extends TaskListPanel {
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

    public void configure(ObservableList<ReadOnlyTask> eventList) {
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

    // since we have no more select command this becomes useless... right?
//    public void scrollTo(int index) {
//        Platform.runLater(() -> {
//            eventListView.scrollTo(index);
//            eventListView.getSelectionModel().clearAndSelect(index);
//        });
//    }
    
}
