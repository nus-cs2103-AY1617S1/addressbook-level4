package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import seedu.agendum.TestApp;

import java.util.Arrays;

//@@author A0148031R
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

    public HelpWindowHandle openHelpWindowUsingMenu() {
        clickOn("Help", "F5");
        return new HelpWindowHandle(guiRobot, primaryStage);
    }

    public HelpWindowHandle openHelpWindowUsingAccelerator() {
        useAcceleratorToOpenHelpWindow();
        return new HelpWindowHandle(guiRobot, primaryStage);
    }
    
    public MainGuiHandle closeHelpWindowUsingAccelerator() {
        useAcceleratorToCloseHelpWindow();
        return new MainGuiHandle(guiRobot, primaryStage);
    }

    private void useAcceleratorToOpenHelpWindow() {
        guiRobot.push(new KeyCodeCombination(KeyCode.F5));
        guiRobot.sleep(500);
    }
    
    private void useAcceleratorToCloseHelpWindow() {
        guiRobot.push(new KeyCodeCombination(KeyCode.ESCAPE));
        guiRobot.sleep(500);
    }

}
