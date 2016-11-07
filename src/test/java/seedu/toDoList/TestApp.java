package seedu.toDoList;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.toDoList.MainApp;
import seedu.toDoList.commons.core.Config;
import seedu.toDoList.commons.core.GuiSettings;
import seedu.toDoList.commons.events.storage.StoragePathEvent;
import seedu.toDoList.model.ReadOnlyTaskManager;
import seedu.toDoList.model.UserPrefs;
import seedu.toDoList.storage.XmlSerializableTaskManager;
import seedu.toDoList.testutil.TestUtil;

import java.util.function.Supplier;

import com.google.common.eventbus.Subscribe;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    protected static final String DEFAULT_PREF_FILE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    public static final String APP_TITLE = "Test App";
    protected static final String ADDRESS_BOOK_NAME = "Test";
    protected Supplier<ReadOnlyTaskManager> initialDataSupplier = () -> null;
    protected String saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyTaskManager> initialDataSupplier, String saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            TestUtil.createDataFileWithData(
                    new XmlSerializableTaskManager(this.initialDataSupplier.get()),
                    this.saveFileLocation);
        }
    }

    @Override
    protected Config initConfig(String configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setTaskManagerFilePath(saveFileLocation);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        config.setTaskManagerName(ADDRESS_BOOK_NAME);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(Config config) {
        UserPrefs userPrefs = super.initPrefs(config);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(1000.0, 727.0, (int) x, (int) y));
        return userPrefs;
    }


    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }
    
    @Override
    @Subscribe
    public void handleStoragePathChangedEvent(StoragePathEvent event) {
        this.config.setTaskManagerFilePath(event.getNewStorageFilePath());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
