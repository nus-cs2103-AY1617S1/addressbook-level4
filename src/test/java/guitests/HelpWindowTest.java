package guitests;

import guitests.guihandles.HelpWindowHandle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
//@@author A0139102U
public class HelpWindowTest extends DailyPlannerGuiTest {

    @Test
    public void openHelpWindow() {

        taskListPanel.clickOnListView();

        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());

        assertHelpWindowOpen(commandBox.runHelpCommand());

    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}
