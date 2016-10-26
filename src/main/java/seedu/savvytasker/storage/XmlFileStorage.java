package seedu.savvytasker.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.savvytasker.commons.exceptions.DataConversionException;
import seedu.savvytasker.commons.util.XmlUtil;

/**
 * Stores savvytasker data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given savvytasker data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableSavvyTasker savvyTasker)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, savvyTasker);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableSavvyTasker loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableSavvyTasker.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
