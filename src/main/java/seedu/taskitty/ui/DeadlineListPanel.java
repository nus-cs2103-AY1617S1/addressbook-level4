package seedu.taskitty.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import seedu.taskitty.model.task.ReadOnlyTask;

//@@author A0130853L
/**
 * Panel containing the list of deadline tasks.
 */
public class DeadlineListPanel extends TaskListPanel {
    private static final String FXML = "DeadlineListPanel.fxml";
    
    @FXML
    private Label header;
    
    @FXML
    private ListView<ReadOnlyTask> deadlineListView;
    
    public static final int DEADLINE_CARD_ID = 1;
    
    public DeadlineListPanel() {
        super();
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    @Override
    public int getTaskCardID() {
        return DEADLINE_CARD_ID;
    }
    
    @Override
    public void configure(ObservableList<ReadOnlyTask> deadlineList) {
    	header.setText("DEADLINES [d]");
    	header.setStyle("-fx-text-fill: white");
        setConnections(deadlineListView, deadlineList);
        addToPlaceholder();
    }
    
}
