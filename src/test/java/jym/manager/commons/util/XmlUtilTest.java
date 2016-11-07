package jym.manager.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.xml.bind.JAXBException;

import java.io.File;
import java.io.FileNotFoundException;

import jym.manager.commons.util.FileUtil;
import jym.manager.commons.util.XmlUtil;
import jym.manager.model.TaskManager;
import jym.manager.storage.XmlSerializableTaskManager;
import jym.manager.testutil.AddressBookBuilder;
import jym.manager.testutil.TestUtil;
import static org.junit.Assert.assertEquals;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validTaskManager.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempTaskManager.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, TaskManager.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, TaskManager.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, TaskManager.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableTaskManager dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableTaskManager.class);
        assertEquals(9, dataFromFile.getTaskList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new TaskManager());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new TaskManager());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableTaskManager dataToWrite = new XmlSerializableTaskManager(new TaskManager());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
<<<<<<< HEAD:src/test/java/jym/manager/commons/util/XmlUtilTest.java
        XmlSerializableTaskManager dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTaskManager.class);
        assertEquals((new TaskManager(dataToWrite)).toString(),(new TaskManager(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        AddressBookBuilder builder = new AddressBookBuilder(new TaskManager());
        dataToWrite = new XmlSerializableTaskManager(builder.withTask(TestUtil.generateSampleTaskData().get(0)).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTaskManager.class);
        assertEquals((new TaskManager(dataToWrite)).toString(),(new TaskManager(dataFromFile)).toString());
=======
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableAddressBook.class);
        assertEquals((new AddressBook(dataToWrite)).toString(), (new AddressBook(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        AddressBookBuilder builder = new AddressBookBuilder(new AddressBook());
        dataToWrite = new XmlSerializableAddressBook(
                builder.withPerson(TestUtil.generateSamplePersonData().get(0)).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableAddressBook.class);
        assertEquals((new AddressBook(dataToWrite)).toString(), (new AddressBook(dataFromFile)).toString());
>>>>>>> nus-cs2103-AY1617S1/master:src/test/java/seedu/address/commons/util/XmlUtilTest.java
    }
}
