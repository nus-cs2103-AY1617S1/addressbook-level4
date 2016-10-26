package seedu.jimi;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.jimi.MainApp;
import seedu.jimi.commons.core.Config;
import seedu.jimi.commons.core.GuiSettings;
import seedu.jimi.logic.commands.SaveAsCommand;
import seedu.jimi.model.ReadOnlyTaskBook;
import seedu.jimi.model.UserPrefs;
import seedu.jimi.storage.XmlSerializableTaskBook;
import seedu.jimi.testutil.TestUtil;

import java.util.function.Supplier;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final String DEFAULT_CONFIG_FILE_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("config.json");
    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    protected static final String DEFAULT_PREF_FILE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    public static final String APP_TITLE = "Test App";
    protected static final String TASK_BOOK_NAME = "Test";
    protected Supplier<ReadOnlyTaskBook> initialDataSupplier = () -> null;
    protected String saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyTaskBook> initialDataSupplier, String saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            TestUtil.createDataFileWithData(
                    new XmlSerializableTaskBook(this.initialDataSupplier.get()),
                    this.saveFileLocation);
        }
    }

    @Override
    protected Config initConfig(String configFilePath) {
        Config config = super.initConfig(DEFAULT_CONFIG_FILE_FOR_TESTING);
        config.setAppTitle(APP_TITLE);
        config.setTaskBookFilePath(saveFileLocation);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        config.setTaskBookName(TASK_BOOK_NAME);
        SaveAsCommand.setConfigFilePath(DEFAULT_CONFIG_FILE_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(Config config) {
        UserPrefs userPrefs = super.initPrefs(config);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(1200.0, 600.0, (int) x, (int) y));
        return userPrefs;
    }


    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
