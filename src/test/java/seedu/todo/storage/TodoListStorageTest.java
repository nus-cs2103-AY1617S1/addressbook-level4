package seedu.todo.storage;

import static org.junit.Assert.*;
import static seedu.todo.testutil.TestUtil.isShallowEqual;
import static org.mockito.Mockito.*;


import java.io.IOException;
import java.util.Collections;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.model.ImmutableTodoList;
import seedu.todo.model.task.Task;

public class TodoListStorageTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Rule 
    public MockitoRule mockito = MockitoJUnit.rule();

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/TodoListStorageTest/");
    private static final String TEST_DATA_FILE = "TestTodoList.xml";

    @Mock private ImmutableTodoList original;
    private String filePath;
    private TodoListStorage todoListStorage;

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER + prefsFileInTestDataFolder : null;
    }

    private java.util.Optional<ImmutableTodoList> readTodoList(String filePath) throws Exception {
        return new TodoListStorage(filePath).read(addToTestDataPathIfNotNull(filePath));
    }
    
    @Before
    public void setUp() {
        filePath = testFolder.getRoot().getPath() + TEST_DATA_FILE;
        todoListStorage = new TodoListStorage(filePath);
        when(original.getTasks()).thenReturn(Collections.emptyList());
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
    public void testEmptySave() throws Exception {
        todoListStorage.save(original);
        ImmutableTodoList readBack = todoListStorage.read(filePath).get();
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testDifferentTodoList() throws Exception {
        todoListStorage.save(original);
        ImmutableTodoList readBack = todoListStorage.read(filePath).get();
        when(original.getTasks()).thenReturn(ImmutableList.of(new Task("Test")));
        assertFalse(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testReadAndSave() throws Exception {
        when(original.getTasks()).thenReturn(ImmutableList.of(new Task("Test")));
        // Save in new file and read back
        todoListStorage.save(original);
        ImmutableTodoList readBack = todoListStorage.read(filePath).get();
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testUpdateAndSave() throws Exception {
        // Save in new file
        todoListStorage.save(original);
        
        // Modify data, overwrite exiting file, and read back
        when(original.getTasks()).thenReturn(ImmutableList.of(new Task("Test")));
        todoListStorage.save(original);

        ImmutableTodoList readBack = todoListStorage.read(filePath).get();
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testReadAndSaveWithoutPath() throws Exception {
        // Save in new file
        todoListStorage.save(original);

        ImmutableTodoList readBack = todoListStorage.read().get();
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testSaveNullTodoList() throws IOException {
        thrown.expect(AssertionError.class);
        todoListStorage.save(null);
    }

    @Test
    public void testSaveNullFilePath() throws IOException {
        thrown.expect(AssertionError.class);
        todoListStorage.save(original, null);
    }

}
