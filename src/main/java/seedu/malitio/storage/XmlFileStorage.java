package seedu.malitio.storage;

import seedu.malitio.commons.exceptions.DataConversionException;
import seedu.malitio.commons.util.XmlUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores Malitio data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given Malitio data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableMalitio malitio)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, malitio);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns malitio in the file or an empty malitio
     */
    public static XmlSerializableMalitio loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableMalitio.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
