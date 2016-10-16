package seedu.todo.storage;

import javax.xml.bind.JAXBException;

import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.XmlUtil;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores addressbook data in an XML file
 */
public class XmlFileStorage {
    
    /**
     * Saves the given todo list data to the specified file.
     */
    public static void saveTodoListToFile(File file, XmlSerializableTodoList todoList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, todoList);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns todo list in the file or an empty todo list
     */
    public static XmlSerializableTodoList loadTodoListFromFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTodoList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
}
