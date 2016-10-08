package seedu.todo;

import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.core.Version;
import seedu.todo.commons.events.ui.ExitAppRequestEvent;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.ConfigUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.Logic;
import seedu.todo.ui.Ui;
import seedu.todo.ui.UiManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);

    protected Ui ui;

    public MainApp() {}

    @Override
    public void init() throws Exception {

    }

    @Override
    public void start(Stage primaryStage) {
        
    }

    @Override
    public void stop() {
        ui.stop();
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
