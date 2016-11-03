package guitests;

import org.junit.Test;

import javafx.scene.input.KeyCode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*
 * GUI test for Main Window
 * //@@author A0121608N
 */

public class MainWindowTest extends TaskBookGuiTest {
    private static final String COMMAND_TEXT_FIELD_ID = "#commandTextField";
    private static final String RESULT_DISPLAY_ID = "#resultDisplay";
    private static final String TASKLIST_VIEW_ID = "#taskListView";
    private static final String EVENTLIST_VIEW_ID = "#eventListView";
    
    
    // test for TAB key press at commandTextField
    @Test
    public void tabKeyPress_commandTextField_moveOne(){
        mainGui.mouseClick(COMMAND_TEXT_FIELD_ID);
        mainGui.keyPress(KeyCode.TAB);
        assertFocusSuccess(RESULT_DISPLAY_ID);
    }
    
    @Test
    public void tabKeyPress_commandTextField_moveTwoOne(){
        mainGui.mouseClick(COMMAND_TEXT_FIELD_ID);
        mainGui.keyPress(KeyCode.TAB);
        mainGui.keyPress(KeyCode.TAB);
        assertFocusSuccess(TASKLIST_VIEW_ID);
        mainGui.keyPress(KeyCode.TAB);
        assertFocusSuccess(EVENTLIST_VIEW_ID);
    }
    
    @Test
    public void tabKeyPress_commandTextField_moveFour(){
        mainGui.mouseClick(COMMAND_TEXT_FIELD_ID);
        mainGui.keyPress(KeyCode.TAB);
        mainGui.keyPress(KeyCode.TAB);
        mainGui.keyPress(KeyCode.TAB);
        mainGui.keyPress(KeyCode.TAB);
        assertFocusSuccess(COMMAND_TEXT_FIELD_ID);
    }
    
    // test for 3 x TAB key press at resultDisplay
    @Test
    public void tabKeyPress_resultDisplay_success(){
        mainGui.mouseClick(RESULT_DISPLAY_ID);
        mainGui.keyPress(KeyCode.TAB);
        mainGui.keyPress(KeyCode.TAB);
        mainGui.keyPress(KeyCode.TAB);
        assertFocusSuccess(COMMAND_TEXT_FIELD_ID);
    }
    
    
    
    private void assertFocusSuccess(String query){
        assertTrue(mainGui.isFocused(query));
    }
    
   

}
