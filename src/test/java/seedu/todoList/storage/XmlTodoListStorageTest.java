package seedu.todoList.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

//import seedu.address.model.AddressBook;
//import seedu.address.model.ReadOnlyAddressBook;
//import seedu.address.model.person.Person;
//import seedu.address.testutil.TypicalTestPersons;
//import seedu.address.model.AddressBook;
//import seedu.address.storage.XmlAddressBookStorage;
//import seedu.address.testutil.TypicalTestPersons;
import seedu.todoList.commons.exceptions.DataConversionException;
import seedu.todoList.commons.util.FileUtil;
import seedu.todoList.model.ReadOnlyTaskList;
//import seedu.todoList.model.ReadOnlyTodoList;
import seedu.todoList.model.TaskList;
import seedu.todoList.model.task.Todo;
import seedu.todoList.storage.XmlTodoListStorage;
import seedu.todoList.testutil.TypicalTestTask;

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


    private java.util.Optional<ReadOnlyTaskList> readTodoList(String filePath) throws Exception {
        return new XmlTodoListStorage(filePath).readTaskList(addToTestDataPathIfNotNull(filePath));

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
    //@@author A0132157M reused
    public void readAndSaveTodoList_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTodoList.xml";
        TypicalTestTask td = new TypicalTestTask();
        TaskList original = td.getTypicalTodoList();
        XmlTodoListStorage xmlTodoListStorage = new XmlTodoListStorage(filePath);

        //Save in new file and read back
        xmlTodoListStorage.saveTaskList(original, filePath);
        ReadOnlyTaskList readBack = xmlTodoListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Todo(TypicalTestTask.a6));
        original.removeTask(new Todo(TypicalTestTask.a1));
        xmlTodoListStorage.saveTaskList(original, filePath);
        readBack = xmlTodoListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        //Save and read without specifying file path
        original.addTask(new Todo(TypicalTestTask.a1));
        xmlTodoListStorage.saveTaskList(original); //file path not specified
        readBack = xmlTodoListStorage.readTaskList().get(); //file path not specified
        assertEquals(original, new TaskList(readBack));

    }

    @Test
    public void saveTodoList_nullTodoList_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTodoList(null, "SomeFile.xml");
    }


    private void saveTodoList(ReadOnlyTaskList TodoList, String filePath) throws IOException {
        new XmlTodoListStorage(filePath).saveTaskList(TodoList, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTodoList_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTodoList(new TaskList(), null);
    }


}
