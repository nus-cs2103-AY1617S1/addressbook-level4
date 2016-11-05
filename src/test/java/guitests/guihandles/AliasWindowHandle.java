package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

//@@author A0138978E
/**
 * Provides a handle to the alias window of the app.
 */
public class AliasWindowHandle extends GuiHandle {

    private static final String ALIAS_WINDOW_TITLE = "Alias List";
    private static final String ALIAS_WINDOW_LIST_FIELD_ID = "#aliasListView";

    public AliasWindowHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, ALIAS_WINDOW_TITLE);
        guiRobot.sleep(1000);
    }

    public boolean isWindowOpen() {
        return getNode(ALIAS_WINDOW_LIST_FIELD_ID) != null;
    }

    public void closeWindow() {
        super.closeWindow();
        guiRobot.sleep(500);
    }

}