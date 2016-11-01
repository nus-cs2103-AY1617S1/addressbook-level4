
package seedu.address.logic.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.util.ConfigUtil;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.ChangeStorageFilePathEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.storage.XmlTaskManagerStorage;

//@@author A0139498J
/**
* Changes storage file location of task manager.
*/
public class StoreCommand extends Command {

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
        this.storageFilePath = storageFilePath;
    }
    
    @Override
    public CommandResult execute() {        
        assert model != null;
        storageFilePath = storageFilePath + XML_FILE_EXTENSION;
        XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(storageFilePath);
        File storageFile = new File(storageFilePath);

        try {
            if (storageFile.exists()) {
                importDataFromExistingStorageFile(xmlTaskManagerStorage);
            } else {
                createNewFileWithExistingData(xmlTaskManagerStorage);
            }
            updateStorageFilePathOfConfigFile();
            return new CommandResult(String.format(MESSAGE_SUCCESS, storageFilePath));
        } catch (IOException ioe) {
            return new CommandResult(MESSAGE_FAILURE);
        } catch (DataConversionException dce) {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    /**
     * Updates the storage file path of config file with the new storage file path
     * provided by user as part of the store command
     * 
     * @throws IOException if there was an error writing to the config file
     */
    private void updateStorageFilePathOfConfigFile() throws IOException {
        Config config = new Config();
        config.setTaskManagerFilePath(storageFilePath);
        ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
    }

    /**
     * Creates a new file, and copies all existing data in task manager over into it
     * 
     * @throws IOException if there was an error writing to the new file
     */
    private void createNewFileWithExistingData(XmlTaskManagerStorage xmlTaskManagerStorage) throws IOException {
        xmlTaskManagerStorage.saveTaskManager(model.getTaskManager(), storageFilePath);
        EventsCenter.getInstance().post(new ChangeStorageFilePathEvent(storageFilePath));
    }

    /**
     * Imports data from the existing storage file
     * Updates the model component of task manager with the imported data
     * 
     * @throws DataConversionException if target file is not in the correct format
     * @throws FileNotFoundException if target file does not exist
     */
    private void importDataFromExistingStorageFile(XmlTaskManagerStorage xmlTaskManagerStorage)
            throws DataConversionException, FileNotFoundException {
        Optional <ReadOnlyTaskManager> readTaskManager = xmlTaskManagerStorage.readTaskManager(storageFilePath);
        assert readTaskManager.isPresent();
        ReadOnlyTaskManager readOnlyTaskManagerWithNewData = readTaskManager.get();
        EventsCenter.getInstance().post(new ChangeStorageFilePathEvent(storageFilePath));
        model.resetData(readOnlyTaskManagerWithNewData);
        model.resetDoneData(readOnlyTaskManagerWithNewData);
    }

}