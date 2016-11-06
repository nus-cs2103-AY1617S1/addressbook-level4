package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import tars.TestApp;
import tars.ui.MainWindow;

/**
 * GUI test for this week panel
 *
 * @@author A0124333U
 */
public class ThisWeekPanelHandle extends GuiHandle {

    private static final String TAB_PANEL_ROOT_FIELD_ID = "#tabPane";

    public ThisWeekPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public boolean isTabSelected() {
        return ((TabPane) getNode(TAB_PANEL_ROOT_FIELD_ID)).getSelectionModel()
                .isSelected(MainWindow.OVERVIEW_PANEL_TAB_PANE_INDEX);
    }

}
