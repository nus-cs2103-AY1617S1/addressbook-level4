package seedu.todo.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.GuiSettings;
import seedu.todo.commons.events.ui.ExitAppRequestEvent;
import seedu.todo.logic.Logic;
import seedu.todo.model.Model;
import seedu.todo.model.UserPrefs;
import seedu.todo.ui.controller.CommandController;
import seedu.todo.ui.util.UiPartLoaderUtil;
import seedu.todo.ui.view.*;

//@@author A0135805H
/**
 * The Main Window. Provides the basic application layout containing placeholders
 * where other JavaFX view elements can be placed.
 */
public class MainWindow extends UiPart {

    /* Constants */
    private static final String ICON = "/images/app_icon.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 400;
    private static final int MIN_WIDTH = 580;

    /* Variables */
    private Logic logic;
    private Config config;
    private UserPrefs userPrefs;
    private Model model;

    /* Independent Ui parts residing in this Ui container */
    private CommandInputView commandInputView;
    private CommandPreviewView commandPreviewView;
    private CommandFeedbackView commandFeedbackView;
    private CommandErrorView commandErrorView;
    private FilterBarView filterBarView;
    private TodoListView todoListView;
    private HelpView helpView;

    /* Layout objects for MainWindow: Handles elements of this Ui container */
    private VBox rootLayout;
    private Scene scene;

    @FXML private AnchorPane commandInputViewPlaceholder;
    @FXML private AnchorPane commandErrorViewPlaceholder;
    @FXML private AnchorPane commandPreviewViewPlaceholder;
    @FXML private AnchorPane commandFeedbackViewPlaceholder;
    @FXML private AnchorPane todoListViewPlaceholder;
    @FXML private AnchorPane helpViewPlaceholder;
    @FXML private AnchorPane filterBarViewPlaceholder;

    /**
     * Loads an instance of the {@link MainWindow} together with the associated view elements.
     *
     * @param primaryStage For the MainWindow to be loaded into.
     * @param config App configuration class file for some properties to be loaded.
     * @param prefs User preference for some properties to be loaded.
     * @param logic The main logic engine for commands to be executed.
     * @return An instance of the {@link MainWindow} element.
     */
    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, Logic logic, Model model) {
        MainWindow mainWindow = UiPartLoaderUtil.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), config, prefs, logic, model);
        return mainWindow;
    }

    private void configure(String appTitle, Config config, UserPrefs prefs, Logic logic, Model model) {
        //Set dependencies
        this.logic = logic;
        this.config = config;
        this.userPrefs = prefs;
        this.model = model;

        //Configure the UI
        setTitle(appTitle);
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
    }

    void fillInnerParts() {
        //Initialise the view elements to each placeholders.
        todoListView = TodoListView.load(primaryStage, todoListViewPlaceholder, model.getObservableList());
        helpView = HelpView.load(primaryStage, helpViewPlaceholder);
        filterBarView = FilterBarView.load(primaryStage, filterBarViewPlaceholder, model.getViewFilter());
        commandPreviewView = CommandPreviewView.load(primaryStage, commandPreviewViewPlaceholder);
        commandFeedbackView = CommandFeedbackView.load(primaryStage, commandFeedbackViewPlaceholder);
        commandInputView = CommandInputView.load(primaryStage, commandInputViewPlaceholder);
        commandErrorView = CommandErrorView.load(primaryStage, commandErrorViewPlaceholder);

        //Constructs a command communication link between the commandXViews and logic.
        CommandController.constructLink(logic,
                commandInputView, commandPreviewView, commandFeedbackView, commandErrorView);
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

    public void hide() {
        primaryStage.hide();
    }

    public void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    /* Override Methods */
    @Override
    public void setNode(Node node) {
        rootLayout = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    /* Getters */
    public TodoListView getTodoListView() {
        return this.todoListView;
    }

    public HelpView getHelpView() {
        return this.helpView;
    }

    public CommandFeedbackView getCommandFeedbackView() {
        return commandFeedbackView;
    }
}
