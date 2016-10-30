package seedu.agendum.commons.util;

import org.junit.Test;
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

    @Test(expected = AssertionError.class)
    public void getDataFromFileNullFileAssertionError() throws Exception {
        XmlUtil.getDataFromFile(null, ToDoList.class);
    }

    @Test(expected = AssertionError.class)
    public void getDataFromFileNullClassAssertionError() throws Exception {
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test(expected = FileNotFoundException.class)
    public void getDataFromFileMissingFileFileNotFoundException() throws Exception {
        XmlUtil.getDataFromFile(MISSING_FILE, ToDoList.class);
    }

    @Test(expected = JAXBException.class)
    public void getDataFromFileEmptyFileDataFormatMismatchException() throws Exception {
        XmlUtil.getDataFromFile(EMPTY_FILE, ToDoList.class);
    }

    @Test
    public void getDataFromFileValidFileValidResult() throws Exception {
        XmlSerializableToDoList dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableToDoList.class);
        assertEquals(9, dataFromFile.getTaskList().size());
    }

    @Test(expected = AssertionError.class)
    public void saveDataToFileNullFileAssertionError() throws Exception {
        XmlUtil.saveDataToFile(null, new ToDoList());
    }

    @Test(expected = AssertionError.class)
    public void saveDataToFileNullClassAssertionError() throws Exception {
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test(expected = FileNotFoundException.class)
    public void saveDataToFileMissingFileFileNotFoundException() throws Exception {
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
