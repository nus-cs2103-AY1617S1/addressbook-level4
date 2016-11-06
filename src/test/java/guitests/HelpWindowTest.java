package guitests;

import guitests.guihandles.HelpWindowHandle;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;



//@@author A0093960X
public class HelpWindowTest extends DearJimGuiTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test 
    public void helpCommand_helpCommandOnlyInUndoneList_helpWindowOpens() {
        assertHelpWindowOpenAndCloseIt(commandBox.runHelpCommand());
    }
    
    @Test 
    public void helpCommand_helpCommandOnlyInDoneList_helpWindowOpens() {
        commandBox.runCommand("list done");
        assertHelpWindowOpenAndCloseIt(commandBox.runHelpCommand());
    }
    
    @Test 
    public void helpCommand_helpCommandWithArgsInUndoneList_helpWindowOpens() {
        assertHelpWindowOpenAndCloseIt(commandBox.runHelpCommandWithArgs("123123123123"));
    }
    
    @Test 
    public void helpCommand_helpCommandWithArgsInDoneList_helpWindowOpens() {
        commandBox.runCommand("list done");
        assertHelpWindowOpenAndCloseIt(commandBox.runHelpCommandWithArgs("123123123123"));
    }
    
    @Test
    public void helpCommand_buttonPressWhileFocusOnHelpWindow_helpWindowCloses() {
        HelpWindowHandle helpWindow = commandBox.runHelpCommand();
        assertHelpWindowOpen(helpWindow);
        helpWindow.pressEnter();
        assertHelpWindowClosed(helpWindow);
    }

    private void assertHelpWindowOpenAndCloseIt(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
    
    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
    }
    
    private void assertHelpWindowClosed(HelpWindowHandle helpWindowHandle) {
        thrown.expect(IllegalStateException.class);
        helpWindowHandle.isWindowOpen();
    }
   
}