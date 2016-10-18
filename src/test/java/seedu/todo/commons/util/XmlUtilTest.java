package seedu.todo.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.todo.testutil.TestUtil.isShallowEqual;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todo.model.AddressBook;
import seedu.todo.model.ImmutableTodoList;
import seedu.todo.model.TodoList;
import seedu.todo.storage.*;
import seedu.todo.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validTodoList.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempTodoList.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, AddressBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, AddressBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, AddressBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableTodoList dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableTodoList.class);
        assertEquals(6, dataFromFile.getTasks().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new AddressBook());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new AddressBook());
    }
  
    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        MovableStorage<ImmutableTodoList> storage = new TodoListStorage(TEMP_FILE.getAbsolutePath());
        XmlSerializableTodoList dataToWrite = new XmlSerializableTodoList(new TodoList(storage));
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableTodoList dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTodoList.class);

        assertTrue(isShallowEqual(dataFromFile.getTasks(), dataToWrite.getTasks()));

        TodoList todoList = new TodoList(storage);
        todoList.add("test");
        dataToWrite = new XmlSerializableTodoList(todoList);
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);

        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTodoList.class);
        assertTrue(isShallowEqual(dataFromFile.getTasks(), dataToWrite.getTasks()));
    }
}
