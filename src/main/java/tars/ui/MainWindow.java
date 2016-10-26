package tars.ui;

import javafx.scene.input.KeyEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tars.commons.core.Config;
import tars.commons.core.GuiSettings;
import tars.commons.events.ui.ExitAppRequestEvent;
import tars.logic.Logic;
import tars.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {

    private static final String ICON = "/images/tars_icon_32.png";
    private static final String FXML = "MainWindow.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;
    
    private double xOffset = 0;
    private double yOffset = 0;

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private InformationHeader infoHeader;
    private TaskListPanel taskListPanel;
    private RsvTaskListPanel rsvTaskListPanel;
    private ResultDisplay resultDisplay;
    private StatusBarFooter statusBarFooter;
    private HelpPanel helpPanel;
    private OverviewPanel overviewPanel;
    private CommandBox commandBox;
    private Config config;
    private UserPrefs userPrefs;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String tarsName;
    
    public static final int helpPanelTabPaneIndex = 2;

    @FXML
    private AnchorPane commandBoxPlaceholder;
    @FXML
    private AnchorPane infoHeaderPlaceholder;
    @FXML
    private AnchorPane taskListPanelPlaceholder;
    @FXML
    private AnchorPane rsvTaskListPanelPlaceholder;
    @FXML
    private AnchorPane resultDisplayPlaceholder;
    @FXML
    private AnchorPane statusbarPlaceholder;
    @FXML
    private AnchorPane overviewPanelPlaceholder;
    @FXML
    private AnchorPane helpPanelPlaceholder;
    
    @FXML
    private Label taskListLabel;
    @FXML
    private Label rsvTaskListLabel;
    
    @FXML
    private TabPane tabPane;
    @FXML
    private AnchorPane overviewTabAnchorPane;
    @FXML
    private AnchorPane rsvTabAnchorPane;
    @FXML
    private AnchorPane helpTabAnchorPane;   


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

    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {

        MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), config.getTarsName(), config, prefs, logic);
        return mainWindow;
    }

    private void configure(String appTitle, String tarsName, Config config, UserPrefs prefs,
            Logic logic) {

        //Set dependencies
        this.logic = logic;
        this.tarsName = tarsName;
        this.config = config;
        this.userPrefs = prefs;

        //Configure the UI
        setTitle(appTitle);
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        
        addMouseEventHandler();
        addTabPaneHandler();
                        
        scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        
    }

    private void addMouseEventHandler() {
        rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
    }


    private void addTabPaneHandler() {
        rootLayout.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    tabPane.getSelectionModel().selectNext();
                    event.consume();
                } else if (event.getCode() == KeyCode.LEFT) {
                    tabPane.getSelectionModel().selectPrevious();
                    event.consume();
                }
            }
        });
    }


    void fillInnerParts() {
        infoHeader = InformationHeader.load(primaryStage, infoHeaderPlaceholder);
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPlaceholder(), logic.getFilteredTaskList());
        rsvTaskListPanel = RsvTaskListPanel.load(primaryStage, getRsvTaskListPlaceholder(), logic.getFilteredRsvTaskList());
        resultDisplay = ResultDisplay.load(primaryStage, getResultDisplayPlaceholder());
        statusBarFooter = StatusBarFooter.load(primaryStage, getStatusbarPlaceholder(), config.getTarsFilePath());
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), resultDisplay, logic);
        helpPanel = HelpPanel.load(primaryStage, getHelpPanelPlaceholder());
        overviewPanel = OverviewPanel.load(primaryStage, getOverviewPanelPlaceholder(), logic.getFilteredTaskList());
    }
    
    /* @@author A0124333U
     * A method to reload the status bar footer
     */
    public void reloadStatusBarFooter(String newTarsFilePath) {
        statusBarFooter = StatusBarFooter.load(primaryStage, getStatusbarPlaceholder(), newTarsFilePath);
    }

    private AnchorPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    private AnchorPane getStatusbarPlaceholder() {
        return statusbarPlaceholder;
    }

    private AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    public AnchorPane getTaskListPlaceholder() {
        return taskListPanelPlaceholder;
    }
    
    public AnchorPane getRsvTaskListPlaceholder() {
        return rsvTaskListPanelPlaceholder;
    }
    
    public AnchorPane getHelpPanelPlaceholder() {
        return helpPanelPlaceholder;
    }
    
    public AnchorPane getOverviewPanelPlaceholder() {
        return overviewPanelPlaceholder;
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
        tabPane.getSelectionModel().select(helpPanelTabPaneIndex);
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

    public TaskListPanel getTaskListPanel() {
        return this.taskListPanel;
    }
    
    public RsvTaskListPanel getRsvTaskListPanel() {
        return this.rsvTaskListPanel;
    }

}
