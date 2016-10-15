package seedu.cmdo.storage;

import seedu.cmdo.commons.util.XmlUtil;
import seedu.cmdo.commons.exceptions.DataConversionException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores todo list data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given todo list data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableToDoList toDoList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, toDoList);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns todo list in the file or an empty address book
     */
    public static XmlSerializableToDoList loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableToDoList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
