package seedu.todo.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.model.ReadOnlyToDoList;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.todo.testutil.TypicalTestTasks;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlToDoListStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlToDoListStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readToDoList_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readToDoList(null);
    }

    private java.util.Optional<ReadOnlyToDoList> readToDoList(String filePath) throws Exception {
        return new XmlToDoListStorage(filePath).readToDoList(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readToDoList("NonExistentFile.xml").isPresent());
    }
   

    @Test
    public void saveToDoList_nullToDoList_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveToDoList(null, "SomeFile.xml");
    }

    private void saveToDoList(ReadOnlyToDoList toDoList, String filePath) throws IOException {
        new XmlToDoListStorage(filePath).saveToDoList(toDoList, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveToDoList_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveToDoList(new DoDoBird(), null);
    }
    
    private DoDoBird addTaskToDoDoBird(ReadOnlyToDoList rotdl) throws DuplicateTaskException {
        DoDoBird readbackDdb = new DoDoBird();
        
        for (int i = rotdl.getTaskList().size() - 1; i >= 0; i--) {
            readbackDdb.addTask(new Task(rotdl.getTaskList().get(i)));
        }
        return readbackDdb;
    }


}
