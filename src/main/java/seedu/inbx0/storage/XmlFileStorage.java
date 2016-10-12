package seedu.inbx0.storage;

import javax.xml.bind.JAXBException;

import seedu.inbx0.commons.exceptions.DataConversionException;
import seedu.inbx0.commons.util.XmlUtil;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores tasklist data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given tasklist data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTaskList tasklist)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, tasklist);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns tasklist in the file or an empty tasklist
     */
    public static XmlSerializableTaskList loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTaskList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
