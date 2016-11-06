package tars.storage;

import tars.commons.exceptions.DataConversionException;
import tars.commons.util.XmlUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores TARS data in an XML file
 */
public class XmlFileStorage {
    private static String MESSAGE_UNEXPECTED_EXCEPTION =
            "Unexpected exception %s";

    /**
     * Saves the given TARS data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTars tars)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, tars);
        } catch (JAXBException e) {
            assert false : String.format(MESSAGE_UNEXPECTED_EXCEPTION,
                    e.getMessage());
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableTars loadDataFromSaveFile(File file)
            throws DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTars.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
