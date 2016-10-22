package seedu.todo.ui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.commons.core.ComponentManager;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.ui.views.View;

import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    // Only one instance of UiManager should be present. 
    private static UiManager instance = null;
    
    // Only one currentView.
    public static View currentView;
    
    private static String currentConsoleMessage = "";
    private static String currentConsoleInputValue = "";

    private Config config;
    private MainWindow mainWindow;
    
    private static final String FATAL_ERROR_DIALOG = "Fatal error during initializing";
    private static final String LOAD_VIEW_ERROR = "Cannot loadView: UiManager not instantiated.";
    
    protected UiManager() {
        // Prevent instantiation.
    }

    public static UiManager getInstance() {
        return instance;
    }

    public static void initialize(Config config) {
        if (instance == null) {
            instance = new UiManager();
        }
        
        instance.config = config;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        // Show main window.
        try {
            mainWindow = MainWindow.load(primaryStage, config);
            mainWindow.render();
            mainWindow.show();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown(FATAL_ERROR_DIALOG, e);
        }
    }

    @Override
    public void stop() {
        mainWindow.hide();
    }
    
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public static <T extends View> T loadView(Class<T> viewClass) {
        if (instance == null) {
            logger.warning(LOAD_VIEW_ERROR);
            return null;
        }
        
        return instance.mainWindow.loadView(viewClass);
    }
    
    /**
     * Updates the currentView and renders it.
     * 
     * @param view   View to render.
     */
    public static void renderView(View view) {
        if (view != null && view.getNode() != null) {
            currentView = view;
            
            // Clear console values first
            currentConsoleInputValue = "";
            currentConsoleMessage = "";
            
            // Render view
            view.render();
        }
    }
    
    public static String getConsoleMessage() {
        return currentConsoleMessage;
    }
    
    public static String getConsoleInputValue() {
        return currentConsoleInputValue;
    }
    
    /**
     * Sets the message shown in the console and reloads the console box.
     * Does not do anything if no views have been loaded yet.
     * 
     * @param consoleMessage   Message to display in the console.
     */
    public static void updateConsoleMessage(String consoleMessage) {
        if (currentView != null) {
            currentConsoleMessage = consoleMessage;
            instance.mainWindow.loadComponents();
        }
    }
    
    /**
     * Sets the message shown in the console input box and reloads the console box.
     * Does not do anything if no views have been loaded yet.
     * 
     * @param consoleInputValue   Message to display in the console input box.
     */
    public static void updateConsoleInputValue(String consoleInputValue) {
        if (currentView != null) {
            currentConsoleInputValue = consoleInputValue;
            instance.mainWindow.loadComponents();
        }
    }


    /** ================ DISPLAY ERRORS ================== **/

    private void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
            String contentText) {
        final Alert alert = new Alert(type);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

}
