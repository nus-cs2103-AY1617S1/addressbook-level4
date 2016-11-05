package seedu.agendum.commons.util;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.agendum.commons.exceptions.FileDeletionException;
import seedu.agendum.testutil.SerializableTestClass;
import seedu.agendum.testutil.TestUtil;

public class FileUtilTest {
    private static final File SERIALIZATION_FILE = new File(TestUtil.getFilePathInSandboxFolder("serialize.json"));

    private String filePathThatExists;
    private File fileThatExists;
    
    private String filePathThatDoesNotExist;
    private File fileThatDoesNotExist;

    private String filePathToBeDeleted;
    private File fileToBeDeleted;
    
    private String filePathWithParentDirectories;
    private File fileWithParentDirectories;

    private String filePathWithInvalidDirectoryName;
    private File fileWithInvalidDirectoryName;
    
    private String filePathWithValidDirectoryName;
    private File fileWithValidDirectoryName;

    //@@author A0148095X
    @Before
    public void setUp() throws IOException {
        filePathThatExists = "file_that_exists.test";
        fileThatExists = new File(filePathThatExists);
        FileUtil.createFile(fileThatExists);
        
        filePathThatDoesNotExist = "file_that_does_not_exist.test";
        fileThatDoesNotExist = new File(filePathThatDoesNotExist);
        
        filePathToBeDeleted = "file_to_be_deleted.test";
        fileToBeDeleted = new File(filePathToBeDeleted);
        FileUtil.createFile(fileToBeDeleted);      
        
        filePathWithParentDirectories = "data/test/file_with_parent_directories.test";
        fileWithParentDirectories = new File(filePathWithParentDirectories);
        
        filePathWithInvalidDirectoryName = "invalid <!.?> \0 /0 directory";
        fileWithInvalidDirectoryName = new File(filePathWithInvalidDirectoryName);
        
        filePathWithValidDirectoryName = "validdirectory";
        fileWithValidDirectoryName = new File(filePathWithValidDirectoryName);
    }

    @After
    public void cleanup() throws IOException {
        fileThatExists.delete();
        fileThatDoesNotExist.delete();
        fileToBeDeleted.delete();
        fileWithParentDirectories.delete();
        fileWithInvalidDirectoryName.delete();
        fileWithValidDirectoryName.delete();
    }
    
    @Test
    public void isFileExists_validPathFileDoesNotExist_returnsFalse() {
        assertFalse(FileUtil.isFileExists(filePathThatDoesNotExist));
        assertFalse(FileUtil.isFileExists(fileThatDoesNotExist));
    }
    
    @Test
    public void isFileExists_fileExists_returnsTrue() {
        assertTrue(FileUtil.isFileExists(filePathThatExists));
        assertTrue(FileUtil.isFileExists(fileThatExists));
    }
    
    @Test 
    public void createFile_validFilePathWithParentDirectories() throws IOException, FileDeletionException {
        assertTrue(FileUtil.createFile(fileWithParentDirectories));
        // create file again to test when it already exists
        assertFalse(FileUtil.createFile(fileWithParentDirectories));
        FileUtil.deleteFile(filePathWithParentDirectories);
    }
    
    @Test(expected = AssertionError.class)
    public void deleteFile_nullFilePath_throwsAssertionError() throws FileDeletionException {
        // invalid filepath
        FileUtil.deleteFile(null);
    }

    @Test
    public void deleteFile_validPathAndfile_success() throws FileDeletionException {
        FileUtil.deleteFile(filePathToBeDeleted);
        assertFalse(FileUtil.isFileExists(filePathToBeDeleted));
    }
    
    @Test (expected = FileDeletionException.class)
    public void deleteFile_validPathInvalidFile_throwsFileDeletionException() throws FileDeletionException {
        FileUtil.deleteFile(filePathThatDoesNotExist);
    }

    @Test
    public void isPathAvailable_validPathExistingFile_returnsTrue() {
        assertTrue(FileUtil.isPathAvailable(filePathThatExists));
    }

    @Test
    public void isPathAvailable_validPathNonExistingFile_returnsTrue() {
        assertTrue(FileUtil.isPathAvailable(filePathThatDoesNotExist));
    }
    
    @Test
    public void isPathAvailable_invalidPath_returnsFalse() {
        assertFalse(FileUtil.isPathAvailable(filePathWithInvalidDirectoryName));
    }    
    
    
    @Test (expected = IOException.class)
    public void createDirs_invalidDirectoryName_throwsIOException() throws IOException {
        FileUtil.createDirs(fileWithInvalidDirectoryName);
    }
    
    @Test
    public void createDirs_validDirectoryName_success() throws IOException {
        FileUtil.createDirs(fileWithValidDirectoryName);
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
