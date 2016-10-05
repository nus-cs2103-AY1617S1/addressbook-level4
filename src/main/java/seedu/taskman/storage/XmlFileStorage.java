package seedu.taskman.storage;

import seedu.taskman.commons.util.XmlUtil;
import seedu.taskman.commons.exceptions.DataConversionException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores taskMan data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given taskMan data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTaskMan taskMan)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, taskMan);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns task man in the file or an empty task man
     */
    public static XmlSerializableTaskMan loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTaskMan.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
