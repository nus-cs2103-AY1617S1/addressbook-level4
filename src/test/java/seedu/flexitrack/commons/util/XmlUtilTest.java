package seedu.flexitrack.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.flexitrack.model.FlexiTrack;
import seedu.flexitrack.storage.XmlSerializableFlexiTrack;
import seedu.flexitrack.testutil.FlexiTrackBuilder;
import seedu.flexitrack.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validFlexiTrack.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempFlexiTrack.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, FlexiTrack.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, FlexiTrack.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, FlexiTrack.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableFlexiTrack dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableFlexiTrack.class);
        assertEquals(9, dataFromFile.getTaskList().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new FlexiTrack());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new FlexiTrack());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableFlexiTrack dataToWrite = new XmlSerializableFlexiTrack(new FlexiTrack());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableFlexiTrack dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableFlexiTrack.class);
        assertEquals((new FlexiTrack(dataToWrite)).toString(), (new FlexiTrack(dataFromFile)).toString());
        // TODO: use equality instead of string comparisons

        FlexiTrackBuilder builder = new FlexiTrackBuilder(new FlexiTrack());
        dataToWrite = new XmlSerializableFlexiTrack(
                builder.withPerson(TestUtil.generateSampleTaskData().get(0)).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableFlexiTrack.class);
        assertEquals((new FlexiTrack(dataToWrite)).toString(), (new FlexiTrack(dataFromFile)).toString());
    }
}
