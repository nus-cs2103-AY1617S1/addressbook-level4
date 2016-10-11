package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.util.AppUtil;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Scene> {

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "/view/MainWindow.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;

    // Independent Ui parts residing in this Ui container
    private TaskListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private CommandBox commandBox;
    private StatusBarFooter statusBarFooter;

    // Handles to elements of this Ui container
    @FXML
    private VBox rootLayout;

    @FXML
    private UiRegion commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private UiRegion taskListPanelPlaceholder;

    @FXML
    private UiRegion resultDisplayPlaceholder;

    @FXML
    private UiRegion statusbarPlaceholder;

    private final Stage primaryStage;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML);
        this.primaryStage = primaryStage;

        //Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        fillInnerParts(config, logic);
        setAccelerators();
    }

    private void setAccelerators() {
        helpMenuItem.setAccelerator(KeyCombination.valueOf("F1"));
    }

    void fillInnerParts(Config config, Logic logic) {
        personListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.setNode(personListPanel.getRoot());
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.setNode(resultDisplay.getRoot());
        commandBox = new CommandBox(resultDisplay, logic);
        commandBoxPlaceholder.setNode(commandBox.getRoot());
        statusBarFooter = new StatusBarFooter(config.getTaskBookFilePath());
        statusbarPlaceholder.setNode(statusBarFooter.getRoot());
    }

    public void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    protected void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
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

    @FXML
    public void handleHelp() {
        final HelpWindow helpWindow = new HelpWindow(primaryStage);
        helpWindow.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public TaskListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    /**
     * Sets the given image as the icon for the primary stage of this UI Part.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        primaryStage.getIcons().add(AppUtil.getImage(iconSource));
    }

}
