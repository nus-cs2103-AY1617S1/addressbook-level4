package guitests;

import guitests.guihandles.HelpWindowHandle;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

//@@author A0148031R
public class HelpWindowTest extends ToDoListGuiTest {

    @Test
    public void openHelpWindow() {
        
        assertHelpWindowOpen(mainMenu.openHelpWindowFromMenu());
        
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        assertHelpWindowOpen(commandBox.runHelpCommand());

    }

    @Test
    public void closeHelpWindow() {
        commandBox.runHelpCommand();
        assertHelpWindowClose(mainMenu.closeHelpWindowUsingAccelerator());
    }
    
    // Tests Ctrl-H to switch between mainwindow and helpwindow
    @Test
    public void toggleHelpWindow() {
        assertHelpWindowClose(mainMenu.toggleHelpWindow());
    }

    private void assertHelpWindowClose(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowClose());
    }
    
    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}
