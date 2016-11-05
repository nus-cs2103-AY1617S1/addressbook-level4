package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.agendum.TestApp;

//@@author A0148031R
/**
 * Handler for the message placeholder of the ui
 */
public class MessageDisplayHandle extends GuiHandle{
    
    public static final String MESSAGE_PLACEHOLDER_ID = "#messagePlaceHolder";

    public MessageDisplayHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public String getText() {
        return getMessageDisplay() == null ? null : getMessageDisplay().getText();
    }

    private Label getMessageDisplay() {
        try {
            StackPane messagePlaceHolder = (StackPane)getNode(MESSAGE_PLACEHOLDER_ID);
            return (Label) messagePlaceHolder.getChildren().get(0);
        } catch (IllegalStateException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
