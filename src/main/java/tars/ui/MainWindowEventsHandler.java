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


/**
 * Handles all events that require main window.
 * 
 * @@author A0121533W
 */
public class MainWindowEventsHandler {

    private double xOffset = 0;
    private double yOffset = 0;
    private final Logger logger = LogsCenter.getLogger(MainWindow.class);

    private VBox rootLayout;
    private TabPane tabPane;
    protected Stage primaryStage;

    public MainWindowEventsHandler(Stage primaryStage, VBox rootLayout,
            TabPane tabPane) {
        this.rootLayout = rootLayout;
        this.primaryStage = primaryStage;
        this.tabPane = tabPane;
        EventsCenter.getInstance().registerHandler(this);
    }

    public void addMouseEventHandler() {
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

    public void addTabPaneHandler() {
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
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                event.getTextFieldValue() + " command detected."));
        if (event.getTextFieldValue().equals(RsvCommand.COMMAND_WORD) || event
                .getTextFieldValue().equals(ConfirmCommand.COMMAND_WORD)) {
            tabPane.getSelectionModel()
                    .select(MainWindow.RSV_TASK_LIST_PANEL_TAB_PANE_INDEX);
        }
    }

    private void cycleTabPaneRight() {
        if (tabPane.getSelectionModel()
                .isSelected((MainWindow.HELP_PANEL_TAB_PANE_INDEX))) {
            tabPane.getSelectionModel().selectFirst();
        } else {
            tabPane.getSelectionModel().selectNext();
        }
    }

    private void cycleTabPaneLeft() {
        if (tabPane.getSelectionModel()
                .isSelected((MainWindow.OVERVIEW_PANEL_TAB_PANE_INDEX))) {
            tabPane.getSelectionModel().selectLast();
        } else {
            tabPane.getSelectionModel().selectPrevious();
        }
    }

    /**
     * @@author A0140022H
     */
    public void handleHelp(HelpPanel helpPanel, String args) {
        helpPanel.loadUserGuide(args);
        tabPane.getSelectionModel()
                .select(MainWindow.HELP_PANEL_TAB_PANE_INDEX);
    }
    // @@author

}
