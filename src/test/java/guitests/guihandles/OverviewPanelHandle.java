package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import tars.TestApp;
import tars.ui.MainWindow;

public class OverviewPanelHandle extends GuiHandle {
    
    private static final String TAB_PANEL_ROOT_FIELD_ID = "#tabPane";

    public OverviewPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }
    
    public boolean isTabSelected() {
        return ((TabPane) getNode(TAB_PANEL_ROOT_FIELD_ID)).getSelectionModel().isSelected(MainWindow.OVERVIEW_PANEL_TAB_PANE_INDEX);
    }

}
