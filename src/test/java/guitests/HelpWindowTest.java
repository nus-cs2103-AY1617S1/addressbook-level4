package guitests;

import guitests.guihandles.HelpWindowHandle;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HelpWindowTest extends AddressBookGuiTest {

    //TODO Help window test
    //@Test
    public void openHelpWindow() {

        datedListPanel.clickOnListView();

        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());

        assertHelpWindowOpen(commandBox.runHelpCommand());

    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}
