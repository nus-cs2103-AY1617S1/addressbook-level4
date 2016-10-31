package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.control.skin.ListViewSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.NewTaskListEvent;
import seedu.address.model.task.Task;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;


    
    @FXML
    private ListView<Task> taskListView;

    public TaskListPanel() {
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

    public static TaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<Task> taskList) {
        TaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configure(taskList);
        return taskListPanel;
    }

    private void configure(ObservableList<Task> taskList) {
        setConnections(taskList);
        addToPlaceholder();
        setSelectableCharacteristics();
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Task> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(panel);
    }
    
    private void setSelectableCharacteristics() {
    	taskListView.setMouseTransparent(true);		
	}

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
        });
    }
    
    //@@author A0138978E
    public void scrollDown() {
    	Pair<Integer, Integer> firstAndLast = getFirstAndLastVisibleIndices(taskListView);
    	int firstIdx = firstAndLast.getKey();
    	int lastIdx = firstAndLast.getValue();
    	int middleIdx = (firstIdx + lastIdx) / 2;
    	
    	logger.info("First Idx: " + firstIdx + " Last Idx: " + lastIdx + " Middle Idx: " + middleIdx);
    	logger.info("Scrolling to item: " + middleIdx);
    	
    	// Scroll to the middle
    	scrollTo(middleIdx);
    }
    
    public void scrollUp() {
    	Pair<Integer, Integer> firstAndLast = getFirstAndLastVisibleIndices(taskListView);
    	int firstIdx = firstAndLast.getKey();
    	int lastIdx = firstAndLast.getValue();
    	int middleIdx = (firstIdx + lastIdx) / 2;
    	
    	int targetIdx = firstIdx - (middleIdx - firstIdx);
    	
    	logger.info("First Idx: " + firstIdx + " Last Idx: " + lastIdx + " Middle Idx: " + middleIdx);
    	logger.info("Scrolling to item: " + targetIdx);
    	// Scroll to the top plus some
    	scrollTo(targetIdx);
    }
    
    // From http://stackoverflow.com/questions/30457708/visible-items-of-listview
    // Gets approximately the first and last viewable items in the scrollable listview
    private Pair<Integer, Integer> getFirstAndLastVisibleIndices(ListView<?> t) {
        try {
            @SuppressWarnings("restriction")
			ListViewSkin<?> ts = (ListViewSkin<?>) t.getSkin();
            @SuppressWarnings("restriction")
			VirtualFlow<?> vf = (VirtualFlow<?>) ts.getChildren().get(0);
            int first = vf.getFirstVisibleCell().getIndex();
            int last = vf.getLastVisibleCell().getIndex();
            return new Pair<Integer, Integer>(first, last);
        } catch (Exception ex) {
            logger.severe("getFirstAndLast for scrolling: Exception " + ex);
            throw ex;
        }
    }
    
    //@@author
    @Subscribe
    public void handleNewTaskListEvent(NewTaskListEvent abce) {
    	FilteredList<Task> newTasks = abce.filteredTasks;
		setConnections(newTasks);
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Refreshed task list"));
    }

    class TaskListViewCell extends ListCell<Task> {

        public TaskListViewCell() {
        }

        //@@author A0138978E
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);
            
            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } 
            else {
            	TaskCard currentCard = TaskCard.load(task, getIndex() + 1);
            	HBox cardPane = currentCard.getLayout();
               
            	setGraphic(cardPane);
            	
            	// Set the color of the card based on whether it's overdue
            	if (task.isOverdue()) {
            		currentCard.setOverdueStyle();
            	} 
            	
            	// Set the color of the card based on whether it's favorited
                if (task.isPinned()) {
                	currentCard.setPinnedStyle();
                } 
            }
        }
    }

}
