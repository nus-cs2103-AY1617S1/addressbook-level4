package seedu.agendum.commons.util;


import org.junit.Test;

import seedu.agendum.commons.exceptions.FileDeletionException;
import seedu.agendum.testutil.SerializableTestClass;
import seedu.agendum.testutil.TestUtil;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileUtilTest {
    private static final File SERIALIZATION_FILE = new File(TestUtil.getFilePathInSandboxFolder("serialize.json"));

    //@@author A0148095X
    @Test
    public void isFileExists() throws IOException, FileDeletionException {
        String filePath = "test.file";
        File file = new File(filePath);
        
        // file does not exist
        assertFalse(FileUtil.isFileExists(file));
        assertFalse(FileUtil.isFileExists(filePath));
        
        // create the file
        FileUtil.createFile(file);
        
        // file exists
        assertTrue(FileUtil.isFileExists(file));
        assertTrue(FileUtil.isFileExists(filePath));
        
        // delete file
        FileUtil.deleteFile(filePath);
    }
    
    
    @Test 
    public void createFile() throws IOException {
        File validFile = new File("test.file");
        File validFileWithParentDirectories = new File("test/test1/test2/test.file");

        // File does not exist
        assertTrue(FileUtil.createFile(validFile));
        
        // File exists
        assertFalse(FileUtil.createFile(validFile));
        
        // File with many parent directories that do not exist
        assertTrue(FileUtil.createFile(validFileWithParentDirectories));
        
        // File with many parent directories that exist
        assertFalse(FileUtil.createFile(validFileWithParentDirectories));
        
        // cleanup the files
        validFile.delete();
        validFileWithParentDirectories.delete();
    }
    
    @Test(expected = AssertionError.class)
    public void deleteFileAtPathInvalidFilePath() throws FileDeletionException, IOException {
        // invalid filepath
        FileUtil.deleteFile(null);
    }

    @Test
    public void deleteFileAtPathValid() throws FileDeletionException, IOException {
        // able to delete
        File file = new File("test.file");
        FileUtil.createFile(file);
        FileUtil.deleteFile(file.getPath());
        assertFalse(FileUtil.isFileExists(file));
    }
    
    @Test
    public void isPathAvailable_pathAvailable() throws IOException, FileDeletionException {
        String availablePath = "testpath/test.txt";
        File file = new File(availablePath);
        
        // Path available
        // file does not exist
        assertTrue(FileUtil.isPathAvailable(availablePath));
        // file exists
        FileUtil.createFile(file);
        assertTrue(FileUtil.isPathAvailable(availablePath));
        
        // delete the file
        FileUtil.deleteFile(availablePath);
    }

    //@@author
    @Test(expected = AssertionError.class)
    public void getPathNullParameter(){
        // valid case
        assertEquals("folder" + File.separator + "sub-folder", FileUtil.getPath("folder/sub-folder"));

        // null parameter -> assertion failure
        FileUtil.getPath(null);
    }

    @Test(expected = AssertionError.class)
    public void getPathNoForwardSlash(){
        // valid case
        assertEquals("folder" + File.separator + "sub-folder", FileUtil.getPath("folder/sub-folder"));

        // no forwards slash -> assertion failure
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
}
