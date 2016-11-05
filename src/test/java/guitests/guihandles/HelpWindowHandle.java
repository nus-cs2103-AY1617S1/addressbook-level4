package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

//@@author A0139661Y
/**
 * Provides a handle to the help window of the app.
 */
public class HelpWindowHandle extends GuiHandle {

    private static final String HELP_WINDOW_TITLE = "Help";

    public HelpWindowHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, HELP_WINDOW_TITLE);
        guiRobot.sleep(1000);
    }

    public boolean isWindowOpen() {
    	return stageTitle.equals("Help");
    }

    public void closeWindow() {
        super.closeWindow();
        guiRobot.sleep(500);
    }
}
