package seedu.lifekeeper.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.lifekeeper.commons.util.FileUtil;
import seedu.lifekeeper.commons.util.XmlUtil;
import seedu.lifekeeper.model.LifeKeeper;
import seedu.lifekeeper.storage.XmlSerializableLifekeeper;
import seedu.lifekeeper.testutil.AddressBookBuilder;
import seedu.lifekeeper.testutil.TestUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Class to test whether XML saving works.
 * 
 */

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
        XmlUtil.getDataFromFile(null, LifeKeeper.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, LifeKeeper.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, LifeKeeper.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableLifekeeper dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableLifekeeper.class);
        assertEquals(9, dataFromFile.getActivityList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new LifeKeeper());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new LifeKeeper());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableLifekeeper dataToWrite = new XmlSerializableLifekeeper(new LifeKeeper());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableLifekeeper dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableLifekeeper.class);
        assertEquals((new LifeKeeper(dataToWrite)).toString(),(new LifeKeeper(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        AddressBookBuilder builder = new AddressBookBuilder(new LifeKeeper());
        dataToWrite = new XmlSerializableLifekeeper(builder.withActivity(TestUtil.generateSampleActivityData().get(0)).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableLifekeeper.class);
        assertEquals((new LifeKeeper(dataToWrite)).toString(),(new LifeKeeper(dataFromFile)).toString());
    }
}
