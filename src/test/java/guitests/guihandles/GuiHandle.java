package guitests.guihandles;

import guitests.GuiRobot;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import seedu.address.TestApp;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Base class for all GUI Handles used in testing.
 */
public class GuiHandle {
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
        return guiRobot.lookup(query).tryQuery().get();
    }

    protected String getTextFieldText(String fieldName) {
        return ((TextField) getNode(fieldName)).getText();
    }
    
    protected void setTextField(String textFieldId, String newText) {
        guiRobot.clickOn(textFieldId);
        ((TextField)guiRobot.lookup(textFieldId).tryQuery().get()).setText(newText);
        guiRobot.sleep(500); // so that the texts stays visible on the GUI for a short period
    }
    
    //@@author A0146123R
    protected boolean getToggleButtonInput(String buttonId) {
        return ((ToggleButton) getNode(buttonId)).isSelected();
    }
    
    protected void clickToggleButton(String toggleButtonId) {
        guiRobot.clickOn(toggleButtonId);
        guiRobot.sleep(500); // so that the texts stays visible on the GUI for a short period
    }
    
    protected String getPriorityInput(String boxId) {
        return ((ChoiceBox<String>) getNode(boxId)).getSelectionModel().getSelectedItem().toString();
    }
    
    protected void chooseChoiceBox(String boxId, String newChoice) {
        Platform.runLater(() -> ((ChoiceBox<String>) getNode(boxId)).getSelectionModel().select(newChoice));
        guiRobot.sleep(500); // so that the texts stays visible on the GUI for a short period
    }
    
    protected boolean isFocused(String id) {
       return getNode(id).isFocused();
    }
    
    protected boolean choiceBoxIsFocused(String id) {
        return isFocused(id) && ((ChoiceBox<String>) getNode(id)).isShowing();
     }
    //@@author

    public void pressEnter() {
        guiRobot.type(KeyCode.ENTER).sleep(500);
    }

    protected String getTextFromLabel(String fieldId, Node parentNode) {
        return ((Label) guiRobot.from(parentNode).lookup(fieldId).tryQuery().get()).getText();
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
