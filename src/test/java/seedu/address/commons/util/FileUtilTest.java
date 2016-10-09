package seedu.address.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileUtilTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getPath_null(){
        // null parameter -> assertion failure
        thrown.expect(AssertionError.class);
        FileUtil.getPath(null);
    }

    @Test
    public void getPath_noForwardSlash(){
        // no forwards slash -> assertion failure
        thrown.expect(AssertionError.class);
        FileUtil.getPath("folder");
    }

    @Test
    public void getPath_valid() {
        // valid case
        assertEquals("folder" + File.separator + "sub-folder", FileUtil.getPath("folder/sub-folder"));
    }

    @Test
    public void serializeObjectToJsonFile_noExceptionThrown() throws IOException {
        JsonSerializable jsonSerializable = new JsonSerializable();
        jsonSerializable.setTestValues();

        File file = folder.newFile();
        FileUtil.serializeObjectToJsonFile(file, jsonSerializable);

        assertEquals(FileUtil.readFromFile(file), JsonSerializable.JSON_STRING_REPRESENTATION);
    }

    @Test
    public void deserializeObjectFromJsonFile_noExceptionThrown() throws IOException {
        File file = folder.newFile();

        FileUtil.writeToFile(file, JsonSerializable.JSON_STRING_REPRESENTATION);

        JsonSerializable jsonSerializable = FileUtil
                .deserializeObjectFromJsonFile(file, JsonSerializable.class);

        assertEquals(jsonSerializable.getName(), JsonSerializable.getNameTestValue());
        assertEquals(jsonSerializable.getListOfLocalDateTimes(), JsonSerializable.getListTestValues());
        assertEquals(jsonSerializable.getMapOfIntegerToString(), JsonSerializable.getHashMapTestValues());
    }
}
