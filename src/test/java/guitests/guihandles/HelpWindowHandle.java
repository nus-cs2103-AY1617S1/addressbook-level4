package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

//@@author A0148031R
/**
 * Provides a handle to the help window of the app.
 */
public class HelpWindowHandle extends GuiHandle {

    private static final String HELP_WINDOW_TITLE = "Help";
    private static final String HELP_WINDOW_ROOT_FIELD_ID = "#helpWindowRoot";

    public HelpWindowHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, HELP_WINDOW_TITLE);
        guiRobot.sleep(1000);
    }

    public boolean isWindowOpen() {
        return getNode(HELP_WINDOW_ROOT_FIELD_ID) != null 
                && getNode(HELP_WINDOW_ROOT_FIELD_ID).getParent() != null;
    }

    public boolean isWindowClose() {
        try {
            getNode(HELP_WINDOW_ROOT_FIELD_ID);
        } catch (IllegalStateException e) {
            return true;
        }
        return false;
    }
    
    public void closeWindow() {
        super.pressEscape();
        guiRobot.sleep(500);
    }

}
