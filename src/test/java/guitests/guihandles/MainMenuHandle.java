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
    private static final String HELP = "Help";
    private static final String HELP_MENU_ITEM = "Help Ctrl-H";
    
    public MainMenuHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public GuiHandle clickOn(String... menuText) {
        Arrays.stream(menuText).forEach((menuItem) -> guiRobot.clickOn(menuItem));
        return this;
    }

    public HelpWindowHandle openHelpWindowFromMenu() {
        useMenuItemToOpenHelpWindow();
        return new HelpWindowHandle(guiRobot, primaryStage);
    }
    
    public HelpWindowHandle openHelpWindowUsingAccelerator() {
        useAcceleratorToOpenHelpWindow();
        return new HelpWindowHandle(guiRobot, primaryStage);
    }
    
    public HelpWindowHandle closeHelpWindowUsingAccelerator() {
        useAcceleratorToCloseHelpWindow();
        return new HelpWindowHandle(guiRobot, primaryStage);
    }
    
    public HelpWindowHandle toggleHelpWindow() {
        toggleBetweenHelpWindowAndMainWindow();
        return new HelpWindowHandle(guiRobot, primaryStage);
    }
    
    private void useMenuItemToOpenHelpWindow() {
        clickOn(HELP, HELP_MENU_ITEM);
    }
    
    private void useAcceleratorToOpenHelpWindow() {
        guiRobot.push(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));
        guiRobot.sleep(500);
    }
    
    private void useAcceleratorToCloseHelpWindow() {
        guiRobot.push(new KeyCodeCombination(KeyCode.ESCAPE));
        guiRobot.sleep(500);
    }
    
    private void toggleBetweenHelpWindowAndMainWindow() {
        KeyCodeCombination toggle = new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN);
        guiRobot.push(toggle);
        guiRobot.sleep(500);
        guiRobot.push(toggle);
        guiRobot.sleep(500);
    }

}
