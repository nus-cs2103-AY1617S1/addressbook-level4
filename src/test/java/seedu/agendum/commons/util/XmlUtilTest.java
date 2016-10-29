package seedu.agendum.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.agendum.model.ToDoList;
import seedu.agendum.storage.XmlSerializableToDoList;
import seedu.agendum.testutil.ToDoListBuilder;
import seedu.agendum.testutil.TestUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validToDoList.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempToDoList.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFileNullFileAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, ToDoList.class);
    }

    @Test
    public void getDataFromFileNullClassAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFileMissingFileFileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, ToDoList.class);
    }

    @Test
    public void getDataFromFileEmptyFileDataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, ToDoList.class);
    }

    @Test
    public void getDataFromFileValidFileValidResult() throws Exception {
        XmlSerializableToDoList dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableToDoList.class);
        assertEquals(9, dataFromFile.getTaskList().size());
    }

    @Test
    public void saveDataToFileNullFileAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new ToDoList());
    }

    @Test
    public void saveDataToFileNullClassAssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFileMissingFileFileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new ToDoList());
    }

    @Test
    public void saveDataToFileValidFileDataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableToDoList dataToWrite = new XmlSerializableToDoList(new ToDoList());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableToDoList dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableToDoList.class);
        assertEquals((new ToDoList(dataToWrite)).toString(),(new ToDoList(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        ToDoListBuilder builder = new ToDoListBuilder(new ToDoList());
        dataToWrite = new XmlSerializableToDoList(builder.withTask(TestUtil.generateSampleTaskData().get(0)).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableToDoList.class);
        assertEquals((new ToDoList(dataToWrite)).toString(),(new ToDoList(dataFromFile)).toString());
    }
}
