package seedu.tasklist.storage;

import javax.xml.bind.JAXBException;

import seedu.tasklist.commons.exceptions.DataConversionException;
import seedu.tasklist.commons.util.XmlUtil;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores tasklist data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given tasklist data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTaskList taskList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, taskList);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns task list in the file or an empty task list
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
