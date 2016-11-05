package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;
import seedu.address.TestApp;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Base class for all GUI Handles used in testing.
 */
public class GuiHandle {
    // @@author A0093960X
    private static final String PRIORITY_LOW = "LOW";
    private static final String PRIORITY_MEDIUM = "MEDIUM";
    private static final String PRIORITY_HIGH = "HIGH";
    private static final String HEXADECIMAL_GREEN = "0x008000ff";
    private static final String HEXADECIMAL_YELLOW = "0xffff00ff";
    private static final String HEXADECIMAL_RED = "0xff0000ff";

    // @@author
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
        java.util.Optional<Window> window = guiRobot.listTargetWindows().stream()
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
        return guiRobot.lookup(query).tryQuery().get();
    }

    protected String getTextFieldText(String filedName) {
        return ((TextField) getNode(filedName)).getText();
    }

    protected void setTextField(String textFieldId, String newText) {
        guiRobot.clickOn(textFieldId);
        ((TextField) guiRobot.lookup(textFieldId).tryQuery().get()).setText(newText);
        guiRobot.sleep(500); // so that the texts stays visible on the GUI for a
                             // short period
    }

    public void pressEnter() {
        guiRobot.type(KeyCode.ENTER).sleep(500);
    }

    public void pressUpArrowKey(String textFieldId) {
        guiRobot.clickOn(textFieldId);
        guiRobot.type(KeyCode.UP).sleep(500);
    }

    public void pressDownArrowKey(String textFieldId) {
        guiRobot.clickOn(textFieldId);
        guiRobot.type(KeyCode.DOWN).sleep(500);
    }

    protected String getTextFromLabel(String fieldId, Node parentNode) {
        return ((Label) guiRobot.from(parentNode).lookup(fieldId).tryQuery().get()).getText();
    }

    // @@author A0093960X
    /**
     * Looks for the priority rectangle and returns a String that 
     * @param fieldId
     * @param parentNode
     * @return
     */
    protected String getTextFromPriorityRectangle(String fieldId, Node parentNode) {
        Paint rectanglePaint = findPriorityRectangleAndGetPaint(fieldId, parentNode);
        String fillColour = rectanglePaint.toString();

        switch (fillColour) {
        case HEXADECIMAL_RED :
            return PRIORITY_HIGH;
        case HEXADECIMAL_YELLOW :
            return PRIORITY_MEDIUM;
        case HEXADECIMAL_GREEN :
            return PRIORITY_LOW;
        default :
            assert false : "Rectangle should only be of the 3 colours above";
            logger.info("Rectangle other than red, yellow or green was detected.");
            return PRIORITY_MEDIUM;
        }
    }

    /**
     * Looks for the priority rectangle and returns the Paint that fills the given priority rectangle.
     * @param fieldId the fieldId of the priority rectangle to find
     * @param parentNode the parent node that contains the priority rectangle
     * @return the Paint that fills the specified priority rectangle
     */
    private Paint findPriorityRectangleAndGetPaint(String fieldId, Node parentNode) {
        return ((Rectangle) guiRobot.from(parentNode).lookup(fieldId).tryQuery().get()).getFill();
    }

    // @@author
    public void focusOnSelf() {
        if (stageTitle != null) {
            focusOnWindow(stageTitle);
        }
    }

    public void focusOnMainApp() {
        this.focusOnWindow(TestApp.APP_TITLE);
    }

    public void closeWindow() {
        java.util.Optional<Window> window = guiRobot.listTargetWindows().stream()
                .filter(w -> w instanceof Stage && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        if (!window.isPresent()) {
            return;
        }

        guiRobot.targetWindow(window.get());
        guiRobot.interact(() -> ((Stage) window.get()).close());
        focusOnMainApp();
    }
}
