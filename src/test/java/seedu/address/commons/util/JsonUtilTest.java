package seedu.address.commons.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import seedu.address.testutil.SerializableTestClass;
import seedu.address.testutil.TestUtil;

/**
 * Tests JSON Read and Write
 */
public class JsonUtilTest {
    private static final File SERIALIZATION_FILE = new File(TestUtil.getFilePathInSandboxFolder("serialize.json"));

    @Test 
    public void jsonUtil_readJsonStringToObjectInstance_correctObject() throws IOException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();
        assertEquals(JsonUtil.fromJsonString(FileUtil.readFromFile(SERIALIZATION_FILE), SerializableTestClass.class), 
                serializableTestClass);
        
    }

//    @Test 
//    public void jsonUtil_writeThenReadObjectToJson_correctObject() throws IOException {
//        SerializableTestClass serializableTestClass = new SerializableTestClass();
//        serializableTestClass.setTestValues();
//        assertEquals(JsonUtil.fromJsonString(FileUtil.readFromFile(SERIALIZATION_FILE), SerializableTestClass.class), 
//                SerializableTestClass.JSON_STRING_REPRESENTATION);
//    }

}
