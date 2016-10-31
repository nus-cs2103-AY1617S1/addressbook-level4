package seedu.Tdoo.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;


import seedu.Tdoo.commons.exceptions.DataConversionException;
import seedu.Tdoo.commons.util.FileUtil;
import seedu.Tdoo.model.ReadOnlyTaskList;
import seedu.Tdoo.model.TaskList;
import seedu.Tdoo.model.task.Deadline;
import seedu.Tdoo.model.task.Event;
import seedu.Tdoo.model.task.Todo;
import seedu.Tdoo.model.task.attributes.*;
import seedu.Tdoo.storage.XmlTodoListStorage;
import seedu.Tdoo.testutil.TypicalTestDeadline;
import seedu.Tdoo.testutil.TypicalTestEvent;
import seedu.Tdoo.testutil.TypicalTestTask;


import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlTodoListStorageTest implements TaskListStorage {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTodoListStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTodoList_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(NullPointerException.class); // AssertionError
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
        readTodoList(".xml");

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
        original.addTask(new Todo(new Name("todo 123"), new StartDate("11-11-2016"), new EndDate("12-11-2016"), new Priority("1"), ("false")));
        original.removeTask(new Todo(new Name("todo 123"), new StartDate("11-11-2016"), new EndDate("12-11-2016"), new Priority("1"), ("false")));
        xmlTodoListStorage.saveTaskList(original, filePath);
        readBack = xmlTodoListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        //Save and read without specifying file path
        original.addTask(new Todo(new Name("todo 123"), new StartDate("11-11-2016"), new EndDate("12-11-2016"), new Priority("1"), ("false")));
        xmlTodoListStorage.saveTaskList(original); //file path not specified
        readBack = xmlTodoListStorage.readTaskList().get(); //file path not specified
        assertEquals(original, new TaskList(readBack));
    }
    
    @Test
    //@@author A0132157M reused
    public void readAndSaveEventList_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempEventList.xml";
        TypicalTestEvent td = new TypicalTestEvent();
        TaskList original = td.getTypicalEventList();
        XmlEventListStorage xmlEventListStorage = new XmlEventListStorage(filePath);

        //Save in new file and read back
        xmlEventListStorage.saveTaskList(original, filePath);
        ReadOnlyTaskList readBack = xmlEventListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Event(new Name("todo 123"), new StartDate("11-11-2016"), new EndDate("12-11-2016"), new StartTime("01:00"), new EndTime("01:30"), ("false")));
        original.removeTask(new Event(new Name("todo 123"), new StartDate("11-11-2016"), new EndDate("12-11-2016"), new StartTime("01:00"), new EndTime("01:30"), ("false")));
        xmlEventListStorage.saveTaskList(original, filePath);
        readBack = xmlEventListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        //Save and read without specifying file path
        original.addTask(new Event(new Name("todo 123"), new StartDate("11-11-2016"), new EndDate("12-11-2016"), new StartTime("01:00"), new EndTime("01:30"), ("false")));
        xmlEventListStorage.saveTaskList(original); //file path not specified
        readBack = xmlEventListStorage.readTaskList().get(); //file path not specified
        assertEquals(original, new TaskList(readBack));
    }
    
    @Test
    //@@author A0132157M reused
    public void readAndSaveDeadlineList_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempDeadlineList.xml";
        TypicalTestDeadline td = new TypicalTestDeadline();
        TaskList original = td.getTypicalDeadlineList();
        XmlDeadlineListStorage xmlDeadlineListStorage = new XmlDeadlineListStorage(filePath);

        //Save in new file and read back
        xmlDeadlineListStorage.saveTaskList(original, filePath);
        ReadOnlyTaskList readBack = xmlDeadlineListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Deadline(new Name("todo 123"), new StartDate("11-11-2016"), new EndTime("01:30"), ("false")));
        original.removeTask(new Deadline(new Name("todo 123"), new StartDate("11-11-2016"), new EndTime("01:30"), ("false")));
        xmlDeadlineListStorage.saveTaskList(original, filePath);
        readBack = xmlDeadlineListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        //Save and read without specifying file path
        original.addTask(new Deadline(new Name("todo 123"), new StartDate("11-11-2016"), new EndTime("01:30"), ("false")));
        xmlDeadlineListStorage.saveTaskList(original); //file path not specified
        readBack = xmlDeadlineListStorage.readTaskList().get(); //file path not specified
        assertEquals(original, new TaskList(readBack));
    }

    @Test
    public void saveTodoList_nullTodoList_assertionFailure() throws IOException {
        thrown.expect(NullPointerException.class);
        saveTaskList(null, ".xml");
    }


    public void saveTaskList(ReadOnlyTaskList TodoList, String filePath) throws IOException {
        new XmlTodoListStorage(filePath).saveTaskList(TodoList, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTodoList_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(NullPointerException.class);
        saveTaskList(new TaskList(), null);
    }


    @Override
    public String getTaskListFilePath() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void setTaskListFilePath(String filePath) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Optional<ReadOnlyTaskList> readTaskList(String filePath) throws DataConversionException, IOException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void saveTaskList(ReadOnlyTaskList TaskList) throws IOException {
        // TODO Auto-generated method stub
        
    }


//    @Override
//    public void saveTaskList(ReadOnlyTaskList TaskList, String filePath) throws IOException {
//        // TODO Auto-generated method stub
//        
//    }


}
