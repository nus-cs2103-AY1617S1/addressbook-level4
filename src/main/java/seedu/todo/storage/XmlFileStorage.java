package seedu.todo.storage;

import javax.xml.bind.JAXBException;

import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.XmlUtil;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores ToDoList data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given ToDoList data to the specified file.
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
