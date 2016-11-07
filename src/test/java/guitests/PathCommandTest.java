package guitests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import seedu.taskitty.logic.commands.PathCommand;

//@@author A0135793W
public class PathCommandTest extends TaskManagerGuiTest {

    @Test
    public void path() {
        
        //delete the temp file to ensure test runs successfully on 
        // a non existent file
        File tempFile = new File("temp.xml");
        tempFile.delete();
        
        commandBox.runCommand("path temp.xml");
        assertResultMessage(String.format(PathCommand.MESSAGE_SAVE_SUCCESS, "temp.xml"));
        
        // check that the temp file is successfully created
        // and delete temp file after testing is complete
        assertTrue(tempFile.delete());
        
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
