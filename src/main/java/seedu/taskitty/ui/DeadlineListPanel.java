package seedu.taskitty.ui;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import seedu.taskitty.model.task.ReadOnlyTask;

/**
 * Panel containing the list of deadline tasks.
 */
public class DeadlineListPanel extends TaskListPanel {
    private static final String FXML = "DeadlineListPanel.fxml";
    
    @FXML
    private Label header;
    
    @FXML
    private ListView<ReadOnlyTask> deadlineListView;
    
    public DeadlineListPanel() {
        super();
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }

    public void configure(ObservableList<ReadOnlyTask> deadlineList) {
    	header.setText("DEADLINES [d]");
    	header.setStyle("-fx-text-fill: white");
        setConnections(deadlineListView, deadlineList);
        addToPlaceholder();
    }

    // since we have no more select command this becomes useless... right?
//    public void scrollTo(int index) {
//        Platform.runLater(() -> {
//            deadlineListView.scrollTo(index);
//            deadlineListView.getSelectionModel().clearAndSelect(index);
//        });
//    }
    
}
