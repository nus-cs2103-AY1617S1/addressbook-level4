package tars.ui;

import javafx.scene.input.KeyEvent;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.EventHandler;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tars.commons.core.EventsCenter;
import tars.commons.core.KeyCombinations;
import tars.commons.core.LogsCenter;
import tars.commons.events.ui.CommandBoxTextFieldValueChangedEvent;
import tars.commons.events.ui.KeyCombinationPressedEvent;
import tars.logic.commands.ConfirmCommand;
import tars.logic.commands.RsvCommand;

// @@author A0121533W
/**
 * Handles all events that require main window.
 */
public class MainWindowEventsHandler {
    
    protected static Stage primaryStage;

    private static String LOG_MESSAGE_COMMAND_DETECTED = "%s command detected.";

    private static double xOffset = 0;
    private static double yOffset = 0;

    private static VBox rootLayout;
    private static TabPane tabPane;
    
    private static final Logger logger = LogsCenter.getLogger(MainWindow.class);
    
    public MainWindowEventsHandler(Stage primaryStage, VBox rootLayout,
            TabPane tabPane) {
        MainWindowEventsHandler.rootLayout = rootLayout;
        MainWindowEventsHandler.primaryStage = primaryStage;
        MainWindowEventsHandler.tabPane = tabPane;
        EventsCenter.getInstance().registerHandler(this);
    }

    public static void addMouseEventHandler() {
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

    public static void addTabPaneHandler() {
        rootLayout.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    cycleTabPaneRight();
                    event.consume();
                } else if (event.getCode() == KeyCode.LEFT) {
                    cycleTabPaneLeft();
                    event.consume();
                }
            }
        });
    }

    @Subscribe
    private void KeyCombinationPressedEventHandler(
            KeyCombinationPressedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                event.getKeyCombination().getDisplayText()));
        if (event
                .getKeyCombination() == KeyCombinations.KEY_COMB_CTRL_RIGHT_ARROW) {
            cycleTabPaneRight();
        } else if (event
                .getKeyCombination() == KeyCombinations.KEY_COMB_CTRL_LEFT_ARROW) {
            cycleTabPaneLeft();
        }
    }

    @Subscribe
    private void CommandBoxTextFieldValueChangedEventHandler(
            CommandBoxTextFieldValueChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, String.format(
                LOG_MESSAGE_COMMAND_DETECTED, event.getTextFieldValue())));
        if (event.getTextFieldValue().equals(RsvCommand.COMMAND_WORD) || event
                .getTextFieldValue().equals(ConfirmCommand.COMMAND_WORD)) {
            tabPane.getSelectionModel()
                    .select(MainWindow.RSV_TASK_LIST_PANEL_TAB_PANE_INDEX);
        }
    }

    private static void cycleTabPaneRight() {
        if (tabPane.getSelectionModel()
                .isSelected((MainWindow.HELP_PANEL_TAB_PANE_INDEX))) {
            tabPane.getSelectionModel().selectFirst();
        } else {
            tabPane.getSelectionModel().selectNext();
        }
    }

    private static void cycleTabPaneLeft() {
        if (tabPane.getSelectionModel()
                .isSelected((MainWindow.OVERVIEW_PANEL_TAB_PANE_INDEX))) {
            tabPane.getSelectionModel().selectLast();
        } else {
            tabPane.getSelectionModel().selectPrevious();
        }
    }

    // @@author A0140022H
    public static void handleHelp(HelpPanel helpPanel, String args) {
        helpPanel.loadUserGuide(args);
        tabPane.getSelectionModel()
                .select(MainWindow.HELP_PANEL_TAB_PANE_INDEX);
    }

}
