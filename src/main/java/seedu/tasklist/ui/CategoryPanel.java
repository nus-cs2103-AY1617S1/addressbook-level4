package seedu.tasklist.ui;

import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.tasklist.commons.core.EventsCenter;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.events.model.TaskCountersChangedEvent;
import seedu.tasklist.model.TaskCounter;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
//@@author A0146107M
/**
 * Panel containing the list of persons.
 */
public class CategoryPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CategoryPanel.class);
    private static final String FXML = "CategoryPanel.fxml";
    private AnchorPane gridpane;
    private AnchorPane placeHolderPane;

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

    public static CategoryPanel load(Stage primaryStage, AnchorPane personListPlaceholder, TaskCounter taskCounter) {
        CategoryPanel categoryPanel =
                UiPartLoader.loadUiPart(primaryStage, personListPlaceholder, new CategoryPanel());
        categoryPanel.configure();
        categoryPanel.setCounts(taskCounter);
        EventsCenter.getInstance().registerHandler(categoryPanel);
        return categoryPanel;
    }
    
    @Subscribe
    private void countsChangedEvent(TaskCountersChangedEvent data) {
    	setCounts(data.data);
    }
    
    private void setCounts(TaskCounter newCounts) {
    	overdueNo.setText(Integer.toString(newCounts.getOverdue()));
    	todayNo.setText(Integer.toString(newCounts.getToday()));
    	tomorrowNo.setText(Integer.toString(newCounts.getTomorrow()));
    	floatingNo.setText(Integer.toString(newCounts.getFloating()));
    	otherNo.setText(Integer.toString(newCounts.getOther()));
    	totalNo.setText(Integer.toString(newCounts.getTotal()));
    	upcomingNo.setText(Integer.toString(newCounts.getUpcoming()));
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
