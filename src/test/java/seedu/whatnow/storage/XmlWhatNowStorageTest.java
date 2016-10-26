package seedu.whatnow.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.whatnow.testutil.TypicalTestTasks;
import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.commons.util.FileUtil;
import seedu.whatnow.model.ReadOnlyWhatNow;
import seedu.whatnow.model.WhatNow;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.storage.XmlWhatNowStorage;

import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlWhatNowStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlWhatNowStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readWhatNow_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readWhatNow(null);
    }

    private java.util.Optional<ReadOnlyWhatNow> readWhatNow(String filePath) throws Exception {
        return new XmlWhatNowStorage(filePath).readWhatNow(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readWhatNow("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readWhatNow("NotXmlFormatWhatNow.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

//    @Test
//    public void readAndSaveWhatNow_allInOrder_success() throws Exception {
//        String filePath = testFolder.getRoot().getPath() + "TempWhatNow.xml";
//        TypicalTestTasks td = new TypicalTestTasks();
//        WhatNow original = td.getTypicalWhatNow();
//        XmlWhatNowStorage xmlWhatNowStorage = new XmlWhatNowStorage(filePath);
//
//        //Save in new file and read back
//        xmlWhatNowStorage.saveWhatNow(original, filePath);
//        ReadOnlyWhatNow readBack = xmlWhatNowStorage.readWhatNow(filePath).get();
//        assertEquals(original, new WhatNow(readBack));
//
//        //Modify data, overwrite exiting file, and read back
//        original.addTask(new Task(TypicalTestTasks.h));
//        original.removeTask(new Task(TypicalTestTasks.a));
//        xmlWhatNowStorage.saveWhatNow(original, filePath);
//        readBack = xmlWhatNowStorage.readWhatNow(filePath).get();
//        assertEquals(original, new WhatNow(readBack));
//
//        //Save and read without specifying file path
//        original.addTask(new Task(TypicalTestTasks.i));
//        xmlWhatNowStorage.saveWhatNow(original); //file path not specified
//        readBack = xmlWhatNowStorage.readWhatNow().get(); //file path not specified
//        assertEquals(original, new WhatNow(readBack));
//
//    }

    @Test
    public void saveWhatNow_nullWhatNow_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveWhatNow(null, "SomeFile.xml");
    }

    private void saveWhatNow(ReadOnlyWhatNow whatNow, String filePath) throws IOException {
        new XmlWhatNowStorage(filePath).saveWhatNow(whatNow, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveWhatNow_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveWhatNow(new WhatNow(), null);
    }


}
