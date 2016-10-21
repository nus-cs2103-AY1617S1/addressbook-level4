package seedu.flexitrack.storage;

import seedu.flexitrack.commons.util.XmlUtil;
import seedu.flexitrack.commons.exceptions.DataConversionException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores FlexiTrack data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given FlexiTrack data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableFlexiTrack flexiTrack) throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, flexiTrack);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns FlexiTrack in the file or an empty FlexiTrack
     */
    public static XmlSerializableFlexiTrack loadDataFromSaveFile(File file)
            throws DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableFlexiTrack.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
