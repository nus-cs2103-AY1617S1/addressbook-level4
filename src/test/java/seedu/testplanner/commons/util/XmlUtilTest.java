package seedu.testplanner.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.dailyplanner.commons.util.FileUtil;
import seedu.dailyplanner.commons.util.XmlUtil;
import seedu.dailyplanner.model.DailyPlanner;
import seedu.dailyplanner.storage.XmlSerializableDailyPlanner;
import seedu.testplanner.testutil.DailyPlannerBuilder;
import seedu.testplanner.testutil.TestUtil;

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
        XmlUtil.getDataFromFile(null, DailyPlanner.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, DailyPlanner.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, DailyPlanner.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableDailyPlanner dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableDailyPlanner.class);
        assertEquals(9, dataFromFile.getTaskList().size());
        assertEquals(0, dataFromFile.getCategoryList().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new DailyPlanner());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new DailyPlanner());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableDailyPlanner dataToWrite = new XmlSerializableDailyPlanner(new DailyPlanner());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableDailyPlanner dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableDailyPlanner.class);
        assertEquals((new DailyPlanner(dataToWrite)).toString(),(new DailyPlanner(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        DailyPlannerBuilder builder = new DailyPlannerBuilder(new DailyPlanner());
        dataToWrite = new XmlSerializableDailyPlanner(builder.withPerson(TestUtil.generateSamplePersonData().get(0)).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableDailyPlanner.class);
        assertEquals((new DailyPlanner(dataToWrite)).toString(),(new DailyPlanner(dataFromFile)).toString());
    }
}
