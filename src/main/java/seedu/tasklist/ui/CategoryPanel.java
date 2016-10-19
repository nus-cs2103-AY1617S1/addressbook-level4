package seedu.tasklist.ui;

import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.tasklist.commons.core.EventsCenter;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.events.model.TaskListChangedEvent;
import seedu.tasklist.model.ReadOnlyTaskList;
import seedu.tasklist.model.task.Task;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

/**
 * Panel containing the list of persons.
 */
public class CategoryPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CategoryPanel.class);
    private static final String FXML = "CategoryPanel.fxml";
    private AnchorPane gridpane;
    private AnchorPane placeHolderPane;
    private ReadOnlyTaskList latestSavedTaskList;

    @FXML
    private Label overdueNo;
    @FXML
    private Label todayNo;
    @FXML
    private Label tomorrowNo;
    @FXML
    private Label floatingNo;
    @FXML
    private Label otherNo;
    @FXML
    private Label totalNo;
    @FXML
    private Label upcomingNo;
    
    
    
    public CategoryPanel() {
        super();
    }
    
    @Override
    public void setNode(Node node) {
        gridpane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static CategoryPanel load(Stage primaryStage, AnchorPane personListPlaceholder, ReadOnlyTaskList readOnlyTaskList) {
        CategoryPanel categoryPanel =
                UiPartLoader.loadUiPart(primaryStage, personListPlaceholder, new CategoryPanel());
        categoryPanel.configure();
        categoryPanel.overdueNo.setText(Integer.toString(Task.overdueCounter));
        categoryPanel.floatingNo.setText(Integer.toString(Task.floatCounter));
        categoryPanel.totalNo.setText(Integer.toString(readOnlyTaskList.getTaskList().size()));
        EventsCenter.getInstance().registerHandler(categoryPanel);
        return categoryPanel;
    }
    
    @Subscribe
    private void modelChangedEvent(TaskListChangedEvent abce) {
        overdueNo.setText(Integer.toString(Task.overdueCounter));
        floatingNo.setText(Integer.toString(Task.floatCounter));
        totalNo.setText(Integer.toString(abce.data.getTaskList().size()));
    }
    
    private void configure() {
        gridpane.setStyle("-fx-background-color: #FFFFFF;");
        addToPlaceholder();
    }

    private void setConnections() {
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(gridpane);
    }
}