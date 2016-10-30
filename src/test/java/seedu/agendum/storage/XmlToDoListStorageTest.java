package seedu.agendum.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.agendum.commons.exceptions.DataConversionException;
import seedu.agendum.commons.util.FileUtil;
import seedu.agendum.model.ReadOnlyToDoList;
import seedu.agendum.model.ToDoList;
import seedu.agendum.model.task.Task;
import seedu.agendum.testutil.TypicalTestTasks;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlToDoListStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlToDoListStorageTest/");

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test(expected = AssertionError.class)
    public void readToDoListNullFilePathAssertionFailure() throws Exception {
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
    public void readMissingFileEmptyResult() throws Exception {
        assertFalse(readToDoList("NonExistentFile.xml").isPresent());
    }

    @Test(expected = DataConversionException.class)
    public void readNotXmlFormatExceptionThrown() throws Exception {

        readToDoList("NotXmlFormatToDoList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveToDoListAllInOrderSuccess() throws Exception {
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

    @Test(expected = AssertionError.class)
    public void saveToDoListNullToDoListAssertionFailure() throws IOException {
        saveToDoList(null, "SomeFile.xml");
    }

    private void saveToDoList(ReadOnlyToDoList toDoList, String filePath) throws IOException {
        new XmlToDoListStorage(filePath).saveToDoList(toDoList, addToTestDataPathIfNotNull(filePath));
    }

    @Test(expected = AssertionError.class)
    public void saveToDoListNullFilePathAssertionFailure() throws IOException {
        saveToDoList(new ToDoList(), null);
    }

    //@@author A0148095X
    @Test(expected = AssertionError.class)
    public void setToDoListFilePathNull() {
        String filePath = testFolder.getRoot().getPath() + "TempToDoList.xml";
        XmlToDoListStorage xmlToDoListStorage = new XmlToDoListStorage(filePath);

        xmlToDoListStorage.setToDoListFilePath(null);
    }

    @Test(expected = AssertionError.class)
    public void setToDoListFilePathEmpty() {
        String filePath = testFolder.getRoot().getPath() + "TempToDoList.xml";
        XmlToDoListStorage xmlToDoListStorage = new XmlToDoListStorage(filePath);

        // empty string
        xmlToDoListStorage.setToDoListFilePath("");
    }

    @Test(expected = AssertionError.class)
    public void setToDoListFilePathInvalid() {
        String filePath = testFolder.getRoot().getPath() + "TempToDoList.xml";
        XmlToDoListStorage xmlToDoListStorage = new XmlToDoListStorage(filePath);

        // invalid file path
        xmlToDoListStorage.setToDoListFilePath("1:/.xml");
    }

    public void setToDoListFilePathValid() {
        String filePath = testFolder.getRoot().getPath() + "TempToDoList.xml";
        XmlToDoListStorage xmlToDoListStorage = new XmlToDoListStorage(filePath);

        // valid file path
        String validPath = "test/test.xml";
        xmlToDoListStorage.setToDoListFilePath(validPath);
        assertEquals(validPath, xmlToDoListStorage.getToDoListFilePath());
    }

}
