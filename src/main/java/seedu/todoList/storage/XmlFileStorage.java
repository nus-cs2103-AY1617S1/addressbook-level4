package seedu.todoList.storage;

import seedu.todoList.commons.exceptions.DataConversionException;
import seedu.todoList.commons.util.XmlUtil;

import javax.xml.bind.JAXBException;

import java.io.File;
import java.io.FileNotFoundException;

//@@author A0144061U-reused
/**
 * Stores TaskList data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given TodoList  data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTaskList TaskList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, TaskList);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns TodoList in the file or an empty TodoList
     */
    public static XmlSerializableTaskList loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
        	if(file.getPath().contains("TodoList.xml")) {
        		return XmlUtil.getDataFromFile(file, XmlSerializableTodoList.class);
        	}
        	else if(file.getPath().contains("EventList.xml")) {
        		return XmlUtil.getDataFromFile(file, XmlSerializableEventList.class);
        	}
        	else if(file.getPath().contains("DeadlineList.xml")) {
        		return XmlUtil.getDataFromFile(file, XmlSerializableDeadlineList.class);
        	}
        	else {
        		return XmlUtil.getDataFromFile(file, XmlSerializableTaskList.class);
        	}
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
