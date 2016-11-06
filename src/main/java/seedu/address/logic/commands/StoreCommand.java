
package seedu.address.logic.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.util.ConfigUtil;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.storage.ChangeStorageFilePathEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.storage.XmlTaskManagerStorage;

//@@author A0139498J
/**
* Changes storage file location of task manager.
*/
public class StoreCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(StoreCommand.class);
    
    public static final String XML_FILE_EXTENSION = ".xml";
    public static final String COMMAND_WORD = "store";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the storage location. "
            + "Parameters: store [FILEPATH]\n" 
            + "Example: " + COMMAND_WORD 
            + "data/dearjim_backup";      
    public static final String TOOL_TIP = "store [FILEPATH]";
    public static final String MESSAGE_SUCCESS = "Storage location changed to %1$s.";
    public static final String MESSAGE_FAILURE = "Unable to write to the storage location specified. "
            + "Please choose another storage location.";
   
    public String storageFilePath;

    public StoreCommand(String storageFilePath) {
        this.storageFilePath = storageFilePath + XML_FILE_EXTENSION;
    }
    
    @Override
    public CommandResult execute() {        
        assert model != null;
        try {
            handleTaskManagerData();
            updateStorageFilePathOfConfigFile();
            return new CommandResult(String.format(MESSAGE_SUCCESS, storageFilePath));
        } catch (FileNotFoundException fnfe) {
            assert false : "File cannot be missing.";
            return new CommandResult(MESSAGE_FAILURE);
        } catch (IOException ioe) {
            return new CommandResult(MESSAGE_FAILURE);
        } catch (DataConversionException dce) {
            assert false : "Data file cannot be in the wrong format.";
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    /**
     * Handles task manager data.
     * If target storage file is present, imports data from this file into task manager.
     * Else creates a new xml file with existing task manager data.
     * 
     * @throws DataConversionException If target file is not in the correct format.
     * @throws FileNotFoundException   If target file does not exist.
     * @throws IOException             If there was an error writing to the new file.
     */
    private void handleTaskManagerData() 
            throws DataConversionException, FileNotFoundException, IOException {
        assert storageFilePath != null;
        XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(storageFilePath);
        File storageFile = new File(storageFilePath);
        if (storageFile.exists()) {
            importDataFromExistingStorageFile(xmlTaskManagerStorage);
        } else {
            createNewFileWithExistingData(xmlTaskManagerStorage);
        }
    }

    /**
     * Updates the storage file path of config file with the new storage file path
     * provided by user as part of the store command.
     * 
     * @throws IOException  If there was an error writing to the config file.
     */
    private void updateStorageFilePathOfConfigFile() throws IOException {
        assert storageFilePath != null;
        logger.info("Updating storage file path in config file to " + storageFilePath);
        Config config = new Config();
        config.setTaskManagerFilePath(storageFilePath);
        ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
    }

    /**
     * Creates a new file, and copies all existing data in task manager over into it.
     * 
     * @throws IOException  If there was an error writing to the new file.
     */
    private void createNewFileWithExistingData(XmlTaskManagerStorage xmlTaskManagerStorage) throws IOException {
        assert xmlTaskManagerStorage != null;
        assert storageFilePath != null;
        logger.info("Storage file does not exist, exporting data to " + storageFilePath);
        xmlTaskManagerStorage.saveTaskManager(model.getTaskManager(), storageFilePath);
        EventsCenter.getInstance().post(new ChangeStorageFilePathEvent(storageFilePath));
    }

    /**
     * Imports data from the existing storage file.
     * Updates the model component of task manager with the imported data.
     * 
     * @throws DataConversionException If target file is not in the correct format.
     * @throws FileNotFoundException   If target file does not exist.
     */
    private void importDataFromExistingStorageFile(XmlTaskManagerStorage xmlTaskManagerStorage)
            throws DataConversionException, FileNotFoundException {
        assert xmlTaskManagerStorage != null;
        assert storageFilePath != null;
        Optional <ReadOnlyTaskManager> readTaskManager = xmlTaskManagerStorage.readTaskManager(storageFilePath);
        assert readTaskManager.isPresent();
        logger.info("Storage file exists, importing data from " + storageFilePath);
        ReadOnlyTaskManager readOnlyTaskManagerWithNewData = readTaskManager.get();
        EventsCenter.getInstance().post(new ChangeStorageFilePathEvent(storageFilePath));
        model.resetData(readOnlyTaskManagerWithNewData);
    }

}