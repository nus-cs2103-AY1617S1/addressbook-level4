package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.TaskManager;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

/**
 * Panel containing the list of tasks.
 */
public class ContentBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(ContentBox.class);
    private static final String FXML = "ContentBox.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    private static ObservableList<ReadOnlyTask> list;

    
    @FXML
    private ImageView imagetest;
    
    @FXML
    private AnchorPane summarypane;
    
    @FXML
    private ImageView todayicon;
    
    @FXML
    private ImageView tmricon;
    
    @FXML
    private ImageView floatingicon;
    
    @FXML
    private ImageView upcomingicon;
    
    @FXML
    private ImageView alltaskicon;
    
    @FXML
    private Label summary;
    
    @FXML
    private GridPane gridpane;
    
    @FXML
    private Label alltask;
    
    @FXML
    private Label today;
    
    @FXML
    private Label tomorrow;
    
    @FXML
    private Label someday;
    
    @FXML
    private Label upcoming;
    
    @FXML
    private Label dummy1;
    
    @FXML
    private Label dummy2;

    @FXML
    private Label dummy3;
    
    @FXML
    private Label dummy4;
    
    @FXML
    private Label dummy5;
    

    public ContentBox() {
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

    public static ContentBox load(Stage primaryStage, AnchorPane ContentBoxPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
    	list = taskList;
    	ContentBox contentbox =
                UiPartLoader.loadUiPart(primaryStage, ContentBoxPlaceholder, new ContentBox());
        contentbox.configure(taskList);
        EventsCenter.getInstance().registerHandler(contentbox);
        return contentbox;
    }
    
    @Subscribe
    private void modelChangedEvent(TaskManagerChangedEvent change) {
    	dummy1.setText(Integer.toString(list.size()));
     	dummy2.setText(Integer.toString(TaskManager.todayCounter));
    	dummy3.setText(Integer.toString(TaskManager.tomorrowCounter));
    	dummy4.setText(Integer.toString(TaskManager.upcomingCounter));
    	dummy5.setText(Integer.toString(TaskManager.floatingCounter));
    }
    
 

    private void configure(ObservableList<ReadOnlyTask> taskList) {
//        setConnections(taskList);
        addToPlaceholder();
    
        
    }
    
    @FXML
    public void initialize() {
       	dummy1.setText(Integer.toString(list.size()));
    	dummy2.setText(Integer.toString(TaskManager.todayCounter));
    	dummy3.setText(Integer.toString(TaskManager.tomorrowCounter));
    	dummy4.setText(Integer.toString(TaskManager.upcomingCounter));
    	dummy5.setText(Integer.toString(TaskManager.floatingCounter));
    }

//    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
//        setEventHandlerForSelectionChangeEvent();
//    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

/*    private void setEventHandlerForSelectionChangeEvent() {
        dummy1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }
*/
/*    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        public TaskListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TaskCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }
    */
}

