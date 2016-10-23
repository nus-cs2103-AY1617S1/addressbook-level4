package seedu.todo.storage;

import static org.junit.Assert.*;
import static seedu.todo.testutil.TestUtil.isShallowEqual;
import static org.mockito.Mockito.*;


import java.io.FileNotFoundException;
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
import seedu.todo.testutil.TaskFactory;

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
    private TodoListStorage storage;

    private String getFilePath(String filename) {
        return filename != null ? TEST_DATA_FOLDER + filename : null;
    }

    private ImmutableTodoList readTodoList(String filePath) throws Exception {
        return new TodoListStorage(filePath).read(getFilePath(filePath));
    }
    
    @Before
    public void setUp() {
        filePath = testFolder.getRoot().getPath() + TEST_DATA_FILE;
        storage = new TodoListStorage(filePath);
        when(original.getTasks()).thenReturn(Collections.emptyList());
    }

    @Test
    public void testReadNullTodoListTestFile() throws Exception {
        thrown.expect(AssertionError.class);
        readTodoList(null);
    }

    @Test
    public void testMissingFile() throws Exception {
        thrown.expect(FileNotFoundException.class);
        readTodoList("NonExistentFile.xml");
    }

    @Test
    public void testReadNonXmlFormatFile() throws Exception {
        thrown.expect(DataConversionException.class);
        readTodoList("NotXmlFormatTodoList.xml");
    }
    
    @Test
    public void testLocationNotChangedAfterError() throws Exception {
        String before = storage.getLocation();
        try {
            readTodoList("NotXmlFormatTodoList.xml");
        } catch (DataConversionException e) {
            assertEquals(before, storage.getLocation());
        }
    }

    @Test
    public void testEmptySave() throws Exception {
        storage.save(original);
        ImmutableTodoList readBack = storage.read(filePath);
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testReadAndSave() throws Exception {
        when(original.getTasks()).thenReturn(TaskFactory.list());
        // Save in new file and read back
        storage.save(original);
        ImmutableTodoList readBack = storage.read(filePath);
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testUpdateAndSave() throws Exception {
        // Save in new file
        storage.save(original);
        
        // Modify data, overwrite exiting file, and read back
        when(original.getTasks()).thenReturn(TaskFactory.list());
        storage.save(original);

        ImmutableTodoList readBack = storage.read(filePath);
        
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testReadAndSaveWithoutPath() throws Exception {
        // Save in new file
        storage.save(original);

        ImmutableTodoList readBack = storage.read();
        assertTrue(isShallowEqual(original.getTasks(), readBack.getTasks()));
    }

    @Test
    public void testSaveNullTodoList() throws IOException {
        thrown.expect(AssertionError.class);
        storage.save(null);
    }

    @Test
    public void testSaveNullFilePath() throws IOException {
        thrown.expect(AssertionError.class);
        storage.save(original, null);
    }
}
