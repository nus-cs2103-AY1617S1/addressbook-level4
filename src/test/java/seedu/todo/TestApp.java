package seedu.todo;

import javafx.stage.Stage;
import seedu.todo.commons.core.Config;
import seedu.todo.model.ImmutableTodoList;
import seedu.todo.storage.XmlSerializableTodoList;
import seedu.todo.testutil.TestUtil;

import java.util.function.Supplier;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    /* Constants */
    //App and To-do list names
    public static final String APP_TITLE = "UJDTDL Testing App";
    private static final String TODO_LIST_NAME = "Test";

    //Saving locations
    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    private static final String DEFAULT_PREF_FILE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("pref_testing.json");

    /* Variables */
    private Supplier<ImmutableTodoList> initialDataSupplier = () -> null;
    private String saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    /* Default Constructor */
    public TestApp() {
    }

    /**
     * Constructs a Test App with custom data supplier, and file location for saving.
     */
    public TestApp(Supplier<ImmutableTodoList> initialDataSupplier, String saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            TestUtil.createDataFileWithData(
                    new XmlSerializableTodoList(this.initialDataSupplier.get()),
                    this.saveFileLocation);
        }
    }

    /* Override Methods */
    @Override
    protected Config initConfig(String configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setTodoListFilePath(saveFileLocation);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        config.setTodoListName(TODO_LIST_NAME);
        return config;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    /* Main Methods */
    public static void main(String[] args) {
        launch(args);
    }
}
