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
import seedu.todo.ui.view.*;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {

    private static final String ICON = "/images/app_icon.png";
    private static final String FXML = "MainWindow.fxml";
    public static final int MIN_HEIGHT = 400;
    public static final int MIN_WIDTH = 580;

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private CommandInputView commandInputView;
    private CommandFeedbackView commandFeedbackView;
    private CommandErrorView commandErrorView;
    private TaskViewFilterView taskViewFilterView;

    private TodoListPanel todoListPanel;
    private HelpPanel helpPanel;

    private Config config;
    private UserPrefs userPrefs;
    private Model model;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String todoListName;

    @FXML
    private AnchorPane todoListPanelPlaceholder;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private AnchorPane errorViewPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;

    @FXML
    private AnchorPane helpPanelPlaceholder;

    @FXML
    private AnchorPane taskViewFilterPanel;

    public MainWindow() {
        super();
    }

    @Override
    public void setNode(Node node) {
        rootLayout = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, Logic logic, Model model) {
        MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), config.getTodoListName(), config, prefs, logic, model);
        return mainWindow;
    }

    private void configure(String appTitle, String todoListName, Config config, UserPrefs prefs, 
                           Logic logic, Model model) {

        //Set dependencies
        this.logic = logic;
        this.todoListName = todoListName;
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
        todoListPanel = TodoListPanel.load(primaryStage, todoListPanelPlaceholder, model.getObservableList());
        helpPanel = HelpPanel.load(primaryStage, helpPanelPlaceholder);
        taskViewFilterView = TaskViewFilterView.load(primaryStage, taskViewFilterPanel, model.getViewFilter());
        commandFeedbackView = CommandFeedbackView.load(primaryStage, resultDisplayPlaceholder);
        commandInputView = CommandInputView.load(primaryStage, commandBoxPlaceholder);
        commandErrorView = CommandErrorView.load(primaryStage, errorViewPlaceholder);
        CommandController.constructLink(logic, commandInputView, commandFeedbackView, commandErrorView);
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

    /* Getters */
    TodoListPanel getTodoListPanel() {
        return this.todoListPanel;
    }

    HelpPanel getHelpPanel() {
        return this.helpPanel;
    }

    CommandFeedbackView getCommandFeedbackView() {
        return commandFeedbackView;
    }
}
