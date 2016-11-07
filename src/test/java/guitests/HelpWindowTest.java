package guitests;

import guitests.guihandles.HelpWindowHandle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HelpWindowTest extends SavvyTaskerGuiTest {

    @Test
    public void openHelpWindow() {

        taskListPanel.clickOnListView();

        // Feature removed
        //assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        // Feature removed
        //assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());

        assertHelpWindowOpen(commandBox.runHelpCommand());

    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}
