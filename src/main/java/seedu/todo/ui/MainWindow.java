package seedu.todo.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todo.MainApp;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.GuiSettings;
import seedu.todo.commons.events.ui.ExitAppRequestEvent;
import seedu.todo.ui.components.Console;
import seedu.todo.ui.components.ConsoleInput;
import seedu.todo.ui.components.Header;
import seedu.todo.ui.views.View;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends View {

	private static final String FXML_PATH = "MainWindow.fxml";
    private static final String ICON_PATH = "/images/logo-512x512.png";
    private static final String OPEN_HELP_KEY_COMBINATION = "F1";
    public static final int MIN_HEIGHT = 800;
    public static final int MIN_WIDTH = 1000;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    // FXML Components
    @FXML
    private MenuItem helpMenuItem;
    @FXML
    private AnchorPane childrenPlaceholder;
    @FXML
    private AnchorPane consolePlaceholder;
    @FXML
    private AnchorPane consoleInputPlaceholder;
    @FXML
    private AnchorPane headerPlaceholder;

    public static MainWindow load(Stage primaryStage, Config config) {
	    MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
	    mainWindow.configure(config);
	    return mainWindow;
	}

    public void configure(Config config) {
    	String appTitle = config.getAppTitle();
    	
        // Configure the UI
        setTitle(appTitle);
        setIcon(ICON_PATH);
        setWindowMinSize();
        scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        
        // Bind accelerators
        setAccelerators();
        
        // Load other components.
        loadComponents();
    }
    
    private void loadComponents() {
    	// Load Header
    	Header header = new Header();
    	header.passInProps(controller -> {
    		Header headerController = (Header) controller;
    		headerController.versionString = MainApp.VERSION.toString();
    		return headerController;
    	});
    	header.render(primaryStage, getHeaderPlaceholder());
    	
    	// Load ConsoleInput
    	new ConsoleInput().render(primaryStage, getConsoleInputPlaceholder());
    	
    	// Load Console
    	new Console().render(primaryStage, getConsolePlaceholder());
    }

	@Override
	public void setNode(Node node) {
        rootLayout = (VBox) node;
	}

	@Override
	public String getFxmlPath() {
		return FXML_PATH;
	}

    public void show() {
        primaryStage.show();
    }

    public void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    public GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /** ================ FXML COMPONENTS ================== **/
    
    public AnchorPane getChildrenPlaceholder() {
        return childrenPlaceholder;
    }
    
    public AnchorPane getConsolePlaceholder() {
        return consolePlaceholder;
    }
    
    public AnchorPane getConsoleInputPlaceholder() {
    	return consoleInputPlaceholder;
    }
    
    public AnchorPane getHeaderPlaceholder() {
        return headerPlaceholder;
    }

    /** ================ ACCELERATORS ================== **/
    
    private void setAccelerators() {
        helpMenuItem.setAccelerator(KeyCombination.valueOf(OPEN_HELP_KEY_COMBINATION));
    }
    
    /** ================ ACTION HANDLERS ================== **/
    
    @FXML
    public void handleHelp() {
    	// TODO: Auto-generated method stub
    }

    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }
}
