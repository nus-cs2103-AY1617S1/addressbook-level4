package seedu.todo.ui;

import javafx.stage.Stage;
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

    private Config config;
    private MainWindow mainWindow;
    private Stage primaryStage;

    public UiManager(Config config) {
        super();
        this.config = config;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        
        // Save primaryStage for later renders.
        this.primaryStage = primaryStage;
        
        // Show main window.
        mainWindow = MainWindow.load(primaryStage, config);
        mainWindow.show();
    }

    @Override
    public void stop() {
        mainWindow.hide();
    }
    
    public void loadView(View view) {
    	if (primaryStage == null)
    		return;
    	
    	assert primaryStage != null;
    	
    	view.render(primaryStage);
    }

}
