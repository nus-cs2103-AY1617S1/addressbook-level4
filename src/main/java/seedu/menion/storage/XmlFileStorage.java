package seedu.menion.storage;

import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.commons.util.XmlUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores activity manager data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given activity manager data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableActivityManager activityManager)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, activityManager);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns activity manager in the file or an empty activity manager
     */
    public static XmlSerializableActivityManager loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableActivityManager.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
