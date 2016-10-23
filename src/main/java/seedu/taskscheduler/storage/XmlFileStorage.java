package seedu.taskscheduler.storage;

import seedu.taskscheduler.commons.exceptions.DataConversionException;
import seedu.taskscheduler.commons.util.XmlUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores Task Scheduler data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given task scheduler data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTaskScheduler taskScheduler)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, taskScheduler);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns task scheduler in the file or an empty task scheduler
     */
    public static XmlSerializableTaskScheduler loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTaskScheduler.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
