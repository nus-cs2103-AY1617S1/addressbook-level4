package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import tars.ui.MainWindow;

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
        return ((TabPane) getNode("#tabPane")).getSelectionModel().isSelected(MainWindow.helpPanelTabPaneIndex);
    }


}
