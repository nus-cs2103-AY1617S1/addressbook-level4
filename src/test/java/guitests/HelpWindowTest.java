package guitests;

import guitests.guihandles.HelpWindowHandle;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@Deprecated
public class HelpWindowTest extends AddressBookGuiTest {

    @Test
    @Ignore
    public void openHelpWindow() {

        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());

    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}
