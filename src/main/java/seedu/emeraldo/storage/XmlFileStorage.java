package seedu.emeraldo.storage;

import seedu.emeraldo.commons.exceptions.DataConversionException;
import seedu.emeraldo.commons.util.XmlUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores emeraldo data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given emeraldo data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableEmeraldo emeraldo)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, emeraldo);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns emeraldo in the file or an empty emeraldo
     */
    public static XmlSerializableEmeraldo loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableEmeraldo.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
