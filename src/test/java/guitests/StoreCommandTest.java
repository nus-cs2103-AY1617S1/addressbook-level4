package guitests;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import seedu.agendum.commons.core.Config;
import seedu.agendum.commons.exceptions.FileDeletionException;
import seedu.agendum.commons.util.FileUtil;
import seedu.agendum.logic.commands.StoreCommand;

//@@author A0148095X
public class StoreCommandTest extends ToDoListGuiTest {

    private final String validLocation = "data/test.xml";
    private final String badLocation = "test/.xml";
    private final String inaccessibleLocation = "C:/windows/system32/agendum/todolist.xml";
    
    @Test
    public void store_validLocation_messageSuccess() {
        //save to a valid directory
        commandBox.runCommand("store " + validLocation);
        assertResultMessage(String.format(StoreCommand.MESSAGE_SUCCESS, validLocation));        
    }
    
    @Test
    public void store_defaultLocation_messageSuccessDefaultLocation() {
        //save to default directory
        commandBox.runCommand("store default");
        assertResultMessage(String.format(StoreCommand.MESSAGE_LOCATION_DEFAULT, Config.DEFAULT_SAVE_LOCATION));
    }
    
    @Test
    public void store_invalidLocation_messageInvalidPath() {        
        //invalid Location
        commandBox.runCommand("store " + badLocation);
        assertResultMessage(StoreCommand.MESSAGE_PATH_WRONG_FORMAT);
    }
    
    @Test
    public void store_inaccessibleLocation_messageLocationInaccessible() {
        //inaccessible location
        commandBox.runCommand("store " + inaccessibleLocation);
        //assertResultMessage(StoreCommand.MESSAGE_LOCATION_INACCESSIBLE);
    }
    
    @Test
    public void store_fileExists_messageFileExists() throws IOException, FileDeletionException {     
        //file exists
        FileUtil.createIfMissing(new File(validLocation));
        commandBox.runCommand("store " + validLocation);
        assertResultMessage(StoreCommand.MESSAGE_FILE_EXISTS);
        FileUtil.deleteFile(validLocation);
        
    }
}