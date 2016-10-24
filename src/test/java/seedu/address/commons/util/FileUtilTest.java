package seedu.address.commons.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.SerializableTestClass;
import seedu.address.testutil.TestUtil;

public class FileUtilTest {
    private static final File SERIALIZATION_FILE = new File(TestUtil.getFilePathInSandboxFolder("serialize.json"));
    private static final File TEST_FILE = new File(TestUtil.getFilePathInSandboxFolder("test.json"));


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getPath(){

        // valid case
        assertEquals("folder" + File.separator + "sub-folder", FileUtil.getPath("folder/sub-folder"));

        // null parameter -> assertion failure
        thrown.expect(AssertionError.class);
        FileUtil.getPath(null);

        // no forwards slash -> assertion failure
        thrown.expect(AssertionError.class);
        FileUtil.getPath("folder");
    }

    @Test
    public void serializeObjectToJsonFile_noExceptionThrown() throws IOException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();

        FileUtil.serializeObjectToJsonFile(SERIALIZATION_FILE, serializableTestClass);

        assertEquals(FileUtil.readFromFile(SERIALIZATION_FILE), SerializableTestClass.JSON_STRING_REPRESENTATION);
    }

    @Test
    public void deserializeObjectFromJsonFile_noExceptionThrown() throws IOException {
        FileUtil.writeToFile(SERIALIZATION_FILE, SerializableTestClass.JSON_STRING_REPRESENTATION);

        SerializableTestClass serializableTestClass = FileUtil
                .deserializeObjectFromJsonFile(SERIALIZATION_FILE, SerializableTestClass.class);

        assertEquals(serializableTestClass.getName(), SerializableTestClass.getNameTestValue());
        assertEquals(serializableTestClass.getListOfLocalDateTimes(), SerializableTestClass.getListTestValues());
        assertEquals(serializableTestClass.getMapOfIntegerToString(), SerializableTestClass.getHashMapTestValues());
    }
    
    @Test
    public void createIfMissing_missingFile() throws IOException {
        TEST_FILE.delete();
        FileUtil.createIfMissing(TEST_FILE);
        assertTrue(TEST_FILE.exists());
    }
      
    @Test
    public void createIfMissing_exisitingFile() throws IOException {
        FileUtil.createIfMissing(TEST_FILE);
        assertTrue(TEST_FILE.exists());
    }
    
    @Test
    public void createFile_existingFile() throws IOException {
        FileUtil.createFile(TEST_FILE);
        assertFalse(FileUtil.createFile(TEST_FILE));
    }
    
    @Test
    public void createFile_missingFile() throws IOException {
        TEST_FILE.delete();
        FileUtil.createFile(TEST_FILE);
        assertTrue(TEST_FILE.exists());
    }
    
    @Test
    public void createDirs_emptyFile() throws IOException {
        thrown.expect(IOException.class);
        FileUtil.createDirs(new File(""));
    }
    
    
}
