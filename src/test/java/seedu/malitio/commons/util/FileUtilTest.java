package seedu.malitio.commons.util;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.malitio.testutil.SerializableTestClass;
import seedu.malitio.testutil.TestUtil;
import seedu.malitio.commons.util.FileUtil;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileUtilTest {
    private static final File SERIALIZATION_FILE = new File(TestUtil.getFilePathInSandboxFolder("serialize.json"));


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
    
    //@@author a0126633j
    @Test
    public void twoFilePathsAreEqual() throws IOException {
        assertTrue(FileUtil.twoFilePathsAreEqual("./data/test/", "data/../data/test/"));
        assertFalse(FileUtil.twoFilePathsAreEqual("./data/tests/", "data/"));
    }
}
