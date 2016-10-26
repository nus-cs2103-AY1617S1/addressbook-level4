package seedu.agendum.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.agendum.commons.exceptions.DataConversionException;
import seedu.agendum.commons.util.FileUtil;
import seedu.agendum.model.ToDoList;
import seedu.agendum.model.task.Task;
import seedu.agendum.model.ReadOnlyToDoList;
import seedu.agendum.testutil.TypicalTestTasks;

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
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readToDoList("NotXmlFormatToDoList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveToDoList_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempToDoList.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        ToDoList original = td.getTypicalToDoList();
        XmlToDoListStorage xmlToDoListStorage = new XmlToDoListStorage(filePath);

        //Save in new file and read back
        xmlToDoListStorage.saveToDoList(original, filePath);
        ReadOnlyToDoList readBack = xmlToDoListStorage.readToDoList(filePath).get();
        assertEquals(original, new ToDoList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.hoon));
        original.removeTask(new Task(TypicalTestTasks.alice));
        xmlToDoListStorage.saveToDoList(original, filePath);
        readBack = xmlToDoListStorage.readToDoList(filePath).get();
        assertEquals(original, new ToDoList(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestTasks.ida));
        xmlToDoListStorage.saveToDoList(original); //file path not specified
        readBack = xmlToDoListStorage.readToDoList().get(); //file path not specified
        assertEquals(original, new ToDoList(readBack));

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
        saveToDoList(new ToDoList(), null);
    }

    //@@author A0148095X
    @Test
    public void setToDoListFilePath() {
        String filePath = testFolder.getRoot().getPath() + "TempToDoList.xml";
        XmlToDoListStorage xmlToDoListStorage = new XmlToDoListStorage(filePath);

        // null
        thrown.expect(AssertionError.class);
        xmlToDoListStorage.setToDoListFilePath(null);

        // empty string
        thrown.expect(AssertionError.class);
        xmlToDoListStorage.setToDoListFilePath("");

        // invalid file path
        thrown.expect(AssertionError.class);
        xmlToDoListStorage.setToDoListFilePath("1:/.xml");
        
        // valid file path
        String validPath = "test/test.xml";
        xmlToDoListStorage.setToDoListFilePath(validPath);
        assertEquals(validPath, xmlToDoListStorage.getToDoListFilePath());
    }


}
