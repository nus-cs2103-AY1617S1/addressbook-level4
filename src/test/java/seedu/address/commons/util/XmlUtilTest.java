package seedu.address.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class XmlUtilTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_AssertionError() throws FileNotFoundException, JAXBException {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, XmlSerializable.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws IOException, JAXBException {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(folder.newFile(), null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws FileNotFoundException, JAXBException {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(new File("file/missing/for/sure"), XmlSerializable.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws IOException, JAXBException {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(folder.newFile(), XmlSerializable.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws IOException, JAXBException {
       JsonSerializable test = new JsonSerializable();
        test.setTestValues();

        // Write to temporary file
        File file = folder.newFile();
        FileUtil.writeToFile(file, XmlSerializable.XML_STRING_REPRESENTATION);

        XmlSerializable actual = XmlUtil.getDataFromFile(file, XmlSerializable.class);

        assertEquals(new XmlSerializable(), actual);
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws FileNotFoundException, JAXBException {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new XmlSerializable());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws IOException, JAXBException {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(folder.newFile(), null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws FileNotFoundException, JAXBException {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(new File("file/missing/for/sure"), new XmlSerializable());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws IOException, JAXBException {
        File file = folder.newFile();
        XmlSerializable dataToWrite = new XmlSerializable();
        XmlUtil.saveDataToFile(file, dataToWrite);

        assertEquals(XmlSerializable.XML_STRING_REPRESENTATION, FileUtil.readFromFile(file));
    }
}
