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
    public void readAndSaveToDoList_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempToDoList.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        DoDoBird original = td.getTypicalToDoList();
        XmlToDoListStorage xmlToDoListStorage = new XmlToDoListStorage(filePath);

        //Save in new file and read back
        xmlToDoListStorage.saveToDoList(original, filePath);
        ReadOnlyToDoList readBack = xmlToDoListStorage.readToDoList(filePath).get();
        DoDoBird readbackDdb = addTaskToDoDoBird(readBack);
        assertEquals(original, readbackDdb);

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.buyNoodles));
        original.deleteTask(new Task(TypicalTestTasks.buyNoodles));
        xmlToDoListStorage.saveToDoList(original, filePath);
        readBack = xmlToDoListStorage.readToDoList(filePath).get();
        readbackDdb = addTaskToDoDoBird(readBack);
        assertEquals(original, readbackDdb);

        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestTasks.buyCheese));
        xmlToDoListStorage.saveToDoList(original); //file path not specified
        readBack = xmlToDoListStorage.readToDoList().get(); //file path not specified
        readbackDdb = addTaskToDoDoBird(readBack);
        assertEquals(original, readbackDdb);

    }

    @Test
    public void saveToDoList_nullToDoList_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveToDoList(null, "SomeFile.xml");
    }

    private void saveToDoList(ReadOnlyToDoList addressBook, String filePath) throws IOException {
        new XmlToDoListStorage(filePath).saveToDoList(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveToDoList_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveToDoList(new DoDoBird(), null);
    }
    
    private DoDoBird addTaskToDoDoBird(ReadOnlyToDoList rotdl) throws DuplicateTaskException {
        DoDoBird readbackDdb = new DoDoBird();
        
        for (int i = rotdl.getTaskList().size() - 1 ; i >= 0 ; i--) {
            readbackDdb.addTask(new Task(rotdl.getTaskList().get(i)));
        }
        return readbackDdb;
    }


}
