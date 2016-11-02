package seedu.forgetmenot.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.forgetmenot.commons.core.EventsCenter;
import seedu.forgetmenot.commons.core.LogsCenter;
import seedu.forgetmenot.commons.events.model.TaskManagerChangedEvent;
import seedu.forgetmenot.model.TaskManager;
import seedu.forgetmenot.model.task.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 * @@author A0139211R
 */
public class ContentBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(ContentBox.class);
    private static final String FXML = "ContentBox.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    
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
    private ImageView overdueicon;
    
    @FXML
    private Label summary;
    
    @FXML
    private GridPane gridpane;
    
    @FXML
    private Label overdue;
    
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
    
    @FXML
    private Label floatheader;
    

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
    	ContentBox contentbox =
                UiPartLoader.loadUiPart(primaryStage, ContentBoxPlaceholder, new ContentBox());
        contentbox.configure(taskList);
        EventsCenter.getInstance().registerHandler(contentbox);
        return contentbox;
    }
    
    @Subscribe
    private void modelChangedEvent(TaskManagerChangedEvent change) {
    	dummy1.setText(Integer.toString(TaskManager.overdueCounter));
     	dummy2.setText(Integer.toString(TaskManager.todayCounter));
    	dummy3.setText(Integer.toString(TaskManager.tomorrowCounter));
    	dummy4.setText(Integer.toString(TaskManager.upcomingCounter));
    	dummy5.setText(Integer.toString(TaskManager.floatingCounter));
    }
    
 

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        addToPlaceholder();
        panel.prefHeightProperty().bind(placeHolderPane.heightProperty());       
    }
    
    @FXML
    public void initialize() {
       	dummy1.setText(Integer.toString(TaskManager.overdueCounter));
    	dummy2.setText(Integer.toString(TaskManager.todayCounter));
    	dummy3.setText(Integer.toString(TaskManager.tomorrowCounter));
    	dummy4.setText(Integer.toString(TaskManager.upcomingCounter));
    	dummy5.setText(Integer.toString(TaskManager.floatingCounter));
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }
}


