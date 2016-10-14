package guitests.guihandles;

import guitests.GuiRobot;
import harmony.mastermind.TestApp;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

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

    private void useF1Accelerator() {
        guiRobot.push(KeyCode.F1);
        guiRobot.sleep(500);
    }
}
