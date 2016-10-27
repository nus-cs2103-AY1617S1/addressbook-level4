package seedu.todo;

import java.io.File;
import java.util.function.Supplier;

import javafx.stage.Stage;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.models.TodoListDB;
import seedu.todo.storage.JsonStorage;
import seedu.todo.storage.Storage;
import seedu.todo.testutil.TestUtil;

public class TestApp extends MainApp {
    
    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("testData.json");
    public static final String CONFIG_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("testConfig.json");
    public static final String APP_TITLE = "Test App";
    protected static final String ADDRESS_BOOK_NAME = "Test";
    protected Supplier<TodoListDB> initialDataSupplier = () -> null;
    protected String saveFileLocation = SAVE_LOCATION_FOR_TESTING;
    protected String configFileLocation = CONFIG_LOCATION_FOR_TESTING;

    public TestApp(Supplier<TodoListDB> initialDataSupplier, String saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;
    }
    
    @Override
    public void init() throws Exception {
        super.init();
        
        // Purge test JSON
        FileUtil.removeFile(new File(saveFileLocation));
        FileUtil.removeFile(new File(configFileLocation));
        
        // If some initial local data has been provided, load that data into TodoListDB instead.
        if (initialDataSupplier.get() != null) {
            TodoListDB db = TodoListDB.getInstance();
            Storage storage = new JsonStorage();
            storage.save(initialDataSupplier.get());
            db.setStorage(storage);
        }
    }

    @Override
    protected Config initConfig() {
        config = super.loadConfigFromFile(configFileLocation);
        config.setAppTitle(APP_TITLE);
        config.setDatabaseFilePath(saveFileLocation);
        return config;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
