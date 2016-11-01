package guitests;

import org.junit.Test;

import seedu.taskitty.logic.commands.PathCommand;

//@@author A0135793W
public class PathCommandTest extends TaskManagerGuiTest {

    @Test
    public void save() {
        commandBox.runCommand("path temp.xml");
        assertResultMessage(String.format(PathCommand.MESSAGE_SUCCESS, "temp.xml"));
        
        //no filepath
        commandBox.runCommand("path");
        assertResultMessage(String.format(PathCommand.MESSAGE_INVALID_MISSING_FILEPATH, 
                PathCommand.MESSAGE_VALID_FILEPATH_USAGE));
        
        commandBox.runCommand("path ");
        assertResultMessage(String.format(PathCommand.MESSAGE_INVALID_MISSING_FILEPATH, 
                PathCommand.MESSAGE_VALID_FILEPATH_USAGE));
        
        //file name does not end with .xml
        commandBox.runCommand("path temp");
        assertResultMessage(String.format(PathCommand.MESSAGE_INVALID_FILEPATH, 
                PathCommand.MESSAGE_VALID_FILEPATH_USAGE));
        
        commandBox.runCommand("path t");
        assertResultMessage(String.format(PathCommand.MESSAGE_INVALID_FILEPATH, 
                PathCommand.MESSAGE_VALID_FILEPATH_USAGE));
        
        commandBox.runCommand("path temp.pdf");
        assertResultMessage(String.format(PathCommand.MESSAGE_INVALID_FILEPATH, 
                PathCommand.MESSAGE_VALID_FILEPATH_USAGE));
        
    }

}
