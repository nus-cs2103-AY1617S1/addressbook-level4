package seedu.todolist.storage;

import seedu.todolist.commons.exceptions.DataConversionException;
import seedu.todolist.commons.util.XmlUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores todolist data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given todolist data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableToDoList ToDoList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, ToDoList);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns address book in the file or an empty address book
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
