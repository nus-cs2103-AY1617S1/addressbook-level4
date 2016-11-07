package guitests;

import org.junit.Test;

import javafx.scene.input.KeyCode;

import static org.junit.Assert.assertTrue;

/**
 *  @@author A0121608N
 *  GUI test for Main Window
 *  
 */

public class MainWindowTest extends TaskBookGuiTest {
    private static final String COMMAND_TEXT_FIELD_ID = "#commandTextField";
    private static final String RESULT_DISPLAY_ID = "#resultDisplay";
    private static final String TASKLIST_VIEW_ID = "#taskListView";
    private static final String EVENTLIST_VIEW_ID = "#eventListView";
    
    
    // test for one TAB key press at commandTextField
    @Test
    public void tabKeyPress_moveToResultDisplay_success(){
        mainGui.mouseClick(COMMAND_TEXT_FIELD_ID);
        mainGui.keyPress(KeyCode.TAB);
        assertFocusSuccess(RESULT_DISPLAY_ID);
    }
    
    // verify that TAB key still works after reaching a new focus window
    @Test
    public void tabKeyPress_moveToListViews_success(){
        mainGui.mouseClick(COMMAND_TEXT_FIELD_ID);
        mainGui.keyPress(KeyCode.TAB);
        mainGui.keyPress(KeyCode.TAB);
        assertFocusSuccess(TASKLIST_VIEW_ID);
        mainGui.keyPress(KeyCode.TAB);
        assertFocusSuccess(EVENTLIST_VIEW_ID);
    }
    
    // verify that TAB key can traverse windows in one loop
    @Test
    public void tabKeyPress_moveToCommandTextFieldLoop_success(){
        mainGui.mouseClick(COMMAND_TEXT_FIELD_ID);
        mainGui.keyPress(KeyCode.TAB);
        mainGui.keyPress(KeyCode.TAB);
        mainGui.keyPress(KeyCode.TAB);
        mainGui.keyPress(KeyCode.TAB);
        assertFocusSuccess(COMMAND_TEXT_FIELD_ID);
    }
    
    // verify that TAB key can work from another window
    @Test
    public void tabKeyPress_moveToCommandTextField_success(){
        mainGui.mouseClick(RESULT_DISPLAY_ID);
        mainGui.keyPress(KeyCode.TAB);
        mainGui.keyPress(KeyCode.TAB);
        mainGui.keyPress(KeyCode.TAB);
        assertFocusSuccess(COMMAND_TEXT_FIELD_ID);
    }
    
    
    // helper function to check if focus is on a specified window
    private void assertFocusSuccess(String query){
        assertTrue(mainGui.isFocused(query));
    }
    
   

}
