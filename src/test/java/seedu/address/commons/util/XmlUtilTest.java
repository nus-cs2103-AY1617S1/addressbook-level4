package seedu.address.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.EmeraldoBuilder;
import seedu.address.testutil.TestUtil;
import seedu.emeraldo.commons.util.FileUtil;
import seedu.emeraldo.commons.util.XmlUtil;
import seedu.emeraldo.model.Emeraldo;
import seedu.emeraldo.storage.XmlSerializableEmeraldo;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validEmeraldo.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempEmeraldo.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, Emeraldo.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, Emeraldo.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, Emeraldo.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableEmeraldo dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableEmeraldo.class);
        assertEquals(9, dataFromFile.getTaskList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new Emeraldo());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new Emeraldo());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableEmeraldo dataToWrite = new XmlSerializableEmeraldo(new Emeraldo());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableEmeraldo dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableEmeraldo.class);
        assertEquals((new Emeraldo(dataToWrite)).toString(),(new Emeraldo(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        EmeraldoBuilder builder = new EmeraldoBuilder(new Emeraldo());
        dataToWrite = new XmlSerializableEmeraldo(builder.withDescription(TestUtil.generateSampleTaskData().get(0)).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableEmeraldo.class);
        assertEquals((new Emeraldo(dataToWrite)).toString(),(new Emeraldo(dataFromFile)).toString());
    }
}
