package seedu.todo.ui;

import javafx.stage.Stage;
import seedu.todo.commons.core.ComponentManager;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.ui.utils.ImageUtil;

import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";

    private Config config;
    private MainWindow mainWindow;

    public UiManager(Config config) {
        super();
        this.config = config;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        // Set the application icon.
        primaryStage.getIcons().add(ImageUtil.getImage(ICON_APPLICATION));
    }

    @Override
    public void stop() {
        mainWindow.hide();
        mainWindow.releaseResources();
    }

}
