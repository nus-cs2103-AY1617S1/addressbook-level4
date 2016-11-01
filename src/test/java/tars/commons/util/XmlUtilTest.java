package tars.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import tars.commons.util.FileUtil;
import tars.commons.util.XmlUtil;
import tars.model.Tars;
import tars.storage.XmlSerializableTars;
import tars.testutil.TarsBuilder;
import tars.testutil.TestUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil
            .getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(
            TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(
            TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(
            TEST_DATA_FOLDER + "validTars.xml");
    private static final File TEMP_FILE = new File(
            TestUtil.getFilePathInSandboxFolder("tempTars.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, Tars.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException()
            throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, Tars.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException()
            throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, Tars.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableTars dataFromFile = XmlUtil.getDataFromFile(VALID_FILE,
                XmlSerializableTars.class);
        assertEquals(1, dataFromFile.getTaskList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new Tars());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException()
            throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new Tars());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableTars dataToWrite = new XmlSerializableTars(new Tars());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableTars dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE,
                XmlSerializableTars.class);
        assertEquals((new Tars(dataToWrite)).toString(),
                (new Tars(dataFromFile)).toString());
        assertEquals((new Tars(dataToWrite)), (new Tars(dataFromFile)));

        TarsBuilder builder = new TarsBuilder(new Tars());
        dataToWrite = new XmlSerializableTars(
                builder.withTask(TestUtil.generateSampleTaskData().get(0))
                        .withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE,
                XmlSerializableTars.class);
        assertEquals((new Tars(dataToWrite)).toString(),
                (new Tars(dataFromFile)).toString());
    }
}
