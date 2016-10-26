package guitests;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import seedu.agendum.commons.exceptions.FileDeletionException;
import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.commons.util.FileUtil;
import seedu.agendum.logic.commands.LoadCommand;
import seedu.agendum.model.ToDoList;
import seedu.agendum.model.task.Name;
import seedu.agendum.model.task.Task;
import seedu.agendum.storage.XmlToDoListStorage;

//@@author A0148095X
public class LoadCommandTest extends ToDoListGuiTest {

    private String command;
    
    @Override
    public void setup() throws Exception{
        super.setup();
        command = LoadCommand.COMMAND_WORD + " ";
    }
    
    @Test
    public void load_pathValid() throws IOException, FileDeletionException, IllegalValueException {
        String fileThatExists = "data/test/FileThatExists.xml";
        String fileThatDoesNotExist = "data/DoesNotExist.xml";
        String fileInWrongFormat = "data/WrongFormat.xml";
        
        // setup storage file
        Task toBeAdded = new Task(new Name("test"));
        ToDoList expectedTDL = new ToDoList();
        expectedTDL.addTask(toBeAdded);
        XmlToDoListStorage xmltdls = new XmlToDoListStorage(fileThatExists);
        xmltdls.saveToDoList(expectedTDL);
        
        // load from an existing file
        commandBox.runCommand(command + fileThatExists);
        assertResultMessage(String.format(LoadCommand.MESSAGE_SUCCESS, fileThatExists));
        
        // load from a non-existing file
        commandBox.runCommand(command + fileThatDoesNotExist);
        assertResultMessage(String.format(LoadCommand.MESSAGE_FILE_DOES_NOT_EXIST, fileThatDoesNotExist));
        
        // file in wrong format
        FileUtil.createFile(new File(fileInWrongFormat)); // create empty file
        commandBox.runCommand(command + fileInWrongFormat);
        assertResultMessage(String.format(LoadCommand.MESSAGE_FILE_WRONG_FORMAT, fileInWrongFormat));
        
        // cleanup
        FileUtil.deleteFile(fileInWrongFormat); 
        FileUtil.deleteFile(fileThatExists);
    }

    @Test
    public void load_pathInvalid(){
        String missingFileType = "test/invalid";
        String missingFileName = "test/.bad";
        
        // invalid file type
        commandBox.runCommand(command + missingFileType);
        assertResultMessage(String.format(LoadCommand.MESSAGE_PATH_INVALID, missingFileType));
        
        // invalid file name
        commandBox.runCommand(command + missingFileName);
        assertResultMessage(String.format(LoadCommand.MESSAGE_PATH_INVALID, missingFileName));
    }
}
