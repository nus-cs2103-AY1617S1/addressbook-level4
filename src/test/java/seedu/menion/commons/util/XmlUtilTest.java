package seedu.menion.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.menion.commons.util.FileUtil;
import seedu.menion.commons.util.XmlUtil;
import seedu.menion.model.ActivityManager;
import seedu.menion.storage.XmlSerializableActivityManager;
import seedu.menion.testutil.MenionBuilder;
import seedu.menion.testutil.TestUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validAddressBook.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempAddressBook.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, ActivityManager.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, ActivityManager.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, ActivityManager.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
  //      XmlSerializableActivityManager dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableActivityManager.class);
//        assertEquals(9, dataFromFile.getTaskList().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new ActivityManager());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new ActivityManager());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableActivityManager dataToWrite = new XmlSerializableActivityManager(new ActivityManager());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableActivityManager dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableActivityManager.class);
        assertEquals((new ActivityManager(dataToWrite)).toString(),(new ActivityManager(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        MenionBuilder builder = new MenionBuilder(new ActivityManager());
        dataToWrite = new XmlSerializableActivityManager(builder.withActivity(TestUtil.generateSamplePersonData().get(0)).build());


        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableActivityManager.class);
        assertEquals((new ActivityManager(dataToWrite)).toString(),(new ActivityManager(dataFromFile)).toString());
    }
}
