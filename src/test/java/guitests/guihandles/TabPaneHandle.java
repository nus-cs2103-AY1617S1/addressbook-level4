package guitests.guihandles;

import guitests.GuiRobot;
import harmony.mastermind.TestApp;
import harmony.mastermind.model.task.ReadOnlyTask;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TabPaneHandle extends GuiHandle {

    private static final String TAB_PANE_ID = "#tabPane";
    
    public TabPaneHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }
    
    public String getCurrentTab() {
        return getTabPane().getSelectionModel().getSelectedItem().getText();
    }
    
    private TabPane getTabPane() {
        return (TabPane) getNode(TAB_PANE_ID);

    }
    
    

}
