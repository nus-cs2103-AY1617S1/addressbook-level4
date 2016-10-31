package guitests;

import org.junit.Test;

import seedu.taskitty.logic.commands.SaveCommand;

//@@author A0135793W
public class SaveCommandTest extends TaskManagerGuiTest {

    @Test
    public void save() {
        commandBox.runCommand("save temp.xml");
        assertResultMessage(String.format(SaveCommand.MESSAGE_SUCCESS, "temp.xml"));
        
        //no filepath
        commandBox.runCommand("save");
        assertResultMessage(String.format(SaveCommand.MESSAGE_INVALID_MISSING_FILEPATH, 
                SaveCommand.MESSAGE_VALID_FILEPATH_USAGE));
        
        commandBox.runCommand("save ");
        assertResultMessage(String.format(SaveCommand.MESSAGE_INVALID_MISSING_FILEPATH, 
                SaveCommand.MESSAGE_VALID_FILEPATH_USAGE));
        
        //file name does not end with .xml
        commandBox.runCommand("save temp");
        assertResultMessage(String.format(SaveCommand.MESSAGE_INVALID_FILEPATH, 
                SaveCommand.MESSAGE_VALID_FILEPATH_USAGE));
        
        commandBox.runCommand("save t");
        assertResultMessage(String.format(SaveCommand.MESSAGE_INVALID_FILEPATH, 
                SaveCommand.MESSAGE_VALID_FILEPATH_USAGE));
        
        commandBox.runCommand("save temp.pdf");
        assertResultMessage(String.format(SaveCommand.MESSAGE_INVALID_FILEPATH, 
                SaveCommand.MESSAGE_VALID_FILEPATH_USAGE));
        
    }

}
