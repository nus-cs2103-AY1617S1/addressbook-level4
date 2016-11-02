package guitests;

import guitests.guihandles.HelpWindowHandle;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HelpWindowTest extends TaskBookGuiTest {

    //TODO Help window test
    @Test
    public void openHelpWindow() {

        //assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());
        //assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());

        assertHelpWindowOpen(commandBox.runHelpCommand());

    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}
