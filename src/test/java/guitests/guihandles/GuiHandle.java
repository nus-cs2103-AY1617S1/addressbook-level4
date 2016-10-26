package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import seedu.todo.TestApp;
import seedu.todo.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Base class for all GUI Handles used in testing.
 */
public class GuiHandle {
    protected static final int GUI_SLEEP_DURATION = 200;
    protected static final int GUI_ENTER_SLEEP_DURATION = 200;

    protected final GuiRobot guiRobot;
    protected final Stage primaryStage;
    protected final String stageTitle;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public GuiHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        this.guiRobot = guiRobot;
        this.primaryStage = primaryStage;
        this.stageTitle = stageTitle;
        focusOnSelf();
    }

    public void focusOnWindow(String stageTitle) {
        logger.info("Focusing " + stageTitle);
        java.util.Optional<Window> window = guiRobot.listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        if (!window.isPresent()) {
            logger.warning("Can't find stage " + stageTitle + ", Therefore, aborting focusing");
            return;
        }

        guiRobot.targetWindow(window.get());
        guiRobot.interact(() -> window.get().requestFocus());
        logger.info("Finishing focus " + stageTitle);
    }

    protected Node getNode(String query) {
        com.google.common.base.Optional<Node> node = guiRobot.lookup(query).tryQuery();
        return (node.isPresent()) ? node.get()
                                  : null;
    }

    public void pressSpace() {
        guiRobot.type(KeyCode.SPACE).sleep(GUI_ENTER_SLEEP_DURATION);
    }

    public void pressEnter() {
        guiRobot.type(KeyCode.ENTER).sleep(GUI_ENTER_SLEEP_DURATION);
    }

    public void focusOnSelf() {
        if (stageTitle != null) {
            focusOnWindow(stageTitle);
        }
    }

    public void focusOnMainApp() {
        this.focusOnWindow(TestApp.APP_TITLE);
    }

    public void closeWindow() {
        java.util.Optional<Window> window = guiRobot.listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        if (!window.isPresent()) {
            return;
        }

        guiRobot.targetWindow(window.get());
        guiRobot.interact(() -> ((Stage)window.get()).close());
        focusOnMainApp();
    }
}
