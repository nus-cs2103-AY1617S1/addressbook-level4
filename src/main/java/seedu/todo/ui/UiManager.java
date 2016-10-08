package seedu.todo.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.todo.MainApp;
import seedu.todo.commons.core.ComponentManager;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.events.storage.DataSavingExceptionEvent;
import seedu.todo.commons.events.ui.JumpToListRequestEvent;
import seedu.todo.commons.events.ui.ShowHelpRequestEvent;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.Logic;

import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";

    private Logic logic;
    private Config config;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config) {
        super();
        this.logic = logic;
        this.config = config;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());
    }

    @Override
    public void stop() {
        mainWindow.hide();
        mainWindow.releaseResources();
    }

}
