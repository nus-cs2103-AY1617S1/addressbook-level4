package seedu.todo.commons.util;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import seedu.todo.testutil.TestUtil;

import java.io.File;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validAddressBook.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempAddressBook.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();
/*
    @Test
    public void getDataFromFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, ToDoList.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, ToDoList.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, ToDoList.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableToDoList dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableToDoList.class);
        assertEquals(9, dataFromFile.getTaskList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new ToDoList());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new ToDoList());
    }
/*
    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableToDoList dataToWrite = new XmlSerializableToDoList(new ToDoList());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableToDoList dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableToDoList.class);
        assertEquals((new ToDoList(dataToWrite)).toString(),(new ToDoList(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        ToDoListBuilder builder = new ToDoListBuilder(new ToDoList());
        dataToWrite = new XmlSerializableToDoList(builder.withTask(TestUtil.generateSamplePersonData().get(0)).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableToDoList.class);
        assertEquals((new ToDoList(dataToWrite)).toString(),(new ToDoList(dataFromFile)).toString());
    } */
    
}
