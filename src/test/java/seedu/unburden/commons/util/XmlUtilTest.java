package seedu.unburden.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.unburden.commons.util.FileUtil;
import seedu.unburden.commons.util.XmlUtil;
import seedu.unburden.model.ListOfTask;
import seedu.unburden.storage.XmlSerializableTaskList;
import seedu.unburden.testutil.TaskListBuilder;
import seedu.unburden.testutil.TestUtil;

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
        XmlUtil.getDataFromFile(null, ListOfTask.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, ListOfTask.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, ListOfTask.class);
    }
/*
    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableTaskList dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableTaskList.class);
        assertEquals(9, dataFromFile.getTaskList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }
*/
    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new ListOfTask());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new ListOfTask());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableTaskList dataToWrite = new XmlSerializableTaskList(new ListOfTask());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableTaskList dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTaskList.class);
        assertEquals((new ListOfTask(dataToWrite)).toString(),(new ListOfTask(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        TaskListBuilder builder = new TaskListBuilder(new ListOfTask());
        dataToWrite = new XmlSerializableTaskList(builder.withTask(TestUtil.generateSampleTaskData().get(0)).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTaskList.class);
        assertEquals((new ListOfTask(dataToWrite)).toString(),(new ListOfTask(dataFromFile)).toString());
    }
}
