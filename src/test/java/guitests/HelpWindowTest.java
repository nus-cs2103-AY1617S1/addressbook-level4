package guitests;

import static org.junit.Assert.assertTrue;

import guitests.guihandles.HelpWindowHandle;

// Comment: At this stage we do not use a help window. @@author A0139661Y
public class HelpWindowTest extends ToDoListGuiTest {

//    @Test
//    public void openHelpWindow() {
//
//        taskListPanel.clickOnListView();
//
//        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());
//
//        assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());
//
//        assertHelpWindowOpen(commandBox.runHelpCommand());
//
//    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}
