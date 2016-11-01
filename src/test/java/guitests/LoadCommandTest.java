package guitests;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.agendum.commons.util.FileUtil;
import seedu.agendum.logic.commands.LoadCommand;
import seedu.agendum.model.ToDoList;
import seedu.agendum.model.task.Name;
import seedu.agendum.model.task.Task;
import seedu.agendum.storage.XmlToDoListStorage;

//@@author A0148095X
public class LoadCommandTest extends ToDoListGuiTest {

    private final String command = LoadCommand.COMMAND_WORD + " ";
    private final String fileThatExists = "data/test/FileThatExists.xml";
    private final String fileThatDoesNotExist = "data/test/DoesNotExist.xml";
    private final String fileInWrongFormat = "data/test/WrongFormat.xml";
    private final String missingFileType = "data/test/invalid";
    private final String missingFileName = "data/test/.bad";    

    @Before
    public void setup() throws Exception {
        super.setup();    

        // setup storage file
        Task toBeAdded = new Task(new Name("test"));
        ToDoList expectedTDL = new ToDoList();
        expectedTDL.addTask(toBeAdded);
        XmlToDoListStorage xmltdls = new XmlToDoListStorage(fileThatExists);
        xmltdls.saveToDoList(expectedTDL);
        
        // create empty file
        FileUtil.createFile(new File(fileInWrongFormat)); 
    }
    
    @After
    public void clean() throws Exception {
        // cleanup
        FileUtil.deleteFile(fileThatExists);
        FileUtil.deleteFile(fileInWrongFormat);
    }

    @Test
    public void load_pathValidFileExists_messageSuccess() {
        // load from an existing file
        commandBox.runCommand(command + fileThatExists);
        assertResultMessage(String.format(LoadCommand.MESSAGE_SUCCESS, fileThatExists));
    }

    @Test
    public void load_pathValidFileDoesNotExist_messageFileDoesNotExist() {
        // load from a non-existing file
        commandBox.runCommand(command + fileThatDoesNotExist);
        assertResultMessage(String.format(LoadCommand.MESSAGE_FILE_DOES_NOT_EXIST, fileThatDoesNotExist));
    }

    @Test
    public void load_pathValidFileWrongFormat_messageFileWrongFormat() {
        // file in wrong format
        commandBox.runCommand(command + fileInWrongFormat);
        assertResultMessage(String.format(LoadCommand.MESSAGE_FILE_WRONG_FORMAT, fileInWrongFormat));
    }

    @Test
    public void load_fileTypeInvalid_messagePathInvalid() {
        // invalid file type
        commandBox.runCommand(command + missingFileType);
        assertResultMessage(String.format(LoadCommand.MESSAGE_PATH_INVALID, missingFileType));
    }

    @Test
    public void load_fileNameInvalid_messagePathInvalid() {
        // invalid file name
        commandBox.runCommand(command + missingFileName);
        assertResultMessage(String.format(LoadCommand.MESSAGE_PATH_INVALID, missingFileName));
    }
}
