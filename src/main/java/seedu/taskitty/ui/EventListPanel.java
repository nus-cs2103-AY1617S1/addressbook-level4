package seedu.taskitty.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import seedu.taskitty.commons.util.DateUtil;
import seedu.taskitty.model.task.ReadOnlyTask;


/**
 * Panel containing the list of event tasks.
 */
public class EventListPanel extends TaskListPanel {
    private static final String FXML = "EventListPanel.fxml";
   
    
    @FXML
    private Label header;
    
    @FXML
    private ListView<ReadOnlyTask> eventListView;
    
    public static final int EVENT_CARD_ID = 2;
    
    public EventListPanel() {
        super();
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    @Override
    public int getTaskCardID() {
        return EVENT_CARD_ID;
    }
    //@@author A0130853L
    @Override
    public void configure(ObservableList<ReadOnlyTask> eventList) {
    	header.setText("EVENTS [e]");
    	header.setStyle("-fx-text-fill: white");
        setConnections(eventListView, eventList);
        addToPlaceholder();
    }
    //@@author
    
}
