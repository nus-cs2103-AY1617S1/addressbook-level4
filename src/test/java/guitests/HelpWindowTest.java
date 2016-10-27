package guitests;

import guitests.guihandles.HelpWindowHandle;
import seedu.taskmanager.logic.commands.HelpCommand;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HelpWindowTest extends TaskManagerGuiTest {

    @Test
    public void openHelpWindow() {

        shortItemListPanel.clickOnListView();

        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());

        assertHelpWindowOpen(commandBox.runHelpShortCommand());

    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}
