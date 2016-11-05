package seedu.oneline.logic;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import seedu.oneline.logic.commands.CommandResult;
import seedu.oneline.logic.commands.LocationCommand;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.task.Task;

public class LocationCommandTest extends LogicTestManager {

    // @@author A0140156R
    
    @Test
    public void locationCommand_checkPath_pathShown() throws Exception {
        String path = this.model.getTaskBookFilePath();
        assertCommandBehavior("loc",
                LocationCommand.MESSAGE_LOCATION + path + ".",
                new TaskBook(), new ArrayList<Task>());
    }
    
    @Test
    public void locationCommand_setNonDirectoryPath_errorMessage() throws Exception {
        String path = "\\non-directory";
        File file = new File(path);
        assertCommandBehavior("loc " + path,
                String.format(LocationCommand.MESSAGE_SET_STORAGE_FAILURE_NOT_DIRECTORY, file.getAbsolutePath()),
                new TaskBook(), new ArrayList<Task>());
    }
    
    @Test
    public void locationCommand_setValidPath1_success() throws Exception {
        String path = "\\directory";
        File file = new File(path);
        if (!file.isDirectory()) {
            if (!file.mkdir()) { // cannot test with valid directory
                return;
            }
        }
        assertCommandBehavior("loc " + path,
                String.format(LocationCommand.MESSAGE_SET_STORAGE_SUCCESS, file.getAbsolutePath()),
                new TaskBook(), new ArrayList<Task>());
        file.delete();
    }
    
    // @@author A0121657H
    
    @Test
    public void locationCommand_setValidPath2_success() {
        String homeDir = System.getProperty("user.home");
        LocationCommand cmd = new LocationCommand(homeDir);
        CommandResult res = cmd.execute();
        String feedback = res.feedbackToUser;
        assertTrue(feedback.equals(String.format(LocationCommand.MESSAGE_SET_STORAGE_SUCCESS, homeDir)));        
    }
    
    @Test
    public void changeSaveLocation_invalidPath_invalidMessageShown() {
        LocationCommand cmd = new LocationCommand("!@#$%^&*");
        CommandResult res = cmd.execute();
        String feedback = res.feedbackToUser;
        assertTrue(feedback.equals(String.format(LocationCommand.MESSAGE_SET_STORAGE_FAILURE_PATH_INVALID, "!@#$%^&*")));        
    }
    
    // @@author
    
}
