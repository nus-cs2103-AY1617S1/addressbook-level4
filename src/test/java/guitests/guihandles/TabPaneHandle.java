package guitests.guihandles;

import guitests.GuiRobot;
import harmony.mastermind.TestApp;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class TabPaneHandle extends GuiHandle {

    private static final String TAB_PANE_ID = "#tabPane";
    
    public TabPaneHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }
    
    public String getCurrentTab() {
        return getTabPane().getSelectionModel().getSelectedItem().getId();
    }
    
    private TabPane getTabPane() {
        return (TabPane) getNode(TAB_PANE_ID);

    }
    
    

}
