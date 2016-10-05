package seedu.Todo.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.todoList.commons.exceptions.DataConversionException;
import seedu.todoList.commons.util.FileUtil;
import seedu.todoList.model.ReadOnlyTodoList;
import seedu.todoList.model.TodoList;
import seedu.todoList.model.task.Task;
import seedu.todoList.storage.XmlTodoListStorage;
import seedu.todoList.testutil.TypicalTesttasks;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlTodoListStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTodoListStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTodoList_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTodoList(null);
    }

    private java.util.Optional<ReadOnlyTodoList> readTodoList(String filePath) throws Exception {
        return new XmlTodoListStorage(filePath).readTodoList(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTodoList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTodoList("NotXmlFormatTodoList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveTodoList_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTodoList.xml";
        TypicalTesttasks td = new TypicalTesttasks();
        TodoList original = td.getTypicalTodoList();
        XmlTodoListStorage xmlTodoListStorage = new XmlTodoListStorage(filePath);

        //Save in new file and read back
        xmlTodoListStorage.saveTodoList(original, filePath);
        ReadOnlyTodoList readBack = xmlTodoListStorage.readTodoList(filePath).get();
        assertEquals(original, new TodoList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addtask(new Task(TypicalTesttasks.hoon));
        original.removetask(new Task(TypicalTesttasks.alice));
        xmlTodoListStorage.saveTodoList(original, filePath);
        readBack = xmlTodoListStorage.readTodoList(filePath).get();
        assertEquals(original, new TodoList(readBack));

        //Save and read without specifying file path
        original.addtask(new Task(TypicalTesttasks.ida));
        xmlTodoListStorage.saveTodoList(original); //file path not specified
        readBack = xmlTodoListStorage.readTodoList().get(); //file path not specified
        assertEquals(original, new TodoList(readBack));

    }

    @Test
    public void saveTodoList_nullTodoList_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTodoList(null, "SomeFile.xml");
    }

    private void saveTodoList(ReadOnlyTodoList TodoList, String filePath) throws IOException {
        new XmlTodoListStorage(filePath).saveTodoList(TodoList, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTodoList_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTodoList(new TodoList(), null);
    }


}
