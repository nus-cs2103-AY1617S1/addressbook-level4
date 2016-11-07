package seedu.lifekeeper.storage;

import seedu.lifekeeper.commons.exceptions.DataConversionException;
import seedu.lifekeeper.commons.util.XmlUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores lifekeeper data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given lifekeeper data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableLifekeeper lifeKeeper)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, lifeKeeper);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableLifekeeper loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableLifekeeper.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
