package seedu.taskman.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.taskman.model.TaskMan;
import seedu.taskman.storage.XmlSerializableTaskMan;
import seedu.taskman.testutil.TaskManBuilder;
import seedu.taskman.testutil.TestUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validTaskMan.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempTaskMan.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, TaskMan.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, TaskMan.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, TaskMan.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableTaskMan dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableTaskMan.class);
        assertEquals(9, dataFromFile.getActivityList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new TaskMan());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new TaskMan());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableTaskMan dataToWrite = new XmlSerializableTaskMan(new TaskMan());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableTaskMan dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTaskMan.class);
        assertEquals((new TaskMan(dataToWrite)).toString(),(new TaskMan(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        TaskManBuilder builder = new TaskManBuilder(new TaskMan());
        dataToWrite = new XmlSerializableTaskMan(builder.withTask(TestUtil.generateSampleTaskData().get(0)).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTaskMan.class);
        assertEquals((new TaskMan(dataToWrite)).toString(),(new TaskMan(dataFromFile)).toString());
    }
}
