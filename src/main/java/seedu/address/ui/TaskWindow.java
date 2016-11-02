package seedu.address.ui;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

//@@author A0135767U
/**
 * Controller for a browser panel extension
 */
public class TaskWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "TaskWindow.fxml";
    
    private static final String TITLE = "Task";
    
    private static final int DEFAULT_TASK_SIZE = 300;
    private static final int DEFAULT_BROWSER_SIZE = 600;
    
    private static UserPrefs sessionPrefs;

    private VBox mainPane;

    private Stage dialogStage;
    
    private BrowserPanel browserPanel;
    
    @FXML
    private AnchorPane browserPlaceholder;
    
    @FXML
    private MenuItem closeMenuItem;

    public static TaskWindow load(Stage primaryStage, UserPrefs prefs) {
    	sessionPrefs = prefs;
    	
        logger.fine("Showing task panel.");
        TaskWindow taskWindow = UiPartLoader.loadUiPart(primaryStage, new TaskWindow());
        taskWindow.configure(sessionPrefs);
        return taskWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    private void configure(UserPrefs prefs){
        Scene scene = new Scene(mainPane);
        scene.setFill(Color.TRANSPARENT);
        
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        
        setIcon(dialogStage, ICON);
        setWindowDefaultSize(prefs);

        browserPanel = BrowserPanel.load(browserPlaceholder);
    }
    
    protected void setWindowDefaultSize(UserPrefs prefs) {
        dialogStage.setHeight(DEFAULT_TASK_SIZE);
        dialogStage.setWidth(DEFAULT_TASK_SIZE);
        
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
        	dialogStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX() - dialogStage.getWidth());
        	dialogStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }
    
    protected void setWindowBrowserSize(UserPrefs prefs) {
        dialogStage.setHeight(DEFAULT_BROWSER_SIZE);
        dialogStage.setWidth(DEFAULT_BROWSER_SIZE);
        
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
        	dialogStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX() - dialogStage.getWidth());
        	dialogStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }
    
    public void loadTaskPage(ReadOnlyTask task) {
        setWindowBrowserSize(sessionPrefs);
    	browserPanel.loadTaskPage(task);
    }
    
    public void loadTaskCard(ReadOnlyTask task) {
    	setWindowDefaultSize(sessionPrefs);
    	browserPanel.loadTaskCard(task);
    }

    public void show() {
    	FadeTransition ft = new FadeTransition();
    	ft.setDuration(Duration.millis(500));
    	ft.setNode(dialogStage.getScene().getRoot());

    	ft.setFromValue(0.0);
    	ft.setToValue(1.0);
    	
    	ft.play();
    	dialogStage.show();
    }
    
    public void hide() {
    	FadeTransition ft = new FadeTransition();
    	ft.setDuration(Duration.millis(500));
    	ft.setNode(dialogStage.getScene().getRoot());
    	
    	ft.setFromValue(1.0);
    	ft.setToValue(0.0);
    	
    	ft.play();
    	ft.setOnFinished((ae) -> dialogStage.hide());
    }
}
