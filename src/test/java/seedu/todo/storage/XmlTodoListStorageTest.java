package seedu.todo.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.todo.testutil.TestUtil.isShallowEqual;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.model.ImmutableTodoList;
import seedu.todo.model.TodoList;
import seedu.todo.testutil.TestTodoList;

public class XmlTodoListStorageTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTodoListStorageTest/");
    private static final String TEST_DATA_FILE = "TestTodoList.xml";

    private String filePath;
    private TodoList original;
    private XmlTodoListStorage xmlTodoListStorage;

    @Before
    public void setUp() {

        filePath = testFolder.getRoot().getPath() + TEST_DATA_FILE;
        original = new TodoList(new MockStorage(new TestTodoList()));
        xmlTodoListStorage = new XmlTodoListStorage(filePath);
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER + prefsFileInTestDataFolder : null;
    }

    private java.util.Optional<ImmutableTodoList> readTodoList(String filePath) throws Exception {
        return new XmlTodoListStorage(filePath).readTodoList(addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void testReadNullTodoListTestFile() throws Exception {
        thrown.expect(AssertionError.class);
        readTodoList(null);
    }

    @Test
    public void testMissingFile() throws Exception {
        assertFalse(readTodoList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void testReadNonXmlFormatFile() throws Exception {
        thrown.expect(DataConversionException.class);
        readTodoList("NotXmlFormatTodoList.xml");
    }

    @Test
    public void testEmptySaveTodoList() throws Exception {
        xmlTodoListStorage.saveTodoList(original, filePath);
        ImmutableTodoList readBack = xmlTodoListStorage.readTodoList(filePath).get();
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testDifferentTodoList() throws Exception {
        xmlTodoListStorage.saveTodoList(original, filePath);
        ImmutableTodoList readBack = xmlTodoListStorage.readTodoList(filePath).get();

        original.add("test");
        assertFalse(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testReadAndSaveTodoList() throws Exception {
        original.add("test");

        // Save in new file and read back
        xmlTodoListStorage.saveTodoList(original, filePath);
        ImmutableTodoList readBack = xmlTodoListStorage.readTodoList(filePath).get();
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testUpdateAndSaveTodoList() throws Exception {
        original.add("test");

        // Save in new file
        xmlTodoListStorage.saveTodoList(original, filePath);

        // Modify data, overwrite exiting file, and read back
        original.add("test1");
        xmlTodoListStorage.saveTodoList(original, filePath);

        ImmutableTodoList readBack = xmlTodoListStorage.readTodoList(filePath).get();
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testReadAndSaveTodoListWithoutPath() throws Exception {
        original.add("test");

        // Save in new file
        xmlTodoListStorage.saveTodoList(original);

        ImmutableTodoList readBack = xmlTodoListStorage.readTodoList().get();
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    private void saveTodoList(ImmutableTodoList addressBook, String filePath) throws IOException {
        new XmlTodoListStorage(filePath).saveTodoList(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void testSaveNullTodoList() throws IOException {
        thrown.expect(AssertionError.class);
        saveTodoList(null, "SomeFile.xml");
    }

    @Test
    public void testSaveTodoListNullFilePath() throws IOException {
        thrown.expect(AssertionError.class);
        saveTodoList(original, null);
    }

}
