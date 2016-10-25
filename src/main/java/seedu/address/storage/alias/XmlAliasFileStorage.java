package seedu.address.storage.alias;

import seedu.address.commons.util.XmlUtil;
import seedu.address.commons.exceptions.DataConversionException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores task manager data in an XML file
 */
//@@author A0143107U
public class XmlAliasFileStorage {

    /**
     * Saves the given task manager data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableAlias alias)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, alias);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns task manager in the file or an empty task manager
     */
    public static XmlSerializableAlias loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {        
            return XmlUtil.getDataFromFile(file, XmlSerializableAlias.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
