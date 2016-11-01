package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;
import seedu.agendum.TestApp;

import java.util.Arrays;

/**
 * Provides a handle to the main menu of the app.
 */
public class MainMenuHandle extends GuiHandle {
    public MainMenuHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public GuiHandle clickOn(String... menuText) {
        Arrays.stream(menuText).forEach((menuItem) -> guiRobot.clickOn(menuItem));
        return this;
    }
    
    public MainGuiHandle closeHelpWindowUsingAccelerator() {
        useAcceleratorToCloseHelpWindow();
        return new MainGuiHandle(guiRobot, primaryStage);
    }
    
    private void useAcceleratorToCloseHelpWindow() {
        guiRobot.push(new KeyCodeCombination(KeyCode.ESCAPE));
        guiRobot.sleep(500);
    }

}
