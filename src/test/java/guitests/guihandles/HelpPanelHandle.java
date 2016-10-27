package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import tars.ui.MainWindow;

/**
 * Provides a handle to the help window of the app.
 */
public class HelpPanelHandle extends GuiHandle {

    private static final String HELP_PANEL_TITLE = "Help";
    private static final String TAB_PANEL_ROOT_FIELD_ID = "#tabPane";

    public HelpPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, HELP_PANEL_TITLE);
        guiRobot.sleep(1000);
    }

    public boolean isTabSelected() {
        return ((TabPane) getNode(TAB_PANEL_ROOT_FIELD_ID)).getSelectionModel().isSelected(MainWindow.HELP_PANEL_TAB_PANE_INDEX);
    }


}
